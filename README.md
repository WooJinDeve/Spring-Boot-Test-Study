### 개요
- 🍃 스프링 부트 테스트 코드 연습 레포지토리입니다.
- 기간 : `2022-09-29 ~ `

### 왜 테스트코드를 작성해야하는가
- 개발 과정 중 예상치 못한 문제를 미리 발견할 수 있다.
- 작성한 코드가 의도한 대로 작동하는지 검증할 수 있다
- 코드 수정이 필요한 상황에서 유연하고 안정적인 대응을 할 수 있게해준다.
  - 코드 변경 시, 변경 부분으로 인한 영향도를 쉽게 파악할 수 있다.
- 코드 리팩토링 시 기능 구현이 동일하게 되었다는 판단을 내릴 수 있다.
- 테스트 코드를 통해 동작하는 방식 및 결과 확인이 가능하다.

### Java 단위테스트의 2가지 라이브러리
- `JUnit5` : 자바 단위 테스트를 위한 테스팅 프레임워크
- `AssertJ` : 자바 테스트를 돕기 위해 다양한 문법을 지원하는 라이브러리

### given/when/then 패턴
- `given` : 어떠한 데이터가 준비되었을 때
- `when` : 어떠한 함수를 실행하면
- `then` : 어떠한 결과가 나와야 한다.