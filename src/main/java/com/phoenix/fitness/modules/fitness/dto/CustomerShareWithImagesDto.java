package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CustomerShareEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerShareWithImagesDto extends CustomerShareEntity {
    /**
     * 分享的照片
     */
    private List<String> customerShareImages=new ArrayList<>();
}
