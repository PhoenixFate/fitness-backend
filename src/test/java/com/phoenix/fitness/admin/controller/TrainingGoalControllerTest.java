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
 * admin端
 * 训练目标controller层测试
 *
 * @author phoenix
 * @email @outlook.com
 * @date 2021-02-08 11:33:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class TrainingGoalControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 训练目标-列表
     *
     * @throws Exception 异常
     */
    @Test
    public void trainingGoalListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/trainingGoal"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 训练目标-详情
     *
     * @throws Exception 异常
     */
    @Test
    public void trainingGoalDetailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/trainingGoal/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 训练目标-保存
     *
     * @throws Exception 异常
     */
    @Test
    public void trainingGoalSaveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sys/trainingGoal")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"goalName\": \"测试\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 训练目标-修改
     *
     * @throws Exception 异常
     */
    @Test
    public void trainingGoalUpdateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/sys/trainingGoal")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{" +
                        "   \"trainingGoalId\": 2,\n" +
                        "   \"goalName\": \"增肌\"" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 训练目标-删除
     *
     * @throws Exception 异常
     */
    @Test
    public void trainingGoalDeleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sys/trainingGoal/2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}
