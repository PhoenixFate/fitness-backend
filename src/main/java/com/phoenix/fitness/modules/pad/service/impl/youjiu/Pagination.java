package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/24 9:09 AM
 */
@Data
public class Pagination {

  private Long total;

  private Integer count;

  private Integer perPage;

  private Integer currentPage;

  private Integer totalPage;

  private String links;
}
