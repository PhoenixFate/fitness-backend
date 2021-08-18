package com.phoenix.fitness.modules.pad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.modules.fitness.dao.CoachDao;
import com.phoenix.fitness.modules.fitness.dao.GymCoachRelationDao;
import com.phoenix.fitness.modules.fitness.dao.GymDao;
import com.phoenix.fitness.modules.fitness.dto.GymWithCoachesDto;
import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import com.phoenix.fitness.modules.fitness.entity.GymCoachRelationEntity;
import com.phoenix.fitness.modules.pad.service.UserService;
import lombok.AllArgsConstructor;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.exception.RRException;
import com.phoenix.fitness.common.validator.Assert;
import com.phoenix.fitness.modules.pad.dao.UserDao;
import com.phoenix.fitness.modules.pad.entity.UserEntity;
import com.phoenix.fitness.modules.pad.form.LoginForm;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service("userService")
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	private final CoachDao coachDao;

	private final GymCoachRelationDao gymCoachRelationDao;

	private final GymDao gymDao;

	@Override
	public UserEntity queryByMobile(String mobile) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile));
	}

	public UserEntity queryByUsername(String username,Integer userType) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username).eq("user_type",userType));
	}

	@Override
	public UserEntity login(LoginForm form) {
		UserEntity user = queryByUsername(form.getUsername(),form.getUserType());
		Assert.isNull(user, "用户名或密码错误");
		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new RRException("用户名或密码错误");
		}
		CoachEntity coach = coachDao.selectOne(new QueryWrapper<CoachEntity>().eq("user_id", user.getUserId()));
		GymCoachRelationEntity gymCoachRelationEntity = gymCoachRelationDao.selectOne(new QueryWrapper<GymCoachRelationEntity>().eq("coach_id", coach.getCoachId()).eq("gym_id", form.getGymId()));
		if(gymCoachRelationEntity==null){
			//该健身房下面无此教练，请先联系健身房
			throw new FitnessException(ExceptionEnum.COACH_NOT_IN_GYM);
		}else {
			user.setGymId(form.getGymId());
			GymWithCoachesDto gym = gymDao.selectGymWithCoaches(form.getGymId());
			if(gym.getPartnerId()!=null){
				user.setPartnerId(gym.getPartnerId());
			}
		}
		return user;
	}
}
