package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/23 6:44 PM
 */
@Data
public class AuthorizationReply {

  private String accessToken;

  private String tokenType;

  private Integer expiresIn;

}
