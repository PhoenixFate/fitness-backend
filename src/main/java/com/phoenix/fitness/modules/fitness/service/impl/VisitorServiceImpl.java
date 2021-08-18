package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.DateUtils;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.PeriodUtil;
import com.phoenix.fitness.modules.admin.form.VisitorSearchForm;
import com.phoenix.fitness.modules.fitness.dao.VisitorDao;
import com.phoenix.fitness.modules.fitness.entity.VisitorEntity;
import com.phoenix.fitness.modules.fitness.service.VisitorService;

import java.text.ParseException;
import java.util.Date;


@Service("visitorService")
@AllArgsConstructor
public class VisitorServiceImpl extends ServiceImpl<VisitorDao, VisitorEntity> implements VisitorService {

    private final VisitorDao visitorDao;

    @Override
    public PageUtils queryPage(VisitorSearchForm visitorSearchForm) throws ParseException {
        if (visitorSearchForm != null && visitorSearchForm.getEndDate() != null) {
            visitorSearchForm.setEndDate(DateUtils.getEndOfOneDay(visitorSearchForm.getEndDate()));
        }
        Page<VisitorEntity> page = new Page<>(visitorSearchForm.getPage(), visitorSearchForm.getLimit());
        IPage<VisitorEntity> visitorPage = visitorDao.selectVisitorList(page, visitorSearchForm);
        return new PageUtils(visitorPage);
    }

    @Override
    public void removeByDeleteFlag(Long id) {
        VisitorEntity visitorEntity = visitorDao.selectById(id);
        if (visitorEntity == null) {
            throw new FitnessException(ExceptionEnum.VISITOR_NOT_FOUND);
        }
        visitorDao.deleteById(id);
    }

    @Override
    public boolean save(VisitorEntity visitorEntity) {
        visitorEntity.setAddDate(new Date());
        visitorEntity.setPeriod(PeriodUtil.getPeriod(visitorEntity.getVisitTime()));
        int count = visitorDao.insert(visitorEntity);
        return true;
    }

    @Override
    public boolean updateById(VisitorEntity visitorEntity) {
        visitorEntity.setPeriod(PeriodUtil.getPeriod(visitorEntity.getVisitTime()));
        visitorDao.updateById(visitorEntity);
        return true;
    }

}