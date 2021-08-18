package com.phoenix.fitness.common.service;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.modules.fitness.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author neil created at 2021/6/22 9:22 AM
 */
@Slf4j
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {


  protected static void fulfillAuditField(BaseEntity entity) {

    for (Field field : entity.getClass().getDeclaredFields()) {
      if (field.isAnnotationPresent(TableId.class)) {
        try {
          //since it is private
          //first allow to access
          field.setAccessible(true);
          Object value = field.get(entity);
          if (value == null) {
            //before insert
            entity.setCreateTime(new Date());
            break;
          }
        } catch (IllegalAccessException e) {
          log.error("unable to access tableId in entity", entity.getClass().getName());
        }
      }
      entity.setUpdateTime(new Date());
    }
  }
}
