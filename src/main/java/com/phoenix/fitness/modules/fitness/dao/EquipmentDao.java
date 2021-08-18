package com.phoenix.fitness.modules.fitness.dao;

import com.phoenix.fitness.modules.fitness.entity.EquipmentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备DAO
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface EquipmentDao extends BaseMapper<EquipmentEntity> {

    Integer deleteByDeleteFlag(Long id);

    Integer deleteBatchByDeleteFlag(List<Long> ids);
}
