package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.dto.CoachDetailDto;
import com.phoenix.fitness.modules.fitness.dto.CoachDetailWithTargetDto;
import com.phoenix.fitness.modules.fitness.dto.CoachWithGymDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithClassInfoDto;
import com.phoenix.fitness.modules.fitness.entity.*;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.constant.TargetPeriodTypeEnum;
import com.phoenix.fitness.common.constant.TargetTypeEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.DateUtils;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.form.CoachSearchForm;
import com.phoenix.fitness.modules.fitness.service.CoachService;
import com.phoenix.fitness.modules.fitness.service.GymCoachRelationService;
import com.phoenix.fitness.modules.fitness.vo.CoachDateVO;
import com.phoenix.fitness.modules.pad.dao.UserDao;
import com.phoenix.fitness.modules.pad.entity.UserEntity;
import com.phoenix.fitness.modules.pad.form.PasswordForm;
import com.phoenix.fitness.modules.pad.form.RegisterForm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("coachService")
@AllArgsConstructor
public class CoachServiceImpl extends ServiceImpl<CoachDao, CoachEntity> implements CoachService {

    private final CoachDao coachDao;

    private final UserDao userDao;

    private final CustomerAddHistoryDao customerAddHistoryDao;

    private final CustomerOpenVipHistoryDao customerOpenVipHistoryDao;

    private final CustomerChargeHistoryDao customerChargeHistoryDao;

    private final CustomerPlanDayDao customerPlanDayDao;

    private final GymCoachRelationService gymCoachRelationService;

    private final GymCoachRelationDao gymCoachRelationDao;

    @Override
    public PageUtils queryPage(CoachSearchForm coachSearchForm) {
        Page<CoachWithGymDto> pageParams = new Page<>(coachSearchForm.getPage(), coachSearchForm.getLimit());
        IPage<CoachWithGymDto> page = coachDao.selectListWithGym(pageParams, coachSearchForm);
        return new PageUtils(page);
    }

