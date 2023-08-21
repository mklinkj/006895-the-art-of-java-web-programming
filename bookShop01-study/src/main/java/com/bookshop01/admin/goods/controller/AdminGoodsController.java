package com.bookshop01.admin.goods.controller;

import static com.bookshop01.common.util.DateUtils.calcSearchPeriod;

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
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/admin/goods")
public class AdminGoodsController extends BaseController {
  private final String currImageRepoPath = ProjectDataUtils.getProperty("image_repo_path");
  private final AdminGoodsService adminGoodsService;

  @RequestMapping(
      value = "/adminGoodsMain.do",
      method = {RequestMethod.POST, RequestMethod.GET})
  public void adminGoodsMain(@RequestParam Map<String, String> paramMap, Model model) {

    String fixedSearchPeriod = paramMap.get("fixedSearchPeriod");
    String section = paramMap.get("section");
    String pageNum = paramMap.get("pageNum");
    String beginDate, endDate;

    String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
    // yyyy-MM-dd 형식 문자열이 입력된다.
    beginDate = tempDate[0];
    endDate = tempDate[1];
    paramMap.put("beginDate", beginDate);
    paramMap.put("endDate", endDate);

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

    if (paramMap.get("search_type") != null) {
      condMap.put("search_type", paramMap.get("search_type"));
      condMap.put("search_word", paramMap.get("search_word"));
      beginDate =
          "%s-%s-%s"
              .formatted(
                  paramMap.get("beginYear"), paramMap.get("beginMonth"), paramMap.get("beginDay"));
      condMap.put("beginDate", beginDate);
      endDate =
          "%s-%s-%s"
              .formatted(paramMap.get("endYear"), paramMap.get("endMonth"), paramMap.get("endDay"));
      condMap.put("endDate", endDate);

      model.addAttribute("search_type", paramMap.get("search_type"));
      model.addAttribute("search_word", paramMap.get("search_word"));
    }

    List<GoodsVO> newGoodsList = adminGoodsService.listNewGoods(condMap);
    model.addAttribute("newGoodsList", newGoodsList);

    String beginDate1[] = beginDate.split("-");
    String endDate2[] = endDate.split("-");
    model.addAttribute("beginYear", beginDate1[0]);
    model.addAttribute("beginMonth", beginDate1[1]);
    model.addAttribute("beginDay", beginDate1[2]);
    model.addAttribute("endYear", endDate2[0]);
    model.addAttribute("endMonth", endDate2[1]);
    model.addAttribute("endDay", endDate2[2]);

    model.addAttribute("section", section);
    model.addAttribute("pageNum", pageNum);
  }

