package com.yang.test.domain_model;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        users.add(new User(1,"one","1234","010-1111-1111"));
        users.add(new User(2,"two","1234","010-1111-1111"));
        users.add(new User(3,"three","1234","010-1111-1111"));

        return users;
    }

    public User findId(int id){
        return new User(1, "yang", "1234", "010-5555-5555");
    }

    public void save(JoinReqDto dto){
        System.out.println("DB에 INSERT하기");
    }

    public void delete(int id){
        System.out.println("DB에 삭제하기");
    }

    public void update(int id, UpdateReqDto dto){
        // DAO 연결하다가 예외가 발생한 상황을 가정
        throw  new IllegalArgumentException("Args 잘못 넣음");
        // System.out.println("DB 업데이트하기");
    }

}
