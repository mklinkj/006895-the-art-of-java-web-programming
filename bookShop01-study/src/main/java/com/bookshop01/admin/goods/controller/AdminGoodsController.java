package com.bookshop01.admin.goods.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface AdminGoodsController {
  ModelAndView adminGoodsMain(
      @RequestParam Map<String, String> dateMap,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception;

  ResponseEntity addNewGoods(
      MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

  ResponseEntity modifyGoodsInfo(
      @RequestParam("goods_id") String goods_id,
      @RequestParam("mod_type") String mod_type,
      @RequestParam("value") String value,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception;

  void removeGoodsImage(
      @RequestParam("goods_id") int goods_id,
      @RequestParam("image_id") int image_id,
      @RequestParam("imageFileName") String imageFileName,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception;

  void addNewGoodsImage(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
      throws Exception;

  void modifyGoodsImageInfo(
      MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;
}
