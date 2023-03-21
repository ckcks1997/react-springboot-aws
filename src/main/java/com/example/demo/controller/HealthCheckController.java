package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
public class HealthCheckController {

    @GetMapping("/")
    public String healthCheck() throws JsonProcessingException {

        //jackson databind 테스트
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userDTO = mapper.readValue("{\"username\":\"Bob\", \"password\":13}", UserDTO.class);
        HashMap<String, String> hm = new HashMap<>();
        hm.put("one", "1");

        String jsonStr = mapper.writeValueAsString(hm);
        log.info("Val: {}", jsonStr );
        
        return "service is running..";
    }
}
