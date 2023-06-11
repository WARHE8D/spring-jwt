package com.warhead.security.service;

import com.warhead.security.requestbody.AuthResponse;
import com.warhead.security.requestbody.AuthenticationReqBody;
import com.warhead.security.requestbody.RegisterReqBody;


public interface AuthService {
    AuthResponse register(RegisterReqBody request);

    AuthResponse authenticate(AuthenticationReqBody request);
}
