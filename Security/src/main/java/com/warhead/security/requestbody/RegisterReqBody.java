package com.warhead.security.requestbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReqBody {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
}
