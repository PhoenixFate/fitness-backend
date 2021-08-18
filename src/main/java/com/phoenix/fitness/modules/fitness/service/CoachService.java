package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.form.CoachSearchForm;
import com.phoenix.fitness.modules.fitness.dto.CoachDetailDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithClassInfoDto;
import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import com.phoenix.fitness.modules.fitness.vo.CoachDateVO;
import com.phoenix.fitness.modules.pad.entity.UserEntity;
import com.phoenix.fitness.modules.pad.form.PasswordForm;
import com.phoenix.fitness.modules.pad.form.RegisterForm;

import java.util.List;

/**
 * 教练service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface CoachService extends IService<CoachEntity> {

    PageUtils queryPage(CoachSearchForm coachSearchForm);

    CoachEntity getByUserId(Long userId);

    Boolean removeByDeleteFlag(Long id);

    CoachDetailDto selectDetailWithTarget(Long id);

    Boolean register(RegisterForm form);

    Boolean updateById(CoachDetailDto coachDetailDto);

    void changePassword(PasswordForm passwordForm, UserEntity user);

    List<CustomerPlanDayWithClassInfoDto> oneDayList(CoachDateVO coachDateVO);
}

