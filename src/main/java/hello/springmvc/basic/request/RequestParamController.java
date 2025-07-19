package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
//return "ok" 를 사용하고 싶은 경우, 그냥 써 버리면 ok라는 웹 리졸버를 찾게 됨..
//그래서 아래처럼 RestController 를 해 주거나!
//@RestController
//아니면 리퀘스트매핑 위에 @ResponseBody 를 적어 주면 된다
//이렇게 하면 ok 라는 문자 그대로 HTTP 응답 메시지에 넣어서 바로 반환
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            //v2에서처럼 ("username") 식으로 적은 걸 생략할 수 있지만,
            //생략하고자 한다면 변수명과 파라미터명이 같아야 함!
            @RequestParam String username,
            @RequestParam int age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //v3에서 더 발전...
    //다 없앨 수 있다!!
    /**
     * @RequestParam 사용
     * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    //대신! 요청 파라미터 이름과 같아야 함
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    } //request param조차 없으면 좀 헷갈릴 수도...
    //애노테이션 사용하는 걸 권장!!

    /**
     * [required]
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            //기본값이 true!! required = true 라는 것은 꼭 들어와야 하는 값이라는 것!
            //없으면 오류가 남
            //required = false 는 없어도 된다는 것
            @RequestParam(required = true) String username,
            //@RequestParam(required = false) int age 라고 할 경우 500 에러가 뜸
            //이유는!! 자바 문법 성격상 어떤 값이라도 들어가야 함!! 0이든 null이든...
            //근데 int에는 null 값을 적을 수가 없기 때문에
            //객체형인 Integer를 사용해 줘야 함!! 객체에는 null이 들어갈 수 있기 때문
            @RequestParam(required = false) Integer age) {

        //주의!!
        //null과 "" 는 다름! "" 를 빈 문자로 보는 것...
        //username= 만 해도 어쨌든 빈 문자라는 값이 들어왔기 때문에 통과가 됨
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * [default value]
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requrestParamDefault(
            //username 파라미터가 안 넘어온다면, 기본값을 guest로 하겠다는 의미
            @RequestParam(required = true, defaultValue = "guest") String username,
            //이렇게 default value를 설정해 줄 경우, int 사용 가능
            //값이 안 들어오면 자동으로 -1이 되기 때문!!
            @RequestParam(required = false, defaultValue = "-1") int age) {
        /**
         * 그래서 defaultValue 가 들어가면 required 가 필요 없음
         * 값이 있든 없든 디폴트 값이 들어가기 때문
         */
        
        //주의!!
        //빈 문자인 경우에도 설정한 기본 값이 적용됨
        //username이 아예 없어도 username=guest로 값이 들어감
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    //모든 요청 파라미터를 다 받고 싶을 때 Map을 사용하면 됨
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
        
        //참고로! 파라미터를 Map이나 MultiValueMap으로 조회할 수 있음
        /**
         *  @RequestParam Map
         * > Map(key=value)
         *  @RequestParam MultiValueMap
         * > MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
         * 예) ?userIds=id1&userIds=id2
         * 파라미터의 값이 1개가 확실하다면 Map 사용해도 가능!!
         * 그렇지 않다면 multi value map을 사용할 것 (근데 보통 하나만 쓰긴 함 ㅎ)
         */
    }

    @ResponseBody
    @RequestMapping("model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
