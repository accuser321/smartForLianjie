package com.nh.smart.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.nh.smart.dao.record.SmartComMuserDao;
import com.nh.smart.exception.MyExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.PostConstruct;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
/**
 * @ClassName OSSUtil
 * @Author: LY
 * @Date: 2019/9/23 10:55
 */
@Configuration
@Component
public class OSSUtil {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    /**
     * 阿里云API的内或外网域名
     */
    private static String endpoint="oss-cn-shenzhen.aliyuncs.com";

    @Autowired
    private static OSSUtil ossUtil;


    @Autowired
    private SmartComMuserDao smartComMuserDao;

    @PostConstruct
    public void init() {
        ossUtil = this;
    }

    /**
     * 上传图片至OSS
     */
    public static JSONObject uploadFile2OSS(MultipartFile file, String prefix, String path, String comid) throws Exception {
        // 获取OSS实例
        Map<String, String> map =ossUtil.smartComMuserDao.getWxInfo(comid);;
        String ossaccesskeyid = map.get("ossaccesskeyid");
        String ossaccesskeysecret = map.get("ossaccesskeysecret");
        String ossbucket = map.get("ossbucket");
        OSSClient ossClient = new OSSClient(endpoint, ossaccesskeyid, ossaccesskeysecret);
        String downFileName;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        // 拼接路径，格式为：渠道/系统/分类/日期/xxx文件
        path = comid + "/" + path + "/" + date + "/";

        JSONObject result = new JSONObject();
        try {
            // 以输入流的形式上传文件
            InputStream is = file.getInputStream();
            // 文件名
            String fileName = file.getOriginalFilename();

            // 文件大小
            Long fileSize = file.getSize();
            if (fileSize > 1024 * 1024 * 10) {
                result.put("msg", "文件不能大于10M");
                return result;
            }

            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            // 上传的文件的长度
            metadata.setContentLength(is.available());
            // 指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            // 指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            // 指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");

            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            // 如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));

            // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            // 如果前缀为空则不使用我们定义的文件名生成规则，使用它原来的名称
            if (StringUtils.isBlank(prefix)) {
                downFileName = fileName;
            } else {
                // 获取文件后缀
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                downFileName = prefix + IdWorker.getTimeId() + "." + suffix;
            }

            metadata.setContentDisposition("filename/filesize=" + downFileName + "/" + fileSize + "Byte.");

            // 上传文件 (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(ossbucket, path + downFileName, is, metadata);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
            throw MyExceptionUtil.mxe("上传失败！");
        }
        String shortUrl = path + downFileName;
        String longUrl = OSSUtil.getPrivateUrl(ossbucket, ossClient, shortUrl);

        result.put("url", longUrl);
        result.put("path", shortUrl);
        return result;
    }

    /**
     * 上传图片输入流到OSS
     *
     * @param instream
     * @return
     */
    public static JSONObject uploadStream2OSS(InputStream instream, String prefix, String path, String str) throws Exception {
        // 获取OSS实例

        String comId = JwtTokenUtil.getComid();
        Map<String, String> map =ossUtil.smartComMuserDao.getWxInfo(comId);;
        String ossaccesskeyid = map.get("ossaccesskeyid");
        String ossaccesskeysecret = map.get("ossaccesskeysecret");
        String ossbucket = map.get("ossbucket");
        OSSClient ossClient = new OSSClient(endpoint, ossaccesskeyid, ossaccesskeysecret);

        String downFileName;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        // 拼接路径，格式为：渠道/系统/分类/日期/xxx文件
        path = comId + "/" + path + "/" + date + "/";

        JSONObject result = new JSONObject();
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + str;
        try {
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            // 上传的文件的长度
            metadata.setContentLength(instream.available());
            // 指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            // 指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            // 指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");

            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            // 如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));

            // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            // 如果前缀为空则不使用我们定义的文件名生成规则，使用它原来的名称
            if (StringUtils.isBlank(prefix)) {
                downFileName = fileName;
            } else {
                // 获取文件后缀
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                downFileName = prefix + IdWorker.getTimeId() + "." + suffix;
            }
            metadata.setContentDisposition("filename/filesize=" + downFileName + "/" + instream.available() + "Byte.");
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(ossbucket, path + downFileName, instream, metadata);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
            throw MyExceptionUtil.mxe("上传失败！");
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String shortUrl = path + downFileName;
        String longUrl = OSSUtil.getPrivateUrl(ossbucket, ossClient, shortUrl);

        result.put("url", longUrl);
        result.put("path", shortUrl);
        return result;
    }


    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        // 文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".png".equalsIgnoreCase(fileExtension)) {
            return "image/png";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if (".pdf".equalsIgnoreCase(fileExtension)) {
            return "application/pdf";
        }
        if (".mp4".equalsIgnoreCase(fileExtension)) {
            return "video/mpeg4";
        }
        // 默认返回类型
        return "";
    }

    /**
     * base64位格式文件转输入流
     *
     * @param img
     * @return
     * @throws Exception
     */
    public static InputStream base64InputStream(String img) throws Exception {
        String base = img.substring(img.indexOf(",") + 1);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = decoder.decodeBuffer(base);
        SerialBlob serialBlob = new SerialBlob(imageByte);
        InputStream inputStream = serialBlob.getBinaryStream();
        return inputStream;
    }


    /**
     * 获得私有url链接
     *
     * @param fileName
     * @return
     */
    public static String getPrivateUrl(String ossbucket, OSSClient ossClient, String fileName) {
        // 设置URL过期时间为100年 3600l* 1000*24*365*100
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(ossbucket, fileName, expiration);
        if (url != null) {
            return StringUtil.subString(url.toString(), "h", "?");
        }
        return null;
    }


    /**
     * 判断文件是否在oss上存在
     *
     * @param comid    渠道编码
     * @param fileName 文件全路径
     * @return
     */
    public static boolean doesObjectExist(String comid, String fileName) throws Exception {
        // 获取OSS实例
        Map<String, String> map =ossUtil.smartComMuserDao.getWxInfo(comid);;
        String ossaccesskeyid = map.get("ossaccesskeyid");
        String ossaccesskeysecret = map.get("ossaccesskeysecret");
        String ossbucket = map.get("ossbucket");
        OSSClient ossClient = new OSSClient(endpoint, ossaccesskeyid, ossaccesskeysecret);
        return ossClient.doesObjectExist(ossbucket, fileName);
    }


    /**
     * 下载文件
     */
    public static void downloadfile(String objectName, String filepath) throws Exception {
        // 获取OSS实例
        String comId = JwtTokenUtil.getComid();
        Map<String, String> map =ossUtil.smartComMuserDao.getWxInfo(comId);;
        String ossaccesskeyid = map.get("ossaccesskeyid");
        String ossaccesskeysecret = map.get("ossaccesskeysecret");
        String ossbucket = map.get("ossbucket");
        OSSClient ossClient = new OSSClient(endpoint, ossaccesskeyid, ossaccesskeysecret);

        ossClient.getObject(new GetObjectRequest(ossbucket, objectName), new File(filepath));
    }
}
