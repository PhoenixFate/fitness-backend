package com.phoenix.fitness.modules.job.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.phoenix.fitness.modules.fitness.dao.CustomerContractDao;
import com.phoenix.fitness.modules.fitness.entity.CustomerContractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.phoenix.fitness.common.constant.ContractStatusEnum;
import com.phoenix.fitness.common.constant.ContractTypeEnum;
import com.phoenix.fitness.common.constant.TrainingTypeEnum;
import com.phoenix.fitness.common.constant.VipCardBigTypeEnum;
import com.phoenix.fitness.common.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * 每天晚上定时计算：
 * 合同剩余时间、剩余金额
 *
 * @author Mark sm516116978@outlook.com
 */
@Component("computeContractTask")
public class ComputeContractTask implements ITask {

    @Autowired
    private CustomerContractDao customerContractDao;

    @Override
    public void run(String params) {
        List<CustomerContractEntity> customerContractEntityList = customerContractDao.selectList(new QueryWrapper<CustomerContractEntity>());
        for (CustomerContractEntity customerContractEntity : customerContractEntityList) {
            Date today = new Date();
            //预收费和已经退费的的合同不进行每日更新
            if (!customerContractEntity.getContractStatus().equals(ContractStatusEnum.PREPAYMENT.getContractStatusName())
                    && !customerContractEntity.getContractStatus().equals(ContractStatusEnum.REFUNDED.getContractStatusName())
            ) {
                //会员卡和私教的合同才需要每日更新
                if (customerContractEntity.getContractType().equals(ContractTypeEnum.VIP_CARD_CONTRACT.getContractTypeName()) ||
                        customerContractEntity.getContractType().equals(ContractTypeEnum.TRAINING_CONTRACT.getContractTypeName())
                ) {
                    //只有会员卡的阶段卡、包月的私教才更新剩余时间
                    if (VipCardBigTypeEnum.DURATION_CARD.getCardTypeName().equals(customerContractEntity.getVipCardBigType())
                            || TrainingTypeEnum.MONTH_TRAINING.getTrainingTypeName().equals(customerContractEntity.getTrainingType())
                    ) {
                        //阶段会员卡
                        //判断开始日期、结束日期和今天的关系
                        if (today.after(customerContractEntity.getEndTime())) {
                            //已经使用完
                            customerContractEntity.setContractStatus(ContractStatusEnum.FINISHED.getContractStatusName());
                            customerContractEntity.setLeftRights("0");
                            customerContractEntity.setLeftMoney("0");
                        } else {
                            if (today.before(customerContractEntity.getActiveTime())) {
                                //未开始
                                customerContractEntity.setContractStatus(ContractStatusEnum.NO_START.getContractStatusName());
                                customerContractEntity.setLeftRights(customerContractEntity.getTotalRights());
                                customerContractEntity.setLeftMoney(customerContractEntity.getPayTotalMoney());
                            } else {
                                //进行中
                                customerContractEntity.setContractStatus(ContractStatusEnum.IN_PROGRESS.getContractStatusName());
                                customerContractEntity.setLeftRights(DateUtils.daysBetweenSpecial(today, customerContractEntity.getEndTime()) + "");
                                Double leftMoney = DateUtils.daysBetweenSpecial(today, customerContractEntity.getEndTime()) / Double.parseDouble(customerContractEntity.getTotalRights()) * Double.parseDouble(customerContractEntity.getPayTotalMoney());
                                customerContractEntity.setLeftMoney(String.format("%.2f", leftMoney));
                            }
                        }
                    } else {
                        //其他的，需要每天检测一下，是否用完，是否过期
                        if (customerContractEntity.getEndTime() != null && today.after(customerContractEntity.getEndTime())) {
                            //是否用完，
                            //如果未用完，更新状态为：未用完，但已过期
                            if (!customerContractEntity.getLeftRights().equals("0")) {
                                customerContractEntity.setContractStatus(ContractStatusEnum.EXPIRED.getContractStatusName());
                            }
                        }
                    }
                    customerContractDao.updateById(customerContractEntity);
                } else {
                    //转卡费、其他类销售，暂时不作处理
                }
            }
        }

    }
}
