package com.bookshop01.common.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class BaseController {

  @RequestMapping(
      value = "/*.do",
      method = {RequestMethod.POST, RequestMethod.GET})
  protected void viewForm() {}
}
