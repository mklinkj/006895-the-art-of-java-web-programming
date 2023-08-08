package org.mklinkj.test.support;

import static org.mockito.Mockito.mockStatic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FixedDateTestHelper {
  /** LocalDateTime의 현재 날짜, 시간을 변경해서 내부 코드 실행 */
  public static void changeNowLocalDateTime(LocalDateTime newNow, ExceptionableRunnable r) {
    try (MockedStatic<LocalDateTime> mockedJSONContext =
        mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
      mockedJSONContext.when(() -> LocalDateTime.now()).thenReturn(newNow);
      r.run();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  /** LocalDate의 현재 날짜를 변경해서 내부 코드 실행 */
  public static void changeNowLocalDate(LocalDate newNow, ExceptionableRunnable r) {
    try (MockedStatic<LocalDate> mockedJSONContext =
        mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
      mockedJSONContext.when(() -> LocalDate.now()).thenReturn(newNow);
      r.run();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  /** LocalTime의 현재 시간을 변경해서 내부 코드 실행 */
  public static void changeNowLocalTime(LocalTime newNow, ExceptionableRunnable r) {
    try (MockedStatic<LocalTime> mockedJSONContext =
        mockStatic(LocalTime.class, Mockito.CALLS_REAL_METHODS)) {
      mockedJSONContext.when(() -> LocalTime.now()).thenReturn(newNow);
      r.run();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
