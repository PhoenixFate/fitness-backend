package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.CoachDao;
import com.phoenix.fitness.modules.fitness.dao.GymCoachRelationDao;
import com.phoenix.fitness.modules.fitness.dao.GymDao;
import com.phoenix.fitness.modules.fitness.dao.PartnerDao;
import com.phoenix.fitness.modules.fitness.dto.GymWithCoachesDto;
import com.phoenix.fitness.modules.fitness.dto.GymWithPartnerDto;
import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import com.phoenix.fitness.modules.fitness.entity.GymCoachRelationEntity;
import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import com.phoenix.fitness.modules.fitness.entity.PartnerEntity;
import com.phoenix.fitness.modules.fitness.service.GymCoachRelationService;
import com.phoenix.fitness.modules.fitness.service.GymService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("gymService")
@AllArgsConstructor
public class GymServiceImpl extends ServiceImpl<GymDao, GymEntity> implements GymService {

    private final GymDao gymDao;

    private final PartnerDao partnerDao;

    private final GymCoachRelationDao gymCoachRelationDao;

    private final GymCoachRelationService gymCoachRelationService;

    private final CoachDao coachDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String gymName = "";
        if (!StringUtils.isEmpty(params.get("gymName"))) {
            gymName = (String) params.get("gymName");
        }
        IPage<GymWithPartnerDto> pageParams = new Query<GymWithPartnerDto>().getPage(params);
        IPage<GymWithPartnerDto> Page = gymDao.selectGymWithPartner(pageParams, gymName);
        return new PageUtils(Page);
    }

    @Override
    public GymWithCoachesDto getDetail(Long id) {
        GymWithCoachesDto gym = gymDao.selectGymWithCoaches(id);
        if(gym==null || gym.getDeleteFlag().equals(1)){
            //根据id未查询到内容或者已经删除
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return gym;
    }

    @Override
    @Transactional
    public Boolean save(GymWithCoachesDto gym) {
        this.checkPartner(gym);
        int count = gymDao.insert(gym);
        if(count==0){
            //新增失败
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        this.saveOrUpdateBase(gym);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateById(GymWithCoachesDto gym) {
        GymEntity temp = gymDao.selectById(gym.getGymId());
        if(temp==null || temp.getDeleteFlag().equals(1)){
            //更新的内容不存在
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        this.checkPartner(gym);
        int count = gymDao.updateById(gym);
        if(count==0){
            //更新失败
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        this.saveOrUpdateBase(gym);
        return true;
    }

    public void checkPartner(GymWithCoachesDto gym) {
        if(gym.getPartner()!=null){
            //检查partner是否存在
            PartnerEntity partner = partnerDao.selectById(gym.getPartner().getPartnerId());
            if(partner==null || partner.getDeleteFlag().equals(1)){
                throw new FitnessException(ExceptionEnum.PARTNER_NOT_FOUND);
            }else {
                //重新修改健身房-合作伙伴关联关系
                gym.setPartnerId(gym.getPartner().getPartnerId());
            }
        }else {
            //将健身房关联的partner 解除关联
            gym.setPartnerId(null);
        }
    }

    public void saveOrUpdateBase(GymWithCoachesDto gym){
        //先删除该健身房下面所有的教练的关联关系
        gymCoachRelationDao.delete(new QueryWrapper<GymCoachRelationEntity>().eq("gym_id",gym.getGymId()));
        //重新添加该健身房下面所有的教练的关联关系
        if(!CollectionUtils.isEmpty(gym.getCoaches())){
            List<GymCoachRelationEntity> gymCoachRelationEntityList=new ArrayList<>();
            for(CoachEntity coach:gym.getCoaches()){
                //检查教练是否存在
                CoachEntity tempCoach = coachDao.selectById(coach.getCoachId());
                if(tempCoach==null){
                    throw new FitnessException(ExceptionEnum.COACH_NOT_FOUND);
                }
                GymCoachRelationEntity coachRelationEntity=new GymCoachRelationEntity();
                coachRelationEntity.setCoachId(coach.getCoachId());
                coachRelationEntity.setGymId(gym.getGymId());
                gymCoachRelationEntityList.add(coachRelationEntity);
            }
            gymCoachRelationService.saveBatch(gymCoachRelationEntityList);
        }
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        GymEntity temp = gymDao.selectById(id);
        if(temp==null || temp.getDeleteFlag().equals(1)){
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = gymDao.deleteByDeleteFlag(id);
        if(count==0){
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        return true;
    }
}