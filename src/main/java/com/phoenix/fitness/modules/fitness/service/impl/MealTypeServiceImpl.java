package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.CollectionUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.MealTypeDao;
import com.phoenix.fitness.modules.fitness.entity.MealTypeEntity;
import com.phoenix.fitness.modules.fitness.service.MealTypeService;

@Service("mealTypeService")
@AllArgsConstructor
public class MealTypeServiceImpl extends ServiceImpl<MealTypeDao, MealTypeEntity> implements MealTypeService {

    private final MealTypeDao mealTypeDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MealTypeEntity> page = this.page(
                new Query<MealTypeEntity>().getPage(params),
                new QueryWrapper<MealTypeEntity>().eq("delete_flag",0).orderByAsc("sort")
        );
        return new PageUtils(page);
    }

    @Override
    public MealTypeEntity getById(Serializable id) {
        MealTypeEntity mealType = mealTypeDao.selectById(id);
        if(mealType==null || mealType.getDeleteFlag().equals(1)){
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return mealType;
    }

    @Override
    public boolean save(MealTypeEntity mealType) {
        if(mealType.getSort()==null){
            //添加默认排序
            List<MealTypeEntity> mealTypeList = mealTypeDao.selectList(new QueryWrapper<MealTypeEntity>().orderByDesc("sort"));
            if(!CollectionUtils.isEmpty(mealTypeList)){
                mealType.setSort(mealTypeList.get(0).getSort()+10);
            }else {
                mealType.setSort(10);
            }
        }
        int count = mealTypeDao.insert(mealType);
        if(count==0){
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        return true;
    }

    @Override
    public boolean updateById(MealTypeEntity mealType) {
        MealTypeEntity temp = mealTypeDao.selectById(mealType.getMealTypeId());
        if(temp==null || temp.getDeleteFlag().equals(1)){
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = mealTypeDao.updateById(mealType);
        if(count==0){
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        return true;
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        MealTypeEntity temp = mealTypeDao.selectById(id);
        if(temp==null || temp.getDeleteFlag().equals(1)){
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = mealTypeDao.deleteByDeleteFlag(id);
        if(count==0){
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        return true;
    }

}