package org.mklinkj.test.support;

/**
 * 실행할 테스트 코드가 checked 예외를 던질 경우<br>
 * 테스트 실행 코드 측에서 try-catch로 감싸는게 좀 너무 복잡해져서 이렇게 함.
 */
@FunctionalInterface
public interface ExceptionableRunnable {
  void run() throws Exception;
}
