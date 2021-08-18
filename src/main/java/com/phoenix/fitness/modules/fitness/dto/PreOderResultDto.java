package com.phoenix.fitness.modules.fitness.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class PreOderResultDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String qrCodeImageUrl;

    private String orderNumber;
}
