package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 训练项
 * 1训练项目 : n动作组
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_exercise")
public class ExerciseEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long exerciseId;
	/**
	 * 训练项名称
	 */
	@NotBlank(message = "训练项名称不能为空")
	@Size(max = 40,message = "训练项名称长度1-40")
	private String exerciseName;
}
