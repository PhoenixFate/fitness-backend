package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.EquipmentDao;
import com.phoenix.fitness.modules.fitness.entity.EquipmentEntity;
import com.phoenix.fitness.modules.fitness.service.EquipmentService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service("equipmentService")
@AllArgsConstructor
public class EquipmentServiceImpl extends ServiceImpl<EquipmentDao, EquipmentEntity> implements EquipmentService {

    private final EquipmentDao equipmentDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EquipmentEntity> page = this.page(
                new Query<EquipmentEntity>().getPage(params),
                new QueryWrapper<EquipmentEntity>().eq("delete_flag",0).orderByDesc("create_time").like("equipment_name",params.get("searchValue")==null?"":params.get("searchValue"))
        );
        return new PageUtils(page);
    }

    @Override
    public EquipmentEntity getById(Serializable id) {
        EquipmentEntity equipment = equipmentDao.selectById(id);
        if(equipment==null || equipment.getDeleteFlag().equals(1)){
            //根据id未查询到内容或者已经删除
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return equipment;
    }

    @Override
    public boolean save(EquipmentEntity equipment) {
        List<EquipmentEntity> equipments = equipmentDao.selectList(new QueryWrapper<EquipmentEntity>().orderByDesc("sort"));
        if(equipment.getSort()==null){
            if(!CollectionUtils.isEmpty(equipments)){
                equipment.setSort(equipments.get(0).getSort()+10);
            }
        }
        int count = equipmentDao.insert(equipment);
        if(count==0){
            //新增失败
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        return true;
    }

    @Override
    public boolean updateById(EquipmentEntity equipment) {
        EquipmentEntity temp = equipmentDao.selectById(equipment.getEquipmentId());
        if(temp==null || temp.getDeleteFlag().equals(1)){
            //要修改的内容不存在
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = equipmentDao.updateById(equipment);
        if(count==0){
            //修改失败
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        return true;
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        EquipmentEntity temp = equipmentDao.selectById(id);
        if(temp==null || temp.getDeleteFlag().equals(1)){
            //要删除的内容不存在
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = equipmentDao.deleteByDeleteFlag(id);
        if (count == 0) {
            //删除失败
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        return true;
    }

    @Override
    public Boolean removeBatchByDeleteFlag(List<Long> ids) {
        for(Long id:ids){
            EquipmentEntity temp = equipmentDao.selectById(id);
            if(temp==null || temp.getDeleteFlag().equals(1)){
                //要删除的内容不存在
                throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
            }
        }
        Integer count = equipmentDao.deleteBatchByDeleteFlag(ids);
        if (count == 0) {
            //删除失败
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        return true;
    }


}