package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.dto.CustomerVipClassCheckinDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerVipClassCheckinEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author neil created at 2021/6/18 1:26 PM
 */
@Mapper
public interface CustomerVipClassCheckinDao extends BaseMapper<CustomerVipClassCheckinEntity> {

  /**
   * @param name
   * @param coachId
   * @param startAt
   * @param endAt
   * @param limit
   * @param offset
   * @return
   */
  List<CustomerVipClassCheckinDto> selectCheckinRecords(@Param("name") String name,
                                                        @Param("coachId") Long coachId,
                                                        @Param("startAt") Date startAt,
                                                        @Param("endAt") Date endAt,
                                                        @Param("limit") long limit,
                                                        @Param("offset") long offset);

  /**
   * @param name
   * @param coachId
   * @param startAt
   * @param endAt
   * @return
   */
  Long selectCheckinRecordsCount(@Param("name") String name,
                                 @Param("coachId") Long coachId,
                                 @Param("startAt") Date startAt,
                                 @Param("endAt") Date endAt);
}
