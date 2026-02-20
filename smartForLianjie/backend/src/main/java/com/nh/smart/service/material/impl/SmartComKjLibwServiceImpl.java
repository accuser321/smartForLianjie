package com.nh.smart.service.material.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nh.smart.constant.BaseConstants;
import com.nh.smart.dao.material.*;
import com.nh.smart.dao.record.SmartComKjActionCountDao;
import com.nh.smart.dao.record.SmartComKjActionRecordDao;
import com.nh.smart.entity.material.SmartComKjLibw;
import com.nh.smart.entity.material.SmartComKjLibwEmpno;
import com.nh.smart.entity.material.SmartComModelsMobileImgs;
import com.nh.smart.entity.material.SmartComMuserCard;
import com.nh.smart.entity.record.SmartComWxmsg;
import com.nh.smart.service.material.SmartComKjLibwService;
import com.nh.smart.util.GenerateImgUtil;
import com.nh.smart.util.JwtTokenUtil;
import com.nh.smart.util.WXWordUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * smart_com_kj_libw#素材库 业务逻辑接口的实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmartComKjLibwServiceImpl extends ServiceImpl<SmartComKjLibwDao, SmartComKjLibw> implements SmartComKjLibwService {

    @Autowired
    private SmartComKjLibwDao smartComKjLibwDao;

    @Autowired
    private SmartComKjLibwEmpnoDao smartComKjLibwEmpnoDao;

    @Autowired
    private SmartComMuserCardDao smartComMuserCardDao;

    // 业务员行为次数dao
    @Autowired
    private SmartComKjActionCountDao smartComKjActionCountDao;

    @Autowired
    private SmartComKjActionRecordDao smartComKjActionRecordDao;

    @Autowired
    private SmartComEmpnoDao smartComEmpnoDao;
    @Autowired
    private SmartComModelsMobileImgsDao smartComModelsMobileImgsDao;

  /**
     * 素材列表查询
     *
     * @param map 参数集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/5
     */
    @Override
    public JSONObject selectPage(Map<String, Object> map) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String comid = JwtTokenUtil.getComid();
        String empno = JwtTokenUtil.getEmpno();

        // 分页参数封装
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());
        Page pageEntity = new Page(page, size);

        // 分类，类型
        String stagcode = (String) map.get("stagcode");
        String btagcode = (String) map.get("btagcode");

        // 搜索条件标题
        String stitle = null;
        if (!StringUtils.isEmpty(map.get("stitle"))) {
            stitle = (String) map.get("stitle");
        }
        // 张宁 大图显示
        String dt = null;
        if (map.get("dt") != null) {
            dt = map.get("dt").toString();
        }
        ;
        // 查询列表标识，0查询素材库，1查询业务员素材表
        Integer flag = Integer.parseInt(map.get("flag").toString());
        if (flag == 0) {
            // 查询素材库
            IPage<SmartComKjLibw> iPage = smartComKjLibwDao.selectPageByBtagcode(pageEntity, stagcode, btagcode, stitle, comid, dt);
            // 总条数
            jsonObject.put(BaseConstants.TOTAL, iPage.getTotal());
            // 每页数据
            jsonObject.put(BaseConstants.ROWS, iPage.getRecords());
            // 总页数
            jsonObject.put(BaseConstants.TOTAL_PAGE, iPage.getPages());
            return jsonObject;

        } else if (flag == 1) {
            // 查询业务员素材表
            IPage<SmartComKjLibwEmpno> iPage = smartComKjLibwEmpnoDao.selectPageByBtagcode(pageEntity, stagcode, btagcode, stitle, comid, empno);

            // 总条数
            jsonObject.put(BaseConstants.TOTAL, iPage.getTotal());
            // 每页数据
            jsonObject.put(BaseConstants.ROWS, iPage.getRecords());
            // 总页数
            jsonObject.put(BaseConstants.TOTAL_PAGE, iPage.getPages());
            return jsonObject;

        } else {
            return null;
        }
    }


    /**
     * 素材详情
     *
     * @param sno      素材编号
     * @param btagcode 素材类型编码
     * @param flag     查询详情标识，0查询素材库，1查询业务员素材表
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    @Override
    public SmartComKjLibwEmpno selectOneBySno(String sno, String btagcode, Integer flag) throws Exception {
        String comid = JwtTokenUtil.getComid();
        String empno = JwtTokenUtil.getEmpno();
//      String comid="NHSX";
//      String empno="TEST";
        SmartComKjLibwEmpno smartComKjLibwEmpno;
        if (flag == 0) {
          // 查询素材库素材地址
          smartComKjLibwEmpno = smartComKjLibwDao.selectOneBySno(comid, sno, btagcode);
          if (smartComKjLibwEmpno == null) {
            return null;
          }

          if ("1".equals(btagcode)) {
            // 以自定义规则生成业务员素材编号
            String osno = smartComKjLibwEmpno.getOsno();
            smartComKjLibwEmpno.setSno("E" + comid + empno + osno);
          }
          // 更新文库素材的阅读次数
          smartComKjLibwDao.updateYdnum(comid, sno);
          return smartComKjLibwEmpno;
        }

      if (flag == 1 || flag == 3) {
            // 查询业务员素材表素材地址
            smartComKjLibwEmpno = smartComKjLibwEmpnoDao.selectOneBySno(comid, sno, btagcode);
            // 如果查询的是文章，并且查询不到，给该业务员补上这篇文章
            if (smartComKjLibwEmpno == null) {
                if ("1".equals(btagcode)) {
                    String str = sno.replace("E", "").replace(comid, "");
                    int wz = str.lastIndexOf("WZ");
                    empno = str.substring(0, wz);
                    String osno = str.substring(wz);
                    smartComKjLibwEmpno = ForwardWZ(sno, osno, empno);
                    if (smartComKjLibwEmpno == null) {
                        return null;
                    }
                } else {
                    return null;
                }
            }

            // 处理标签
            ArrayList<String> list = new ArrayList<>();
            String tagcodeStr = smartComKjLibwEmpno.getTagcodeStr();
            if (!org.apache.commons.lang3.StringUtils.isBlank(tagcodeStr)) {
                String[] split = tagcodeStr.split(",");
                for (String s : split) {
                    list.add(s);
                }
            }
            smartComKjLibwEmpno.setBq(list);

            if (flag == 1) {
                if (!smartComKjLibwEmpno.getOsno().equals(smartComKjLibwEmpno.getSno())) {
                    smartComKjLibwDao.updateYdnum(comid, smartComKjLibwEmpno.getOsno());
                }
                smartComKjLibwEmpnoDao.updateYdnum(comid, sno);
            }
            return smartComKjLibwEmpno;
        }
        return null;
    }


    /**
     * 文章导入
     *
     * @param url   素材编号
     * @param bq    文章标签编码集合
     * @param comid 渠道编码
     * @param empno 业务员工号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    @Override
    public JSONObject importWZ(String url, List<String> bq, String comid, String empno) throws Exception {
        JSONObject jsonObject = new JSONObject();

        // 调用url工具类
        SmartComKjLibwEmpno result = WXWordUtil.getMessage(url, comid, empno);
        if (result == null) {
            jsonObject.put("type", 1);
            return jsonObject;
        }

        if (ObjectUtils.allNotNull(result)) {
            Date date = new Date();
            result.setComid(comid);
            result.setBtagcode("1");
            result.setFbtime(date);
            result.setYdnum(0);
            result.setSharenum(0);
            result.setStatus("0");
            result.setEmpno(empno);
            result.setOptime(date);
            result.setOsno(result.getSno());
            result.setAutor(empno);
            result.setReadonly("0");

            // 保存数据到渠道业务员材料表
            smartComKjLibwEmpnoDao.insert(result);

            // 保存数据到渠道素材标签表
            saveSCAndBQ(comid, empno, result.getSno(), bq);
            jsonObject.put("type", 3);
            jsonObject.put("obj", result);
            return jsonObject;
        }
        jsonObject.put("type", 2);
        return jsonObject;
    }


    /**
     * 文章制作
     *
     * @param title 文章标题
     * @param text  文章内容文本
     * @param bq    文章标签编码集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    @Override
    public void insertWZ(String title, String text, List<String> bq) throws Exception {
        String comid = JwtTokenUtil.getComid();
        String empno = JwtTokenUtil.getEmpno();

        Date date = new Date();
        String code = "EWZ" + IdWorker.getTimeId();
        // 上传文章文本到oss
        String wzurl = WXWordUtil.uploadhtml(code, text, comid);

        // 获取文章首图
        String pichttp = "";
        String ossUrl = "https://hxywtest.oss-cn-shenzhen.aliyuncs.com/";
        int i = text.indexOf("src=");
        if (i != -1) {
            pichttp = text.substring(i + 5);
            pichttp = pichttp.substring(0, pichttp.indexOf("\"")).replace(ossUrl, "");
        } else {
            ArrayList<String> list = new ArrayList<>();
            list.add("sharelogo");
            String setcode = " sharelogo";
            pichttp =  "NHWX/STYLEIMGS/comimg/logo/2020-03-21/QDLOGO20200321163840334178.jpg";
        }
        // 获取文章的最佳阅读时长
        Document wechartArticlDoc = Jsoup.parse(text);
        Elements content = wechartArticlDoc.select(".rich_media_content#js_content");
        String contenttext = content.text();
        String time = wechartArticlDoc.select(".rich_media_meta_list").select("rich_media_meta rich_media_meta_text").text();
        int timelength = 0;
        if (org.apache.commons.lang3.StringUtils.isEmpty(time)) {
            timelength = contenttext.length() / 400;
        }

        SmartComKjLibwEmpno smartComKjLibwEmpno = SmartComKjLibwEmpno.builder().comid(comid).btagcode("1").sno(code).stitle(title).pichttp(pichttp).conthttp(wzurl).
                autor(empno).readonly("0").fbtime(date).ydnum(0).sharenum(0).status("0").empno(empno).optime(date).osno(code).basttimes(timelength).build();

        // 保存数据到渠道业务员材料表
        smartComKjLibwEmpnoDao.insert(smartComKjLibwEmpno);

        // 保存数据到渠道素材标签表
        saveSCAndBQ(comid, empno, code, bq);
    }


    /**
     * 文章编辑
     *
     * @param sno   文章编号
     * @param osno  原始文章编号
     * @param title 文章标题
     * @param text  文章内容文本
     * @param bq    文章标签编码集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    @Override
    public void updateWZ(String sno, String osno, String title, String text, List<String> bq) throws Exception {
        String comid = JwtTokenUtil.getComid();
        String empno = JwtTokenUtil.getEmpno();

        // 上传文章文本到oss
        String code = "EWZ" + IdWorker.getTimeId();
        String wzurl = WXWordUtil.uploadhtml(code, text, comid);

        // 只更新stitle和conthttp即可
        SmartComKjLibwEmpno smartComKjLibwEmpno = SmartComKjLibwEmpno.builder().stitle(title).osno(sno).conthttp(wzurl).optime(new Date()).build();

        // 更新数据到渠道业务员材料表
        UpdateWrapper<SmartComKjLibwEmpno> smartComKjLibwEmpnoUpdateWrapper = new UpdateWrapper<>();
        smartComKjLibwEmpnoUpdateWrapper.eq("sno", sno).eq("comid", comid);
        smartComKjLibwEmpnoDao.update(smartComKjLibwEmpno, smartComKjLibwEmpnoUpdateWrapper);
        // 修改标签
        saveSCAndBQ(comid, empno, sno, bq);
    }

    /**
     * 文库文章转发
     *
     * @param sno   文章编号
     * @param osno  原始文章编号
     * @param empno 工号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/8
     */
    @Override
    public SmartComKjLibwEmpno ForwardWZ(String sno, String osno, String empno) throws Exception {
        String comid = JwtTokenUtil.getComid();
        // 先根据素材编号去业务员素材表中查询该业务员名下是否已经有这篇文章
        QueryWrapper<SmartComKjLibwEmpno> smartComKjLibwEmpnoQueryWrapper = new QueryWrapper<>();
        smartComKjLibwEmpnoQueryWrapper.eq("comid", comid).eq("btagcode", "1").eq("osno", osno).eq("status", "0").eq("autor", empno);
        Integer integer = smartComKjLibwEmpnoDao.selectCount(smartComKjLibwEmpnoQueryWrapper);
        // 如果有，说明该业务员名下已经有这篇文章，直接返回
        if (integer != 0) {
            return null;
        }

        Date date = new Date();
        // 根据素材编号查询素材库的文章信息和素材标签信息
        SmartComKjLibw smartComKjLibw = smartComKjLibwDao.selectWZInfoFromK(comid, osno);

        // 保存文章信息到业务员素材表
        SmartComKjLibwEmpno smartComKjLibwEmpno = SmartComKjLibwEmpno.builder().comid(comid).btagcode("1").sno(sno).osno(osno).stitle(smartComKjLibw.getStitle()).sdesc(smartComKjLibw.getSdesc()).
                pichttp(smartComKjLibw.getPichttp()).fbtime(date).autor(empno).conthttp(smartComKjLibw.getConthttp()).basttimes(smartComKjLibw.getBasttimes()).stagcode(smartComKjLibw.getStagcode()).
                readonly(smartComKjLibw.getReadonly()).ydnum(0).sharenum(0).status("0").empno(empno).optime(date).build();
        smartComKjLibwEmpnoDao.insert(smartComKjLibwEmpno);

        // 保存数据到渠道素材标签表
        saveSCAndBQ(comid, empno, sno, smartComKjLibw.getBq());
        return smartComKjLibwEmpno;
    }


    /**
     * 变成我的
     *
     * @param sno    文章编号
     * @param osno   原始文章编号
     * @param oempno 原始业务员编号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/12
     */
    @Override
    public String BecomeWZ(String sno, String osno, String oempno) throws Exception {
        String comid = JwtTokenUtil.getComid();
        String empno = JwtTokenUtil.getEmpno();

        // 先根据素材编号去业务员素材表中查询该业务员名下是否已经有这篇文章
        QueryWrapper<SmartComKjLibwEmpno> smartComKjLibwEmpnoQueryWrapper = new QueryWrapper<>();
        smartComKjLibwEmpnoQueryWrapper.eq("comid", comid).eq("btagcode", "1").eq("status", "0").eq("empno", empno);
        if (osno.startsWith("E")) {
            smartComKjLibwEmpnoQueryWrapper.eq("osno", sno);
        } else {
            smartComKjLibwEmpnoQueryWrapper.eq("osno", osno);
        }
        SmartComKjLibwEmpno libwEmpno = smartComKjLibwEmpnoDao.selectOne(smartComKjLibwEmpnoQueryWrapper);
        // 如果有，说明该业务员名下已经有这篇文章，直接返回
        if (libwEmpno != null) {
            return libwEmpno.getSno();
        }

        // 数据信息
        Date date = new Date();
        SmartComKjLibwEmpno smartComKjLibwEmpno;
        List<String> bq;
        String code = "EWZ" + IdWorker.getTimeId();

        // 如果原始文章编号以"E"开头，则说明是业务员自己的文章，否则是文库文章
        if (osno.startsWith("E")) {
            // 根据素材编号查询业务员素材表的文章信息和素材标签信息
            SmartComKjLibwEmpno info = smartComKjLibwEmpnoDao.selectWZInfoFromE(comid, sno, oempno);
            bq = info.getBq();
            String stitle = info.getStitle();
            String sdesc = info.getSdesc();
            String pichttp = info.getPichttp();
            String conthttp = info.getConthttp();
            Integer basttimes = info.getBasttimes();

            smartComKjLibwEmpno = SmartComKjLibwEmpno.builder().comid(comid).btagcode("1").sno(code).osno(sno).stitle(stitle).sdesc(sdesc).autor(empno).readonly("1").
                    pichttp(pichttp).conthttp(conthttp).basttimes(basttimes).fbtime(date).ydnum(0).sharenum(0).status("0").empno(empno).optime(date).build();

        } else {
            // 根据素材编号查询素材库的文章信息和素材标签信息
            SmartComKjLibw info = smartComKjLibwDao.selectWZInfoFromK(comid, osno);
            bq = info.getBq();
            String stitle = info.getStitle();
            String sdesc = info.getSdesc();
            String pichttp = info.getPichttp();
            String conthttp = info.getConthttp();
            Integer basttimes = info.getBasttimes();
            String stagcode = info.getStagcode();
            String readonly = info.getReadonly();

            // 保存文章信息到业务员素材表
            smartComKjLibwEmpno = SmartComKjLibwEmpno.builder().comid(comid).btagcode("1").sno(code).osno(osno).stitle(stitle).sdesc(sdesc).pichttp(pichttp).fbtime(date).
                    autor(empno).conthttp(conthttp).basttimes(basttimes).stagcode(stagcode).readonly(readonly).ydnum(0).sharenum(0).status("0").empno(empno).optime(date).build();
        }

        // 保存文章信息到业务员素材表
        smartComKjLibwEmpnoDao.insert(smartComKjLibwEmpno);
        // 保存数据到渠道素材标签表
        saveSCAndBQ(comid, empno, code, bq);
        return code;
    }


    /**
     * 我的素材列表删除
     *
     * @param sno 素材编号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/8
     */
    @Override
    public void delete(String sno) throws Exception {
        String comid = JwtTokenUtil.getComid();
        String empno = JwtTokenUtil.getEmpno();
        smartComKjLibwEmpnoDao.deleteBySno(sno, comid, empno);
    }

  /**
     * 保存渠道素材标签表
     *
     * @param comid 渠道编码
     * @param empno 工号
     * @param sno   素材编号
     * @param bq    文章标签编码集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    @Override
    public void saveSCAndBQ(String comid, String empno, String sno, List<String> bq) throws Exception {
        // 先删除原有标签素材关系
        smartComKjLibwEmpnoDao.deleteBQBySno(comid, sno);
        if (bq != null && bq.size() > 0) {
            Date date = new Date();
            List<HashMap<String, Object>> list = new ArrayList<>();

            for (String s : bq) {
                // 再保存关系到表中
                HashMap<String, Object> map = new HashMap<>(5);
                map.put("comid", comid);
                map.put("sno", sno);
                map.put("tagcode", s);
                map.put("operno", empno);
                map.put("optime", date);
                map.put("tagtype", "1");
                list.add(map);
            }
            smartComKjLibwEmpnoDao.batchSave(list);
        }
    }

    @Override
    public List<Map<String, Object>> selectKJFL(String tagcode) throws Exception {
      String comid = JwtTokenUtil.getComid();
      return smartComKjLibwDao.selectKJFL(tagcode, comid);
    }




  /**
   * 查询业务员名片
   *
   * @param empno      用户工号
   * @param flag       是否需要查询风采照片，0不查询代表在文章里面调用名片接口，1查询代表访问的是AI名片页面
   * @param type       用户身份标识，W外勤，N内勤
   * @param serverName 当前访问的域名
   * @return
   * @throws Exception
   * @author 王名渤
   * @date 2019/11/6
   */
  @Override
  public SmartComMuserCard selectUserCard(String empno, String flag, String type, String serverName) throws Exception {
    // 渠道编码
    String comid = JwtTokenUtil.getComid();

    int dznum = 0;
    Boolean isdz = false;
    // 封装数据返回
    JSONObject jsonObject = new JSONObject();

    // 查询业务员名片信息
    SmartComMuserCard smartComMuserCard = smartComMuserCardDao.selectUserCard(empno, comid, type);

    // 查询用户信息
    Map<String, Object> userInfo = smartComMuserCardDao.getUserInfo(comid, type, empno);
    Integer userId = (Integer) userInfo.get("userid");
    String headimg = userInfo.get("headimg").toString();

    // 名片二维码路径
    String wxewmurl = serverName + "/visitingcard?empno=" + empno + "&suserid=" + userId + "&isshare=1&rytype=" + type;

    // 风采照片
    String defaultPhoto = null;
    // 微官网
    String webSite = null;
    // 如果业务员名片信息不为空，查询名片照片再返回
    if (!org.springframework.util.ObjectUtils.isEmpty(smartComMuserCard) && smartComMuserCard.getCardempname() != null) {
      if ("1".equals(flag)) {
        // 设置微官网
        smartComMuserCard.setWebSite(webSite);

        List<String> photoList = smartComMuserCardDao.selectPhotoList(empno, comid, BaseConstants.CARD_ENUM);
        if (photoList != null && photoList.size() > 0) {
          smartComMuserCard.setPhoto(photoList);
        } else {
          // 如果名片照片为空，则设置默认风采照片为名片照片
          if (defaultPhoto != null) {
            ArrayList<String> list = new ArrayList<>();
            list.add(defaultPhoto);
            smartComMuserCard.setPhoto(list);
          }
        }

        Integer userid = JwtTokenUtil.getUserid();
        // 计算名片或赞数
        dznum = smartComKjActionCountDao.selectMpDzByEmpno(comid, empno, userid);
        // 查询用户是否已经点赞
        isdz = smartComKjActionRecordDao.selectDZFlag(comid, empno, userid) > 0 ? true : false;
        smartComMuserCard.setDznum(dznum);
        smartComMuserCard.setIsdz(isdz);
        // 统计多少个人浏览过
        Page<SmartComWxmsg> pageModel = new Page<>(1, 3);
        IPage<Map<String, Object>> result = smartComKjActionRecordDao.selectCardKH(pageModel, comid, empno);
        // 总条数
        jsonObject.put(BaseConstants.TOTAL, result.getTotal());
        // 每页数据
        jsonObject.put(BaseConstants.ROWS, result.getRecords());
        smartComMuserCard.setHeadimgArray(jsonObject);
      }

      String qrcodeImg = GenerateImgUtil.generateQRcodeImg(wxewmurl);
      smartComMuserCard.setMpurl(qrcodeImg);
      smartComMuserCard.setUserid(userId);
      return smartComMuserCard;
    }

    // 如果业务员名片信息为空，则业务员为第一次访问，给业务员创建名片信息
    // 姓名
    String cardempname;
    // 手机号
    String cardmobile = "";
    // 性别
    String cardsex = "";
    // 电子邮箱
    String email = "";
    // 名片职位
    String carddegreeno = "";

    if ("N".equals(type)) {
      // 外勤，查询业务员表的用户信息
      Map<String, String> empnoInfo = smartComEmpnoDao.selectInfoByEmpno(empno, comid);
      cardempname = empnoInfo.get("empname");
      cardsex = empnoInfo.get("sex") == null ? "" : empnoInfo.get("sex");
      cardmobile = empnoInfo.get("phone") == null ? "" : empnoInfo.get("phone");
      email = empnoInfo.get("email") == null ? "" : empnoInfo.get("email");
    }else {
      return null;
    }

    SmartComMuserCard comMuserCard =SmartComMuserCard.builder().comid(comid).empno(empno).cardempname(cardempname).headimg(headimg).cardsex(cardsex).
      cardstyle("1").cardmobile(cardmobile).workarea("").email(email).pdesc("").wxnumber("").wxewmurl("").carddegreeno(carddegreeno).flushtime(new Date()).build();

    // 保存到名片表中
    smartComMuserCardDao.insert(comMuserCard);

    if ("1".equals(flag)) {
      // 设置微官网
      comMuserCard.setWebSite(webSite);

      // 设置默认风采照片
      if (defaultPhoto != null) {
        ArrayList<String> list = new ArrayList<>();
        list.add(defaultPhoto);
        comMuserCard.setPhoto(list);
      }

      comMuserCard.setDznum(dznum);
      comMuserCard.setIsdz(isdz);
      comMuserCard.setHeadimgArray(jsonObject);
    }

    String qrcodeImg = GenerateImgUtil.generateQRcodeImg(wxewmurl);
    comMuserCard.setMpurl(qrcodeImg);
    comMuserCard.setUserid(userId);
    // 官网
    comMuserCard.setGwbrowsenum(smartComMuserCard.getGwbrowsenum());
    comMuserCard.setGwforwardnum(smartComMuserCard.getGwforwardnum());
    // 名片
    comMuserCard.setMpbrowsenum(smartComMuserCard.getMpbrowsenum());
    comMuserCard.setMpforwardnum(smartComMuserCard.getMpforwardnum());
    return comMuserCard;
  }


  /**
   * 手机端图片列表
   *
   * @return: List<SmartComModelsMobileImgs>
   * @Author: 刘阳光
   * @Date: 2020/01/19
   */
  @Override
  public Map pictureList() throws Exception {
    //获取comid
    String comid= JwtTokenUtil.getComid();
    List<SmartComModelsMobileImgs> smartComModelsMobileImgs=  smartComModelsMobileImgsDao.pictureList(comid);
    Map Imgmap = new HashMap<>();
    for (SmartComModelsMobileImgs Img : smartComModelsMobileImgs) {
      Imgmap.put(Img.getImgcode(), Img.getImgurl());
    }
    return Imgmap;
  }

}
