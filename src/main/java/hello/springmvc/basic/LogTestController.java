package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//restcontroller
//REST API의 약자
//스트링이 그대로 반환!!
//HTTP 메시지 바디에 해당 데이터를 넣어버림
@RestController
public class LogTestController {

    //Logger : slf4j로 사용할 것
//    private final Logger log = LoggerFactory.getLogger(getClass()); //클래스 지정
    //위의 코드를 일일이 작성하기 번거로움 ㅡㅡ!!
    //롬복이 제공하는 애노테이션 @Slf4j 사용하면 쉽다!

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name); //얘는 무조건 출력
        //운영 서버에 로그 다 남기면... 안 됨... 지저분해짐

        //로그 찍을 때 레벨을 정할 수 있음 (어떤 상태인지)
        log.trace(" trace log={}", name);
        log.debug(" debug log={}", name); //디버그할 때 (개발 서버에서) 보는 로그

        //이 코드 하나 찍어 줬는데 굉장히 많은 정보를 볼 수 있음!
        //컨트롤러 이름이나 시간, 메시지 등
        log.info(" info log ={}", name); //중요한 정보의 로그 (비즈니스 등)

        log.warn(" warn log={}", name); //경고 로그
        log.error(" error log={}", name); //에러 로그

        //실행해 보면 info, warn, error만 뜸!
        //로컬에서 개발하는데 모든 로그가 보고 싶다면 aaplication.properties 들어가서 코드 추가
        //trace 말고 debug로 설정하면, debug/info/warn/error만 나옴
        //로그 레벨 설정하는 것!! trace > debug > info > warn > error (단계별로 높아짐)
        //개발 서버는 debug 출력, 운영 서버는 info 출력되게끔 하는 것

        //운영 시스템에는 중요한 로그만 남겨야 함!!
        //각 서버마다 다르게 로그 레벨 설정하면 됨

        return "ok";
    }
}

/**
 * @RestController
 * > @Controller는 반환 값이 String이면 뷰 이름으로 인식됨! 그래서 뷰를 찾고 뷰가 렌더링됨
 * > @RestController는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력함
 * > 따라서 실행 결과로 ok 메시지를 받을 수 있다!
 */