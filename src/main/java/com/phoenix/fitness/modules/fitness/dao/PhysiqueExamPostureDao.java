package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.dto.PostureRecord;
import com.phoenix.fitness.modules.fitness.entity.PhysiqueExamPostureEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author neil created at 2021/7/1 2:10 PM
 */
@Mapper
public interface PhysiqueExamPostureDao extends BaseMapper<PhysiqueExamPostureEntity> {

  List<PostureRecord> findCustomerPostureReport(@Param("customerId") Long customerId, @Param("startAt") Date startAt,
                                                @Param("endAt") Date endAt, @Param("latest") Integer latest);
}
