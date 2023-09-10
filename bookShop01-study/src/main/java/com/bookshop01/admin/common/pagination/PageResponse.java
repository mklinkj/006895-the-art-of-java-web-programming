package com.bookshop01.admin.common.pagination;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageResponse<E> {
  private final int pageNumber;
  private final int pageSize;
  private final long total;

  private final int navigationSize = PageConstants.NAVIGATION_SIZE;

  /** 네비게이션에서의 시작 페이지 번호 */
  private final int start;

  /** 네이베기션에서의 끝 페이지 번호 */
  private final int end;

  /** 이전 페이지의 존재 여부 */
  private final boolean prev;

  /** 다음 페이지의 존재 여부 */
  private final boolean next;

  private final List<E> content;

  @Builder(builderMethodName = "withAll")
  public PageResponse(List<E> content, PageRequest pageRequest, long total) {
    this.pageNumber = pageRequest.getPage();
    this.pageSize = pageRequest.getSize();

    this.content = content;
    this.total = total;

    final int end =
        (int) (Math.ceil((double) pageNumber / (double) navigationSize)) * navigationSize;
    this.start = end - (navigationSize - 1);

    final int last = (int) (Math.ceil((double) total / (double) pageSize));
    this.end = Math.min(end, last);

    this.prev = this.start > 1;
    this.next = total > ((long) this.end * this.pageSize);
  }
}
