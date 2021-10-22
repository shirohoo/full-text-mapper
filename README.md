# 👀 전문(Full-Text)

통신 당사자들이 서로 주고 받을 데이터의 포맷을 미리 약속한 후 약속된 데이터 포맷에 맞춘 데이터 패킷을 송수신 하는 것.

전문 통신에서 가장 중요한 것은 데이터의 포맷과 길이이다.

따라서 정해진 길이가 항상 존재하므로 이 길이를 맞추기 위해 패딩을 채워넣게 된다.

이 패딩은 데이터가 `문자일 경우 공백(" ")`, `숫자일 경우 0`으로 채워넣는 경우가 일반적이며, 이는 통신 당사자들의 약속에 따라 언제든지 바뀔 수 있다.

예를 들어 `총 길이가 9`인 전문을 주고받는다고 가정할 때

- `0~1`: 데이터 타입 구분
- `2~6`: 사용자 이름
- `7~9`: 사용자 나이

라고 서로 약속 했고, 데이터가

- `데이터 타입 구분`: 1
- `사용자 이름`: siro
- `사용자 나이`: 28

일 경우 전문은 다음과 같다.

> `1 siro028`

<br />

## ✔ 고려사항

- 데이터의 길이
- 데이터의 포맷
- 데이터의 인코딩
- 패딩 문자

<br />

# ⚙ 사용 방법

기본적으로 전문 한줄과 객체 하나가 일대일로 매핑됩니다.

전문과 매핑될 클래스를 작성하고 `@FullText`를 각 필드에 정의합니다.

***이때 `기본생성자`가 반드시 필요하며, `접근제한자`가 `private`이여도 괜찮습니다.***

<br />

```java
public class TestModel {

    @FullText(length = 1)
    private String headerType;

    @FullText(length = 8)
    private LocalDate createAt; // yyyyMMdd

    @FullText(length = 91)
    private String headerPadding;

    @FullText(length = 1)
    private String dataType;

    @FullText(length = 10)
    private String name;

    @FullText(length = 3)
    private int age;

    @FullText(length = 86)
    private String dataPadding;

    @FullText(length = 1)
    private String trailerType;

    @FullText(length = 99)
    private String trailerPadding;

}
```

<br />

`FullTextMapper` 인스턴스를 생성하고 `readValue(String, T)`를 호출합니다.

<br />

```java
private FullTextMapper mapper = FullTextMapper.create();
TestModel testModel = mapper.readValue(data, TestModel.class);
```

<br />

간단한 테스트 코드는 다음과 같습니다

<br />

```java
class FullTextMapperTest {

    private FullTextMapper mapper = FullTextMapper.create();

    @Test
    void readValue() throws Exception {
        assertThat(mapper.readValue(mockData(), TestModel.class))
            .isEqualTo(expectedModel());
    }

    private String mockData() {
        return "120211011                                                                                           " +
            "2      siro 28                                                                                      " +
            "3                                                                                                   ";
    }

    private TestModel expectedModel() {
        return TestModel.builder()
            .headerType("1")
            .createAt(LocalDate.parse("20211011", DateTimeFormatter.BASIC_ISO_DATE))
            .headerPadding("")
            .dataType("2")
            .name("siro")
            .age(28)
            .dataPadding("")
            .trailerType("3")
            .trailerPadding("")
            .build();
    }

}
```

<br />
