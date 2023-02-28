package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public String testService(){
        TodoEntity entity = TodoEntity.builder().title("todo item").build();
        repository.save(entity);
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();

    }

    public List<TodoEntity>  testService2(){
        TodoEntity entity = TodoEntity.builder().title("todo item2").build();
        repository.save(entity);
        List<TodoEntity> byUserIdQuery = repository.findByTitleQuery(entity.getTitle());
        return byUserIdQuery;
    }

    public List<TodoEntity> create(final TodoEntity entity){
        validate(entity);

        repository.save(entity);

        log.info("entity id: {} is saved.", entity.getUserId());
        return repository.findByUserId(entity.getUserId());
    }

    private static void validate(TodoEntity entity) {
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

    public List<TodoEntity> retrieve(final String userId){
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity){
        validate(entity);
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo ->{
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            repository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity){
        validate(entity);
        try {
            repository.delete(entity);
        } catch(Exception e){
            log.error("error", entity.getId(), e);
            throw new RuntimeException("error deleting entity"+entity.getId());
        }
        return retrieve(entity.getUserId());
    }
}
