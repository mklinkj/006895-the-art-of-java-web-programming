package com.bookshop01.common.file;

import com.bookshop01.common.util.ProjectDataUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileDownloadController {
  private final String currImageRepoPath = ProjectDataUtils.getProperty("image_repo_path");

  @RequestMapping("/download")
  protected void download(
      @RequestParam("fileName") String fileName,
      @RequestParam("goods_id") String goods_id,
      HttpServletResponse response)
      throws Exception {
    OutputStream out = response.getOutputStream();
    String filePath = currImageRepoPath + File.separator + goods_id + File.separator + fileName;
    File image = new File(filePath);

    response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
    response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + fileName);
    FileInputStream in = new FileInputStream(image);
    byte[] buffer = new byte[1024 * 8];
    while (true) {
      int count = in.read(buffer); // 버퍼에 읽어들인 문자개수
      if (count == -1) // 버퍼의 마지막에 도달했는지 체크
      break;
      out.write(buffer, 0, count);
    }
    in.close();
    out.close();
  }

  @RequestMapping("/thumbnails.do")
  protected void thumbnails(
      @RequestParam("fileName") String fileName,
      @RequestParam("goods_id") String goods_id,
      HttpServletResponse response)
      throws Exception {
    OutputStream out = response.getOutputStream();
    String filePath = currImageRepoPath + File.separator + goods_id + File.separator + fileName;
    File image = new File(filePath);

    if (image.exists()) {
      Thumbnails.of(image).size(121, 154).outputFormat("png").toOutputStream(out);
    }
    byte[] buffer = new byte[1024 * 8];
    out.write(buffer);
    out.close();
  }
}
