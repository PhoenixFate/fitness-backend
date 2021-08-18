package com.phoenix.fitness.business.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.fitness.modules.fitness.dao.CustomerDao;
import com.phoenix.fitness.modules.fitness.dao.PhysiqueExamSyncingLogDao;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.PhysiqueExamSyncingLog;
import com.phoenix.fitness.modules.fitness.service.PhysiqueExamPostureService;
import com.phoenix.fitness.modules.fitness.dto.PostureRecord;
import com.phoenix.fitness.modules.pad.service.impl.youjiu.CompleteReport;
import com.phoenix.fitness.modules.pad.service.impl.youjiu.EntityWrapper;
import com.phoenix.fitness.modules.pad.service.impl.youjiu.Posture;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @author neil created at 2021/7/1 4:27 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class PhysiqueExamPostureServiceTest {

  @Resource
  private PhysiqueExamSyncingLogDao examSyncingLogDao;

  @Resource
  private PhysiqueExamPostureService physiqueExamPostureService;

  @Resource
  private CustomerDao customerDao;


  @Test
  public void testingExamSaveAndQuery() {
    List<PhysiqueExamSyncingLog> logs = examSyncingLogDao.selectList(
        new QueryWrapper<PhysiqueExamSyncingLog>().eq("processed", 0));

    for (PhysiqueExamSyncingLog log : logs) {
      EntityWrapper wrapper = JSON.parseObject(log.getContent(), EntityWrapper.class);
      CompleteReport report = JSON.parseObject(wrapper.getData().toString(), CompleteReport.class);
      if (report.getPosture() != null) {
        Integer randomCustomerId = new Random().nextInt();
        physiqueExamPostureService.processPostureReport(report, randomCustomerId);
        List<PostureRecord> postures = physiqueExamPostureService
            .findCustomerPostureReport(randomCustomerId.longValue(), null, null, 10);
        Assert.assertEquals(1, postures.size());
        Posture pos = postures.get(0);
        Assert.assertEquals(pos.getScore(), report.getPosture().getScore());
      }
    }
  }

  @Test
  @Rollback(false)
  public void fixedPreviousData() {
    List<PhysiqueExamSyncingLog> logs = examSyncingLogDao.selectList(
        new QueryWrapper<PhysiqueExamSyncingLog>().eq("processed", 0));

    for (PhysiqueExamSyncingLog log : logs) {
      EntityWrapper wrapper = JSON.parseObject(log.getContent(), EntityWrapper.class);
      CompleteReport report = JSON.parseObject(wrapper.getData().toString(), CompleteReport.class);
      String reportRelatedPhone = report.getMeasurement().getPhone();

      CustomerEntity matchingEntity = customerDao.selectOne
          (new QueryWrapper<CustomerEntity>().eq("mobile", reportRelatedPhone));
      if (matchingEntity == null) {
        continue;

      }
      if (report.getPosture() != null) {
        physiqueExamPostureService.processPostureReport(report, matchingEntity.getCustomerId().intValue());
      }
    }
  }
}
