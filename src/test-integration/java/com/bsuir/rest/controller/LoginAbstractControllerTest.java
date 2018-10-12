package com.bsuir.rest.controller;

import com.bsuir.rest.model.LoginForm;
import com.bsuir.rest.transfer.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class LoginAbstractControllerTest extends AbstractControllerTestUtility {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void loginControllerTestWhereStatusIsOk() throws Exception {

        LoginForm loginForm = new LoginForm(USERNAME_VALID, PASSWORD);

        Gson gson = new Gson();
        String jsonLoginForm = gson.toJson(loginForm);

        MvcResult response =  mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonLoginForm))
                .andExpect(status().isOk())
                .andReturn();

        TokenDto tokenDto = objectMapper.readValue(response.getResponse().getContentAsByteArray(), TokenDto.class);

        Assert.assertNotNull(tokenDto.getValue());
    }

    @Test
    public void loginControllerTestWhereStatusIsNotFound() throws Exception {

        LoginForm loginForm = new LoginForm(USERNAME_INVALID, PASSWORD);

        Gson gson = new Gson();
        String jsonLoginForm = gson.toJson(loginForm);

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonLoginForm))
                .andExpect(status().isNotFound());
    }
}
