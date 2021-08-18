package com.phoenix.fitness.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithClassInfoDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerVipClassCheckinDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerVipClassCheckinEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerPlanDayService;
import com.phoenix.fitness.modules.fitness.service.CustomerVipClassService;

import java.util.Date;
import java.util.List;

/**
 * @author neil created at 2021/6/21 6:24 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class CustomerVipClassTest {

  @Autowired
  private CustomerVipClassService customervipclass;


  @Autowired
  private CustomerPlanDayService customerPlanDayService;


  @Test
  public void customerCheckinTest() {
    CustomerVipClassCheckinEntity entity = new CustomerVipClassCheckinEntity();

    List<CustomerPlanDayWithClassInfoDto> customerPlanDayEntity =
        customerPlanDayService.customerAllDays(199L);
    CustomerPlanDayWithClassInfoDto classInfoDto = customerPlanDayEntity.stream()
        .filter(c -> c.getCustomerIsSure() == 0).findFirst().get();

    entity.setCustomerId(classInfoDto.getCustomerId());
    entity.setTrainerId(classInfoDto.getCoachId());
    entity.setCheckinTime(new Date());
    entity.setClassId(classInfoDto.getCustomerPlanDayId());


    entity = customervipclass.customerVipClassCheckin(entity);

    Assert.assertNotNull(entity.getId());

    Page<CustomerVipClassCheckinDto> records = customervipclass
        .getCustomerCheckinRecord("", null,
            null,null, PageRequest.of(0, 10));

    Assert.assertTrue(records.getSize() > 0);

    CustomerVipClassCheckinDto record = records.getRecords().get(0);
    Assert.assertEquals(record.getCustomer().getCustomerId(), classInfoDto.getCustomerId());
    //Assert.assertEquals(record.getClassId(), classInfoDto.getCustomerPlanDayId());
    Assert.assertNotNull(record.getCheckinTime());
  }


}

