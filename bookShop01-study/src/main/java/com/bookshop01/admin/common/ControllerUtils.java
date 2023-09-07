package com.bookshop01.admin.common;

import static com.bookshop01.common.constants.Constants.DATE_FORMAT_YYYY_MM_DD;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.ui.Model;

@NoArgsConstructor
public class ControllerUtils {

  /**
   * 어드민 컨트롤러의 목록 처리 공통 로직 메서드
   *
   * @param func 실행 코드
   * @param paramMap 파라미터 맵
   * @param model Spring 컨트롤러 Model
   * @return 목록 결과 값
   * @param <R> 목록 결과 타입
   */
  public static <R> R processList(
      Function<Map<String, Object>, R> func, Map<String, String> paramMap, Model model) {
    String section = paramMap.get("section");
    String pageNum = paramMap.get("pageNum");
    String beginDate = paramMap.get("beginDate");
    String endDate = paramMap.get("endDate");

    if (beginDate == null || endDate == null) {
      LocalDate today = LocalDate.now();
      beginDate = today.minusMonths(4).format(DATE_FORMAT_YYYY_MM_DD);
      endDate = today.format(DATE_FORMAT_YYYY_MM_DD);
    }

    Map<String, Object> condMap = new HashMap<>();
    if (section == null) {
      section = "1";
    }
    condMap.put("section", section);
    if (pageNum == null) {
      pageNum = "1";
    }
    condMap.put("pageNum", pageNum);
    condMap.put("beginDate", beginDate);
    condMap.put("endDate", endDate);

    val command = paramMap.get("command");
    model.addAttribute("command", command);

    if ("detail_search".equals(command)) {
      val searchType = paramMap.get("search_type");
      val searchWord = paramMap.get("search_word");
      condMap.put("search_type", searchType);
      condMap.put("search_word", searchWord);
      model.addAttribute("search_type", searchType);
      model.addAttribute("search_word", searchWord);
    }

    R resultList = func.apply(condMap);

    model.addAttribute("beginDate", beginDate);
    model.addAttribute("endDate", endDate);

    model.addAttribute("section", section);
    model.addAttribute("pageNum", pageNum);

    return resultList;
  }
}
