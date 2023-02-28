package com.example.demo.service;

import com.example.demo.model.ToDoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public String testService(){
        ToDoEntity entity = ToDoEntity.builder().title("todo item").build();
        repository.save(entity);
        ToDoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();

    }

    public List<ToDoEntity>  testService2(){
        ToDoEntity entity = ToDoEntity.builder().title("todo item2").build();
        repository.save(entity);
        List<ToDoEntity> byUserIdQuery = repository.findByTitleQuery(entity.getTitle());
        return byUserIdQuery;
    }

    public List<ToDoEntity> create(final ToDoEntity entity){
        validate(entity);

        repository.save(entity);

        log.info("entity id: {} is saved.", entity.getUserId());
        return repository.findByUserId(entity.getUserId());
    }

    private static void validate(ToDoEntity entity) {
        //validations
        if(entity == null){
            log.warn("entity can't be null");
            throw new RuntimeException("entity is null");
        }
        if(entity.getUserId() == null){
            log.warn("unknown user");
            throw new RuntimeException("unknown user");
        }
    }

    public List<ToDoEntity> retrieve(final String userId){
        return repository.findByUserId(userId);
    }

}
