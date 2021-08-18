package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 销售人员
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-06-24 10:45:05
 */
@Data
@TableName("tb_salesman")
public class SalesmanEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 销售人员id
     */
    @TableId
    private Long salesmanId;
    /**
     * 姓名
     */
    private String salesmanName;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 出生年月
     */
    private Date birthday;
    /**
     * 手机号
     */
    private String mobile;

}
