package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.ToDoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<ToDoEntity> toDoEntities = todoService.testService2();

        return ResponseEntity.ok().body(toDoEntities.toArray());
    }

    @PostMapping("test3")
    public ResponseEntity createTodo(@RequestBody TodoDTO dto){
        try {
            String tempUsrId = "temp-user";
            ToDoEntity entity = TodoDTO.toDoEntity(dto);

            entity.setId(null);
            entity.setUserId(tempUsrId);

            List<ToDoEntity> entities = todoService.create(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

}