    @Override
    public CoachEntity getByUserId(Long userId) {
        CoachEntity coach = coachDao.selectOne(new QueryWrapper<CoachEntity>().eq("user_id", userId));
        if (coach == null || coach.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return coach;
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        CoachEntity temp = coachDao.selectById(id);
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = coachDao.deleteByDeleteFlag(id);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        return true;
    }

    @Override
    public CoachDetailDto selectDetailWithTarget(Long id) {
        CoachDetailDto coachDetail = coachDao.selectDetailWithTarget(id);
        if (!CollectionUtils.isEmpty(coachDetail.getCoachTargetDetailList())) {
            List<Date> weekFirstEndDay = DateUtils.getWeekFirstEndDay(new Date());
            List<Date> monthFirstEndDay = DateUtils.getMonthFirstEndDay(new Date());
            //???????????????????????????????????????
            for (CoachDetailWithTargetDto coachDetailWithTargetDto : coachDetail.getCoachTargetDetailList()) {
                //?????????
                if (coachDetailWithTargetDto.getTargetType().equals(TargetTypeEnum.NEW_CUSTOMER.getTypeName())) {
                    if (coachDetailWithTargetDto.getTargetPeriodType().equals(TargetPeriodTypeEnum.WEEK_TARGET.getStatusName())) {
                        //?????????????????????????????????
                        List<CustomerAddHistoryEntity> customerAddHistoryList = customerAddHistoryDao.selectList(new QueryWrapper<CustomerAddHistoryEntity>().ge("add_date", weekFirstEndDay.get(0)).le("add_date", weekFirstEndDay.get(1)));
                        coachDetailWithTargetDto.setCoachFinishedNumber(customerAddHistoryList.size() + "");
                    } else if (coachDetailWithTargetDto.getTargetPeriodType().equals(TargetPeriodTypeEnum.MONTH_TARGET.getStatusName())) {
                        //?????????????????????????????????
                        List<CustomerAddHistoryEntity> customerAddHistoryList = customerAddHistoryDao.selectList(new QueryWrapper<CustomerAddHistoryEntity>().ge("add_date", monthFirstEndDay.get(0)).le("add_date", monthFirstEndDay.get(1)));
                        coachDetailWithTargetDto.setCoachFinishedNumber(customerAddHistoryList.size() + "");
                    }
                }
                //??????vip
                else if (coachDetailWithTargetDto.getTargetType().equals(TargetTypeEnum.NEW_VIP.getTypeName())) {
                    if (coachDetailWithTargetDto.getTargetPeriodType().equals(TargetPeriodTypeEnum.WEEK_TARGET.getStatusName())) {
                        //????????????????????????vip
                        List<CustomerOpenVipHistoryEntity> customerOpenVipHistoryList = customerOpenVipHistoryDao.selectList(new QueryWrapper<CustomerOpenVipHistoryEntity>().ge("operation_date", weekFirstEndDay.get(0)).le("operation_date", weekFirstEndDay.get(1)));
                        coachDetailWithTargetDto.setCoachFinishedNumber(customerOpenVipHistoryList.size() + "");
                    } else if (coachDetailWithTargetDto.getTargetPeriodType().equals(TargetPeriodTypeEnum.MONTH_TARGET.getStatusName())) {
                        //????????????????????????vip
                        List<CustomerOpenVipHistoryEntity> customerOpenVipHistoryList = customerOpenVipHistoryDao.selectList(new QueryWrapper<CustomerOpenVipHistoryEntity>().ge("operation_date", weekFirstEndDay.get(0)).le("operation_date", weekFirstEndDay.get(1)));
                        coachDetailWithTargetDto.setCoachFinishedNumber(customerOpenVipHistoryList.size() + "");
                    }
                }
                //????????????
                else if (coachDetailWithTargetDto.getTargetType().equals(TargetTypeEnum.SALE_MONEY.getTypeName())) {
                    if (coachDetailWithTargetDto.getTargetPeriodType().equals(TargetPeriodTypeEnum.WEEK_TARGET.getStatusName())) {
                        //?????????, ??????????????????
                        List<CustomerChargeHistoryEntity> customerChargeHistoryList = customerChargeHistoryDao.selectList(new QueryWrapper<CustomerChargeHistoryEntity>().ge("charge_date", weekFirstEndDay.get(0)).le("charge_date", weekFirstEndDay.get(1)));
                        BigDecimal totalMoney = new BigDecimal(0);
                        for (CustomerChargeHistoryEntity customerChargeHistoryEntity : customerChargeHistoryList) {
                            totalMoney = totalMoney.add(new BigDecimal(customerChargeHistoryEntity.getMoney()));
                        }
                        coachDetailWithTargetDto.setCoachFinishedNumber(totalMoney.toString());
                    } else if (coachDetailWithTargetDto.getTargetPeriodType().equals(TargetPeriodTypeEnum.MONTH_TARGET.getStatusName())) {
                        //??????????????????????????????
                        List<CustomerChargeHistoryEntity> customerChargeHistoryList = customerChargeHistoryDao.selectList(new QueryWrapper<CustomerChargeHistoryEntity>().ge("charge_date", monthFirstEndDay.get(0)).le("charge_date", monthFirstEndDay.get(1)));
                        BigDecimal totalMoney = new BigDecimal(0);
                        for (CustomerChargeHistoryEntity customerChargeHistoryEntity : customerChargeHistoryList) {
                            totalMoney = totalMoney.add(new BigDecimal(customerChargeHistoryEntity.getMoney()));
                        }
                        coachDetailWithTargetDto.setCoachFinishedNumber(totalMoney.toString());
                    }
                }
            }
        }
        return coachDetail;
    }

    @Override
    public Boolean register(RegisterForm form) {
        //???????????????????????????
        UserEntity temp = userDao.selectOne(new QueryWrapper<UserEntity>().eq("username", form.getUsername()));
        if (temp != null) {
            throw new FitnessException(ExceptionEnum.USERNAME_EXISTS);
        }
        //????????????????????????
        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getUsername());
        if (!StringUtils.isEmpty(form.getPassword())) {
            user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        } else {
            // ????????????
            user.setPassword(DigestUtils.sha256Hex("123456"));
        }
        user.setUserType(2);
        userDao.insert(user);
        //??????
        CoachEntity coachEntity = new CoachEntity();
        coachEntity.setCoachName(form.getCoachName());
        coachEntity.setUsername(form.getUsername());
        coachEntity.setMobile(form.getMobile());
        coachEntity.setGender(form.getGender());
        coachEntity.setEmail(form.getEmail());
        coachEntity.setIdentityCard(form.getIdentityCard());
        coachEntity.setBirthday(form.getBirthday());
        coachEntity.setAvatar(form.getAvatar());
        coachEntity.setUserId(user.getUserId());
        coachEntity.setPerClassPrice(form.getPerClassPrice());
        coachDao.insert(coachEntity);

        //????????????-???????????????
        if (!CollectionUtils.isEmpty(form.getGyms())) {
            List<GymCoachRelationEntity> gymCoachRelationEntities = new ArrayList<>();
            for (GymEntity gymEntity : form.getGyms()) {
                GymCoachRelationEntity gymCoachRelationEntity = new GymCoachRelationEntity();
                gymCoachRelationEntity.setGymId(gymEntity.getGymId());
                gymCoachRelationEntity.setCoachId(coachEntity.getCoachId());
                gymCoachRelationEntities.add(gymCoachRelationEntity);
            }
            gymCoachRelationService.saveBatch(gymCoachRelationEntities);
        }
        return true;
    }

