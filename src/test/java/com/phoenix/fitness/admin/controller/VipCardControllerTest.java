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
 * 会员卡controller层测试
 *
 * @author phoenix
 * @email @outlook.com
 * @date 2021-02-08 11:33:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class VipCardControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 会员卡-列表
     *
     * @throws Exception 异常
     */
    @Test
    public void vipCardListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/vipCard"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 会员卡-详情
     *
     * @throws Exception 异常
     */
    @Test
    public void vipCardDetailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/vipCard/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 会员卡-保存
     *
     * @throws Exception 异常
     */
    @Test
    public void vipCardSaveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sys/vipCard")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"vipCardType\": \"MONTH_CARD\",\n" +
                        "    \"vipCardName\": \"测试卡\",\n" +
                        "    \"price\": \"100\",\n" +
                        "    \"favorablePrice\": \"100\",\n" +
                        "    \"addVipDays\": 40\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 会员卡-修改
     *
     * @throws Exception 异常
     */
    @Test
    public void vipCardUpdateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/sys/vipCard")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"vipCardId\": 2,\n" +
                        "    \"vipCardType\": \"MONTH_CARD\",\n" +
                        "    \"vipCardName\": \"月卡\",\n" +
                        "    \"vipCardImage\": null,\n" +
                        "    \"price\": \"100\",\n" +
                        "    \"favorablePrice\": \"100\",\n" +
                        "    \"addVipDays\": 30" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 会员卡-删除
     *
     * @throws Exception 异常
     */
    @Test
    public void vipCardDeleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sys/vipCard/2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}
