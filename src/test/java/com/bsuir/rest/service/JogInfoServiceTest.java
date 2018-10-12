package com.bsuir.rest.service;

import com.bsuir.rest.entity.JogInfoEntity;
import com.bsuir.rest.entity.UserEntity;
import com.bsuir.rest.exception.NotFoundException;
import com.bsuir.rest.model.JogInfoForm;
import com.bsuir.rest.model.Role;
import com.bsuir.rest.repository.JogInfoRepository;
import com.bsuir.rest.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JogInfoServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    JogInfoRepository jogInfoRepository;

    @InjectMocks
    JogInfoServiceImpl jogInfoServiceImpl;

    @Test(expected = NotFoundException.class)
    public void findAllByUserIdWhereInvalidUserId() {
        Long invalidUserId = 0L;

        when(userRepository.findOneById(invalidUserId)).thenReturn(null);

        jogInfoServiceImpl.findAllByUserId(invalidUserId);
    }

    @Test
    public void findAllByUserIdWhereValidUserId() {
        Long validUserId = 1L;
        UserEntity expectedUserEntity = UserEntity.builder()
                                    .username("Username")
                                    .hashPassword("1234567890")
                                    .role(Role.valueOf("USER"))
                                    .build();

        JogInfoEntity expectedJogInfo = JogInfoEntity.builder()
                                    .distance(10.0)
                                    .time(LocalTime.parse("04:30:20"))
                                    .date(LocalDate.parse("2018-06-03"))
                                    .build();

        when(userRepository.findOneById(validUserId))
                                .thenReturn(expectedUserEntity);
        when(jogInfoRepository.findAllByUserEntityId(validUserId))
                                .thenReturn(Collections.singletonList(expectedJogInfo));

        assertEquals(Collections.singletonList(JogInfoForm.from(expectedJogInfo)), jogInfoServiceImpl.findAllByUserId(validUserId));
    }

    @Test(expected = NotFoundException.class)
    public void createJogInfoWhereInvalidUserId() {
        Long invalidUserId = 0L;

        when(userRepository.findOneById(invalidUserId)).thenReturn(null);

        jogInfoServiceImpl.findAllByUserId(invalidUserId);
    }

    @Test
    public void createJogInfoWhereValidUserId() {
        Long validUserId = 1L;
        UserEntity expectedUserEntity = UserEntity.builder()
                .username("Username")
                .hashPassword("1234567890")
                .role(Role.valueOf("USER"))
                .build();

        JogInfoEntity expectedJogInfoEntity = JogInfoEntity.builder()
                .userEntity(expectedUserEntity)
                .distance(10.0)
                .time(LocalTime.parse("04:30:20"))
                .date(LocalDate.parse("2018-06-03"))
                .build();

        JogInfoForm jogInfoForm = new JogInfoForm(10.0, "04:30:20", "2018-06-03");

        when(userRepository.findOneById(validUserId)).thenReturn(expectedUserEntity);
        when(jogInfoRepository.save(expectedJogInfoEntity)).thenReturn(expectedJogInfoEntity);

        jogInfoServiceImpl.createJogInfo(jogInfoForm, validUserId);

        verify(userRepository, times(1)).findOneById(validUserId);
        verify(jogInfoRepository, times(1)).save(expectedJogInfoEntity);
    }

    @Test(expected = NotFoundException.class)
    public void updateJogInfoWhereInvalidUserId() {
        Long invalidUserId = 0L;
        Long validJogInfoId = 1L;
        JogInfoForm jogInfoForm = new JogInfoForm(10.0, "04:30:20", "2018-06-03");

        when(jogInfoRepository.findOneByIdAndUserEntityId(validJogInfoId, invalidUserId)).thenReturn(null);

        jogInfoServiceImpl.updateJogInfo(jogInfoForm, validJogInfoId, invalidUserId);
    }

    @Test(expected = NotFoundException.class)
    public void updateJogInfoWhereInvalidJogInfoId() {
        Long validUserId = 1L;
        Long invalidJogInfoId= 0L;
        JogInfoForm jogInfoForm = new JogInfoForm(10.0, "04:30:20", "2018-06-03");

        when(jogInfoRepository.findOneByIdAndUserEntityId(invalidJogInfoId, validUserId)).thenReturn(null);

        jogInfoServiceImpl.updateJogInfo(jogInfoForm, invalidJogInfoId, validUserId);
    }

    @Test
    public void updateJogInfoWhereValidUserAndJogId() {
        Long validUserId = 1L;
        Long validJogInfoId = 1L;

        JogInfoEntity expectedJogInfoEntity = JogInfoEntity.builder()
                .distance(10.0)
                .time(LocalTime.parse("04:30:20"))
                .date(LocalDate.parse("2018-06-03"))
                .build();

        JogInfoForm jogInfoForm = new JogInfoForm(10.0, "04:30:20", "2018-06-03");

        when(jogInfoRepository.findOneByIdAndUserEntityId(validJogInfoId, validUserId)).thenReturn(expectedJogInfoEntity);
        when(jogInfoRepository.save(expectedJogInfoEntity)).thenReturn(expectedJogInfoEntity);

        jogInfoServiceImpl.updateJogInfo(jogInfoForm, validUserId, validJogInfoId);

        verify(jogInfoRepository, times(1)).findOneByIdAndUserEntityId(validUserId, validUserId);
        verify(jogInfoRepository, times(1)).save(expectedJogInfoEntity);
    }

    @Test(expected = NotFoundException.class)
    public void deleteJogInfoWhereInvalidUserId() {
        Long invalidUserId = 0L;
        Long validJogInfoId = 1L;

        when(jogInfoRepository.findOneByIdAndUserEntityId(validJogInfoId, invalidUserId)).thenReturn(null);

        jogInfoServiceImpl.deleteJogInfo(validJogInfoId, invalidUserId);
    }

    @Test(expected = NotFoundException.class)
    public void deleteJogInfoWhereInvalidJogInfoId() {
        Long validUserId = 1L;
        Long invalidJogInfoId = 0L;

        when(jogInfoRepository.findOneByIdAndUserEntityId(invalidJogInfoId, validUserId)).thenReturn(null);

        jogInfoServiceImpl.deleteJogInfo(invalidJogInfoId, validUserId);
    }

    @Test
    public void deleteJogInfoWhereValidUserAndJogId() {
        Long validUserId = 1L;
        Long validJogInfoId = 1L;

        JogInfoEntity expectedJogInfoEntity = JogInfoEntity.builder()
                .distance(10.0)
                .time(LocalTime.parse("04:30:20"))
                .date(LocalDate.parse("2018-06-03"))
                .build();

        when(jogInfoRepository.findOneByIdAndUserEntityId(validJogInfoId, validUserId)).thenReturn(expectedJogInfoEntity);

        jogInfoServiceImpl.deleteJogInfo(validUserId, validJogInfoId);

        verify(jogInfoRepository, times(1)).deleteOneById(validJogInfoId);
    }
}
