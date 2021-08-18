package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

import java.util.List;

/**
 * @author neil created at 2021/6/23 7:18 PM
 */
@Data
public class ListWrapper<T> {

  private List<T> data;

  private Meta meta;
}
