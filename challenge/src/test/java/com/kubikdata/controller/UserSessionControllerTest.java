package com.kubikdata.controller;

import com.kubikdata.controller.request.UserSessionRequest;
import com.kubikdata.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserSessionControllerTest {

    @InjectMocks
    UserSessionController userSessionController;

    @Spy
    SessionRepository tokenToDate = new SessionRepository();

    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    public void postSessionTest() {
        UserSessionRequest userSessionRequest = new UserSessionRequest();
        userSessionRequest.setUsername("Pepe");

        ResponseEntity<String> response = userSessionController.addSession(userSessionRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String token = response.getBody();
        verify(tokenToDate).put(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), any());
        assertEquals(stringArgumentCaptor.getAllValues(), Arrays.asList("Pepe", token));
        assertEquals(10, token.length());
    }

    @Test
    public void postSessionNoDuplicateTest() {
        UserSessionRequest userSessionRequest = new UserSessionRequest();
        userSessionRequest.setUsername("Pepe");

        ResponseEntity<String> response = userSessionController.addSession(userSessionRequest);
        String token = response.getBody();

        response = userSessionController.addSession(userSessionRequest);
        String token2 = response.getBody();
        assertNotEquals(token, token2);
    }
}
