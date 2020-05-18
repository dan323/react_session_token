package com.kubikdata.repository;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In Memory data store for the challenge application
 */
public class SessionRepository {

    private Map<String, Map<String, Date>> data;

    public SessionRepository() {
        data = new ConcurrentHashMap<>();
    }

    /**
     * Associate the date to the username and token
     * @param username name of user
     * @param token session token
     * @param date creation date
     */
    public void put(String username, String token, Date date) {
        if (data.get(username) != null) {
            data.get(username).put(token, date);
        } else {
            Map<String, Date> init = new ConcurrentHashMap<>();
            init.put(token, date);
            data.put(username, init);
        }
    }

    /**
     * obtain a date for a given user and token
     * @param username name of the user
     * @param token session token
     * @return date at which the token was created
     */
    public Optional<Date> getDate(String username, String token) {
        if (data.get(username) != null) {
            return Optional.ofNullable(data.get(username).get(token));
        }
        return Optional.empty();
    }

}
