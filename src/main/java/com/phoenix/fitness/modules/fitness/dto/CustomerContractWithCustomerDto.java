package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CustomerContractEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import lombok.Data;

@Data
public class CustomerContractWithCustomerDto extends CustomerContractEntity {

    private CustomerEntity customer;

}
