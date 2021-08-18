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
 * 合作伙伴controller层测试
 *
 * @author phoenix
 * @email @outlook.com
 * @date 2021-02-08 11:33:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class PartnerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 合作伙伴-列表
     *
     * @throws Exception 异常
     */
    @Test
    public void  partnerListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/partner"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 合作伙伴-详情
     *
     * @throws Exception 异常
     */
    @Test
    public void  partnerDetailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/partner/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 合作伙伴-保存
     *
     * @throws Exception 异常
     */
    @Test
    public void  partnerSaveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sys/partner")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"partnerName\": \"测试公司44\",\n" +
                        "    \"bossName\": \"测试\",\n" +
                        "    \"bossMobile\": \"12323424232\",\n" +
                        "    \"gyms\":[\n" +
                        "        {\n" +
                        "            \"gymId\": 3,\n" +
                        "            \"gymName\":\"测试健身房1\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 合作伙伴-修改
     *
     * @throws Exception 异常
     */
    @Test
    public void  partnerUpdateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/sys/partner")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"partnerId\": 2,\n" +
                        "    \"partnerName\": \"测试公司55\",\n" +
                        "    \"bossName\": \"测试\",\n" +
                        "    \"bossMobile\": \"12323424232\",\n" +
                        "    \"gyms\": [\n" +
                        "        {\n" +
                        "            \"gymId\": 3,\n" +
                        "            \"gymName\": \"测试健身房1\",\n" +
                        "            \"gymImageUrl\": null,\n" +
                        "            \"city\": null,\n" +
                        "            \"address\": null,\n" +
                        "            \"partnerId\": 7\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 合作伙伴-删除
     *
     * @throws Exception 异常
     */
    @Test
    public void  partnerDeleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sys/partner/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}
