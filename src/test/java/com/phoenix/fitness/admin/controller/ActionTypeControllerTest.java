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
 * 动作分类controller层测试
 *
 * @author phoenix
 * @email @outlook.com
 * @date 2021-02-08 11:33:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class ActionTypeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 动作分类-列表
     *
     * @throws Exception 异常
     */
    @Test
    public void actionTypeListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/actionType"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 动作分类-详情
     *
     * @throws Exception 异常
     */
    @Test
    public void actionTypeDetailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/actionType/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 动作分类-保存
     *
     * @throws Exception 异常
     */
    @Test
    public void actionTypeSaveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sys/actionType")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"actionTypeName\":\"测试\",\n" +
                        "    \"actionTypeImageUrl\":\"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/actionType/body.png\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 动作分类-修改
     *
     * @throws Exception 异常
     */
    @Test
    public void actionTypeUpdateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/sys/actionType")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"actionTypeId\": 3,\n" +
                        "    \"actionTypeName\": \"胸部\",\n" +
                        "    \"actionTypeImageUrl\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/actionType/chest.png\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 动作分类-删除
     *
     * @throws Exception 异常
     */
    @Test
    public void actionTypeDeleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sys/actionType/2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}
