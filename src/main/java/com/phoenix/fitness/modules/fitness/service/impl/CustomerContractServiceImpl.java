package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.constant.*;
import com.phoenix.fitness.common.utils.*;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.*;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.*;
import com.phoenix.fitness.modules.admin.dto.TotalBillDto;
import com.phoenix.fitness.modules.admin.form.BillSearchForm;
import com.phoenix.fitness.modules.admin.form.ContractSearchForm;
import com.phoenix.fitness.modules.admin.form.FinishTimesForm;
import com.phoenix.fitness.modules.admin.form.RefundContractForm;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.dto.CustomerContractWithCustomerDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerContractWithLogDto;
import com.phoenix.fitness.modules.fitness.entity.*;
import com.phoenix.fitness.modules.fitness.service.CustomerContractService;
import com.phoenix.fitness.modules.fitness.service.CustomerService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Service("customerContractService")
@RequiredArgsConstructor
public class CustomerContractServiceImpl extends ServiceImpl<CustomerContractDao, CustomerContractEntity> implements CustomerContractService {

    private final CustomerContractDao customerContractDao;

    private final CustomerSureClassLogDao customerSureClassLogDao;

    private final CustomerService customerService;

    private final VipCardDao vipCardDao;

    private final CustomerDao customerDao;

    private final TrainingPlanDao trainingPlanDao;

    private final CoachDao coachDao;

    @Value("${fitness.coach.oneClassTime}")
    private Integer coachOneClassTime;

