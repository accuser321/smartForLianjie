package com.nh.smart.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import static com.google.zxing.client.j2se.MatrixToImageConfig.BLACK;
import static com.google.zxing.client.j2se.MatrixToImageConfig.WHITE;

/**
 * @description:
 * @author: ww
 * @create: 2020/5/19 17:15
 **/
public class GenerateImgUtil {

  /**
   * 生成二维码
   *
   * @param mpurl 二维码信息url
   * @return
   * @author 王名渤
   * @date 2019-11-13 17:06
   */
  public static String generateQRcodeImg(String mpurl) throws Exception {
    // 生成二维码
    BufferedImage bufferedImage = createQRcode(mpurl);

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    String imageString = null;
    try {
      ImageIO.write(bufferedImage, "png", bos);
      byte[] imageBytes = bos.toByteArray();
      BASE64Encoder encoder = new BASE64Encoder();
      imageString = encoder.encode(imageBytes);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      bos.close();
    }
    return imageString;
  }



  /**
   * 生成名片二维码
   *
   * @param url 二维码内容
   * @return 返回新image
   */
  public static BufferedImage createQRcode(String url) throws Exception {
    int width = 300;
    int height = 300;

    Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    hints.put(EncodeHintType.MARGIN, 0);
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);

    // 生成二维码
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
      }
    }
        /*File f = new File("E:/test.jpg");
        ImageIO.write(image, "jpg", f);*/
    return image;
  }
}
