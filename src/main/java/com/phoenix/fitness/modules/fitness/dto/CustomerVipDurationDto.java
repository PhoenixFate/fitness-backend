package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerVipDurationEntity;
import lombok.Data;

import java.util.List;

@Data
public class CustomerVipDurationDto {

    private CustomerEntity customer;

    private List<CustomerVipDurationEntity> customerVipDurations;

}
