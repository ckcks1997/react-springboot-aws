package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @PostMapping
    public ResponseEntity createTodo(@RequestBody TodoDTO dto){
        try {
            String tempUsrId = "temp-user";
            TodoEntity entity = TodoDTO.toEntity(dto);

            entity.setId(null);
            entity.setUserId(tempUsrId);

            List<TodoEntity> entities = service.create(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity retrieveTodoList(){
        String tempUsrId = "temp-user";
        List<TodoEntity> entities = service.retrieve(tempUsrId);
        List<TodoDTO> response = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity updateTodo(@RequestBody TodoDTO dto){
        String tempUserId = "temp-user";
        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(tempUserId);
        List<TodoEntity> entities = service.update(entity);
        List<TodoDTO> response = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);

    }

    @DeleteMapping
    public ResponseEntity deleteTodo(@RequestBody TodoDTO dto){ //id값(키)만 있으면 데이터를 지울 수 있다.
        try {
            String tempUserId = "temp-user";
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(tempUserId);
            List<TodoEntity> entities = service.delete(entity);
            List<TodoDTO> response = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
