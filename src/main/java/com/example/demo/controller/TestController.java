package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public String testController(){
        return "hello";
    }

    @GetMapping("/{id}")
    public String withPathVariables(@PathVariable(required = true) int id){
        return "hello2" + id;
    }

    @GetMapping("/testRequestBody")
    public String testRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO){
        return "hello3" + testRequestBodyDTO.getId() + ", msg: "+testRequestBodyDTO.getMessage();
    }

    @GetMapping("test")
    public ResponseEntity<?> testService(){
        String str = todoService.testService();
        List<String> li = new ArrayList<>();
        li.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(li).build();
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("test2")
    public ResponseEntity testService2(){
        List<TodoEntity> toDoEntities = todoService.testService2();

        return ResponseEntity.ok().body(toDoEntities.toArray());
    }


}
