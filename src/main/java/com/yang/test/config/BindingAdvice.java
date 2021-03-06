package com.yang.test.config;

import com.yang.test.domain_model.CommonDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

// @Component : 컨트롤러 다음에 뜬다
// @Configuration : 설정할 때 쓰인다

// Aspect는 함수를 찾은 뒤에 실행되어야 하기에
// 컨트롤러가 메모리에 뜬 뒤에 수행해야하기에
// Component로 명시해준다.

@Component
@Aspect
public class BindingAdvice {


    // 로그를 남길 때!
    private static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);


    // 어떤함수가 언제 몇번 실행됐는지 횟수같은거 로그 남기기
    @Before("execution(* com.yang.test.web_service..*Controller.*(..))")
    public void testCheck() {
        //request 값 처리 못하나요?
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        System.out.println("주소 : " + request.getRequestURI());

        System.out.println("전처리 로그를 남겼습니다.");

    }

    @After("execution(* com.yang.test.web_service..*Controller.*(..))")
    public void testCheck2() {

        System.out.println("후처리 로그를 남겼습니다.");

    }


    // 함수 : 앞 뒤
    // 함수 : 앞 (username이 안들어왔으면 내가 강제로 넣어주고 실행)
    // 함수 : 뒤 (응답만 관리)

    //@Before
    //@After
    @Around("execution(* com.yang.test.web_service..*Controller.*(..))")
    public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();

        System.out.println("type : " + type);
        System.out.println("method : " + method);

        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                // 서비스 : 정상적인 화면 -> 사용자요청
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {






                        errorMap.put(error.getField(), error.getDefaultMessage());
                        // 로그 레벨
                        // error(제일 치명적)
                        // warn
                        // info(일반적)
                        // debug


                        // info 설정 시 info 이상의 등급만 로그가 발생한다!
                        // 해당 등급 이하의 로그는 뜨질 않는다.
                        log.warn(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
                        log.debug(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
                       // Sentry.captureMessage(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());

                        //DB연결 -> DB남기기
                        //File file = new File();






                    }

                    return new CommonDto<>(HttpStatus.BAD_REQUEST.value(), errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed(); // 함수의 스택을 실행해라
    }
}


