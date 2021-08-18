package com.phoenix.fitness.modules.fitness.dao;

import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithAllDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithClassInfoDto;
import com.phoenix.fitness.modules.fitness.dto.MonthClassCountDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanDayEntity;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.phoenix.fitness.modules.fitness.vo.CoachDateVO;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.Date;
import java.util.List;

/**
 * 训练辅助DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface CustomerPlanDayDao extends BaseMapper<CustomerPlanDayEntity> {

    List<CustomerPlanDayEntity> selectCustomerPlanDayWithDetailByDate(@Param("date") Date date, @Param("user") UserEntity user);

    CustomerPlanDayWithAllDto selectCustomerPlanDayWithDetail(Long id);

    List<CustomerPlanDayEntity> selectOneCustomerPlan(Long id);

    List<CustomerPlanDayEntity> selectOneCustomerHistory(Long id);

    List<MonthClassCountDto> listOneMonthCount(@Param("yearMonth")String yearMonth, @Param("user")UserEntity user);

    List<CustomerPlanDayWithClassInfoDto> selectCustomerAllDays(Long customerId);

    List<CustomerPlanDayWithClassInfoDto> selectCoachOneDay(@Param("coachDateVO")CoachDateVO coachDateVO);
}
