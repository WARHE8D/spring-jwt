package com.warhead.security.democontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/demo-controller")
@RequiredArgsConstructor
public class Demo {
    @GetMapping()
    public ResponseEntity<String> helo(){
        return ResponseEntity.ok("just because you're secured, it doesn't mean that like i like you b-baka");
    }
}
