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

## 📜 Maven
```xml
<!--pom.xml-->
<dependency>
  <groupId>io.github.shirohoo</groupId>
  <artifactId>full-text-mapper</artifactId>
  <version>1.1</version>
</dependency>
```

## 📜 Gradle
```groovy
// build.gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.shirohoo:full-text-mapper:1.1'
}
```

<br />

기본적으로 전문 한줄과 객체 하나가 일대일로 매핑됩니다.

**이때 `기본생성자`가 반드시 필요하며, `접근제한자`는 `private`이여도 괜찮습니다.**

전문과 매핑될 클래스를 작성하고 `@FullText`를 클래스 레벨에, `@Field`를 필드 레벨에 정의합니다.

전문과 매핑할 클래스의 필드 타입을 기반으로 형변환 매핑합니다.

예를 클래스의 필드를 `private int age;` 라고 선언하였고 전문에서 `0038` 이라는 값을 읽어왔다면 `int` 타입의 `38`로 형변환되어 됩니다.

현재 지원되는 타입은 다음과 같습니다.

- `String`
- `int` or `Integer`
- `long` or `Long`
- `double` or `Double`
- `LocalDate` - 현재 `yyyyMMdd`만 지원
- `LocalDateTime` - 현재 `yyyyMMddHHmmss`만 지원
- `BigDecimal`

<br />

유의해야 할 사항은 다음과 같습니다.

<br />

- `FullTextMapper`의 구현체는 불변이며 스레드 세이프합니다.
- `@Field`의 속성이 `@FullText`의 속성보다 더 우선적으로 적용됩니다.
- `@FullText`에 선언된 `길이(length)`와 `각 필드에 선언된 @Field.length의 총합`이 일치하지 않을 경우 예외를 발생시킵니다.
- 클래스 레벨에 `@FullText`가 누락돼있다면 예외를 발생시킵니다.
- 필드 레벨에 `@Field`가 누락돼있다면 예외를 발생시킵니다.

<br />

구체적인 선언 방법은 하기와 같습니다.

<br />

```java
@FullText(
    length = 300,
    encoding = Charset.UTF_8, // 명시하지 않을 경우 기본값은 UTF-8입니다.
    padChar = PadCharacter.SPACE, // 명시하지 않을 경우 기본값은 공백문자(" ")입니다.
    padPosition = PadPosition.LEFT_PAD // 명시하지 않을 경우 기본적으로 왼쪽에 패딩문자를 채워넣습니다.
)
public class ValidOptionModel {

    @Field(length = 1)
    private String headerType;

    @Field(length = 8)
    private LocalDate createAt; // yyyyMMdd

    @Field(length = 91)
    private String headerPadding;

    @Field(length = 1)
    private String dataType;

    @Field(length = 10, padPosition = PadPosition.RIGHT_PAD) // @Field의 속성이 @FullText보다 우선됩니다.
    private String name;

    @Field(length = 3, padChar = PadCharacter.ZERO) // @Field의 속성이 @FullText보다 우선됩니다.
    private int age;

    @Field(length = 86)
    private String dataPadding;

    @Field(length = 1)
    private String trailerType;

    @Field(length = 99)
    private String trailerPadding;

}
```

<br />

## 📌 읽기

`FullTextMapperFactory` 를 통해 `FullTextMapper` 인스턴스를 획득하고 `readValue(String, T)`를 호출합니다.

<br />

```java
FullTextMapper mapper = FullTextMapperFactory.lineFullTextMapper();
Optional<ValidModel> validModel = mapper.readValue(FullTextCreator.validData(), ValidModel.class);
```

<br />

현재 작성 된 간단한 테스트 코드는 다음과 같습니다

<br />

```java
class LineFullTextMapperTest {

    private FullTextMapper mapper = FullTextMapperFactory.lineFullTextMapper();

    @Test
    void readValue() throws Exception {
        Optional<ValidModel> actual = mapper.readValue(FullTextCreator.validData(), ValidModel.class);
        assertThat(actual.get()).isEqualTo(ModelCreator.validModel());
    }

}
```

<br />

## 📌 쓰기

`FullTextMapperFactory` 를 통해 `FullTextMapper` 인스턴스를 획득하고 `write(Object)`를 호출합니다.

<br />

```java
FullTextMapper mapper = FullTextMapperFactory.lineFullTextMapper();
String fullText = mapper.write(ModelCreator.validModel());
```

<br />

현재 작성 된 간단한 테스트 코드는 다음과 같습니다

<br />

```java
class LineFullTextMapperTest {

    private FullTextMapper mapper = FullTextMapperFactory.lineFullTextMapper();

    @Test
    void write() throws Exception {
        String actual = mapper.write(ModelCreator.validModel());
        assertThat(actual).isEqualTo(FullTextCreator.validModel());
    }

}
```

<br />
