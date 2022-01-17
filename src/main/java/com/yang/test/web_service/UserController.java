package com.yang.test.web_service;

import com.yang.test.domain_model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@RequiredArgsConstructor // 요구되는 변수가 있으면 초기화된 생성자를 만들어준다. (final)
@RestController
public class UserController {

    //private final UserRepository userRepository;
    // final : 런타임 시점이 아닌 컴파일 시점에 초기화되어 있어야 한다.
    // 한 번 만들어지면 변경될 수 없다.

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // http://localhost:8080/user
    @GetMapping("/user")
    public CommonDto<List<User>> findAll() {
        // System.out.println("findAll");
        return new CommonDto<>(HttpStatus.OK.value(), userRepository.findAll());
        // ViewResolver가 아닌 MessageConverter 동작
        // (자바오브젝트를 -> 제이슨 문자열로 변환하여 응답답
    }

    // http://localhost:8080/user/{id}
    // @PathVariable : 주소로 들어오는 값 스트링을 int로 바꿔 넣어주는 어노테이션
    @GetMapping("/user/{id}")
    public CommonDto<User> findId(@PathVariable int id) {
        return new CommonDto<>(HttpStatus.OK.value(), userRepository.findId(id));
    }

    // text/plain => @RequestBody 어노테이션
    // application/json => @RequestBody 어노테이션 + 오브젝트
    @PostMapping("/user")
    // x-www-form-urlencoded 데이터 유형을 요청할 시에
    // == 오브젝트값이 아니라 인자에 일반 필드가 적혀있는 경우,
    // request.getParameter()로 동작하여 매칭되어
    // 다음의 인자에 넣어준다.
    public CommonDto<String> save(@RequestBody JoinReqDto dto) {

        System.out.println("user : " + dto);
        userRepository.save(dto);

        return new CommonDto<String>(HttpStatus.CREATED.value(), "OK");

    }

    @DeleteMapping("/user/{id}")
    public CommonDto delete(@PathVariable int id) {
        System.out.println("delete");
        userRepository.delete(id);
        return new CommonDto<>(HttpStatus.OK.value());
    }

    @PutMapping("/user/{id}")
    public CommonDto update(@PathVariable int id, @RequestBody UpdateReqDto dto) {

        System.out.println("update");
        System.out.println(dto);

        userRepository.update(id, dto);
        return new CommonDto<>(HttpStatus.OK.value());
    }

}
