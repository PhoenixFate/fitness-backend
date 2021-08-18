package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.SalesmanDao;
import com.phoenix.fitness.modules.fitness.entity.SalesmanEntity;
import com.phoenix.fitness.modules.fitness.service.SalesmanService;

import java.util.Map;

@Service("salesmanService")
@AllArgsConstructor
public class SalesmanServiceImpl extends ServiceImpl<SalesmanDao, SalesmanEntity> implements SalesmanService {

    private final SalesmanDao salesmanDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SalesmanEntity> salesmanEntityQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(params.get("salesmanName"))) {
            String salesmanName = (String) params.get("salesmanName");
            salesmanEntityQueryWrapper.like("salesman_name", salesmanName);
        }
        salesmanEntityQueryWrapper.eq("delete_flag", 0).orderByDesc("create_time");
        IPage<SalesmanEntity> page = this.page(
                new Query<SalesmanEntity>().getPage(params),
                salesmanEntityQueryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void removeByDeleteFlag(Long id) {
        salesmanDao.deleteById(id);
    }

}