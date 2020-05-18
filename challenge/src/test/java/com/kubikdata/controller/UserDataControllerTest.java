package com.kubikdata.controller;

import com.kubikdata.controller.request.UserDataRequest;
import com.kubikdata.controller.response.UserResponse;
import com.kubikdata.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class UserDataControllerTest {

    @InjectMocks
    UserDataController userDataController;

    @Spy
    SessionRepository tokenToDate = new SessionRepository();

    @Test
    public void getDateTest() {
        UserDataRequest request = new UserDataRequest();
        request.setUsername("pepe");
        request.setToken("token");
        ResponseEntity<UserResponse> response = userDataController.userInfoGet(request);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Date date = new Date();
        tokenToDate.put("pepe", "token", date);

        response = userDataController.userInfoGet(request);
        assertEquals("pepe", response.getBody().getUsername());
        assertEquals("token", response.getBody().getToken());
        assertEquals(date, response.getBody().getDate());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
