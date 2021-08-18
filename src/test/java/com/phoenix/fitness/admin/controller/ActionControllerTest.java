package com.phoenix.fitness.admin.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * pad端
 * 动作controller层测试
 *
 * @author phoenix
 * @email @outlook.com
 * @date 2021-02-08 11:33:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class ActionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 动作-列表
     *
     * @throws Exception 异常
     */
    @Test
    public void actionListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/action"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 动作-详情
     *
     * @throws Exception 异常
     */
    @Test
    public void actionDetailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/action/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 动作-保存
     *
     * @throws Exception 异常
     */
    @Test
    public void actionSaveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sys/action")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"actionName\": \"测试123\",\n" +
                        "    \"actionDescription\": \"单腿训练对于发展身体的平衡能力是非常重要的工具，在过去，单脚训练动作只是作为传统双腿训练的辅助或增补的工具，而现在一些专家正开始视单腿训练为主要的训练。下面看看单腿杠铃深蹲可以有效锻炼股四头肌。\",\n" +
                        "    \"containWeight\": 1,\n" +
                        "    \"actionType\": {\n" +
                        "        \"actionTypeId\": 3,\n" +
                        "        \"actionTypeName\": \"胸部\",\n" +
                        "        \"actionTypeImageUrl\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/actionType/chest.png\"\n" +
                        "    },\n" +
                        "    \"actionExamples\": [\n" +
                        "        \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/action/%E5%8D%95%E8%85%BF%E6%9D%A0%E9%93%83%E6%B7%B1%E8%B9%B21.jpg\",\n" +
                        "        \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/action/%E5%8D%95%E8%85%BF%E6%9D%A0%E9%93%83%E6%B7%B1%E8%B9%B22.jpg\"\n" +
                        "    ],\n" +
                        "    \"equipments\": [\n" +
                        "        {\n" +
                        "            \"equipmentId\": 12,\n" +
                        "            \"equipmentName\": \"综合推拉训练器\",\n" +
                        "            \"equipmentImageUrl\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/equipment/%E7%BB%BC%E5%90%88%E6%8E%A8%E6%8B%89%E8%AE%AD%E7%BB%83%E5%99%A8.jpg\",\n" +
                        "            \"sort\": 65\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 动作-修改
     *
     * @throws Exception 异常
     */
    @Test
    public void actionUpdateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/sys/action")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"actionId\": 76,\n" +
                        "    \"actionName\": \"测试9999\",\n" +
                        "    \"actionDescription\": \"单腿训练对于发展身体的平衡能力是非常重要的工具，在过去，单脚训练动作只是作为传统双腿训练的辅助或增补的工具，而现在一些专家正开始视单腿训练为主要的训练。下面看看单腿杠铃深蹲可以有效锻炼股四头肌。\",\n" +
                        "    \"containWeight\": 1,\n" +
                        "    \"actionType\": {\n" +
                        "        \"actionTypeId\": 3,\n" +
                        "        \"actionTypeName\": \"胸部\",\n" +
                        "        \"actionTypeImageUrl\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/actionType/chest.png\"\n" +
                        "    },\n" +
                        "    \"actionExamples\": [\n" +
                        "        \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/action/%E5%8D%95%E8%85%BF%E6%9D%A0%E9%93%83%E6%B7%B1%E8%B9%B21.jpg\",\n" +
                        "        \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/action/%E5%8D%95%E8%85%BF%E6%9D%A0%E9%93%83%E6%B7%B1%E8%B9%B22.jpg\"\n" +
                        "    ],\n" +
                        "    \"equipments\": [\n" +
                        "        {\n" +
                        "            \"equipmentId\": 12,\n" +
                        "            \"equipmentName\": \"综合推拉训练器\",\n" +
                        "            \"equipmentImageUrl\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/equipment/%E7%BB%BC%E5%90%88%E6%8E%A8%E6%8B%89%E8%AE%AD%E7%BB%83%E5%99%A8.jpg\",\n" +
                        "            \"sort\": 65\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 动作-删除
     *
     * @throws Exception 异常
     */
    @Test
    public void actionDeleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sys/action/9"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}
