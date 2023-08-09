package com.bookshop01.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectDataUtils {
  /** 프로퍼티 파일은 한번만 로드 */
  private static final Properties PROPERTIES = ProjectDataUtilsHolder.PROPERTIES;

  /** Properties를 외부로 공개하지 말고, 키로 값을 가져올 수 있는 getProperty 메서드만 추가해주자! */
  public static String getProperty(String key) {
    return PROPERTIES.getProperty(key);
  }

  public static int getIntProperty(String key) {
    return Integer.parseInt(getProperty(key));
  }

  public static long getLongProperty(String key) {
    return Long.parseLong(getProperty(key));
  }

  private static class ProjectDataUtilsHolder {
    private static final String PROPERTIES_LOCATION = "config/project-data.properties";

    private static final Properties PROPERTIES;

    static {
      try {
        PROPERTIES =
            PropertiesLoaderUtils.loadProperties(
                new EncodedResource(
                    new ClassPathResource(PROPERTIES_LOCATION), StandardCharsets.UTF_8));
        LOGGER.info("### {} 프로퍼티 파일 로드 성공!!! 😄👍 ###", PROPERTIES_LOCATION);
      } catch (IOException e) {
        throw new IllegalStateException("### " + PROPERTIES_LOCATION + " 프로퍼티 파일 로드 실패 🤪 ###", e);
      }
    }
  }
}
