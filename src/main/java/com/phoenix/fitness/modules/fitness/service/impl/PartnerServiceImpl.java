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
import com.phoenix.fitness.modules.fitness.dao.GymDao;
import com.phoenix.fitness.modules.fitness.dao.PartnerDao;
import com.phoenix.fitness.modules.fitness.dto.PartnerWithGymsDto;
import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import com.phoenix.fitness.modules.fitness.entity.PartnerEntity;
import com.phoenix.fitness.modules.fitness.service.GymService;
import com.phoenix.fitness.modules.fitness.service.PartnerService;
import java.util.List;
import java.util.Map;

@Service("partnerService")
@AllArgsConstructor
public class PartnerServiceImpl extends ServiceImpl<PartnerDao, PartnerEntity> implements PartnerService {

    private final GymService gymService;

    private final PartnerDao partnerDao;

    private final GymDao gymDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PartnerEntity> partnerEntityQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(params.get("partnerName"))) {
            String partnerName = (String) params.get("partnerName");
            partnerEntityQueryWrapper.like("partner_name", partnerName);
        }
        partnerEntityQueryWrapper.eq("delete_flag", 0).orderByDesc("create_time");
        IPage<PartnerEntity> page = this.page(
                new Query<PartnerEntity>().getPage(params),
                partnerEntityQueryWrapper
        );
        List<PartnerEntity> partnerEntityList = page.getRecords();
        for(PartnerEntity partner:partnerEntityList){
            List<GymEntity> gyms = gymDao.selectList(new QueryWrapper<GymEntity>().eq("partner_id", partner.getPartnerId()).eq("delete_flag",0));
            if(CollectionUtils.isEmpty(gyms)){
                partner.setGymCount(0);
            }else {
                partner.setGymCount(gyms.size());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public PartnerWithGymsDto getDetail(Long id) {
        PartnerWithGymsDto partner= partnerDao.getPartnerWithGyms(id);
        if(partner==null || partner.getDeleteFlag().equals(1)){
            //根据id未查询到内容或者已经删除
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return partner;
    }

    @Override
    @Transactional
    public Boolean save(PartnerWithGymsDto partner) {
        int count = partnerDao.insert(partner);
        if(count==0){
            //新增失败
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        this.updateGym(partner);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateById(PartnerWithGymsDto partner) {
        PartnerEntity temp = partnerDao.selectById(partner.getPartnerId());
        if(temp==null || temp.getDeleteFlag().equals(1)){
            //要修改的内容不存在或者已经被删除
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = partnerDao.updateById(partner);
        if(count==0){
            //修改失败
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        this.updateGym(partner);
        return true;
    }

    public void updateGym(PartnerWithGymsDto partner){
        //先解除该partner下面所有健身房的关联
        List<GymEntity> gymList = gymDao.selectList(new QueryWrapper<GymEntity>().eq("partner_id", partner.getPartnerId()));
        if(!CollectionUtils.isEmpty(gymList)){
            for(GymEntity gym:gymList){
                gym.setPartnerId(null);
            }
            gymService.saveOrUpdateBatch(gymList);
        }
        //再重新关联partner与gym
        if(!CollectionUtils.isEmpty(partner.getGyms())){
            for(GymEntity gym:partner.getGyms()){
                if(gym.getGymId()==null){
                    throw new FitnessException(ExceptionEnum.GYM_NOT_FOUND);
                }
                GymEntity tempGym = gymDao.selectById(gym.getGymId());
                if(tempGym==null){
                    throw new FitnessException(ExceptionEnum.GYM_NOT_FOUND);
                }
                tempGym.setPartnerId(partner.getPartnerId());
                gymDao.updateById(tempGym);
            }
        }
    }


    @Override
    public Boolean removeByDeleteFlag(Long id) {
        PartnerEntity temp = partnerDao.selectById(id);
        if(temp==null || temp.getDeleteFlag().equals(1)){
            //要删除的内容不存在或者已经被删除
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = partnerDao.deleteByDeleteFlag(id);
        if(count==0){
            //删除失败
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        return true;
    }

}