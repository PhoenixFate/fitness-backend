package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.constant.TargetPeriodTypeEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.CoachDao;
import com.phoenix.fitness.modules.fitness.dao.TargetCoachRelationDao;
import com.phoenix.fitness.modules.fitness.dao.TargetDao;
import com.phoenix.fitness.modules.fitness.dto.TargetWithAllDto;
import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import com.phoenix.fitness.modules.fitness.entity.TargetCoachRelationEntity;
import com.phoenix.fitness.modules.fitness.entity.TargetEntity;
import com.phoenix.fitness.modules.fitness.service.TargetCoachRelationService;
import com.phoenix.fitness.modules.fitness.service.TargetService;
import com.phoenix.fitness.modules.fitness.vo.TargetDeleteCoachVO;
import com.phoenix.fitness.modules.fitness.vo.TargetSearchForm;
import com.phoenix.fitness.modules.fitness.vo.TargetUpdateCoachVO;

import java.util.*;

@Service("targetService")
@AllArgsConstructor
public class TargetServiceImpl extends ServiceImpl<TargetDao, TargetEntity> implements TargetService {

    private final TargetDao targetDao;

    private final TargetCoachRelationDao targetCoachRelationDao;

    private final TargetCoachRelationService targetCoachRelationService;

    private final CoachDao coachDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        TargetSearchForm targetSearchForm = new TargetSearchForm();
        if (!StringUtils.isEmpty(params.get("targetType"))) {
            targetSearchForm.setTargetType((String) params.get("targetType"));
        }
        if (!StringUtils.isEmpty(params.get("targetPeriodType"))) {
            targetSearchForm.setTargetPeriodType((String) params.get("targetPeriodType"));
        }
        if (!StringUtils.isEmpty(params.get("targetName"))) {
            targetSearchForm.setTargetName((String) params.get("targetName"));
        }
        IPage<TargetEntity> pageParams = new Query<TargetEntity>().getPage(params);
        IPage<TargetEntity> targetPage = targetDao.selectTargetWithTargetType(pageParams, targetSearchForm);
        return new PageUtils(targetPage);
    }


    @Override
    public TargetWithAllDto getDetail(Long id) {
        TargetWithAllDto target = targetDao.selectTargetWithAll(id);
        if (target == null || target.getDeleteFlag().equals(1)) {
            //??????id?????????????????????????????????
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return target;
    }

    @Override
    @Transactional
    public boolean save(TargetWithAllDto target) {
        //??????????????????????????????
        this.checkTargetType(target);
        //????????????????????????????????????
        this.checkTargetPeriodType(target);
        int count = targetDao.insert(target);
        if (count == 0) {
            //????????????
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        this.saveOrUpdateBase(target.getCoaches(), target.getTargetId());
        return true;
    }

    @Override
    @Transactional
    public boolean updateById(TargetWithAllDto target) {
        //??????target????????????
        TargetEntity temp = targetDao.selectById(target.getTargetId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //?????????????????????????????????????????????
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        //??????????????????????????????
        this.checkTargetType(target);
        //????????????????????????????????????
        this.checkTargetPeriodType(target);
        int count = targetDao.updateById(target);
        if (count == 0) {
            //????????????
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        this.saveOrUpdateBase(target.getCoaches(), target.getTargetId());
        return true;
    }

    /**
     * ??????????????????????????????
     *
     * @param target ??????
     */
    public void checkTargetType(TargetWithAllDto target) {

    }

    /**
     * ????????????????????????????????????
     *
     * @param target ??????
     */
    public void checkTargetPeriodType(TargetWithAllDto target) {
        List<String> targetPeriodTypeList = new ArrayList<>();
        targetPeriodTypeList.add(TargetPeriodTypeEnum.DAT_TARGET.getStatusName());
        targetPeriodTypeList.add(TargetPeriodTypeEnum.WEEK_TARGET.getStatusName());
        targetPeriodTypeList.add(TargetPeriodTypeEnum.MONTH_TARGET.getStatusName());
        targetPeriodTypeList.add(TargetPeriodTypeEnum.SPECIAL_TARGET.getStatusName());
        boolean flag = false;
        for (String targetPeriodType : targetPeriodTypeList) {
            if (targetPeriodType.equals(target.getTargetPeriodType())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            //????????????????????????4?????????
            throw new FitnessException(ExceptionEnum.TARGET_PERIOD_TYPE_ERROR);
        }
    }

    /**
     * ????????????????????????????????????
     */
    public void saveOrUpdateBase(List<CoachEntity> coaches, Long targetId) {
        //????????????????????????-??????????????????
        targetCoachRelationDao.delete(new QueryWrapper<TargetCoachRelationEntity>().eq("target_id", targetId));
        //?????????????????????-??????????????????
        if (!CollectionUtils.isEmpty(coaches)) {
            List<TargetCoachRelationEntity> targetCoachRelationEntityList = new ArrayList<>();
            List<Long> coachIds1 = new ArrayList<>();
            Set<Long> coachIds2 = new HashSet<>();
            for (CoachEntity coach : coaches) {
                coachIds1.add(coach.getCoachId());
                coachIds2.add(coach.getCoachId());
                //??????????????????
                CoachEntity tempCoach = coachDao.selectById(coach.getCoachId());
                if (tempCoach == null || tempCoach.getDeleteFlag().equals(1)) {
                    //??????????????????????????????????????????
                    throw new FitnessException(ExceptionEnum.COACH_NOT_FOUND);
                }
                TargetCoachRelationEntity targetCoachRelationEntity = new TargetCoachRelationEntity();
                targetCoachRelationEntity.setCoachId(coach.getCoachId());
                targetCoachRelationEntity.setTargetId(targetId);
                targetCoachRelationEntityList.add(targetCoachRelationEntity);
            }
            if (coachIds1.size() != coachIds2.size()) {
                //?????????????????????????????????????????????
                throw new FitnessException(ExceptionEnum.COACH_DIFFERENT_TARGET);
            }
            //?????????????????????-??????????????????
            targetCoachRelationService.saveBatch(targetCoachRelationEntityList);
        }
    }

    @Override
    public void removeByDeleteFlag(Long id) {
        //??????target????????????
        TargetEntity temp = targetDao.selectById(id);
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //target??????????????????????????????
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = targetDao.deleteByDeleteFlag(id);
        if (count == 0) {
            //????????????
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
    }

    @Override
    public void updateCoachRelation(TargetUpdateCoachVO target) {
        //??????target????????????
        TargetEntity temp = targetDao.selectById(target.getTargetId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //target??????????????????????????????
            throw new FitnessException(ExceptionEnum.TARGET_NOT_FOUND);
        }
        this.saveOrUpdateBase(target.getCoaches(), target.getTargetId());
    }

    @Override
    public void deleteCoachRelation(TargetDeleteCoachVO target) {
        //??????target????????????
        TargetWithAllDto temp = targetDao.selectTargetWithAll(target.getTargetId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //target??????????????????????????????
            throw new FitnessException(ExceptionEnum.TARGET_NOT_FOUND);
        }
        //?????????????????????????????????????????????
        boolean flag = false;
        for (CoachEntity coach : temp.getCoaches()) {
            if (coach.getCoachId().equals(target.getCoach().getCoachId())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            //????????????????????????????????????
            throw new FitnessException(ExceptionEnum.TARGET_DONT_HAVE_COACH);
        }
        //????????????????????????????????????????????????
        targetCoachRelationDao.delete(new QueryWrapper<TargetCoachRelationEntity>().eq("target_id", target.getTargetId()).eq("coach_id", target.getCoach().getCoachId()));
    }


}