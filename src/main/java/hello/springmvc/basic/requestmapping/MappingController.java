package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

//요청 매핑
@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    //@RequestMapping
    //배열도 제공되므로 다중 설정 가능 {"/hello-basic", "/hello-go"}
    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET)
    public String helloBasic() {
        log.info("basic");
        return "ok";
    }

    /**
     * method 특정 HTTP 메서드 요청만 허용
     * GET, HEAD, POST, PUT, PATCH, DELETE
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "mapping v1 ok";
    }

    /**
     * 편리한 축약 애노테이션 (코드보기)
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "mapping v2 ok";
    }

    /**
     * [경로 변수]
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable String userId
     * url 자체에 값이 들어가 있는 것
     */
    @GetMapping("/mapping/{userId}")
    //pathVariable의 이름과 파라미터 이름이 같으면 생략 가능
    //String data > String userId라면, @PathVariable String name으로 해도 됨
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return "pathVariable ok";
    }

    //PathVariable 다중 사용
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    //userA(userID)가 주문한 주문번호(orderId) 100번을 달라!
    // /mapping/users/userA/orders/100 이렇게 포스트맨으로 확인해 보면 로그 찍힘
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "pathVariable multi ok";
    }

    /**
     * [특정 파라미터 조건 매핑]
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode" 모드가 없어야 한다-도 가능
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    //파라미터에 mode=debug라는 게 있어야 호출되는 것! 없으면 호출 안 됨
    //특정 파라미터가 있으면 호출이 된다!
    //url 경로뿐만 아니라 파라미터 정보까지 추가로 매핑한 것 (거의 사용 안 함)
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * [특정 헤더 조건 매핑]
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    //얘는 header에 키/벨류 값이 있어야 함
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * [미디어 타입 조건 매핑 - HTTP 요청 Content-Type, consume]
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    //@PostMapping(value = "/mapping-consume", consumes = "application/json")
    //위처럼 사용하는 것보다 아래처럼 사용하는 게 나음 (문자를 직접적으로 적는 것보단 !!)
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    //헤더 컨텐트 타입이 json일 경우에만 호출됨
    //consume : 컨텐트 타입 정보를 소비하는 것
    //produce : 내가 생산해내는 것
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    //얘도 produces = "text/html" 이렇게 직접 적는 것보단
    //미디어타입 벨류 설정해 주는 게 더 낫다
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    //accept : 이런 컨텐트/미디어 타입을 받아들일 수 있다!는 클라이언트 요청 정보
    //accept가 */*로 되어 있으면 호출되지만, 
    //accept가 application/json으로 되어 있다면 json인 컨텐트 타입만 받아들인다는 뜻!
    //내가 설정한 건 text/html이기 때문에 이게 안 맞아서 처리를 못하는 것
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
