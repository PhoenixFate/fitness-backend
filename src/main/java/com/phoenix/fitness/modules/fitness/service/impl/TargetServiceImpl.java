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
            //根据id查询失败或者已经被删除
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return target;
    }

    @Override
    @Transactional
    public boolean save(TargetWithAllDto target) {
        //检查目标分类是否存在
        this.checkTargetType(target);
        //检查目标阶段类型是否正确
        this.checkTargetPeriodType(target);
        int count = targetDao.insert(target);
        if (count == 0) {
            //新增失败
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        this.saveOrUpdateBase(target.getCoaches(), target.getTargetId());
        return true;
    }

    @Override
    @Transactional
    public boolean updateById(TargetWithAllDto target) {
        //检查target是否存在
        TargetEntity temp = targetDao.selectById(target.getTargetId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //修改的内容不存在或者已经被删除
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        //检查目标分类是否存在
        this.checkTargetType(target);
        //检查目标阶段类型是否正确
        this.checkTargetPeriodType(target);
        int count = targetDao.updateById(target);
        if (count == 0) {
            //修改失败
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        this.saveOrUpdateBase(target.getCoaches(), target.getTargetId());
        return true;
    }

    /**
     * 检查目标分类是否存在
     *
     * @param target 目标
     */
    public void checkTargetType(TargetWithAllDto target) {

    }

    /**
     * 检查目标阶段类型是否正确
     *
     * @param target 目标
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
            //目标阶段类型不是4中之一
            throw new FitnessException(ExceptionEnum.TARGET_PERIOD_TYPE_ERROR);
        }
    }

    /**
     * 更新目标和教练的关联关系
     */
    public void saveOrUpdateBase(List<CoachEntity> coaches, Long targetId) {
        //先删除所有的教练-目标对应关系
        targetCoachRelationDao.delete(new QueryWrapper<TargetCoachRelationEntity>().eq("target_id", targetId));
        //再重新添加教练-目标对应关系
        if (!CollectionUtils.isEmpty(coaches)) {
            List<TargetCoachRelationEntity> targetCoachRelationEntityList = new ArrayList<>();
            List<Long> coachIds1 = new ArrayList<>();
            Set<Long> coachIds2 = new HashSet<>();
            for (CoachEntity coach : coaches) {
                coachIds1.add(coach.getCoachId());
                coachIds2.add(coach.getCoachId());
                //教练是否存在
                CoachEntity tempCoach = coachDao.selectById(coach.getCoachId());
                if (tempCoach == null || tempCoach.getDeleteFlag().equals(1)) {
                    //教练不存在或者教练已经被删除
                    throw new FitnessException(ExceptionEnum.COACH_NOT_FOUND);
                }
                TargetCoachRelationEntity targetCoachRelationEntity = new TargetCoachRelationEntity();
                targetCoachRelationEntity.setCoachId(coach.getCoachId());
                targetCoachRelationEntity.setTargetId(targetId);
                targetCoachRelationEntityList.add(targetCoachRelationEntity);
            }
            if (coachIds1.size() != coachIds2.size()) {
                //不能给同一个教练下发相同的目标
                throw new FitnessException(ExceptionEnum.COACH_DIFFERENT_TARGET);
            }
            //再重新添加教练-目标对应关系
            targetCoachRelationService.saveBatch(targetCoachRelationEntityList);
        }
    }

    @Override
    public void removeByDeleteFlag(Long id) {
        //检查target是否存在
        TargetEntity temp = targetDao.selectById(id);
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //target不存在或者已经被删除
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = targetDao.deleteByDeleteFlag(id);
        if (count == 0) {
            //删除失败
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
    }

    @Override
    public void updateCoachRelation(TargetUpdateCoachVO target) {
        //检查target是否存在
        TargetEntity temp = targetDao.selectById(target.getTargetId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //target不存在或者已经被删除
            throw new FitnessException(ExceptionEnum.TARGET_NOT_FOUND);
        }
        this.saveOrUpdateBase(target.getCoaches(), target.getTargetId());
    }

    @Override
    public void deleteCoachRelation(TargetDeleteCoachVO target) {
        //检查target是否存在
        TargetWithAllDto temp = targetDao.selectTargetWithAll(target.getTargetId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //target不存在或者已经被删除
            throw new FitnessException(ExceptionEnum.TARGET_NOT_FOUND);
        }
        //检查该目标下面是否关联了该教练
        boolean flag = false;
        for (CoachEntity coach : temp.getCoaches()) {
            if (coach.getCoachId().equals(target.getCoach().getCoachId())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            //该目标下面没有关联该教练
            throw new FitnessException(ExceptionEnum.TARGET_DONT_HAVE_COACH);
        }
        //删除该目标下面的该教练的关联信息
        targetCoachRelationDao.delete(new QueryWrapper<TargetCoachRelationEntity>().eq("target_id", target.getTargetId()).eq("coach_id", target.getCoach().getCoachId()));
    }


}