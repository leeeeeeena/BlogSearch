# 블로그검색 서비스

![image](https://user-images.githubusercontent.com/125448783/226576528-9d9a2dbd-133f-420b-af49-62ca7084db36.png)

### 요구사항

**1. 블로그 검색**

- 카카오 API 활용하여 키워드로 블로그 검색
- 검색 결과에서 Sorting(정확도순,최신순) 과 Pagination 제공
- 카카오 API에 문제가 있을 시 네이버 API 사용

</br>

**2. 인기 검색어 목록**

- 사용자들이 많이 검색한 순서대로 최대 10개의 검색 키워드 목록 제공
- 키워드 별로 검색된 횟수도 함께 표기

</br>


# 테스트 방법

  1. BlogSearch-yurim.jar을 내려받습니다..   
  2. 파일이 있는 디렉토리에서 다음 명령어를 실행합니다.

```
java -jar BlogSearch-yurim.jar
```
3. API 요청을 한다. 예시는 아래를 참고하시면 됩니다.

**Embedded Redis 실행 script로 인해 윈도우 환경에서는 테스트가 어려울 수 있습니다.(리눅스 정상작동 확인)**

</br>

검색 요청/인기검색어 조회시 redis를 사용하거나, 사용하고 싶지 않다면 application.yml의 다음 필드를 변경하여 적용할 수 있습니다.   

```
infra:
  enable:
    redis: false # or true
```

</br>

### API 요청 cURL

1. 블로그검색

`카카오뱅크` 키워드를 UTF-8 로 인코딩하여 요청 예시

```
curl --location 'localhost:9090/v1/search/blog?query=%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%B1%85%ED%81%AC&page=2&size=10&sort=accuracy' \
    -H "Accept: application/json" \
    -H "Content-Type: application/json;charset=UTF-8"
```

</br>

2. 인기 검색어 목록

```
curl --location 'localhost:9090/v1/search/rank'
    -H "Accept: application/json" \
    -H "Content-Type: application/json;charset=UTF-8"
```

<br>

## 사용 기술

| 분야          | stack            |
| ------------- | ---------------- |
| 언어          | Java (zulu 11)   |
| 프레임워크    | springBoot 2.7.9 |
| DB            | h2            |
| ORM            | JPA           |
| Cache            | Redis            |
| 빌드 툴       | Gradle            |
| API 테스트 툴 | Junit            |
| IDE           | IntelliJ         |

<br>

## 라이브러리

| 라이브러리                                                                                                                          | 이유                                                 |
| ----------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------- |
| Lombok                                                                                                                              | 어노테이션 사용                                      |
| Spring Web                                                                                                                          | HTTP 요청 API 구현 및 서블릿 환경 사용               |
| Spring Cloud openfeign                                                                                                                      | 외부API 호출 시 Feign 구현을 위해 사용                     |
| Spring Redis                                                                                                                        | spring framework에서 redis 코드레벨 구현을 위해 사용 |
| it.ozimov:embedded-redis                                                                                                                       | Embedded Redis를 위해  |
| Spring JPA                                                                                                                        | 객체관점에서 쿼리 요청을 위해 사용 |

<br>

# API 명세

## 블로그 검색 API

#### 요청 규격

| 이름    | 타입   | 필수 | 설명        |
| ------- | ------ | ---- | ----------- |
| query | string | ○    | 검색 키워드 |
| page | int | ○    | 결과 페이지 번호. 기본값 1|
| size | int | ○    | 한 페이지에 보여질 문서 수 . 기본값 10 |
| sort | string | ○    | 정렬방식.  accuracy(정확도순) , recency(최신순) |

</br>

#### 응답 규격

| 이름           | 타입                 | 설명                         |
| -------------- | -------------------- | ---------------------------- |
| code          | string                  | 응답코드 |
| message | string | 응답메세지          |
| data | SearchResponse | 블로그 검색 응답 |

##### SearchResponse

| 이름        | 타입   | 설명                  |
| ----------- | ------ | --------------------- |
| total   | int | 블로그 검색 결과 총 개수 |
| size     | int | 표시할 검색 결과 개수     |
| elements | BlogPost | 키워드 검색 결과 개수 |


##### BlogPosts

| 이름        | 타입   | 설명                  |
| ----------- | ------ | --------------------- |
| postName   | string | 게시글 이름 |
| url     | string | 게시글 url    |
| blogName | string | 블로그 이름 |
| description | string | 게시글 요약내용 |
| thumbnail | string | 썸네일 |
| date | date | 블로그 작성일. yyyy-MM-dd |

</br>

#### 응답 예시

```json
{
    "message": "요청 성공하였습니다.",
    "code": "2000",
    "data": {
        "total": 484657,
        "size": 2,
        "elements": [
            {
                "postName": "<b>카카오</b><b>뱅크</b> 통장 사본 출력 방법 쉽게 알아보기",
                "url": "http://machine0825.tistory.com/809",
                "blogName": "KM의 취미 생활 [3D프린터/여행/맛집/일상]",
                "description": "요즘 <b>카카오</b> <b>뱅크</b>를 사용하지 않는 분들은 거의 없을 거라 생각합니다. 편리하긴 하지만 한 번씩 관련 문서가 필요할 때 곤란한 경우가 있는데요, <b>카카오</b><b>뱅크</b> 통장 사본 출력 방법을 쉽게 알려드리겠습니다. <b>카카오</b><b>뱅크</b> 통장 사본 출력 방법 (모바일 버전) <b>카카오</b><b>뱅크</b> 통장 사본을 출력하는 방법은 모바일 버전과 PC버전...",
                "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/6qQLuEm2LHM",
                "date": "2023-02-13"
            },
            {
                "postName": "<b>카카오</b><b>뱅크</b> 저금통 동전 모으기 결과 공개",
                "url": "http://gyulnlove.tistory.com/461",
                "blogName": "쉿  너만 있어",
                "description": "<b>카카오</b><b>뱅크</b> 저금통 동전 모으기 결과 공개 안녕하세요! 여러분은 혹시 <b>카카오</b> <b>뱅크</b>를 사용하고 계신가요? 저는 은행에 갈 필요없는 <b>카카오</b><b>뱅크</b>로 진작 갈아탔습니다 . <b>카카오</b><b>뱅크</b>에 활용할수 있는 아이템들이 여러가지가 있는데요. 오늘은 저는 잔돈모으기를 하는 저금통에 관련된 이야기를 써보려고 합니다 . <b>카카오</b>...",
                "thumbnail": "",
                "date": "2023-02-03"
            }
        ]
    }
}
```

<br>

## 인기 검색어 목록 API

#### 응답 규격

| 이름           | 타입                 | 설명                         |
| -------------- | -------------------- | ---------------------------- |
| code          | string                  | 응답코드 |
| message | string | 응답메세지          |
| data | SearchCount | 인기검색어 목록 |

##### SearchCounts

| 이름        | 타입   | 설명        |
| ----------- | ------ | ----------- |
| query     | string | 키워드      |
| count | int    | 검색된 횟수 |

<br>

#### 응답 예시

```json
{
    "message": "요청 성공하였습니다.",
    "code": "2000",
    "data": {
        "topSearchQueries": [
            {
                "query": "은행",
                "count": 113
            },
            {
                "query": "카카오",
                "count": 50
            },
            {
                "query": "대출",
                "count": 24
            }
        ]
    }
}
```

<br>

## 요청 공통

#### `헤더`

| 항목         | 값 (예)                        | 설명                            |
| ------------ | ------------------------------ | ------------------------------- |
| Content-Type | application/json;charset=UTF-8 | `JSON`으로 요청. `UTF-8` 인코딩 |
| Accept       | application/json               | `JSON` 허용                     |

<br>

## 응답 공통

#### `HTTP 응답코드`

| 응답코드 | 설명                  |
| -------- | --------------------- |
| `200`    | **정상 응답**         |
| `400`    | 잘못된 요청           |
| `404`    | 리소스를 찾을 수 없음 |
| `500`    | 시스템 에러           |
| `503`    | 서비스 사용 불가        |

</br>

#### `에러코드 및 메시지`

| 에러코드           | 메시지                             |
| ------------------ | ---------------------------------- |
| 사용자 요청 오류   |                                    |
| `4000`             | 잘못 된 요청입니다                 |
| `4200`             | 지원하지 않는 요청입니다           |
| `4201`             | 지원하지 않는 요청입니다           |
| `4202`             | 잘못 된 요청입니다                 |
| `4300`             | 필수 값이 누락되었습니다           |
| `4301`             | 요청 값이 유효하지 않습니다        |
| `4302`             | 요청 값이 허용되지 않습니다        |
| `4400`             | 요청 대상을 찾을 수 없습니다       |
| `4401`             | 요청 대상이 중복 되었습니다        |
| 서버 내부 오류     |                                    |
| `5000`             | 알 수 없는 오류가 발생하였습니다   |
| `5100`             | DB 연결 실패하였습니다             |
| `5101`             | DB 연결이 오래 걸립니다            |
| `5200`             | MW 연결 실패하였습니다             |
| `5201`             | MW 연결이 오래 걸립니다            |
| 외부 API 연동 오류 |                                    |
| `6000`             | 알 수 없는 오류가 발생하였습니다   |
| `6001`             | 허가 되지 않은 요청입니다          |
| `6002`             | 외부 요청 생성에 실패하였습니다    |
| `6003`             | 외부 요청 대상이 존재하지 않습니다 |
| `6004`             | 지원이 되지 않는 외부 요청입니다   |
| `6005`             | 요청 값이 올바르지 않습니다        |
| `6100`             | 연동 API 연결 실패하였습니다       |

</br>

#### 에러응답

| 이름    | 타입   | 설명             |
| ------- | ------ | ---------------- |
| code    | string | 응답 코드        |
| message | string | API 별 응답 내용 |

</br>

#### 에러응답 예시

```json
{
  “code”: “4300",
  “message”: “필수 값이 누락되었습니다 ”
}
```

</br>

