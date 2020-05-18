package com.kubikdata.controller;

import com.kubikdata.controller.request.UserDataRequest;
import com.kubikdata.controller.response.UserResponse;
import com.kubikdata.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * this class is used to return data info based on its session token,
 * choose one of the endpoints to return data info
 */
@RestController
public class UserDataController {

    @Autowired
    private SessionRepository tokenToDate;

    // This endpoint should be a post to avoid showing the session token
    @PostMapping(value = "/info")
    @CrossOrigin
    public ResponseEntity<UserResponse> userInfoGet(@RequestBody UserDataRequest userDataRequest) {
        return tokenToDate.getDate(userDataRequest.getUsername(), userDataRequest.getToken())
                .map(d -> new UserResponse(userDataRequest.getUsername(), userDataRequest.getToken(), d))
                .map(b -> new ResponseEntity<>(b, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }
}
