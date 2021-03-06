package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.constant.VipCardBigTypeEnum;
import com.phoenix.fitness.common.constant.VipCardTypeEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.VipCardDao;
import com.phoenix.fitness.modules.fitness.entity.VipCardEntity;
import com.phoenix.fitness.modules.fitness.service.VipCardService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("vipCardService")
@AllArgsConstructor
public class VipCardServiceImpl extends ServiceImpl<VipCardDao, VipCardEntity> implements VipCardService {

    private final VipCardDao vipCardDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<VipCardEntity> vipCardEntityQueryWrapper = new QueryWrapper<>();
        String vipCardName = (String) params.get("vipCardName");
        if (!StringUtils.isEmpty(vipCardName)) {
            vipCardEntityQueryWrapper.and(wrapper -> wrapper.like("vip_card_name", vipCardName));
        }
        vipCardEntityQueryWrapper.eq("delete_flag", 0).orderByDesc("create_time");

        IPage<VipCardEntity> page = this.page(
                new Query<VipCardEntity>().getPage(params),
                vipCardEntityQueryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public VipCardEntity getById(Serializable id) {
        VipCardEntity vipCard = vipCardDao.selectById(id);
        if (vipCard == null || vipCard.getDeleteFlag().equals(1)) {
            //??????id?????????????????????????????????
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return vipCard;
    }

    @Override
    public boolean save(VipCardEntity vipCard) {
        //?????????????????????
        // this.checkVipCardType(vipCard);
        if (vipCard.getSort() == null) {
            //????????????????????????+10
            List<VipCardEntity> vipCardEntityList = vipCardDao.selectList(new QueryWrapper<VipCardEntity>().orderByDesc("sort"));
            if (!CollectionUtils.isEmpty(vipCardEntityList)) {
                if (vipCardEntityList.get(0).getSort() != null) {
                    vipCard.setSort(vipCardEntityList.get(0).getSort() + 10);
                } else {
                    vipCard.setSort(10);
                }
            }
        }
        if (vipCard.getVipCardBigType().equals(VipCardBigTypeEnum.DURATION_CARD.getCardTypeName())) {
            //?????????
            if (vipCard.getAddVipDaysFemale() == null) {
                //???????????????????????????????????????????????????????????????????????????
                vipCard.setAddVipDaysFemale(vipCard.getAddVipDays());
            }
        }
        int count = vipCardDao.insert(vipCard);
        if (count == 0) {
            //????????????
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        return true;
    }

    @Override
    public boolean updateById(VipCardEntity vipCard) {
        VipCardEntity temp = vipCardDao.selectById(vipCard.getVipCardId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //???????????????????????????
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        //?????????????????????
        // this.checkVipCardType(vipCard);
        int count = vipCardDao.updateById(vipCard);
        if (count == 0) {
            //????????????
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        return true;
    }

    /**
     * ?????????????????????
     * ???????????????????????????6???????????????????????????
     *
     * @param vipCard ?????????
     */
    public void checkVipCardType(VipCardEntity vipCard) {
        List<String> vipCardTypeList = new ArrayList<>();
        vipCardTypeList.add(VipCardTypeEnum.WEEK_CARD.getStatusName());
        vipCardTypeList.add(VipCardTypeEnum.MONTH_CARD.getStatusName());
        vipCardTypeList.add(VipCardTypeEnum.SEASON_CARD.getStatusName());
        vipCardTypeList.add(VipCardTypeEnum.HALF_YEAR_CARD.getStatusName());
        vipCardTypeList.add(VipCardTypeEnum.THREE_SEASON_CARD.getStatusName());
        vipCardTypeList.add(VipCardTypeEnum.YEAR_CARD.getStatusName());
        boolean flag = false;
        for (String vipCardType : vipCardTypeList) {
            if (vipCardType.equals(vipCard.getVipCardType())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            //?????????????????????6?????????
            throw new FitnessException(ExceptionEnum.VIP_CARD_TYPE_ERROR);
        }
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        VipCardEntity temp = vipCardDao.selectById(id);
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //???????????????????????????
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = vipCardDao.deleteByDeleteFlag(id);
        if (count == 0) {
            //????????????
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        return true;
    }

}