package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.TrainingGoalDao;
import com.phoenix.fitness.modules.fitness.entity.TrainingGoalEntity;
import com.phoenix.fitness.modules.fitness.service.TrainingGoalService;

@Service("trainingGoalService")
@AllArgsConstructor
public class TrainingGoalServiceImpl extends ServiceImpl<TrainingGoalDao, TrainingGoalEntity> implements TrainingGoalService {

    private final TrainingGoalDao trainingGoalDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TrainingGoalEntity> page = this.page(
                new Query<TrainingGoalEntity>().getPage(params),
                new QueryWrapper<TrainingGoalEntity>().eq("delete_flag",0).orderByDesc("create_time")
        );
        return new PageUtils(page);
    }

    @Override
    public TrainingGoalEntity getById(Serializable id) {
        TrainingGoalEntity trainingGoal = trainingGoalDao.selectById(id);
        if(trainingGoal==null || trainingGoal.getDeleteFlag().equals(1)){
            //根据id查询失败或者已经被删除
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return trainingGoal;
    }

    @Override
    public boolean updateById(TrainingGoalEntity trainingGoal) {
        TrainingGoalEntity temp = trainingGoalDao.selectById(trainingGoal.getTrainingGoalId());
        if(temp==null || temp.getDeleteFlag().equals(1)){
            //更新的内容不存在
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = trainingGoalDao.updateById(trainingGoal);
        if(count==0){
            //更新失败
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        return true;
    }

    @Override
    public void removeByDeleteFlag(Long id) {
        TrainingGoalEntity temp = trainingGoalDao.selectById(id);
        if(temp==null || temp.getDeleteFlag().equals(1)){
            //更新的内容不存在
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count=trainingGoalDao.deleteByDeleteFlag(id);
        if(count==0){
            //删除失败
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
    }

}