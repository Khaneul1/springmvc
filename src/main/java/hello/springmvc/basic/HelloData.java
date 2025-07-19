package hello.springmvc.basic;

import lombok.Data;

@Data
//@Data(롬복 제공)를 쓰면
//Getter Setter ToString EqualsAndHashCode RequiredArgsConstructor 를 자동으로 적용해 줌
public class HelloData {
    private String username;
    private int age;
}
