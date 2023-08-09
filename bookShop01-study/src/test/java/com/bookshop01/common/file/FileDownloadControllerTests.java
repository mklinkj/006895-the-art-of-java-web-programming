package com.bookshop01.common.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.File;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringJUnitWebConfig(
    locations = {
      "classpath:config/spring/root-context.xml",
      "classpath:config/spring/servlet-context.xml"
    })
class FileDownloadControllerTests {
  private final String IMAGE_FILE_NAME_450_GOODS = "450_goods_image.png";

  private final int goodsId = 450;

  @TempDir private File tempDir;

  @Autowired private FileDownloadController fileDownloadController;

  @Autowired private WebApplicationContext context;

  private MockMvc mockMvc;

  @BeforeEach
  void beforeEach() {
    mockMvc = webAppContextSetup(context).build();
    LOGGER.info("### tempDir: {}", tempDir);
    ReflectionTestUtils.setField(
        fileDownloadController, "currImageRepoPath", tempDir.getAbsolutePath());
  }

  /* 이미지 파일명과 상품 아이디로 이미지를 다운로드 한다. */
  @Test
  void download() throws Exception {
    initUploadFile();

    mockMvc
        .perform(
            get("/download") //
                .param("fileName", IMAGE_FILE_NAME_450_GOODS)
                .param("goods_id", Integer.toString(goodsId))) //
        .andDo(print())
        .andExpect(
            result -> {
              assertThat(result.getResponse().getStatus()) //
                  .isEqualTo(HttpStatus.OK.value());
              assertThat(result.getResponse().getContentType()) //
                  .isNotEqualTo(MediaType.IMAGE_PNG_VALUE)
                  .describedAs("컨트롤러 메서드에서 명시적으로 설정을 하지 않아서 null");
              assertThat(result.getResponse().getHeader(HttpHeaders.CONTENT_DISPOSITION)) //
                  .isEqualTo("attachment; fileName=%s".formatted(IMAGE_FILE_NAME_450_GOODS));
            });
  }

  /*
   이미지 파일명과 상품 아이디로 이미지의 썸네일을 스트림으로 받는다. 다운로드나 마찬가지이긴 할 텐데..
  */
  @Test
  void thumbnails() throws Exception {
    initUploadFile();

    mockMvc
        .perform(
            get("/thumbnails.do") //
                .param("fileName", IMAGE_FILE_NAME_450_GOODS)
                .param("goods_id", Integer.toString(goodsId))) //
        .andDo(print())
        .andExpect(
            result -> {
              assertThat(result.getResponse().getStatus()) //
                  .isEqualTo(HttpStatus.OK.value());
              assertThat(result.getResponse().getContentType()) //
                  .isNotEqualTo(MediaType.IMAGE_PNG_VALUE)
                  .describedAs("컨트롤러 메서드에서 명시적으로 설정을 하지 않아서 null");
            });
  }

  /*
    JUnit이 관리하는 임시 디렉토리에 상품 이미지 파일 초기화
    임시 디렉토리는 테스트 메서드가 완료되면 자동으로 삭제된다.
  */
  private void initUploadFile() throws Exception {
    ClassPathResource imageResource =
        new ClassPathResource(
            "/upload_test_file/%s/%s".formatted(goodsId, IMAGE_FILE_NAME_450_GOODS));

    File goodsImageDir = new File(tempDir + File.separator + goodsId);
    if (!goodsImageDir.mkdirs()) {
      throw new IllegalStateException("JUnit 임시 폴더 생성 실패");
    }
    LOGGER.info("### goodsImageDir: {}", goodsImageDir);
    File imageFile = new File(goodsImageDir, IMAGE_FILE_NAME_450_GOODS);

    Files.copy(imageResource.getFile().toPath(), imageFile.toPath());
  }
}
