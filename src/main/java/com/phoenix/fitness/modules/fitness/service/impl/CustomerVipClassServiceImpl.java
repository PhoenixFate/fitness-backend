package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.common.service.BaseServiceImpl;
import com.phoenix.fitness.modules.fitness.dao.CustomerVipClassCheckinDao;
import com.phoenix.fitness.modules.fitness.dto.CustomerVipClassCheckinDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerVipClassCheckinEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerVipClassService;

import java.util.Date;
import java.util.List;

/**
 * @author neil created at 2021/6/21 9:30 AM
 */
@Service
@AllArgsConstructor
public class CustomerVipClassServiceImpl extends BaseServiceImpl<CustomerVipClassCheckinDao,
    CustomerVipClassCheckinEntity> implements CustomerVipClassService {

  private final CustomerVipClassCheckinDao customerVipClassCheckinDao;

  @Override
  public CustomerVipClassCheckinEntity customerVipClassCheckin(CustomerVipClassCheckinEntity entity) {
    fulfillAuditField(entity);
    int result = customerVipClassCheckinDao.insert(entity);

    if (result > 0) {
      return entity;
    }
    return null;
  }

  @Override
  public Page<CustomerVipClassCheckinDto> getCustomerCheckinRecord(String name, Long coachId,
                                                                   Date startAt, Date endAt,
                                                                   PageRequest pageRequest) {

    List<CustomerVipClassCheckinDto> result = customerVipClassCheckinDao
        .selectCheckinRecords(name, coachId, startAt, endAt,
            pageRequest.getPageSize(), pageRequest.getOffset());

    Long count = customerVipClassCheckinDao.selectCheckinRecordsCount(name, coachId, startAt, endAt);
    Page<CustomerVipClassCheckinDto> pageData = new Page<>(pageRequest.getPageNumber(),
        pageRequest.getPageSize(), count);
    pageData.setRecords(result);
    return pageData;
  }

}
