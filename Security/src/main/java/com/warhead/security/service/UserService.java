package com.warhead.security.service;

import com.warhead.security.model.User;
public interface UserService {
    User getUserByUsername(String userName);
}
