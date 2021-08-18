package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.dto.CoachDetailDto;
import com.phoenix.fitness.modules.fitness.dto.CoachWithGymDto;
import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.phoenix.fitness.modules.admin.form.CoachSearchForm;

/**
 * 教练DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface CoachDao extends BaseMapper<CoachEntity> {

    Integer deleteByDeleteFlag(Long id);

    CoachDetailDto selectDetailWithTarget(Long id);

    IPage<CoachWithGymDto> selectListWithGym(IPage<CoachWithGymDto> pageParams, @Param("searchForm") CoachSearchForm searchForm);

}
