package com.danosoftware.servicemocks.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebAppConfiguration
//@ContextConfiguration(classes = {RecommendationConfig.class, RecommendationController.class})
//@ActiveProfiles("local")
public class RecommendationControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private MockRestServiceServer mockRestServiceServer;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockRestServiceServer = MockRestServiceServer.createServer(new RestTemplate());
    }


//    @Test
//    public void shouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(get("/api/movies/recommendations")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello World")));
//    }

    @Test
    public void shouldReturnMovie() throws Exception {

        mockRestServiceServer.expect(requestTo("http://localhost:9090/api/movies-service/recommend"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("resultSuccess", MediaType.TEXT_PLAIN));

        mockMvc.perform(get("/api/movies/recommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Star Wars")));

        mockRestServiceServer.verify();
    }
}
