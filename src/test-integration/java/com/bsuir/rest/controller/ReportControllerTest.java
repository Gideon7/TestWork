package com.bsuir.rest.controller;

import com.bsuir.rest.application.Application;
import com.bsuir.rest.entity.JogInfoEntity;
import com.bsuir.rest.entity.TokenEntity;
import com.bsuir.rest.entity.UserEntity;
import com.bsuir.rest.model.ReportForm;
import com.bsuir.rest.model.Role;
import com.bsuir.rest.repository.JogInfoRepository;
import com.bsuir.rest.repository.TokenRepository;
import com.bsuir.rest.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ReportControllerTest {

    private final String USERNAME = "TestUser";
    private final String PASSWORD = "Password";
    private final String ROLE = "USER";
    private final String TOKEN_VALUE = "0123456789";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JogInfoRepository jogInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void init() {

        UserEntity userEntity = UserEntity.builder()
                .username(USERNAME)
                .hashPassword(passwordEncoder.encode(PASSWORD))
                .role(Role.valueOf(ROLE))
                .build();

        userRepository.save(userEntity);

        tokenRepository.save(TokenEntity.builder()
                .userEntity(userRepository.findOneByUsername(USERNAME))
                .value(TOKEN_VALUE)
                .build());

        List<JogInfoEntity> jogInfoEntityList = new ArrayList<>();

        jogInfoEntityList.add(new JogInfoEntity(4.0, LocalTime.parse("00:10:10"), LocalDate.parse("2018-06-04"), userEntity));
        jogInfoEntityList.add(new JogInfoEntity(6.0, LocalTime.parse("00:12:45"), LocalDate.parse("2019-06-05"), userEntity));
        jogInfoEntityList.add(new JogInfoEntity(15.6, LocalTime.parse("01:12:45"), LocalDate.parse("2018-06-05"), userEntity));
        jogInfoEntityList.add(new JogInfoEntity(10.3, LocalTime.parse("00:32:45"), LocalDate.parse("2018-06-10"), userEntity));
        jogInfoEntityList.add(new JogInfoEntity(7.8, LocalTime.parse("00:08:12"), LocalDate.parse("2018-06-11"), userEntity));
        jogInfoEntityList.add(new JogInfoEntity(7.0, LocalTime.parse("00:09:10"), LocalDate.parse("2018-06-12"), userEntity));
        jogInfoEntityList.add(new JogInfoEntity(8.4, LocalTime.parse("00:10:45"), LocalDate.parse("2018-06-13"), userEntity));
        jogInfoEntityList.add(new JogInfoEntity(6.6, LocalTime.parse("00:07:15"), LocalDate.parse("2018-06-14"), userEntity));
        jogInfoEntityList.add(new JogInfoEntity(3.2, LocalTime.parse("00:03:32"), LocalDate.parse("2019-06-06"), userEntity));
        jogInfoEntityList.add(new JogInfoEntity(3.2, LocalTime.parse("00:03:32"), LocalDate.parse("2020-08-03"), userEntity));

        jogInfoRepository.save(jogInfoEntityList);
    }

    @After
    public void deleteTestRecords() {

        Long userId = userRepository.findOneByUsername(USERNAME).getId();

        tokenRepository.deleteOneByUserEntityId(userId);
        jogInfoRepository.deleteAllByUserEntityId(userId);
        userRepository.delete(userId);
    }

    @Test
    public void reportTestWhereStatusIsOk() throws Exception {

        MvcResult response = mvc.perform(get("/report")
                .param("userId", userRepository.findOneByUsername(USERNAME).getId().toString())
                .header("tokenValue", TOKEN_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        List<ReportForm> reportFormList = objectMapper.readValue(response.getResponse().getContentAsByteArray(),
                new TypeReference<List<ReportForm>>() {});

        Assertions.assertThat(reportFormList).containsExactly(
                new ReportForm(0, 23, "2018-06-04", "2018-06-10",
                        BigDecimal.valueOf(4.308), "00:38:33", BigDecimal.valueOf(29.9)),
                new ReportForm(1, 24, "2018-06-11", "2018-06-17",
                        BigDecimal.valueOf(14.043), "00:08:50", BigDecimal.valueOf(29.8)),
                new ReportForm(2, 23, "2019-06-03", "2019-06-09",
                        BigDecimal.valueOf(9.417), "00:08:08", BigDecimal.valueOf(9.2)),
                new ReportForm(3, 32, "2020-08-03", "2020-08-09",
                        BigDecimal.valueOf(15.094), "00:03:32", BigDecimal.valueOf(3.2)));
    }

    @Test
    public void reportTestWhereStatusIsForbidden() throws Exception {

        mvc.perform(get("/report")
                .param("userId", "1"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void reportTestWhereStatusIsBadRequest() throws Exception {

        mvc.perform(get("/report")
                .param("userId", "0"))
                .andExpect(status().isForbidden());
    }
}
