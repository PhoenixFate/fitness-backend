package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;
import com.phoenix.fitness.modules.pad.service.SubscribeBodyTestData;

/**
 * @author neil created at 2021/6/24 9:17 AM
 */
@Data
public class EntityWrapper<T> extends SubscribeBodyTestData {

  private T data;
}
