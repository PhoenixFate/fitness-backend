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
import com.phoenix.fitness.modules.fitness.dao.ActionTypeDao;
import com.phoenix.fitness.modules.fitness.entity.ActionTypeEntity;
import com.phoenix.fitness.modules.fitness.service.ActionTypeService;

@Service("actionTypeService")
@AllArgsConstructor
public class ActionTypeServiceImpl extends ServiceImpl<ActionTypeDao, ActionTypeEntity> implements ActionTypeService {

    private final ActionTypeDao actionTypeDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActionTypeEntity> page = this.page(
                new Query<ActionTypeEntity>().getPage(params),
                new QueryWrapper<ActionTypeEntity>().eq("delete_flag",0).like("action_type_name",params.get("searchValue")==null?"":params.get("searchValue")).orderByDesc("create_time")
        );
        return new PageUtils(page);
    }

    @Override
    public ActionTypeEntity getById(Serializable id) {
        ActionTypeEntity actionType = actionTypeDao.selectById(id);
        if(actionType==null || actionType.getDeleteFlag().equals(1)){
            //根据id未查询到内容
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return actionType;
    }

    @Override
    public boolean save(ActionTypeEntity actionType) {
        int count = actionTypeDao.insert(actionType);
        if(count==0){
            //新增失败
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        return true;
    }

    @Override
    public boolean updateById(ActionTypeEntity actionType) {
        ActionTypeEntity temp = actionTypeDao.selectById(actionType.getActionTypeId());
        if(temp==null || temp.getDeleteFlag().equals(1)){
            //要修改的内容不存在
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = actionTypeDao.updateById(actionType);
        if(count==0){
            //修改失败
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        return true;
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        ActionTypeEntity temp = actionTypeDao.selectById(id);
        if(temp==null || temp.getDeleteFlag().equals(1)){
            //要删除的内容不存在
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = actionTypeDao.deleteByDeleteFlag(id);
        if(count==0){
            //删除失败
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        return true;
    }

}