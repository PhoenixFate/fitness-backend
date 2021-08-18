package com.phoenix.fitness.modules.fitness.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.common.service.BaseServiceImpl;
import com.phoenix.fitness.modules.fitness.dao.PhysiqueExamPostureDao;
import com.phoenix.fitness.modules.fitness.dao.PhysiqueExamPostureImageDao;
import com.phoenix.fitness.modules.fitness.dao.PhysiqueExamPostureItemDao;
import com.phoenix.fitness.modules.fitness.entity.PhysiqueExamPostureEntity;
import com.phoenix.fitness.modules.fitness.entity.PhysiqueExamPostureImageEntity;
import com.phoenix.fitness.modules.fitness.entity.PhysiqueExamPostureItemEntity;
import com.phoenix.fitness.modules.fitness.service.PhysiqueExamPostureService;
import com.phoenix.fitness.modules.fitness.dto.PostureRecord;
import com.phoenix.fitness.modules.pad.service.impl.youjiu.CompleteReport;
import com.phoenix.fitness.modules.pad.service.impl.youjiu.Posture;
import com.phoenix.fitness.modules.pad.service.impl.youjiu.PostureDetailValue;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @author neil created at 2021/7/1 2:34 PM
 */
@Service
@Slf4j
public class PhysiqueExamPostureServiceImpl extends BaseServiceImpl<PhysiqueExamPostureDao,
    PhysiqueExamPostureEntity> implements PhysiqueExamPostureService {

  @Resource
  private PhysiqueExamPostureDao physiqueExamPostureDao;

  @Resource
  private PhysiqueExamPostureItemDao physiqueExamPostureItemDao;

  @Resource
  private PhysiqueExamPostureImageDao physiqueExamPostureImageDao;


  @Override
  public List<PostureRecord> findCustomerPostureReport(Long customerId, @Nullable Date startAt,
                                                       @Nullable Date endAt, @Nullable Integer latest) {
    return physiqueExamPostureDao.findCustomerPostureReport(customerId, startAt, endAt, latest);


  }

  @Override
  public PhysiqueExamPostureEntity processPostureReport(CompleteReport report, Integer customerId) {

    Posture posture = report.getPosture();

    PhysiqueExamPostureEntity savingEntity = new PhysiqueExamPostureEntity();
    //setup simple field
    savingEntity.setScore(posture.getScore());
    savingEntity.setExamTime(report.getMeasurement().getStartTime());
    savingEntity.setEvaluation(posture.getEvaluation());
    savingEntity.setCustomerId(customerId);
    fulfillAuditField(savingEntity);

    physiqueExamPostureDao.insert(savingEntity);

    assignPhysiqueExamPostureItemFields(posture, savingEntity);

    assignPhysiqueExamImageField(posture, savingEntity);

    physiqueExamPostureDao.updateById(savingEntity);

    return savingEntity;

  }

  private void assignPhysiqueExamImageField(Posture posture, PhysiqueExamPostureEntity savingEntity) {
    PhysiqueExamPostureImageEntity sourceImageEntity = new PhysiqueExamPostureImageEntity();
    BeanUtils.copyProperties(posture.getSourceImage(), sourceImageEntity);
    sourceImageEntity.setPostureExamId(savingEntity.getId());
    physiqueExamPostureImageDao.insert(sourceImageEntity);
    savingEntity.setSourceImageId(sourceImageEntity.getId());

    PhysiqueExamPostureImageEntity spineImageEntity = new PhysiqueExamPostureImageEntity();
    BeanUtils.copyProperties(posture.getSpineImage(), spineImageEntity);
    spineImageEntity.setPostureExamId(savingEntity.getId());
    physiqueExamPostureImageDao.insert(spineImageEntity);
    savingEntity.setSpineImageId(spineImageEntity.getId());

  }

  private void assignPhysiqueExamPostureItemFields(Posture posture, PhysiqueExamPostureEntity savingEntity) {
    //since postureDetailValue too much
    //handle each PostureDetailValue using reflect

    for (Field field : Posture.class.getDeclaredFields()) {
      if (field.getType().equals(PostureDetailValue.class)) {
        field.setAccessible(true);
        try {
          Object originValue = field.get(posture);
          PhysiqueExamPostureItemEntity entity = new PhysiqueExamPostureItemEntity();
          BeanUtils.copyProperties(originValue, entity);
          entity.setPostureExamId(savingEntity.getId());

          physiqueExamPostureItemDao.insert(entity);
          Field settingValue = PhysiqueExamPostureEntity.class.getDeclaredField(field.getName() + "Id");
          settingValue.setAccessible(true);
          settingValue.set(savingEntity, entity.getId());
        } catch (IllegalAccessException | NoSuchFieldException e) {
          log.error("error during assign object", e);
        }
      }
    }
  }
}
