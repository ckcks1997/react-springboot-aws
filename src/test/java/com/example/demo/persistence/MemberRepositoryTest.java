package com.example.demo.persistence;

import com.example.demo.model.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memRepo;

    @Test
    @Rollback(value = false)
    public void memberInsert(){
        MemberEntity mem1 = MemberEntity.builder().userName("test").build();
        memRepo.save(mem1);

    }



}