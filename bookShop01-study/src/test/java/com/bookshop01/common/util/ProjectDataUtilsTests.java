package com.bookshop01.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProjectDataUtilsTests {

  @Test
  void getProperty() {
    String path = ProjectDataUtils.getProperty("image_repo_path");
    assertThat(path).isEqualTo("C:\\shopping\\file_repo");
  }
}
