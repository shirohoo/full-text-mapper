# ๐ ์ ๋ฌธ(Full-Text)

![](https://img.shields.io/github/issues/shirohoo/full-text-mapper)
![](https://img.shields.io/github/forks/shirohoo/full-text-mapper)
![](https://img.shields.io/github/stars/shirohoo/full-text-mapper)
![](https://img.shields.io/github/license/shirohoo/full-text-mapper)

ํต์  ๋น์ฌ์๋ค์ด ์๋ก ์ฃผ๊ณ  ๋ฐ์ ๋ฐ์ดํฐ์ ํฌ๋งท์ ๋ฏธ๋ฆฌ ์ฝ์ํ ํ ์ฝ์๋ ๋ฐ์ดํฐ ํฌ๋งท์ ๋ง์ถ ๋ฐ์ดํฐ ํจํท์ ์ก์์  ํ๋ ๊ฒ.

์ ๋ฌธ ํต์ ์์ ๊ฐ์ฅ ์ค์ํ ๊ฒ์ ๋ฐ์ดํฐ์ ํฌ๋งท๊ณผ ๊ธธ์ด์๋๋ค.

์ ๋ฌธ ํต์ ์์๋ ์ด ์ ํด์ง ๊ธธ์ด๋ฅผ ๋ง์ถ๊ธฐ ์ํด ํจ๋ฉ๋ฌธ์๋ฅผ ์ฑ์๋ฃ๊ฒ ๋ฉ๋๋ค.

ํจ๋ฉ๋ฌธ์๋ ๋ฐ์ดํฐ๊ฐ `๋ฌธ์์ผ ๊ฒฝ์ฐ ๊ณต๋ฐฑ(" ")`, `์ซ์์ผ ๊ฒฝ์ฐ 0`์ผ๋ก ์ฑ์๋ฃ๋ ๊ฒฝ์ฐ๊ฐ ์ผ๋ฐ์ ์ด๋ฉฐ, ์ด๋ ํต์  ๋น์ฌ์๋ค์ ์ฝ์์ ๋ฐ๋ผ ์ธ์ ๋ ์ง ๋ฐ๋ ์ ์์ต๋๋ค.

์๋ฅผ ๋ค์ด `์ด ๊ธธ์ด๊ฐ 9`์ธ ์ ๋ฌธ์ ์ฃผ๊ณ ๋ฐ๋๋ค๊ณ  ๊ฐ์ ํ  ๋

- `0~1`: ๋ฐ์ดํฐ ํ์ ๊ตฌ๋ถ
- `2~6`: ์ฌ์ฉ์ ์ด๋ฆ
- `7~9`: ์ฌ์ฉ์ ๋์ด

๋ผ๊ณ  ์๋ก ์ฝ์ ํ๊ณ , ๋ฐ์ดํฐ๊ฐ

- `๋ฐ์ดํฐ ํ์ ๊ตฌ๋ถ`: 1
- `์ฌ์ฉ์ ์ด๋ฆ`: siro
- `์ฌ์ฉ์ ๋์ด`: 28

์ผ ๊ฒฝ์ฐ ์ ๋ฌธ์ ๋ค์๊ณผ ๊ฐ์ต๋๋ค.

> `1 siro028`

<br />

## โ ๊ณ ๋ ค์ฌํญ

- ๋ฐ์ดํฐ์ ๊ธธ์ด
- ๋ฐ์ดํฐ์ ํฌ๋งท
- ๋ฐ์ดํฐ์ ์ธ์ฝ๋ฉ
- ํจ๋ฉ ๋ฌธ์

<br />

# โ ์ฌ์ฉ ๋ฐฉ๋ฒ

์ด ๋ชจ๋์ ์์ํ๊ฒ ์๋ฐ 1.8๋ก ์์ฑ๋์ต๋๋ค. 

๋ฐ๋ผ์ ์๊ตฌํ๋ ์ต์ํ์ ์๋ฐ ๋ฒ์ ์ 1.8์๋๋ค.

## ๐ Maven
```xml
<!--pom.xml-->
<dependency>
  <groupId>io.github.shirohoo</groupId>
  <artifactId>full-text-mapper</artifactId>
  <version>1.4</version>
</dependency>
```

## ๐ Gradle
```groovy
// build.gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.shirohoo:full-text-mapper:1.4'
}
```

<br />

๊ธฐ๋ณธ์ ์ผ๋ก ์ ๋ฌธ ํ์ค๊ณผ ๊ฐ์ฒด ํ๊ฐ๊ฐ ์ผ๋์ผ๋ก ๋งคํ๋ฉ๋๋ค.

**๋งคํ์ ํ๊ธฐ ์ํด์๋ `๊ธฐ๋ณธ์์ฑ์`๊ฐ ๋ฐ๋์ ํ์ํ๋ฉฐ, `์ ๊ทผ์ ํ์`๋ `private`์ด์ฌ๋ ๊ด์ฐฎ์ต๋๋ค.**

์ ๋ฌธ๊ณผ ๋งคํ๋  ํด๋์ค๋ฅผ ์์ฑํ๊ณ  `@FullText`๋ฅผ ํด๋์ค ๋ ๋ฒจ์, `@Field`๋ฅผ ํ๋ ๋ ๋ฒจ์ ์ ์ํฉ๋๋ค.

๋งคํ์ ์ ๋ฌธ๊ณผ ๋งคํํ  ํด๋์ค์ ํ๋ ์ ์ธ ํ์์ ๊ธฐ๋ฐ์ผ๋ก ํ๋ณํ ๋งคํ๋ฉ๋๋ค.

์๋ฅผ ๋ค์๋ฉด, ํด๋์ค์ ํ๋๋ฅผ `private int age;` ๋ผ๊ณ  ์ ์ธํ์๊ณ  ์ ๋ฌธ์์ `0038` ์ด๋ผ๋ ๊ฐ์ ์ฝ์ด์๋ค๋ฉด ๋ฌธ์์ด `0038`์  `int` ํ์์ `38`๋ก ํ๋ณํ๋์ด ๋งคํ ๋ฉ๋๋ค.

ํ์ฌ ์ง์๋๋ ํ์์ ๋ค์๊ณผ ๊ฐ์ต๋๋ค.

- `String`
- `int` or `Integer`
- `long` or `Long`
- `double` or `Double`
- `LocalDate` - ๊ธฐ๋ณธ ํฌ๋งท `yyyyMMdd` **(๋ณ๊ฒฝ ๊ฐ๋ฅ)**
- `LocalDateTime` - ๊ธฐ๋ณธ ํฌ๋งท `yyyyMMddHHmmss` **(๋ณ๊ฒฝ ๊ฐ๋ฅ)**
- `BigDecimal`

<br />

์ฌ์ฉ ์ ์ ์ํด์ผ ํ  ์ฌํญ์ ๋ค์๊ณผ ๊ฐ์ต๋๋ค.

<br />

- ์ต์  ๋ฒ์  ๊ธฐ์ค์ผ๋ก ๊ฐ ํ๋์ ์ฒ๋ฆฌ ์์๋ `Top-down` ์๋๋ค. ์ ์ธ ์์๋ฅผ ์ ์ง์ผ์ฃผ์ธ์.
- `FullTextMapper`์ ๊ตฌํ์ฒด๋ ๋ถ๋ณ์ด๋ฉฐ ์ค๋ ๋ ์ธ์ดํํฉ๋๋ค.
- `@Field`์ ์์ฑ์ด `@FullText`์ ์์ฑ๋ณด๋ค ๋ ์ฐ์ ์ ์ผ๋ก ์ ์ฉ๋ฉ๋๋ค.
- `@FullText`์ ์ ์ธ๋ `๊ธธ์ด(length)`์ `๊ฐ ํ๋์ ์ ์ธ๋ @Field.length์ ์ดํฉ`์ด ์ผ์นํ์ง ์์ ๊ฒฝ์ฐ ์์ธ๋ฅผ ๋ฐ์์ํต๋๋ค.
- ํด๋์ค ๋ ๋ฒจ์ `@FullText`๊ฐ ๋๋ฝ๋ผ์๋ค๋ฉด ์์ธ๋ฅผ ๋ฐ์์ํต๋๋ค.
- ํ๋ ๋ ๋ฒจ์ `@Field`๊ฐ ๋๋ฝ๋ผ์๋ค๋ฉด ์์ธ๋ฅผ ๋ฐ์์ํต๋๋ค.
- `@FullText`์ `@Field`์ ์์ฑ๋ค (`PadPosition`, `PadCharacter`)์ด ๋ชจ๋ `NONE`์ด๋ฉด ์์ธ๋ฅผ ๋ฐ์์ํต๋๋ค.
- `LocalDate`์ ๊ธฐ๋ณธ ํฌ๋งท์ `yyyyMMdd` ์ด๋ฉฐ, ์ด๋ฅผ ๋ณ๊ฒฝํ๊ธฐ ์ํด์๋ `@Field(localDateFormat = "{format}")`์ ์์ ํ์ญ์์ค.
- `LocalDateTime`์ ๊ธฐ๋ณธ ํฌ๋งท์ `yyyyMMdd` ์ด๋ฉฐ, ์ด๋ฅผ ๋ณ๊ฒฝํ๊ธฐ ์ํด์๋ `@Field(localDateTimeFormat = "{format}")`์ ์์ ํ์ญ์์ค.

<br />

๊ตฌ์ฒด์ ์ธ ์ ์ธ ๋ฐฉ๋ฒ์ ํ๊ธฐ์ ๊ฐ์ต๋๋ค.

<br />

```java
@FullText(
    length = 300,
    encoding = Charset.UTF_8, // ๋ช์ํ์ง ์์ ๊ฒฝ์ฐ ๊ธฐ๋ณธ๊ฐ์ UTF-8์๋๋ค.
    padChar = PadCharacter.SPACE, // ๋ช์ํ์ง ์์ ๊ฒฝ์ฐ ๊ธฐ๋ณธ๊ฐ์ ๊ณต๋ฐฑ๋ฌธ์(" ")์๋๋ค.
    padPosition = PadPosition.LEFT_PAD // ๋ช์ํ์ง ์์ ๊ฒฝ์ฐ ๊ธฐ๋ณธ์ ์ผ๋ก ์ผ์ชฝ์ ํจ๋ฉ๋ฌธ์๋ฅผ ์ฑ์๋ฃ์ต๋๋ค.
)
public class ValidOptionModel {
    @Field(length = 1)
    private String headerType;

    @Field(length = 10, localDateFormat = "yyyy-MM-dd") // ๊ธฐ๋ณธ๊ฐ์ yyyyMMdd ์ด๋ฉฐ, ๋ณ๊ฒฝ๊ฐ๋ฅํฉ๋๋ค.
    private LocalDate createAt;

    @Field(length = 91)
    private String headerPadding;

    @Field(length = 1)
    private String dataType;

    @Field(length = 10, padPosition = PadPosition.RIGHT_PAD) // @Field์ ์์ฑ์ด @FullText๋ณด๋ค ์ฐ์ ๋ฉ๋๋ค.
    private String name;

    @Field(length = 3, padChar = PadCharacter.ZERO) // @Field์ ์์ฑ์ด @FullText๋ณด๋ค ์ฐ์ ๋ฉ๋๋ค.
    private int age;

    @Field(length = 84)
    private String dataPadding;

    @Field(length = 1)
    private String trailerType;

    @Field(length = 99)
    private String trailerPadding;
}
```

<br />

## ๐ ์ฝ๊ธฐ

`FullTextMapperFactory` ๋ฅผ ํตํด `FullTextMapper` ์ธ์คํด์ค๋ฅผ ํ๋ํ๊ณ  `readValue(String, T)`๋ฅผ ํธ์ถํฉ๋๋ค.

<br />

```java
FullTextMapper mapper = FullTextMapperFactory.lineFullTextMapper();
FullTextModel fullTextModel = mapper.readValue(getFullText(), FullTextModel.class);
```

<br />

## ๐ ์ฐ๊ธฐ

`FullTextMapperFactory` ๋ฅผ ํตํด `FullTextMapper` ์ธ์คํด์ค๋ฅผ ํ๋ํ๊ณ  `write(Object)`๋ฅผ ํธ์ถํฉ๋๋ค.

<br />

```java
FullTextMapper mapper = FullTextMapperFactory.lineFullTextMapper();
String fullText = mapper.write(getFullTextModel());
```

<br />

> ๊ธฐ๋ณธ์ ์ผ๋ก ์ ์ ๋ณด๋ค๋ง ์์งํ๋ค๋ฉด ์ฌ์ฉํ๋๋ฐ ๋ฌธ์ ๋ ์์๊ฒ์๋๋ค. 
> 
> ๋์ฑ ์์ธํ ๋ด์ฉ๋ค์ด ๊ถ๊ธํ๋ค๋ฉด ๊ฐ ํด๋์ค์ ์ ์๋ javadoc์ ์ฐธ๊ณ ํ์ธ์.

<br />
