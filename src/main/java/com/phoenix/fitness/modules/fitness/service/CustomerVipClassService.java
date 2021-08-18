package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import com.phoenix.fitness.modules.fitness.dto.CustomerVipClassCheckinDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerVipClassCheckinEntity;

import java.util.Date;

/**
 * @author neil created at 2021/6/21 9:21 AM
 */
public interface CustomerVipClassService extends IService<CustomerVipClassCheckinEntity> {

  /**
   * customer check in record
   *
   * @param entity entity to record
   * @return
   */
  CustomerVipClassCheckinEntity customerVipClassCheckin(CustomerVipClassCheckinEntity entity);

  /**
   * record query via customer id or coachId
   *
   * @param name
   * @param coachId
   * @return
   */
  Page<CustomerVipClassCheckinDto> getCustomerCheckinRecord(@Nullable String name,
                                                            @Nullable Long coachId,
                                                            @Nullable Date startAt,
                                                            @Nullable Date endAt,
                                                            PageRequest pageRequest);
}
