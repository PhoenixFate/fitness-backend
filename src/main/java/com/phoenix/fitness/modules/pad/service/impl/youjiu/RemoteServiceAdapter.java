package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.annotation.*;

/**
 * @author neil created at 2021/6/23 2:12 PM
 */
public interface RemoteServiceAdapter {

  String BASE_URL = "https://open.youjiuhealth.com/api/";

  /**
   * 从远端获得TOKEN
   *
   * @param appId
   * @param appSecret
   * @return
   */
  @Post(url = BASE_URL + "session",
      headers = {
          "Accept: application/vnd.XoneAPI.v3+json"
      })
  @LogEnabled(logResponseContent = true)
  AuthorizationReply authorization(@Query("app_id") String appId, @Query("app_secret") String appSecret);

  /**
   * 查询符合条件的 measurements
   *
   * @param query
   * @return
   */
  @Get(url = BASE_URL + "reports",
      headers = {
          "Accept: application/vnd.XoneAPI.v3+json",
          "Authorization: Bearer ${token}"
      })
  @LogEnabled(logResponseContent = true)
  ListWrapper<CompleteReport> queryReports(@Query ReportQuery query, @Var("token") String token);

  /**
   * 依据measurementID获取report
   *
   * @param measurementId
   * @param token
   * @return
   */
  @Get(url = BASE_URL + "reports/${id}",
      headers = {
          "Accept: application/vnd.XoneAPI.v3+json",
          "Authorization: Bearer ${token}"
      })
  @LogEnabled(logResponseContent = true)
  EntityWrapper<CompleteReport> getReportsDetailViaMeasurementId(@Var("id") String measurementId,
                                                                 @Var("token") String token);

  /**
   * Get mini program code
   *
   * @param measurementId
   * @param token
   * @return
   */
  @Post(url = BASE_URL + "/reports/${measurementId}/miniProgramCode",
      headers = {
          "Accept: application/vnd.XoneAPI.v3+json",
          "Authorization: Bearer ${token}"
      })
  JSONObject getMiniProgramCode(@Var("measurementId") String measurementId,
                                @Var("token") String token);
}
