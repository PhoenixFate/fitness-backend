package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;
import com.phoenix.fitness.modules.fitness.entity.PhysiqueExamPostureEntity;
import com.phoenix.fitness.modules.fitness.dto.PostureRecord;
import com.phoenix.fitness.modules.pad.service.impl.youjiu.CompleteReport;

import java.util.Date;
import java.util.List;

/**
 * @author neil created at 2021/7/1 2:29 PM
 */
public interface PhysiqueExamPostureService extends IService<PhysiqueExamPostureEntity> {

  /**
   * find user posture report
   *
   * @param customerId
   * @param startAt
   * @param endAt
   * @param latest
   * @return
   */
  List<PostureRecord> findCustomerPostureReport(Long customerId, @Nullable Date startAt,
                                                @Nullable Date endAt, @Nullable Integer latest);


  /**
   * process posture report from subscription
   *
   * @param report
   * @param customerId
   */
  PhysiqueExamPostureEntity processPostureReport(CompleteReport report, Integer customerId);

}
