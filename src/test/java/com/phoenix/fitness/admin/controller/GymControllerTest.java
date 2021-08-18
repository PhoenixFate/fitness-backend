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
 * 健身房controller层测试
 *
 * @author phoenix
 * @email @outlook.com
 * @date 2021-02-08 11:33:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class GymControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 健身房-列表
     *
     * @throws Exception 异常
     */
    @Test
    public void  gymListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/gym"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 健身房-详情
     *
     * @throws Exception 异常
     */
    @Test
    public void  gymDetailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/gym/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 健身房-保存
     *
     * @throws Exception 异常
     */
    @Test
    public void  gymSaveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sys/gym")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"gymName\": \"测试健身房899\",\n" +
                        "    \"gymImageUrl\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/gym/20210302/bf7585327be347ca9a5dec322284398d.jpg\",\n" +
                        "    \"city\": \"南京\",\n" +
                        "    \"address\": \"测试地址888\",\n" +
                        "    \"partner\": {\n" +
                        "        \"partnerId\": 1,\n" +
                        "        \"partnerName\": \"南京宁纽信息科技有限公司\",\n" +
                        "        \"bossName\": \"胡源\",\n" +
                        "        \"bossMobile\": \"12323424232\"\n" +
                        "    },\n" +
                        "    \"coaches\": [\n" +
                        "        {\n" +
                        "            \"id\": 1,\n" +
                        "            \"coachName\": \"胡教练\",\n" +
                        "            \"mobile\": \"18751801512\",\n" +
                        "            \"username\": \"coachHu\",\n" +
                        "            \"gender\": 1,\n" +
                        "            \"email\": null,\n" +
                        "            \"identityCard\": null,\n" +
                        "            \"birthday\": \"1989-03-03\",\n" +
                        "            \"avatar\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/coach/20201112/36b6f186b8334cf690a45a46ae065d27.jpg\",\n" +
                        "            \"perClassPrice\": null\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 4,\n" +
                        "            \"coachName\": \"张教练\",\n" +
                        "            \"mobile\": \"18751801519\",\n" +
                        "            \"username\": \"coachZhang\",\n" +
                        "            \"gender\": 1,\n" +
                        "            \"email\": \"234234@163.com\",\n" +
                        "            \"identityCard\": \"234131231212312\",\n" +
                        "            \"birthday\": \"2000-01-01\",\n" +
                        "            \"avatar\": \"\",\n" +
                        "            \"perClassPrice\": null\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 健身房-修改
     *
     * @throws Exception 异常
     */
    @Test
    public void  gymUpdateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/sys/gym")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "        \"gymId\": 1,\n" +
                        "        \"gymName\": \"宁纽金鹰健身房\",\n" +
                        "        \"gymImageUrl\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/gym/20210302/bf7585327be347ca9a5dec322284398d.jpg\",\n" +
                        "        \"city\": \"南京\",\n" +
                        "        \"address\": \"金鹰11楼\",\n" +
                        "        \"partner\": {\n" +
                        "            \"partnerId\": 1,\n" +
                        "            \"partnerName\": \"南京宁纽信息科技有限公司\",\n" +
                        "            \"bossName\": \"胡源\",\n" +
                        "            \"bossMobile\": \"12323424232\"\n" +
                        "        },\n" +
                        "        \"coaches\": [\n" +
                        "            {\n" +
                        "                \"id\": 1,\n" +
                        "                \"coachName\": \"胡教练\",\n" +
                        "                \"mobile\": \"18751801512\",\n" +
                        "                \"username\": \"coachHu\",\n" +
                        "                \"gender\": 1,\n" +
                        "                \"email\": null,\n" +
                        "                \"identityCard\": null,\n" +
                        "                \"birthday\": \"1989-03-03\",\n" +
                        "                \"avatar\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/coach/20201112/36b6f186b8334cf690a45a46ae065d27.jpg\",\n" +
                        "                \"perClassPrice\": \"300\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\": 4,\n" +
                        "                \"coachName\": \"张教练\",\n" +
                        "                \"mobile\": \"18751801519\",\n" +
                        "                \"username\": \"coachZhang\",\n" +
                        "                \"gender\": 1,\n" +
                        "                \"email\": \"234234@163.com\",\n" +
                        "                \"identityCard\": \"234131231212312\",\n" +
                        "                \"birthday\": \"2000-01-01\",\n" +
                        "                \"avatar\": \"\",\n" +
                        "                \"perClassPrice\": null\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 健身房-删除
     *
     * @throws Exception 异常
     */
    @Test
    public void  gymDeleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sys/gym/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}
