package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 设备
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_equipment")
public class EquipmentEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long equipmentId;
    /**
     * 设备名称
     */
    @NotBlank(message = "设备名称不能为空")
    @Size(max = 40,message = "设备名称长度1-40")
    private String equipmentName;
    /**
     * 设备图片
     */
    @NotBlank(message = "设备图片不能为空")
    @Size(max = 256,message = "设备图片URL长度1-256")
    private String equipmentImageUrl;
    /**
     * 排序
     */
    private Integer sort;
}
