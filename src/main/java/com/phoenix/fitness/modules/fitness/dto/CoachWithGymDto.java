package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import lombok.Data;

import java.util.List;

@Data
public class CoachWithGymDto extends CoachEntity {

    List<GymEntity> gyms;
}