    @Override
    public Boolean updateById(CoachDetailDto coachDetailDto) {
        CoachEntity coach = new CoachEntity();
        coach.setCoachId(coachDetailDto.getCoachId());
        coach.setCoachName(coachDetailDto.getCoachName());
        coach.setAvatar(coachDetailDto.getAvatar());
        coach.setMobile(coachDetailDto.getMobile());
        coach.setGender(coachDetailDto.getGender());
        coach.setPerClassPrice(coachDetailDto.getPerClassPrice());
        coach.setEmail(coachDetailDto.getEmail());
        coach.setIdentityCard(coachDetailDto.getIdentityCard());
        coach.setBirthday(coachDetailDto.getBirthday());
        coachDao.updateById(coach);
        gymCoachRelationDao.delete(new QueryWrapper<GymCoachRelationEntity>().eq("coach_id", coachDetailDto.getCoachId()));
        //????????????-???????????????
        if (!CollectionUtils.isEmpty(coachDetailDto.getGyms())) {
            List<GymCoachRelationEntity> gymCoachRelationEntities = new ArrayList<>();
            for (GymEntity gymEntity : coachDetailDto.getGyms()) {
                GymCoachRelationEntity gymCoachRelationEntity = new GymCoachRelationEntity();
                gymCoachRelationEntity.setGymId(gymEntity.getGymId());
                gymCoachRelationEntity.setCoachId(coachDetailDto.getCoachId());
                gymCoachRelationEntities.add(gymCoachRelationEntity);
            }
            gymCoachRelationService.saveBatch(gymCoachRelationEntities);
        }
        return null;
    }

    @Override
    public void changePassword(PasswordForm passwordForm, UserEntity user) {
        UserEntity temp = userDao.selectById(user.getUserId());
        if (!temp.getPassword().equals(DigestUtils.sha256Hex(passwordForm.getOldPassword()))) {
            //????????????????????????????????????
            throw new FitnessException(ExceptionEnum.OLD_PASSWORD_ERROR);
        }
        user.setPassword(DigestUtils.sha256Hex(passwordForm.getNewPassword()));
        userDao.updateById(user);
    }

    @Override
    public List<CustomerPlanDayWithClassInfoDto> oneDayList(CoachDateVO coachDateVO) {
        return customerPlanDayDao.selectCoachOneDay(coachDateVO);
    }

}