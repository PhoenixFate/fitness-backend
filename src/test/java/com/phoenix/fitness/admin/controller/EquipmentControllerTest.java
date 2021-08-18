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
 * 设备controller层测试
 *
 * @author phoenix
 * @email @outlook.com
 * @date 2021-02-08 11:33:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class EquipmentControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 设备-列表
     *
     * @throws Exception 异常
     */
    @Test
    public void equipmentListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/equipment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 设备-详情
     *
     * @throws Exception 异常
     */
    @Test
    public void equipmentDetailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/equipment/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 设备-保存
     *
     * @throws Exception 异常
     */
    @Test
    public void equipmentSaveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sys/equipment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                         "    \"equipmentName\": \"测试设备\",\n" +
                         "    \"equipmentImageUrl\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/equipment/%E6%A4%AD%E5%9C%86%E6%9C%BA.jpg\"\n" +
                         "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 设备-修改
     *
     * @throws Exception 异常
     */
    @Test
    public void equipmentUpdateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/sys/equipment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                         "    \"equipmentId\": 9,\n" +
                         "    \"equipmentName\": \"椭圆机\",\n" +
                         "    \"equipmentImageUrl\": \"https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/equipment/%E6%A4%AD%E5%9C%86%E6%9C%BA.jpg\",\n" +
                         "    \"sort\": 50\n" +
                         "}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 设备-删除
     *
     * @throws Exception 异常
     */
    @Test
    public void equipmentDeleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sys/equipment/9"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}
