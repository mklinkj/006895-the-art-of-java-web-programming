package com.bookshop01.admin.goods.controller;

import com.bookshop01.admin.goods.service.AdminGoodsService;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.common.util.ProjectDataUtils;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.member.vo.MemberVO;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/admin/goods")
public class AdminGoodsController extends BaseController {
  private final String currImageRepoPath = ProjectDataUtils.getProperty("image_repo_path");
  private final AdminGoodsService adminGoodsService;

  @RequestMapping(
      value = "/adminGoodsMain.do",
      method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView adminGoodsMain(
      @RequestParam Map<String, String> dateMap, HttpServletRequest request) throws Exception {
    String viewName = (String) request.getAttribute("viewName");
    ModelAndView mav = new ModelAndView(viewName);

    String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
    String section = dateMap.get("section");
    String pageNum = dateMap.get("pageNum");
    String beginDate, endDate;

    String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
    // yyyy-MM-dd 형식 문자열이 입력된다.
    beginDate = tempDate[0];
    endDate = tempDate[1];
    dateMap.put("beginDate", beginDate);
    dateMap.put("endDate", endDate);

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
    List<GoodsVO> newGoodsList = adminGoodsService.listNewGoods(condMap);
    mav.addObject("newGoodsList", newGoodsList);

    String beginDate1[] = beginDate.split("-");
    String endDate2[] = endDate.split("-");
    mav.addObject("beginYear", beginDate1[0]);
    mav.addObject("beginMonth", beginDate1[1]);
    mav.addObject("beginDay", beginDate1[2]);
    mav.addObject("endYear", endDate2[0]);
    mav.addObject("endMonth", endDate2[1]);
    mav.addObject("endDay", endDate2[2]);

    mav.addObject("section", section);
    mav.addObject("pageNum", pageNum);
    return mav;
  }

  @RequestMapping(
      value = "/addNewGoods.do",
      method = {RequestMethod.POST})
  public ResponseEntity addNewGoods(
      MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
    multipartRequest.setCharacterEncoding("utf-8");
    response.setContentType("text/html; charset=UTF-8");
    String imageFileName = null;

    Map newGoodsMap = new HashMap();
    Enumeration enu = multipartRequest.getParameterNames();
    while (enu.hasMoreElements()) {
      String name = (String) enu.nextElement();
      String value = multipartRequest.getParameter(name);
      newGoodsMap.put(name, value);
    }

    HttpSession session = multipartRequest.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String reg_id = memberVO.getMember_id();

    List<ImageFileVO> imageFileList = upload(multipartRequest);
    if (imageFileList != null && imageFileList.size() != 0) {
      for (ImageFileVO imageFileVO : imageFileList) {
        imageFileVO.setReg_id(reg_id);
      }
      newGoodsMap.put("imageFileList", imageFileList);
    }

    String message = null;
    ResponseEntity resEntity = null;
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
    try {
      int goods_id = adminGoodsService.addNewGoods(newGoodsMap);
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile = new File(currImageRepoPath + "\\" + "temp" + "\\" + imageFileName);
          File destDir = new File(currImageRepoPath + "\\" + goods_id);
          FileUtils.moveFileToDirectory(srcFile, destDir, true);
        }
      }
      message = "<script>";
      message += " alert('새상품을 추가했습니다.');";
      message +=
          " location.href='"
              + multipartRequest.getContextPath()
              + "/admin/goods/addNewGoodsForm.do';";
      message += ("</script>");
    } catch (Exception e) {
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile = new File(currImageRepoPath + "\\" + "temp" + "\\" + imageFileName);
          srcFile.delete();
        }
      }

      message = "<script>";
      message += " alert('오류가 발생했습니다. 다시 시도해 주세요');";
      message +=
          " location.href='"
              + multipartRequest.getContextPath()
              + "/admin/goods/addNewGoodsForm.do';";
      message += ("</script>");
      e.printStackTrace();
    }
    resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
    return resEntity;
  }

  @RequestMapping(
      value = "/modifyGoodsForm.do",
      method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView modifyGoodsForm(
      @RequestParam("goods_id") int goods_id,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    String viewName = (String) request.getAttribute("viewName");
    ModelAndView mav = new ModelAndView(viewName);

    Map goodsMap = adminGoodsService.goodsDetail(goods_id);
    mav.addObject("goodsMap", goodsMap);

    return mav;
  }

  @RequestMapping(
      value = "/modifyGoodsInfo.do",
      method = {RequestMethod.POST})
  public ResponseEntity modifyGoodsInfo(
      @RequestParam("goods_id") String goods_id,
      @RequestParam("attribute") String attribute,
      @RequestParam("value") String value,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    // System.out.println("modifyGoodsInfo");

    Map<String, String> goodsMap = new HashMap<String, String>();
    goodsMap.put("goods_id", goods_id);
    goodsMap.put(attribute, value);
    adminGoodsService.modifyGoodsInfo(goodsMap);

    String message = null;
    ResponseEntity resEntity = null;
    HttpHeaders responseHeaders = new HttpHeaders();
    message = "mod_success";
    resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
    return resEntity;
  }

  @RequestMapping(
      value = "/modifyGoodsImageInfo.do",
      method = {RequestMethod.POST})
  public void modifyGoodsImageInfo(
      MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
    System.out.println("modifyGoodsImageInfo");
    multipartRequest.setCharacterEncoding("utf-8");
    response.setContentType("text/html; charset=utf-8");
    String imageFileName = null;

    Map goodsMap = new HashMap();
    Enumeration enu = multipartRequest.getParameterNames();
    while (enu.hasMoreElements()) {
      String name = (String) enu.nextElement();
      String value = multipartRequest.getParameter(name);
      goodsMap.put(name, value);
    }

    HttpSession session = multipartRequest.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String reg_id = memberVO.getMember_id();

    List<ImageFileVO> imageFileList = null;
    int goods_id = 0;
    int image_id = 0;
    try {
      imageFileList = upload(multipartRequest);
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          goods_id = Integer.parseInt((String) goodsMap.get("goods_id"));
          image_id = Integer.parseInt((String) goodsMap.get("image_id"));
          imageFileVO.setGoods_id(goods_id);
          imageFileVO.setImage_id(image_id);
          imageFileVO.setReg_id(reg_id);
        }

        adminGoodsService.modifyGoodsImage(imageFileList);
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile = new File(currImageRepoPath + "\\" + "temp" + "\\" + imageFileName);
          File destDir = new File(currImageRepoPath + "\\" + goods_id);
          FileUtils.moveFileToDirectory(srcFile, destDir, true);
        }
      }
    } catch (Exception e) {
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile = new File(currImageRepoPath + "\\" + "temp" + "\\" + imageFileName);
          srcFile.delete();
        }
      }
      e.printStackTrace();
    }
  }

  @RequestMapping(
      value = "/addNewGoodsImage.do",
      method = {RequestMethod.POST})
  public void addNewGoodsImage(
      MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
    System.out.println("addNewGoodsImage");
    multipartRequest.setCharacterEncoding("utf-8");
    response.setContentType("text/html; charset=utf-8");
    String imageFileName = null;

    Map goodsMap = new HashMap();
    Enumeration enu = multipartRequest.getParameterNames();
    while (enu.hasMoreElements()) {
      String name = (String) enu.nextElement();
      String value = multipartRequest.getParameter(name);
      goodsMap.put(name, value);
    }

    HttpSession session = multipartRequest.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String reg_id = memberVO.getMember_id();

    List<ImageFileVO> imageFileList = null;
    int goods_id = 0;
    try {
      imageFileList = upload(multipartRequest);
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          goods_id = Integer.parseInt((String) goodsMap.get("goods_id"));
          imageFileVO.setGoods_id(goods_id);
          imageFileVO.setReg_id(reg_id);
        }

        adminGoodsService.addNewGoodsImage(imageFileList);
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile = new File(currImageRepoPath + "\\" + "temp" + "\\" + imageFileName);
          File destDir = new File(currImageRepoPath + "\\" + goods_id);
          FileUtils.moveFileToDirectory(srcFile, destDir, true);
        }
      }
    } catch (Exception e) {
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile = new File(currImageRepoPath + "\\" + "temp" + "\\" + imageFileName);
          srcFile.delete();
        }
      }
      e.printStackTrace();
    }
  }

  @RequestMapping(
      value = "/removeGoodsImage.do",
      method = {RequestMethod.POST})
  public void removeGoodsImage(
      @RequestParam("goods_id") int goods_id,
      @RequestParam("image_id") int image_id,
      @RequestParam("imageFileName") String imageFileName,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {

    adminGoodsService.removeGoodsImage(image_id);
    try {
      File srcFile = new File(currImageRepoPath + "\\" + goods_id + "\\" + imageFileName);
      srcFile.delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected List<ImageFileVO> upload(MultipartHttpServletRequest multipartRequest)
      throws Exception {
    List<ImageFileVO> fileList = new ArrayList<>();
    Iterator<String> fileNames = multipartRequest.getFileNames();
    while (fileNames.hasNext()) {
      ImageFileVO imageFileVO = new ImageFileVO();
      String fileName = fileNames.next();
      imageFileVO.setFileType(fileName);
      MultipartFile mFile = multipartRequest.getFile(fileName);
      String originalFileName = mFile.getOriginalFilename();
      imageFileVO.setFileName(originalFileName);
      fileList.add(imageFileVO);

      File file = new File(currImageRepoPath + File.separator + fileName);
      if (mFile.getSize() != 0) { // File Null Check
        if (!file.exists()) { // 경로상에 파일이 존재하지 않을 경우
          if (file.getParentFile().mkdirs()) { // 경로에 해당하는 디렉토리들을 생성
            file.createNewFile(); // 이후 파일 생성
          }
        }
        mFile.transferTo(
            new File(
                currImageRepoPath
                    + File.separator
                    + "temp"
                    + File.separator
                    + originalFileName)); // 임시로 저장된 multipartFile을 실제 파일로 전송
      }
    }
    return fileList;
  }
}
