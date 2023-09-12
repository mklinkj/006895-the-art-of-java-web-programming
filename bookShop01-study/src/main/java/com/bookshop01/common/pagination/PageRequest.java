package com.bookshop01.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {

  @Builder.Default private int page = 1;

  @Builder.Default private int size = PageConstants.PAGE_SIZE;

  /** MyBatis Mapper.xml 내에서 호출해서 사용한다. */
  @SuppressWarnings("unused")
  public int getOffset() {
    return (page - 1) * size;
  }
}
