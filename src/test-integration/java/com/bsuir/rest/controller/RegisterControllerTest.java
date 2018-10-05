package com.bsuir.rest.controller;

import com.bsuir.rest.application.Application;
import com.bsuir.rest.model.RegisterForm;
import com.bsuir.rest.repository.UserRepository;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class RegisterControllerTest {

    private final String USERNAME = "TestUser";
    private final String PASSWORD = "Password";
    private final String ROLE_USER = "USER";
    private final String ROLE_ADMIN = "ADMIN";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void registerControllerTestWhereStatusIsOk() throws Exception {

        RegisterForm registerForm = new RegisterForm(USERNAME, PASSWORD, ROLE_USER);

        Gson gson = new Gson();
        String jsonRegisterForm = gson.toJson(registerForm);

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRegisterForm))
                .andExpect(status().isOk());

        Assert.assertNotNull(userRepository.findOneByUsername(USERNAME));

        userRepository.deleteOneByUsername(USERNAME);
    }

    @Test
    public void registerControllerTestWhereStatusIsBadRequest() throws Exception {

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerControllerTestWhereStatusIsForbidden() throws Exception {

        RegisterForm registerForm = new RegisterForm(USERNAME, PASSWORD, ROLE_ADMIN);

        Gson gson = new Gson();
        String jsonRegisterForm = gson.toJson(registerForm);

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRegisterForm))
                .andExpect(status().isForbidden());
    }
}
