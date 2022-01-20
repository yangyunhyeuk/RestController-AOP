package com.yang.test.web_service;

import com.yang.test.domain_model.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserRepository userRepository;

    // DI = 의존성 주입
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // http://localhost:8080/user
    @GetMapping("/user")
    public CommonDto<List<User>> findAll() {
        System.out.println("findAll()");
        return new CommonDto<>(HttpStatus.OK.value(), userRepository.findAll()); // MessageConverter (JavaObject -> Json String)
    }

    // http://localhost:8080/user/2
    @GetMapping("/user/{id}")
    public CommonDto<User> findById(@PathVariable int id) {
        System.out.println("findById() : id : " + id);
        return new CommonDto<>(HttpStatus.OK.value(), userRepository.findId(id));
    }

    @CrossOrigin
    // http://localhost:8080/user
    @PostMapping("/user")
    // x-www-form-urlencoded => request.getParamter()
    // text/plain => @RequestBody 어노테이션
    // application/json => @ResponseBody 어노테이션 + 오브젝트로 받기
    public CommonDto<?> save(@Valid @RequestBody JoinReqDto dto, BindingResult bindingResult) {

        System.out.println("save()");
        System.out.println("user : " + dto);
        userRepository.save(dto);

//		System.out.println("data : "+data);
//		System.out.println("username : "+username);
//		System.out.println("password : "+password);
//		System.out.println("phone : "+phone);

        return new CommonDto<>(HttpStatus.CREATED.value(), "ok");
    }

    // http://localhost:8080/user/2
    @DeleteMapping("/user/{id}")
    public CommonDto delete(@PathVariable int id) {
        System.out.println("delete()");
        userRepository.delete(id);
        return new CommonDto<>(HttpStatus.OK.value());
    }

    // http://localhost:8080/user/2
    @PutMapping("/user/{id}")
    public CommonDto update(@PathVariable int id, @Valid @RequestBody UpdateReqDto dto, BindingResult bindingResult) {

        System.out.println("update()");
        userRepository.update(id, dto);
        return new CommonDto<>(HttpStatus.OK.value());
    }
}