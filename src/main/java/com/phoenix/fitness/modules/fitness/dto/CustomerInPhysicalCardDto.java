package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CustomerClockInEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerContractEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import lombok.Data;

import java.util.List;

@Data
public class CustomerInPhysicalCardDto {

    private CustomerEntity customer;

    private List<CustomerContractEntity> customerContractList;

    private CustomerClockInEntity customerClockIn;
}
