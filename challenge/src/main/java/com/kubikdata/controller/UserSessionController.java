package com.kubikdata.controller;

import com.kubikdata.controller.request.UserSessionRequest;
import com.kubikdata.repository.SessionRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserSessionController {

    @Autowired
    SessionRepository tokenToDate;

    /**
     * this endpoint is needed to add a sesssion id to a specific username
     *
     * @param userSessionRequest
     * @return
     */
    @PostMapping(value = "/session")
    @CrossOrigin
    public ResponseEntity<String> addSession(@RequestBody UserSessionRequest userSessionRequest) {
        String token = generateToken();
        tokenToDate.put(userSessionRequest.getUsername(), token, new Date());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * @return a random string of 10 alphanumeric characters
     */
    private String generateToken() {
        return RandomStringUtils.randomAlphanumeric(10);
    }


}