    @Override
    public PageUtils queryPage(ContractSearchForm contractSearchForm) {
        //分页对象
        Page<CustomerContractWithCustomerDto> pageParams = new Page<>(contractSearchForm.getPage(), contractSearchForm.getLimit());
        IPage<CustomerContractWithCustomerDto> page = customerContractDao.selectContractListWithCustomer(pageParams, contractSearchForm);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryBillPage(BillSearchForm billSearchForm) throws ParseException {
        billSearchForm.setEndDate(DateUtils.getEndOfOneDay(billSearchForm.getEndDate()));

        //分页对象
        Page<CustomerContractWithLogDto> pageParams = new Page<>(billSearchForm.getPage(), billSearchForm.getLimit());
        IPage<CustomerContractWithLogDto> page = customerContractDao.selectContractListWithLog(pageParams, billSearchForm);

        List<CustomerContractWithLogDto> records = page.getRecords();
        this.computeMoney(records, billSearchForm);
        return new PageUtils(page);
    }

    public void computeMoney(List<CustomerContractWithLogDto> contractList, BillSearchForm billSearchForm) {
        for (CustomerContractWithLogDto customerContractWithLogDto : contractList) {
            //总的使用权益
            customerContractWithLogDto.setTotalUsedCount(Integer.parseInt(customerContractWithLogDto.getTotalRights()) - Integer.parseInt(customerContractWithLogDto.getLeftRights()) + "");
            BigDecimal usedMoney = new BigDecimal(customerContractWithLogDto.getPayTotalMoney()).subtract(new BigDecimal(customerContractWithLogDto.getLeftMoney()));
            //总的使用金额
            customerContractWithLogDto.setTotalUsedMoney(usedMoney.toString());

            //退款的合同，则结束日期为退费日期
            if (customerContractWithLogDto.getContractStatus().equals(ContractStatusEnum.REFUNDED.getContractStatusName())) {
                // 已退费
                customerContractWithLogDto.setEndTime(customerContractWithLogDto.getRefundDate());
            }

            if ((customerContractWithLogDto.getPayTime().after(billSearchForm.getStartDate()) &&
                    customerContractWithLogDto.getPayTime().before(billSearchForm.getEndDate()))
                    || (customerContractWithLogDto.getPayTime().equals(billSearchForm.getStartDate()))
            ) {
                //合同的开始日期在 时间段之间，则说明，该金额流水属于当前时间段内
                customerContractWithLogDto.setInAccountMoney(customerContractWithLogDto.getPayTotalMoney());
                //流水
                BigDecimal inAccountMoney = new BigDecimal(customerContractWithLogDto.getInAccountMoney());
                //券后流水
                BigDecimal afterCouponInAccountMoney = new BigDecimal(customerContractWithLogDto.getInAccountMoney());
                //税后流水
                BigDecimal afterTax = inAccountMoney.multiply(new BigDecimal("1").subtract(BigDecimal.valueOf(TaxUtil.getTax(customerContractWithLogDto.getPayType()))));
                //抵用券不为0，扣除抵用券的费用
                if (customerContractWithLogDto.getCouponMoney() != null && !customerContractWithLogDto.getCouponMoney().equals(new Double("0"))) {
                    afterTax = afterTax.subtract(BigDecimal.valueOf(customerContractWithLogDto.getCouponMoney()));
                    afterCouponInAccountMoney = afterCouponInAccountMoney.subtract(BigDecimal.valueOf(customerContractWithLogDto.getCouponMoney()));
                }
                customerContractWithLogDto.setAfterCouponInAccountMoney(afterCouponInAccountMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                if (StringUtils.isEmpty(customerContractWithLogDto.getAfterTaxPayMoney())) {
                    customerContractWithLogDto.setAfterTaxInAccountMoney(afterTax.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                } else {
                    customerContractWithLogDto.setAfterTaxInAccountMoney(customerContractWithLogDto.getAfterTaxPayMoney());
                }
            } else {
                customerContractWithLogDto.setInAccountMoney("0");
                customerContractWithLogDto.setAfterTaxInAccountMoney("0");
                customerContractWithLogDto.setAfterCouponInAccountMoney("0");
            }

            //计算在指定时间段内的实际收入
            if (customerContractWithLogDto.getContractType().equals(ContractTypeEnum.TRANSFER_CARD.getContractTypeName())
                    || customerContractWithLogDto.getContractType().equals(ContractTypeEnum.OTHER_SALES.getContractTypeName())
                    || customerContractWithLogDto.getContractType().equals(ContractTypeEnum.GROUP_PURCHASE_PRICE.getContractTypeName())
                    || customerContractWithLogDto.getContractType().equals(ContractTypeEnum.REFUND.getContractTypeName())
            ) {
                //转卡费用 或者 其他类销售
                if ((customerContractWithLogDto.getActiveTime().after(billSearchForm.getStartDate()) &&
                        customerContractWithLogDto.getActiveTime().before(billSearchForm.getEndDate()))
                        || (customerContractWithLogDto.getActiveTime().equals(billSearchForm.getStartDate()))
                ) {
                    customerContractWithLogDto.setRealUsedCount("1");
                    customerContractWithLogDto.setRealIncome(customerContractWithLogDto.getPayTotalMoney());
                    customerContractWithLogDto.setAfterTaxRealIncome(customerContractWithLogDto.getAfterTaxPayMoney());
                } else {
                    customerContractWithLogDto.setRealUsedCount("0");
                    customerContractWithLogDto.setRealIncome("0");
                    customerContractWithLogDto.setAfterTaxRealIncome("0");
                }
            } else {
                //非转卡费：会员卡费用和私教费用
                if (VipCardBigTypeEnum.COUNT_CARD.getCardTypeName().equals(customerContractWithLogDto.getVipCardBigType())
                        || TrainingTypeEnum.EXPERIENCE_TRAINING.getTrainingTypeName().equals(customerContractWithLogDto.getTrainingType())
                        || TrainingTypeEnum.CLASS_TRAINING.getTrainingTypeName().equals(customerContractWithLogDto.getTrainingType())
                ) {
                    //次课和上课卡
                    //计算实际销卡次数
                    List<CustomerSureClassLog> customerSureClassLogList = customerSureClassLogDao.selectList(new QueryWrapper<CustomerSureClassLog>().eq("contract_id", customerContractWithLogDto.getCustomerContractId()).ge("sure_class_time", billSearchForm.getStartDate()).le("sure_class_time", billSearchForm.getEndDate()));
                    if (!CollectionUtils.isEmpty(customerSureClassLogList)) {
                        customerContractWithLogDto.setRealUsedCount(customerSureClassLogList.size() + "");
                        Double realIncome = customerSureClassLogList.size() / Double.parseDouble(customerContractWithLogDto.getTotalRights()) * Double.parseDouble(customerContractWithLogDto.getPayTotalMoney());
                        Double afterTaxRealIncome = customerSureClassLogList.size() / Double.parseDouble(customerContractWithLogDto.getTotalRights()) * Double.parseDouble(customerContractWithLogDto.getAfterTaxPayMoney());
                        customerContractWithLogDto.setRealIncome(String.format("%.2f", realIncome));
                        customerContractWithLogDto.setAfterTaxRealIncome(String.format("%.2f", afterTaxRealIncome));
                    } else {
                        customerContractWithLogDto.setRealUsedCount("0");
                        customerContractWithLogDto.setRealIncome("0");
                        customerContractWithLogDto.setAfterTaxRealIncome("0");
                    }
                } else {
                    //阶段会员卡、包月的私教课

                    //判断是否预先收费
                    //预先收费，实际收入为0
                    if (customerContractWithLogDto.getContractStatus().equals(ContractStatusEnum.PREPAYMENT.getContractStatusName())) {
                        //预先收费
                        customerContractWithLogDto.setRealUsedCount("0");
                        customerContractWithLogDto.setRealIncome("0");
                        customerContractWithLogDto.setAfterTaxRealIncome("0");
                    } else {
                        //非预先收费，正常模式
                        //计算实际消耗的天数
                        Long usedDays;
                        if (customerContractWithLogDto.getActiveTime().before(billSearchForm.getStartDate())) {
                            if (customerContractWithLogDto.getEndTime().before(billSearchForm.getStartDate())) {
                                //结束了
                                usedDays = 0L;
                            } else {
                                if (customerContractWithLogDto.getEndTime().before(billSearchForm.getEndDate())) {
                                    //时间段为搜索段开始日期-合同段截止日期
                                    usedDays = DateUtils.daysBetween(billSearchForm.getStartDate(), customerContractWithLogDto.getEndTime());
                                } else {
                                    usedDays = DateUtils.daysBetween(billSearchForm.getStartDate(), billSearchForm.getEndDate());
                                }
                            }
                        } else {
                            if (customerContractWithLogDto.getActiveTime().before(billSearchForm.getEndDate())) {
                                if (customerContractWithLogDto.getEndTime().before(billSearchForm.getEndDate())) {
                                    //时间段为合同开始日期-合同结束日期
                                    usedDays = DateUtils.daysBetween(customerContractWithLogDto.getActiveTime(), customerContractWithLogDto.getEndTime());
                                } else {
                                    //时间段为合同开始日期-搜索结束日期
                                    usedDays = DateUtils.daysBetween(customerContractWithLogDto.getActiveTime(), billSearchForm.getEndDate());
                                }
                            } else {
                                //合同未开始
                                usedDays = 0L;
                            }
                        }
                        customerContractWithLogDto.setRealUsedCount(usedDays + "");
                        Double realUsedMoney = usedDays / Double.parseDouble(customerContractWithLogDto.getTotalRights()) * Double.parseDouble(customerContractWithLogDto.getPayTotalMoney());
                        customerContractWithLogDto.setRealIncome(String.format("%.2f", realUsedMoney));

                        Double afterTaxRealIncome = usedDays / Double.parseDouble(customerContractWithLogDto.getTotalRights()) * Double.parseDouble(customerContractWithLogDto.getAfterTaxPayMoney());
                        customerContractWithLogDto.setAfterTaxRealIncome(String.format("%.2f", afterTaxRealIncome));
                    }
                }
            }
        }
    }

    @Override
    public TotalBillDto getTotal(BillSearchForm billSearchForm) throws ParseException {
        billSearchForm.setEndDate(DateUtils.getEndOfOneDay(billSearchForm.getEndDate()));

        List<CustomerContractWithLogDto> list = customerContractDao.selectContractListWithLogNoPage(billSearchForm);
        BigDecimal totalInAccountMoney = new BigDecimal("0");
        BigDecimal totalRealMoney = new BigDecimal("0");
        BigDecimal totalAfterInAccountMoney = new BigDecimal("0");
        BigDecimal totalAfterTaxRealMoney = new BigDecimal("0");
        BigDecimal totalAfterCouponInAccountMoney = new BigDecimal("0");
        this.computeMoney(list, billSearchForm);

        for (CustomerContractWithLogDto customerContractWithLogDto : list) {
            totalInAccountMoney = totalInAccountMoney.add(new BigDecimal(customerContractWithLogDto.getInAccountMoney()));
            totalRealMoney = totalRealMoney.add(new BigDecimal(customerContractWithLogDto.getRealIncome()));
            totalAfterInAccountMoney = totalAfterInAccountMoney.add(new BigDecimal(customerContractWithLogDto.getAfterTaxInAccountMoney()));
            totalAfterTaxRealMoney = totalAfterTaxRealMoney.add(new BigDecimal(customerContractWithLogDto.getAfterTaxRealIncome()));
            totalAfterCouponInAccountMoney = totalAfterCouponInAccountMoney.add(new BigDecimal(customerContractWithLogDto.getAfterCouponInAccountMoney()));
        }
        TotalBillDto totalBillDto = new TotalBillDto();
        totalBillDto.setTotalInAccountMoney(totalInAccountMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        totalBillDto.setTotalRealMoney(totalRealMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        totalBillDto.setTotalAfterTaxInAccountMoney(totalAfterInAccountMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        totalBillDto.setTotalAfterTaxRealMoney(totalAfterTaxRealMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        totalBillDto.setTotalAfterCouponInAccountMoney(totalAfterCouponInAccountMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        return totalBillDto;
    }

    @Override
    public void active(CustomerContractEntity temp) throws ParseException {
        //预收费的合同激活合同
        //目前只有会员卡是预先收费的
        CustomerContractEntity customerContract = customerContractDao.selectById(temp.getCustomerContractId());
        CustomerEntity customerEntity = customerDao.selectById(customerContract.getCustomerId());
        customerContract.setActiveTime(temp.getActiveTime());
        if (customerContract.getContractType().equals(ContractTypeEnum.VIP_CARD_CONTRACT.getContractTypeName())) {
            //会员卡
            if (customerContract.getVipCardBigType().equals(VipCardBigTypeEnum.DURATION_CARD.getCardTypeName())) {
                //阶段卡
                VipCardEntity vipCardEntity = vipCardDao.selectById(customerContract.getVipCardId());

                if (vipCardEntity.getVipCardType().equals(VipCardTypeEnum.MONTH_CARD.getStatusName())) {
                    //月卡男31天，女34天
                    if (customerEntity.getGender().equals(1)) {
                        //男
                        customerContract.setTotalRights(vipCardEntity.getAddVipDays() + customerContract.getFreeDays() + "");
                    } else {
                        //女
                        customerContract.setTotalRights(vipCardEntity.getAddVipDaysFemale() + customerContract.getFreeDays() + "");
                    }
                } else {
                    //非月卡
                    customerContract.setTotalRights(vipCardEntity.getAddVipDays() + customerContract.getFreeDays() + "");
                }
                //计算结束时间
                Date endTime = DateUtils.addDateDays(customerContract.getActiveTime(), Integer.parseInt(customerContract.getTotalRights()));
                customerContract.setEndTime(endTime);
                Date today = new Date();
                //判断开始日期、结束日期和今天的关系
                if (today.after(endTime)) {
                    //已经使用完
                    customerContract.setLeftRights("0");
                    customerContract.setLeftMoney("0");
                    customerContract.setContractStatus(ContractStatusEnum.FINISHED.getContractStatusName());
                } else {
                    if (today.before(customerContract.getActiveTime())) {
                        //未开始
                        customerContract.setLeftRights(customerContract.getTotalRights());
                        customerContract.setLeftMoney(customerContract.getPayTotalMoney());
                        customerContract.setContractStatus(ContractStatusEnum.NO_START.getContractStatusName());
                    } else {
                        //进行中
                        customerContract.setLeftRights(DateUtils.daysBetweenSpecial(today, endTime) + "");
                        Double leftMoney = DateUtils.daysBetweenSpecial(today, endTime) / Double.parseDouble(customerContract.getTotalRights()) * Double.parseDouble(customerContract.getPayTotalMoney());
                        customerContract.setLeftMoney(String.format("%.2f", leftMoney));
                        customerContract.setContractStatus(ContractStatusEnum.IN_PROGRESS.getContractStatusName());
                    }
                }
                customerContractDao.updateById(customerContract);
                customerService.updateCustomerVipDate(customerEntity);
            }
        }
    }

    @Override
    public List<CustomerContractEntity> getOneCustomerContracts(Long customerId) {
        return customerContractDao.selectCustomerContracts(customerId);
    }

    @Override
    public void saveContract(CustomerContractEntity customerContractEntity) throws ParseException {
        CustomerEntity customerEntity = customerDao.selectById(customerContractEntity.getCustomerId());
        if (customerEntity == null) {
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        customerContractEntity.setContractNumber(OrderNumberGenerator.getLongOrderNumber(null));

        Date today = new Date();
        if (customerContractEntity.getActiveTime() != null) {
            if (today.before(customerContractEntity.getActiveTime())) {
                //未开始
                customerContractEntity.setContractStatus(ContractStatusEnum.NO_START.getContractStatusName());
            } else {
                //进行中
                customerContractEntity.setContractStatus(ContractStatusEnum.IN_PROGRESS.getContractStatusName());
            }
        }
        if (customerContractEntity.getPayTime() == null) {
            //正常情况前端会传支付时间，若没有传，设置默认支付时间
            if (customerContractEntity.getActiveTime() != null) {
                customerContractEntity.setPayTime(customerContractEntity.getActiveTime());
            } else {
                customerContractEntity.setPayTime(new Date());
            }
        }
        //计算支付总额：支付总额=支付金额1+支付金额2
        if (!StringUtils.isEmpty(customerContractEntity.getPayMoney2())) {
            BigDecimal payTotalMoney = new BigDecimal(customerContractEntity.getPayMoney()).add(new BigDecimal(customerContractEntity.getPayMoney2()));
            customerContractEntity.setPayTotalMoney(payTotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        } else {
            customerContractEntity.setPayMoney2("0");
            customerContractEntity.setPayTotalMoney(customerContractEntity.getPayMoney());
        }

        //计算税后支付总金额
        if (StringUtils.isEmpty(customerContractEntity.getAfterTaxPayMoney())) {
            BigDecimal afterTaxTotal = new BigDecimal(customerContractEntity.getPayMoney()).multiply(new BigDecimal("1").subtract(BigDecimal.valueOf(TaxUtil.getTax(customerContractEntity.getPayType()))));
            if (!StringUtils.isEmpty(customerContractEntity.getPayMoney2())) {
                BigDecimal afterTax2 = new BigDecimal(customerContractEntity.getPayMoney2()).multiply(new BigDecimal("1").subtract(BigDecimal.valueOf(TaxUtil.getTax(customerContractEntity.getPayType2()))));
                afterTaxTotal = afterTaxTotal.add(afterTax2);
            }
            //扣除卡券费用
            //不为0，扣除抵用券的费用
            if (customerContractEntity.getCouponMoney() != null && !customerContractEntity.getCouponMoney().equals(new Double("0"))) {
                afterTaxTotal = afterTaxTotal.subtract(BigDecimal.valueOf(customerContractEntity.getCouponMoney()));
            }
            customerContractEntity.setAfterTaxPayMoney(afterTaxTotal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        }

        //判断是会员卡还是私教课还是转卡费
        if (ContractTypeEnum.TRAINING_CONTRACT.getContractTypeName().equals(customerContractEntity.getContractType())) {
            //私教课
            TrainingPlanEntity trainingPlanEntity = trainingPlanDao.selectById(customerContractEntity.getTrainingPlanId());
            if (trainingPlanEntity == null) {
                throw new FitnessException(ExceptionEnum.TRAINING_PLAN_NOT_FOUND);
            }
            //自动根据销售员添加上课教练-- 只能教练才能卖课，所以销售员就上课教练
            List<CoachEntity> coachList = coachDao.selectList(new QueryWrapper<CoachEntity>().eq("delete_flag", 0));
            for (CoachEntity coach : coachList) {
                if (coach.getCoachName().equals(customerContractEntity.getSalesmanName())) {
                    customerContractEntity.setCoachId(coach.getCoachId());
                    customerContractEntity.setCoachName(coach.getCoachName());
                    break;
                }
            }
            customerContractEntity.setTrainingType(trainingPlanEntity.getTrainingType());
            customerContractEntity.setTotalMoney(trainingPlanEntity.getPrice());
            if (trainingPlanEntity.getTrainingType().equals(TrainingTypeEnum.MONTH_TRAINING.getTrainingTypeName())) {
                //包月私教
                customerContractEntity.setTotalRights(31 + "");
                Date endTime = DateUtils.addDateDays(customerContractEntity.getActiveTime(), 31);
                customerContractEntity.setEndTime(endTime);
                //判断开始日期、结束日期和今天的关系
                if (today.after(endTime)) {
                    //已经使用完
                    customerContractEntity.setContractStatus(ContractStatusEnum.FINISHED.getContractStatusName());
                    customerContractEntity.setLeftRights(0 + "");
                    customerContractEntity.setLeftMoney(0 + "");
                } else {
                    if (today.before(customerContractEntity.getActiveTime())) {
                        //未开始
                        customerContractEntity.setLeftRights(31 + "");
                        customerContractEntity.setLeftMoney(customerContractEntity.getPayTotalMoney());
                    } else {
                        //进行中
                        customerContractEntity.setLeftRights(DateUtils.daysBetweenSpecial(today, endTime) + "");
                        Double leftMoney = DateUtils.daysBetweenSpecial(today, endTime) / 31.0 * Double.parseDouble(customerContractEntity.getPayTotalMoney());
                        customerContractEntity.setLeftMoney(String.format("%.2f", leftMoney));
                    }
                }
            } else {
                //非包月私教
                customerContractEntity.setTotalRights(trainingPlanEntity.getTotalClass() + "");
                customerContractEntity.setLeftRights(trainingPlanEntity.getTotalClass() + "");
                customerContractEntity.setLeftMoney(customerContractEntity.getPayTotalMoney());
                //私教，单节课的有效期为5天
                //非包月私教的结束日期为 开始日期 + 课程数量*5
                Date endTime = DateUtils.addDateDays(customerContractEntity.getActiveTime(), trainingPlanEntity.getTotalClass() * coachOneClassTime);
                customerContractEntity.setEndTime(endTime);
            }
        } else if (ContractTypeEnum.VIP_CARD_CONTRACT.getContractTypeName().equals(customerContractEntity.getContractType())) {
            //会员卡
            VipCardEntity vipCardEntity = vipCardDao.selectById(customerContractEntity.getVipCardId());
            if (vipCardEntity == null) {
                throw new FitnessException(ExceptionEnum.VIP_CARD_NOT_FOUND);
            }
            customerContractEntity.setVipCardBigType(vipCardEntity.getVipCardBigType());
            customerContractEntity.setTotalMoney(vipCardEntity.getPrice());
            //阶段卡还是次卡
            if (vipCardEntity.getVipCardBigType().equals(VipCardBigTypeEnum.COUNT_CARD.getCardTypeName())) {
                //次卡
                customerContractEntity.setTotalRights(vipCardEntity.getUseCount() + "");
                customerContractEntity.setLeftRights(vipCardEntity.getUseCount() + "");
                customerContractEntity.setLeftMoney(vipCardEntity.getPrice());
                //次卡有效期默认为1年
                Date endTime = DateUtils.addDateDays(customerContractEntity.getActiveTime(), 365);
                customerContractEntity.setEndTime(endTime);
            } else {
                //阶段卡

                if (vipCardEntity.getVipCardType().equals(VipCardTypeEnum.MONTH_CARD.getStatusName())) {
                    //月卡男31天，女34天
                    if (customerEntity.getGender().equals(1)) {
                        //男
                        customerContractEntity.setTotalRights(vipCardEntity.getAddVipDays() + customerContractEntity.getFreeDays() + "");
                    } else {
                        //女
                        customerContractEntity.setTotalRights(vipCardEntity.getAddVipDaysFemale() + customerContractEntity.getFreeDays() + "");
                    }
                } else {
                    //非月卡
                    customerContractEntity.setTotalRights(vipCardEntity.getAddVipDays() + customerContractEntity.getFreeDays() + "");
                }

                //是否预先收费
                if (ContractStatusEnum.PREPAYMENT.getContractStatusName().equals(customerContractEntity.getContractStatus())) {
                    //预收费
                    customerContractEntity.setActiveTime(null);
                    customerContractEntity.setEndTime(null);
                    customerContractEntity.setLeftRights(customerContractEntity.getTotalRights());
                    customerContractEntity.setLeftMoney(customerContractEntity.getPayTotalMoney());
                    customerContractEntity.setContractStatus(ContractStatusEnum.PREPAYMENT.getContractStatusName());
                } else {
                    //非预先收费，正常流程
                    //计算结束时间
                    Date endTime = DateUtils.addDateDays(customerContractEntity.getActiveTime(), Integer.parseInt(customerContractEntity.getTotalRights()));
                    customerContractEntity.setEndTime(endTime);
                    //判断开始日期、结束日期和今天的关系
                    if (today.after(endTime)) {
                        //已经使用完
                        customerContractEntity.setContractStatus(ContractStatusEnum.FINISHED.getContractStatusName());
                        customerContractEntity.setLeftRights(0 + "");
                        customerContractEntity.setLeftMoney(0 + "");
                    } else {
                        if (today.before(customerContractEntity.getActiveTime())) {
                            //未开始
                            customerContractEntity.setLeftRights(customerContractEntity.getTotalRights());
                            customerContractEntity.setLeftMoney(customerContractEntity.getPayTotalMoney());
                            customerContractEntity.setContractStatus(ContractStatusEnum.NO_START.getContractStatusName());
                        } else {
                            //进行中
                            customerContractEntity.setLeftRights(DateUtils.daysBetweenSpecial(today, endTime) + "");
                            Double leftMoney = DateUtils.daysBetweenSpecial(today, endTime) / Double.parseDouble(customerContractEntity.getTotalRights()) * Double.parseDouble(customerContractEntity.getPayTotalMoney());
                            customerContractEntity.setLeftMoney(String.format("%.2f", leftMoney));
                            customerContractEntity.setContractStatus(ContractStatusEnum.IN_PROGRESS.getContractStatusName());
                        }
                    }
                }
            }
        } else if (ContractTypeEnum.TRANSFER_CARD.getContractTypeName().equals(customerContractEntity.getContractType()) ||
                ContractTypeEnum.OTHER_SALES.getContractTypeName().equals(customerContractEntity.getContractType())
        ) {
            // 转卡费 或者 其他销售收入
            customerContractEntity.setEndTime(customerContractEntity.getActiveTime());
            customerContractEntity.setTotalMoney(customerContractEntity.getPayTotalMoney());
            customerContractEntity.setTotalRights("1");
            customerContractEntity.setLeftRights("0");
            customerContractEntity.setLeftMoney("0");
            customerContractEntity.setContractStatus(ContractStatusEnum.FINISHED.getContractStatusName());
        } else if (ContractTypeEnum.GROUP_PURCHASE_PRICE.getContractTypeName().equals(customerContractEntity.getContractType())) {
            // 团购补差价
            customerContractEntity.setTotalMoney(customerContractEntity.getPayTotalMoney());
            customerContractEntity.setTotalRights("1");
            customerContractEntity.setLeftRights("0");
            customerContractEntity.setLeftMoney("0");
            customerContractEntity.setContractStatus(ContractStatusEnum.FINISHED.getContractStatusName());
        } else {
            //类型不对
            throw new FitnessException(ExceptionEnum.CONTRACT_TYPE_ERROR);
        }
        customerContractDao.insert(customerContractEntity);
        customerService.updateCustomerVipDate(customerEntity);
    }

    @Override
    public void deleteContract(Long customerContractId) throws ParseException {
        CustomerContractEntity customerContractEntity = customerContractDao.selectById(customerContractId);
        CustomerEntity customerEntity = customerDao.selectById(customerContractEntity.getCustomerId());
        customerContractDao.deleteById(customerContractId);
        customerService.updateCustomerVipDate(customerEntity);
    }

    @Override
    public void finishTimes(FinishTimesForm finishTimesForm) {
        CustomerContractEntity customerContractEntity = customerContractDao.selectById(finishTimesForm.getCustomerContractId());
        if (customerContractEntity.getLeftRights().equals("0")) {
            throw new FitnessException(ExceptionEnum.NO_TIMES_TO_USE);
        }
        Integer leftRights = Integer.parseInt(customerContractEntity.getLeftRights()) - 1;
        if (leftRights == 0) {
            //更新为已经完成
            customerContractEntity.setContractStatus(ContractStatusEnum.FINISHED.getContractStatusName());
            customerContractEntity.setLeftMoney("0");
        } else {
            //更新剩余金额、剩余权益
            Double leftMoney = leftRights / Double.parseDouble(customerContractEntity.getTotalRights()) * Double.parseDouble(customerContractEntity.getPayTotalMoney());
            customerContractEntity.setLeftMoney(String.format("%.2f", leftMoney));
        }
        customerContractEntity.setLeftRights(leftRights + "");
        customerContractDao.updateById(customerContractEntity);
        //记录销卡销课日志
        CustomerSureClassLog customerSureClassLog = new CustomerSureClassLog();
        customerSureClassLog.setCustomerId(customerContractEntity.getCustomerId());
        customerSureClassLog.setSureClassTime(new Date());
        customerSureClassLog.setCoachId(customerContractEntity.getCoachId());
        customerSureClassLog.setClient(finishTimesForm.getClient());
        customerSureClassLog.setContractId(customerContractEntity.getCustomerContractId());
        customerSureClassLog.setOperationName(finishTimesForm.getOperationName());
        customerSureClassLogDao.insert(customerSureClassLog);
    }

    @Override
    public void exportToExcel(BillSearchForm billSearchForm, HttpServletResponse response) throws ParseException {
        billSearchForm.setEndDate(DateUtils.getEndOfOneDay(billSearchForm.getEndDate()));
        List<CustomerContractWithLogDto> list = customerContractDao.selectContractListWithLogNoPage(billSearchForm);
        this.computeMoney(list, billSearchForm);
        for (CustomerContractWithLogDto customerContractWithLogDto : list) {
            customerContractWithLogDto.setPayType(PayTypeUtil.getPayTypeChinese(customerContractWithLogDto.getPayType()));
            customerContractWithLogDto.setPayType2(PayTypeUtil.getPayTypeChinese(customerContractWithLogDto.getPayType2()));
            customerContractWithLogDto.setActiveTimeStr(DateUtils.format(customerContractWithLogDto.getActiveTime()));
            customerContractWithLogDto.setEndTimeStr(DateUtils.format(customerContractWithLogDto.getEndTime()));
            customerContractWithLogDto.setPayTimeStr(DateUtils.format(customerContractWithLogDto.getPayTime()));
            customerContractWithLogDto.setOperationTimeStr(DateUtils.formatLong(customerContractWithLogDto.getOperationTime()));
            customerContractWithLogDto.setContractStatus(ContractStatusUtil.getContractStatusChinese(customerContractWithLogDto.getContractStatus()));
        }
        Map<String, Object> filedNames = new LinkedHashMap<String, Object>();
        String titleName = "";
        Class<?> objClass = null;
        Object obj = null;
        try {
            if (!CollectionUtils.isEmpty(list)) {
                // 声明一个list集合接收Dao层查询所返回来的值
                obj = list;
                // excel标题和字段
                filedNames.put("customerName", "顾客姓名");
                filedNames.put("contractName", "合同名称");
                filedNames.put("payTotalMoney", "支付总金额");
                filedNames.put("inAccountMoney", "收入");
                filedNames.put("afterTaxInAccountMoney", "税后券后收入");
                filedNames.put("payMoney", "支付金额1");
                filedNames.put("payType", "支付方式1");
                filedNames.put("payMoney2", "支付金额2");
                filedNames.put("payType2", "支付方式2");
                filedNames.put("realUsedCount", "会员使用天数/私教使用次数/使用权益");
                filedNames.put("realIncome", "毛利润");
                filedNames.put("afterTaxRealIncome", "税后毛利润");
                filedNames.put("totalRights", "总的权益");
                filedNames.put("totalUsedCount", "总使用权益");
                filedNames.put("totalUsedMoney", "总使用金额");
                filedNames.put("leftRights", "总剩余权益");
                filedNames.put("leftMoney", "总剩余金额");
                filedNames.put("freeDays", "赠送权益");
                filedNames.put("couponMoney", "抵用券金额");
                filedNames.put("activeTimeStr", "激活时间");
                filedNames.put("endTimeStr", "结束时间");
                filedNames.put("contractStatus", "合同状态");
                filedNames.put("payTimeStr", "支付时间");
                filedNames.put("operationTimeStr", "操作时间");

                titleName = "账单表";
                objClass = CustomerContractWithLogDto.class;
                Integer[] widths = new Integer[filedNames.size()];
                Arrays.fill(widths, 15);
                widths[0] = 10;
                widths[1] = 26;
                widths[19] = 18;
                widths[20] = 18;
                widths[21] = 10;
                widths[22] = 18;
                widths[23] = 26;
                // 调用ExcelResponseUtil
                ExcelResponseUtil.exportToExcel(response, obj, filedNames, titleName, objClass, widths);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void refundContract(RefundContractForm refundContractForm) {
        CustomerContractEntity customerContractEntity = customerContractDao.selectById(refundContractForm.getCustomerContractId());
        if (customerContractEntity == null) {
            throw new FitnessException(ExceptionEnum.CONTRACT_NOT_FOUND);
        }
        CustomerEntity customer = customerDao.selectById(customerContractEntity.getCustomerId());

        double refundMoney = Double.parseDouble(refundContractForm.getRefundMoney());
        String refundMoneyPositive = "";
        String refundMoneyNegative = "";

        if (refundMoney < 0) {
            refundMoneyPositive = (0 - refundMoney) + "";
            refundMoneyNegative = refundMoney + "";
        } else {
            refundMoneyPositive = refundMoney + "";
            refundMoneyNegative = (0 - refundMoney) + "";
        }
        //更新该合同的基本信息
        customerContractEntity.setRefundMoney(refundMoneyPositive);
        customerContractEntity.setRefundPayType(refundContractForm.getRefundPayType());
        customerContractEntity.setRefundDate(refundContractForm.getRefundDate());
        customerContractEntity.setRefundOperationTime(new Date());
        customerContractEntity.setLeftMoney("0");
        customerContractEntity.setLeftRights("0");
        customerContractEntity.setContractStatus(ContractStatusEnum.REFUNDED.getContractStatusName());
        customerContractDao.updateById(customerContractEntity);

        //新增退款合同
        CustomerContractEntity newContract = new CustomerContractEntity();
        newContract.setContractNumber(OrderNumberGenerator.getLongOrderNumber(null));
        newContract.setCustomerId(customerContractEntity.getCustomerId());
        newContract.setContractType(ContractTypeEnum.REFUND.getContractTypeName());
        newContract.setContractName(customer.getName() + "-退费");
        newContract.setTotalMoney(refundMoneyNegative);
        newContract.setPayTotalMoney(refundMoneyNegative);
        newContract.setPayMoney(refundMoneyNegative);
        BigDecimal afterTaxMoney = new BigDecimal(refundMoneyNegative).multiply(new BigDecimal("1").subtract(BigDecimal.valueOf(TaxUtil.getTax(refundContractForm.getRefundPayType()))));
        newContract.setAfterTaxPayMoney(afterTaxMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        newContract.setPayType(refundContractForm.getRefundPayType());
        newContract.setTotalRights("1");
        newContract.setLeftRights("0");
        newContract.setLeftMoney("0");
        newContract.setContractStatus(ContractStatusEnum.FINISHED.getContractStatusName());
        newContract.setActiveTime(refundContractForm.getRefundDate());
        newContract.setEndTime(refundContractForm.getRefundDate());
        newContract.setPayTime(refundContractForm.getRefundDate());
        customerContractDao.insert(newContract);
    }

}