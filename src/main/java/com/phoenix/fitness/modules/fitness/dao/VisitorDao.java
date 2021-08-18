package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.entity.VisitorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.phoenix.fitness.modules.admin.dto.PeriodCountDto;
import com.phoenix.fitness.modules.admin.form.VisitorSearchForm;

import java.util.List;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-07-20 16:03:42
 */
@Mapper
public interface VisitorDao extends BaseMapper<VisitorEntity> {
    IPage<VisitorEntity> selectVisitorList(IPage<VisitorEntity> pageParams,
                                           @Param("searchForm") VisitorSearchForm searchForm);
    List<PeriodCountDto> selectCustomerPeriodCount();
    List<PeriodCountDto> selectVisitorPeriodCount();
}