  @RequestMapping(
      value = "/addNewGoods.do",
      method = {RequestMethod.POST})
  @ResponseBody
  public ResponseEntity<String> addNewGoods(MultipartHttpServletRequest multipartRequest)
      throws Exception {
    String imageFileName;

    Map<String, Object> newGoodsMap = new HashMap<>();
    Enumeration<String> enu = multipartRequest.getParameterNames();
    while (enu.hasMoreElements()) {
      String name = enu.nextElement();
      String value = multipartRequest.getParameter(name);
      newGoodsMap.put(name, value);
    }

    HttpSession session = multipartRequest.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String regId = memberVO.getMember_id();

    List<ImageFileVO> imageFileList = upload(multipartRequest);
    if (imageFileList != null && !imageFileList.isEmpty()) {
      for (ImageFileVO imageFileVO : imageFileList) {
        imageFileVO.setReg_id(regId);
      }
      newGoodsMap.put("imageFileList", imageFileList);
    }

    String message;
    ResponseEntity resEntity;
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
    try {
      int goods_id = adminGoodsService.addNewGoods(newGoodsMap);
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile =
              new File(
                  currImageRepoPath + File.separator + "temp" + File.separator + imageFileName);
          File destDir = new File(currImageRepoPath + File.separator + goods_id);
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
      if (imageFileList != null && !imageFileList.isEmpty()) {
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile =
              new File(
                  currImageRepoPath + File.separator + "temp" + File.separator + imageFileName);
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
      LOGGER.error(e.getMessage(), e);
    }
    resEntity = new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    return resEntity;
  }

  @RequestMapping(
      value = "/modifyGoodsForm.do",
      method = {RequestMethod.GET, RequestMethod.POST})
  public void modifyGoodsForm(@RequestParam("goods_id") int goodsId, Model model) {
    Map<String, ?> goodsMap = adminGoodsService.goodsDetail(goodsId);
    model.addAttribute("goodsMap", goodsMap);
  }

  @RequestMapping(
      value = "/modifyGoodsInfo.do",
      method = {RequestMethod.POST})
  @ResponseBody
  public ResponseEntity<String> modifyGoodsInfo(
      @RequestParam("goods_id") String goodsId,
      @RequestParam("attribute") String attribute,
      @RequestParam("value") String value) {

    Map<String, String> goodsMap = new HashMap<>();
    goodsMap.put("goods_id", goodsId);
    goodsMap.put(attribute, value);
    adminGoodsService.modifyGoodsInfo(goodsMap);

    HttpHeaders responseHeaders = new HttpHeaders();
    String message = "mod_success";
    return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
  }

  @RequestMapping(
      value = "/modifyGoodsImageInfo.do",
      method = {RequestMethod.POST})
  public void modifyGoodsImageInfo(MultipartHttpServletRequest multipartRequest) {
    LOGGER.info("### modifyGoodsImageInfo ###");

    String imageFileName;
    Map<String, Object> goodsMap = new HashMap<>();
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
    int goodsId = 0;
    int imageId;
    try {
      imageFileList = upload(multipartRequest);
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          goodsId = Integer.parseInt((String) goodsMap.get("goods_id"));
          imageId = Integer.parseInt((String) goodsMap.get("image_id"));
          imageFileVO.setGoods_id(goodsId);
          imageFileVO.setImage_id(imageId);
          imageFileVO.setReg_id(reg_id);
        }

        adminGoodsService.modifyGoodsImage(imageFileList);
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile =
              new File(
                  currImageRepoPath + File.separator + "temp" + File.separator + imageFileName);
          File destDir = new File(currImageRepoPath + File.separator + goodsId);
          FileUtils.moveFileToDirectory(srcFile, destDir, true);
        }
      }
    } catch (Exception e) {
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile =
              new File(
                  currImageRepoPath + File.separator + "temp" + File.separator + imageFileName);
          srcFile.delete();
        }
      }
      LOGGER.error(e.getMessage(), e);
    }
  }

  @RequestMapping(
      value = "/addNewGoodsImage.do",
      method = {RequestMethod.POST})
  public void addNewGoodsImage(MultipartHttpServletRequest multipartRequest) {
    System.out.println("addNewGoodsImage");
    String imageFileName;

    Map<String, Object> goodsMap = new HashMap<>();
    Enumeration<String> enu = multipartRequest.getParameterNames();
    while (enu.hasMoreElements()) {
      String name = enu.nextElement();
      String value = multipartRequest.getParameter(name);
      goodsMap.put(name, value);
    }

    HttpSession session = multipartRequest.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String regId = memberVO.getMember_id();

    List<ImageFileVO> imageFileList = null;
    int goodsId = 0;
    try {
      imageFileList = upload(multipartRequest);
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          goodsId = Integer.parseInt((String) goodsMap.get("goods_id"));
          imageFileVO.setGoods_id(goodsId);
          imageFileVO.setReg_id(regId);
        }

        adminGoodsService.addNewGoodsImage(imageFileList);
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile =
              new File(
                  currImageRepoPath + File.separator + "temp" + File.separator + imageFileName);
          File destDir = new File(currImageRepoPath + File.separator + goodsId);
          FileUtils.moveFileToDirectory(srcFile, destDir, true);
        }
      }
    } catch (Exception e) {
      if (imageFileList != null && imageFileList.size() != 0) {
        for (ImageFileVO imageFileVO : imageFileList) {
          imageFileName = imageFileVO.getFileName();
          File srcFile =
              new File(
                  currImageRepoPath + File.separator + "temp" + File.separator + imageFileName);
          srcFile.delete();
        }
      }
      LOGGER.error(e.getMessage(), e);
    }
  }

  @RequestMapping(
      value = "/removeGoodsImage.do",
      method = {RequestMethod.POST})
  public void removeGoodsImage(
      @RequestParam("goods_id") int goodsId,
      @RequestParam("image_id") int imageId,
      @RequestParam("imageFileName") String imageFileName) {

    adminGoodsService.removeGoodsImage(imageId);
    try {
      File srcFile =
          new File(currImageRepoPath + File.separator + goodsId + File.separator + imageFileName);
      srcFile.delete();
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
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
