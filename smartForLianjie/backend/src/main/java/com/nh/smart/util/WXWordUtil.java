package com.nh.smart.util;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hankcs.hanlp.HanLP;
import com.nh.smart.constant.BaseConstants;
import com.nh.smart.entity.material.SmartComKjLibwEmpno;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WXWordUtil {

    /**
     * @Param: [url]
     * @return: java.lang.String
     * @Author: 王明义
     * @Date: 2019/10/15
     * 文章内容获取
     */
    public static String userWXUrlGetMessage(String url) {

        StringBuilder pageHTML = new StringBuilder();
        try {
            URL urls = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            connection.setRequestProperty("User-Agent", "MSIE&nbsp;7.0");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            String line;
            while ((line = br.readLine()) != null) {
                pageHTML.append(line);
                pageHTML.append("\r\n");
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageHTML.toString();
    }


    /**
     * @Param: [html]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: 王明义
     * @Date: 2019/10/15
     * 文章内容解析
     */
    public static SmartComKjLibwEmpno getMessage(String url, String comid, String empno) throws Exception {
        // 返回结果实体
      SmartComKjLibwEmpno smartComKjLibwEmpno = new SmartComKjLibwEmpno();

        // 判断url是否为微信url
        if (!url.split("/")[2].equals("mp.weixin.qq.com")) {
            return null;
        }

        String html = WXWordUtil.userWXUrlGetMessage(url);
        // 解析请求体获取文章标题 、摘要、描述、图片、阅读时长
        Document wechartArticlDoc = Jsoup.parse(html);

        // 音频处理
        Elements mpvoice = wechartArticlDoc.getElementsByTag("mpvoice");
        if (!mpvoice.isEmpty()) {
            String voice_name = mpvoice.attr("name");
            String voice_path = "https://res.wx.qq.com/voice/getvoice?mediaid=" + mpvoice.attr("voice_encode_fileid") + "&voice_type=1";
            String voice_length = (int) Math.floor(Integer.valueOf(mpvoice.attr("play_length")) / 60000) + ":" + (int) (Math.floor(Integer.valueOf(mpvoice.attr("play_length")) % 60000) / 1000);
            String voice_key = "0";
            String html11 = "<style>\n" +
                    "                .audio-wrapper {\n" +
                    "                    background-color: #fcfcfc;\n" +
                    "                    margin: 10px auto;\n" +
                    "                    max-width: 670px;\n" +
                    "                    height: 70px;\n" +
                    "                    border: 1px solid #e0e0e0;\n" +
                    "                }\n" +
                    "\n" +
                    "                .audio-left {\n" +
                    "                    float: left;\n" +
                    "                    text-align: center;\n" +
                    "                    width: 18%;\n" +
                    "                    height: 100%;\n" +
                    "                }\n" +
                    "\n" +
                    "                .audio-left img {\n" +
                    "                    width: 40px;\n" +
                    "                    position: relative;\n" +
                    "                    top: 15px;\n" +
                    "                    margin: 0;\n" +
                    "                    display: initial;   /* 解除与app的样式冲突 */\n" +
                    "                    cursor: pointer;\n" +
                    "                }\n" +
                    "\n" +
                    "                .audio-right {\n" +
                    "                    margin-right: 2%;\n" +
                    "                    margin-top: 10px;\n" +
                    "                    float: right;\n" +
                    "                    width: 80%;\n" +
                    "                    height: 100%;\n" +
                    "                }\n" +
                    "\n" +
                    "                .audio-right p {\n" +
                    "                    font-size: 12px;\n" +
                    "                    height: 22%;\n" +
                    "                    margin: 8px 0;\n" +
                    "\n" +
                    "                    /* 歌曲名称只显示在一行，超出部分显示为省略号 */\n" +
                    "                    overflow: hidden;\n" +
                    "                    white-space: nowrap;\n" +
                    "                    text-overflow: ellipsis;\n" +
                    "                    max-width: 243px;   /* 要适配小屏幕手机，所以最大宽度先设小一点，后面js根据屏幕大小重新设置 */    \n" +
                    "                }\n" +
                    "\n" +
                    "                .progress-bar-bg {\n" +
                    "                    background-color: #d9d9d9;\n" +
                    "                    position: relative;\n" +
                    "                    height: 2px;\n" +
                    "                    cursor: pointer;\n" +
                    "                }\n" +
                    "\n" +
                    "                .progress-bar {\n" +
                    "                    background-color: #649fec;\n" +
                    "                    width: 0;\n" +
                    "                    height: 2px;\n" +
                    "                }\n" +
                    "\n" +
                    "                .progress-bar-bg span {\n" +
                    "                    content: \" \";\n" +
                    "                    width: 10px;\n" +
                    "                    height: 10px;\n" +
                    "                    border-radius: 50%;\n" +
                    "                    -moz-border-radius: 50%;\n" +
                    "                    -webkit-border-radius: 50%;\n" +
                    "                    background-color: #3e87e8;\n" +
                    "                    position: absolute;\n" +
                    "                    left: 0;\n" +
                    "                    top: 50%;\n" +
                    "                    margin-top: -5px;\n" +
                    "                    margin-left: -5px;\n" +
                    "                    cursor: pointer;\n" +
                    "                }\n" +
                    "\n" +
                    "                .audio-time {\n" +
                    "                    overflow: hidden;\n" +
                    "                    margin-top: -1px;\n" +
                    "                }\n" +
                    "\n" +
                    "                .audio-length-total {\n" +
                    "                    float: right;\n" +
                    "                    font-size: 12px;\n" +
                    "                }\n" +
                    "\n" +
                    "                .audio-length-current {\n" +
                    "                    float: left;\n" +
                    "                    font-size: 12px;\n" +
                    "                }\n" +
                    "                </style>\n" +
                    "                <script src=\"https://cdn.bootcss.com/jquery/3.4.0/jquery.min.js\"></script>\n" +
                    "                <div class=\"audio-wrapper\">\n" +
                    "                <script>\n" +
                    "                        $(function(){\n" +
                    "                            initAudioEvent(" + voice_key + ");\n" +
                    "                            var index = 0;\n" +
                    "                            /**\n" +
                    "                             * 初始化音频控制事件\n" +
                    "                             */\n" +
                    "                            function initAudioEvent() {\n" +
                    "                                var audio = $(\"audio\")[0];\n" +
                    "\n" +
                    "                                // 点击播放/暂停图片时，控制音乐的播放与暂停\n" +
                    "                                $(\"#audioPlayer\").click(function () {\n" +
                    "\n" +
                    "                                    // 监听音频播放时间并更新进度条\n" +
                    "                                    audio.addEventListener(\"timeupdate\", function () {\n" +
                    "                                        updateProgress(audio);\n" +
                    "                                    }, false);\n" +
                    "\n" +
                    "                                    // 监听播放完成事件\n" +
                    "                                    audio.addEventListener(\"ended\", function () {\n" +
                    "                                        audioEnded();\n" +
                    "                                    }, false);\n" +
                    "\n" +
                    "                                    // 改变播放/暂停图片\n" +
                    "                                    if (audio.paused) {\n" +
                    "                                        // 开始播放当前点击的音频\n" +
                    "                                        audio.play();\n" +
                    "                                        $(\"#audioPlayer\").attr(\"src\", \"data:image/gif;base64,R0lGODlhVABUAPfJAButGiKwIe747m7Kbe/47/r8+vj7+J3bnB+vHqDcny20LByuG+j16Pz9/HvPeiOwIk/ATuT05FLBUTa3Np7bnTm4OCqzKdXv1ff79ySwI8Lowi+1Lj66Pb3mvdvx23nPeaTepMjqyLXktVzEW63hrTW2NEu+So7WjdLu0j66PrzmvKrgqn7QfeL04p/cnkm+SCiyJ7/nvmTHYyGwIPn8+fX69d7y3vb69iWxJE2/TPL58iuzKqzgrHjOeEW8RPT69PH58ZXYlNDtz4bThSyzK+337eb15mLGYdbv1mnJaW/Lb8bqxja3NeX15V3FXPD48GHGYfP689fw11HAUHzQe3fOd0q+STi4N8rrytPu01/FXz25PGzKa17FXez37CCvH6ngqaDcoOn26ODz3x6vHZLXkiaxJef150y/S+Hz4NDt0E/AT77nvrTjtJfZlnrPeje3N0K7QWvKaoXThLvmu8fqxmXHZIPSg5bZlavgq8/tz9zx3JzbnI/WjtHu0Ue9RkS8Q93y3ZTYk6LdovH58FfDV2DGYInUiX3QfIfThmfIZtnw2Dq4OZDXkLrlulDAUIzVi43VjG/LbsPpw/n7+XTNc1TBUx6uHcDnv3DLcDG1MN/y31/FXsjqx2jJaFPBUi60LS60LljDWHXNdGbIZTS2M6/hrnfOdke9R9Tv1FbCVWPHYkC6P1rEWbDir0a8RVXCVMnryYvVi4jUiMHowVnDWMvry+v36zO2Mqjfpx2uHGfIZyeyJsDowLHisZHXkZPYk7nluXbNddnw2fv8+4fUhzC1LxqtGf39/QAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH/C05FVFNDQVBFMi4wAwEAAAAh/wtYTVAgRGF0YVhNUDw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMDY3IDc5LjE1Nzc0NywgMjAxNS8wMy8zMC0yMzo0MDo0MiAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo4NzEyYzBkMi03NGJlLTQ5MTEtYmQyMi1lNmI4ZTlhZmQ5ZGIiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QkUzMTAyRkEyMjg0MTFFN0JDNzBCMEY5NjNCMDhDQjQiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QkUzMTAyRjkyMjg0MTFFN0JDNzBCMEY5NjNCMDhDQjQiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTUgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4NzEyYzBkMi03NGJlLTQ5MTEtYmQyMi1lNmI4ZTlhZmQ5ZGIiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6ODcxMmMwZDItNzRiZS00OTExLWJkMjItZTZiOGU5YWZkOWRiIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+Af/+/fz7+vn49/b19PPy8fDv7u3s6+rp6Ofm5eTj4uHg397d3Nva2djX1tXU09LR0M/OzczLysnIx8bFxMPCwcC/vr28u7q5uLe2tbSzsrGwr66trKuqqainpqWko6KhoJ+enZybmpmYl5aVlJOSkZCPjo2Mi4qJ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\");\n" +
                    "\n" +
                    "                                    } else {\n" +
                    "                                        audio.pause();\n" +
                    "                                        $(\"#audioPlayer\").attr(\"src\", \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFQAAABUCAMAAAArteDzAAAAaVBMVEUAAAAarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRlIa6J1AAAAInRSTlMA9wYa38QR7ZJnMK1IIqBsO3fXDbSGQudZz5fKpV0rfbpRlHIjYQAAA35JREFUWMPFWduyqjAMDS0tgtwEFBGv/P9Hntmh3cWDTYsMs/Oio3SRy0qapuCU7PXIRdUGQxCFncgfrwzWCb/l4TCTML/xbxFlIQariEJ+AZnkwUBKkCdLIZvBQ5olsPw61Uhc4vTOa4Ca39P4IqYWXH2dyw5mWXUs2ez/8liZVx6YD2bW6wXRzmpesov0U70HxW5azTBmpD1xqJW9uUzfaS0Lp1ms0Nru6Nfv9WPSi8lahT2BKoWyvARPKZUPhLRiduq9ckHaKds6y5pa6XmARXJQutaEP4MzLJTzyJfmk193I2YKiyUdUXcf+OnCdKPO+JqNvxO2kx4YNcr+c2jvjpE7Wv27W4uRS/C1jFEu3mpdhJyX34PWISY3ByNj/SxhhZRjfZ0UMkUJt3Bxx08rJU2xbFB16YEZDiG3JSy6sHlXNPbCHIbOVpHiN1VzjBLzKOCkmxjGKld6B4oNbjkiqi3rkJeBNN8jBj7SUEaxyGgnjE1OkS0mHkUAgd5X/qWF80mWR7PaOY0410GrnHHXVHpSqlZII521RzeXqtpkTkgEEitIiwF1YeLDJgQnIldbgAx5wMBj5z4br+aWB5GdGbxUxGjUp6ESLmxhJsaMFzx+Pi5+VIpN6bTUlcvPfw/InXlvjO5MjsdE/ucg6DjxRlEJY4Wb0J1IlnR0ZoXGEHF/6l1I68d+vj3ho9xH0mO+cjumNiMxvg/tTOWYcIAkqCl+XjRbtH7CHv4aCQrIQIui3TCxNPyN1BMXfhQFFxCgJ/yzmYAaTpGgEZpPoOq60GJctfkRaX5IBApRVTNTm/TvnYHqCEoh6kMzUCuNxnUUpVzkB/2+/Pc5iTpT5PdNUx78FrMT6kymqbugmEpxNZU4JXaph7v0GbOGxJQ3SZU+ryINSWT8iAt6skg7txPD1wCJN/rrQG0nZuNzo54nHQOnNj6zRTtRj5Pe5klu0d7NBGTThvFENhNE20NQS5BtD9GgUdQqyQZtaSuZ4bIr1fUGcmHTCz1SRpJNL9GeE3xNHe35/CDhRj04DhLzI48b9eI48mxxONvyGLn+wGtsLTY5mm87RFg/7jhNxh3bD2aANWtHSFsOu7Yfy60fIG4/6lw/lN14fOwedJdWXxKD7m1H8u7LAwZMZsn88mCDa46/v5DZ6OoIhcf7dg7Y7mPalb7XcVEwDEFU+V3H/QOplcP+ctPpgwAAAABJRU5ErkJggg==\");\n" +
                    "                                    }\n" +
                    "                                });\n" +
                    "                            }\n" +
                    "\n" +
                    "                            /**\n" +
                    "                             * 更新进度条与当前播放时间\n" +
                    "                             * @param {object} audio - audio对象\n" +
                    "                             */\n" +
                    "                            function updateProgress(audio) {\n" +
                    "                                console.log(\"sss\");\n" +
                    "                                var value = audio.currentTime / audio.duration;\n" +
                    "                                console.log(value);\n" +
                    "                                $(\"#progressBar\"+index).css(\"width\", value * 100 + \"%\");\n" +
                    "                                $(\"#progressDot\"+index).css(\"left\", value * 100 + \"%\");\n" +
                    "                                $(\"#audioCurTime\"+index).html(transTime(audio.currentTime));\n" +
                    "                                console.log(\"xxx\");\n" +
                    "                            }\n" +
                    "\n" +
                    "                            /**\n" +
                    "                             * 播放完成时把进度调回开始的位置\n" +
                    "                             */\n" +
                    "                            function audioEnded() {\n" +
                    "                                $(\"#progressBar\"+index).css(\"width\", 0);\n" +
                    "                                $(\"#progressDot\"+index).css(\"left\", 0);\n" +
                    "                                $(\"#audioCurTime\"+index).html(\"00:00\");\n" +
                    "                                $(\"#audioPlayer\").attr(\"src\", \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFQAAABUCAMAAAArteDzAAAAaVBMVEUAAAAarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRlIa6J1AAAAInRSTlMA9wYa38QR7ZJnMK1IIqBsO3fXDbSGQudZz5fKpV0rfbpRlHIjYQAAA35JREFUWMPFWduyqjAMDS0tgtwEFBGv/P9Hntmh3cWDTYsMs/Oio3SRy0qapuCU7PXIRdUGQxCFncgfrwzWCb/l4TCTML/xbxFlIQariEJ+AZnkwUBKkCdLIZvBQ5olsPw61Uhc4vTOa4Ca39P4IqYWXH2dyw5mWXUs2ez/8liZVx6YD2bW6wXRzmpesov0U70HxW5azTBmpD1xqJW9uUzfaS0Lp1ms0Nru6Nfv9WPSi8lahT2BKoWyvARPKZUPhLRiduq9ckHaKds6y5pa6XmARXJQutaEP4MzLJTzyJfmk193I2YKiyUdUXcf+OnCdKPO+JqNvxO2kx4YNcr+c2jvjpE7Wv27W4uRS/C1jFEu3mpdhJyX34PWISY3ByNj/SxhhZRjfZ0UMkUJt3Bxx08rJU2xbFB16YEZDiG3JSy6sHlXNPbCHIbOVpHiN1VzjBLzKOCkmxjGKld6B4oNbjkiqi3rkJeBNN8jBj7SUEaxyGgnjE1OkS0mHkUAgd5X/qWF80mWR7PaOY0410GrnHHXVHpSqlZII521RzeXqtpkTkgEEitIiwF1YeLDJgQnIldbgAx5wMBj5z4br+aWB5GdGbxUxGjUp6ESLmxhJsaMFzx+Pi5+VIpN6bTUlcvPfw/InXlvjO5MjsdE/ucg6DjxRlEJY4Wb0J1IlnR0ZoXGEHF/6l1I68d+vj3ho9xH0mO+cjumNiMxvg/tTOWYcIAkqCl+XjRbtH7CHv4aCQrIQIui3TCxNPyN1BMXfhQFFxCgJ/yzmYAaTpGgEZpPoOq60GJctfkRaX5IBApRVTNTm/TvnYHqCEoh6kMzUCuNxnUUpVzkB/2+/Pc5iTpT5PdNUx78FrMT6kymqbugmEpxNZU4JXaph7v0GbOGxJQ3SZU+ryINSWT8iAt6skg7txPD1wCJN/rrQG0nZuNzo54nHQOnNj6zRTtRj5Pe5klu0d7NBGTThvFENhNE20NQS5BtD9GgUdQqyQZtaSuZ4bIr1fUGcmHTCz1SRpJNL9GeE3xNHe35/CDhRj04DhLzI48b9eI48mxxONvyGLn+wGtsLTY5mm87RFg/7jhNxh3bD2aANWtHSFsOu7Yfy60fIG4/6lw/lN14fOwedJdWXxKD7m1H8u7LAwZMZsn88mCDa46/v5DZ6OoIhcf7dg7Y7mPalb7XcVEwDEFU+V3H/QOplcP+ctPpgwAAAABJRU5ErkJggg==\");\n" +
                    "                            }\n" +
                    "\n" +
                    "                            /**\n" +
                    "                             * 音频播放时间换算\n" +
                    "                             * @param {number} value - 音频当前播放时间，单位秒\n" +
                    "                             */\n" +
                    "                            function transTime(value) {\n" +
                    "                                var time = \"\";\n" +
                    "                                var h = parseInt(value / 3600);\n" +
                    "                                value %= 3600;\n" +
                    "                                var m = parseInt(value / 60);\n" +
                    "                                var s = parseInt(value % 60);\n" +
                    "                                if (h > 0) {\n" +
                    "                                    time = formatTime(h + \":\" + m + \":\" + s);\n" +
                    "                                } else {\n" +
                    "                                    time = formatTime(m + \":\" + s);\n" +
                    "                                }\n" +
                    "\n" +
                    "                                return time;\n" +
                    "                            }\n" +
                    "\n" +
                    "                            /**\n" +
                    "                             * 格式化时间显示，补零对齐\n" +
                    "                             * eg：2:4  -->  02:04\n" +
                    "                             * @param {string} value - 形如 h:m:s 的字符串 \n" +
                    "                             */\n" +
                    "                            function formatTime(value) {\n" +
                    "                                var time = \"\";\n" +
                    "                                var s = value.split(\":\");\n" +
                    "                                var i = 0;\n" +
                    "                                for (; i < s.length - 1; i++) {\n" +
                    "                                    time += s[i].length == 1 ? (\"0\" + s[i]) : s[i];\n" +
                    "                                    time += \":\";\n" +
                    "                                }\n" +
                    "                                time += s[i].length == 1 ? (\"0\" + s[i]) : s[i];\n" +
                    "\n" +
                    "                                return time;\n" +
                    "                            }\n" +
                    "                        });\n" +
                    "\n" +
                    "                        \n" +
                    "                    </script>\n" +
                    "                    <audio>\n" +
                    "                        <source src=\"" + voice_path + "\" type=\"audio/mp3\">\n" +
                    "                    </audio>\n" +
                    "                    <div class=\"audio-left\" id=\"audioPlayer" + voice_key + "\" onclick=\"initAudioEvent(" + voice_key + ");\">\n" +
                    "                        <image src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFQAAABUCAMAAAArteDzAAAAaVBMVEUAAAAarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRkarRlIa6J1AAAAInRSTlMA9wYa38QR7ZJnMK1IIqBsO3fXDbSGQudZz5fKpV0rfbpRlHIjYQAAA35JREFUWMPFWduyqjAMDS0tgtwEFBGv/P9Hntmh3cWDTYsMs/Oio3SRy0qapuCU7PXIRdUGQxCFncgfrwzWCb/l4TCTML/xbxFlIQariEJ+AZnkwUBKkCdLIZvBQ5olsPw61Uhc4vTOa4Ca39P4IqYWXH2dyw5mWXUs2ez/8liZVx6YD2bW6wXRzmpesov0U70HxW5azTBmpD1xqJW9uUzfaS0Lp1ms0Nru6Nfv9WPSi8lahT2BKoWyvARPKZUPhLRiduq9ckHaKds6y5pa6XmARXJQutaEP4MzLJTzyJfmk193I2YKiyUdUXcf+OnCdKPO+JqNvxO2kx4YNcr+c2jvjpE7Wv27W4uRS/C1jFEu3mpdhJyX34PWISY3ByNj/SxhhZRjfZ0UMkUJt3Bxx08rJU2xbFB16YEZDiG3JSy6sHlXNPbCHIbOVpHiN1VzjBLzKOCkmxjGKld6B4oNbjkiqi3rkJeBNN8jBj7SUEaxyGgnjE1OkS0mHkUAgd5X/qWF80mWR7PaOY0410GrnHHXVHpSqlZII521RzeXqtpkTkgEEitIiwF1YeLDJgQnIldbgAx5wMBj5z4br+aWB5GdGbxUxGjUp6ESLmxhJsaMFzx+Pi5+VIpN6bTUlcvPfw/InXlvjO5MjsdE/ucg6DjxRlEJY4Wb0J1IlnR0ZoXGEHF/6l1I68d+vj3ho9xH0mO+cjumNiMxvg/tTOWYcIAkqCl+XjRbtH7CHv4aCQrIQIui3TCxNPyN1BMXfhQFFxCgJ/yzmYAaTpGgEZpPoOq60GJctfkRaX5IBApRVTNTm/TvnYHqCEoh6kMzUCuNxnUUpVzkB/2+/Pc5iTpT5PdNUx78FrMT6kymqbugmEpxNZU4JXaph7v0GbOGxJQ3SZU+ryINSWT8iAt6skg7txPD1wCJN/rrQG0nZuNzo54nHQOnNj6zRTtRj5Pe5klu0d7NBGTThvFENhNE20NQS5BtD9GgUdQqyQZtaSuZ4bIr1fUGcmHTCz1SRpJNL9GeE3xNHe35/CDhRj04DhLzI48b9eI48mxxONvyGLn+wGtsLTY5mm87RFg/7jhNxh3bD2aANWtHSFsOu7Yfy60fIG4/6lw/lN14fOwedJdWXxKD7m1H8u7LAwZMZsn88mCDa46/v5DZ6OoIhcf7dg7Y7mPalb7XcVEwDEFU+V3H/QOplcP+ctPpgwAAAABJRU5ErkJggg==\" width=50px id=\"audioPlayer\">\n" +
                    "                    </div>\n" +
                    "                    <div class=\"audio-right\">\n" +
                    "                        <p style=\"max-width: 536px;\">" + voice_name + "</p>\n" +
                    "                        <div class=\"progress-bar-bg\" id=\"progressBarBg" + voice_key + "\">\n" +
                    "                            <span id=\"progressDot" + voice_key + "\"></span>\n" +
                    "                            <div class=\"progress-bar\" id=\"progressBar" + voice_key + "\"></div>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"audio-time\">\n" +
                    "                            <span class=\"audio-length-current\" id=\"audioCurTime" + voice_key + "\">00:00</span>\n" +
                    "                            <span class=\"audio-length-total\">" + voice_length + "</span>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                    \n" +
                    "               </div>";
            mpvoice.before(html11);
            wechartArticlDoc.getElementsByTag("mpvoice").remove();
        }

        // 标题
        String tittle = wechartArticlDoc.head().select("meta[property=og:title]").attr("content");
        // 描述
        String sdesc = wechartArticlDoc.head().select("meta[property=og:description]").attr("content");
        // 图片
        String image = wechartArticlDoc.head().select("meta[property=twitter:image]").attr("content");

        // 获取图片字节流
        String imgurl = "";
        if (!StringUtils.isEmpty(image)) {
            String code = "EWZ" + IdWorker.getTimeId();
            HttpURLConnection httpUrl;
            URL urls;
            int len;
            byte[] buff = new byte[1024];
            urls = new URL(image);
            httpUrl = (HttpURLConnection) urls.openConnection();
            httpUrl.connect();
            String contentType = httpUrl.getContentType();
            String textFieldName = code + "." + contentType.split("/")[1];
            FileItemFactory factory = new DiskFileItemFactory(16, null);
            FileItem item = factory.createItem(textFieldName, contentType, true, textFieldName);
            OutputStream os = item.getOutputStream();
            InputStream in = httpUrl.getInputStream();
            while ((len = in.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            in.close();
            os.close();
            MultipartFile htmlFile = new CommonsMultipartFile(item);
            // 上传oss
            // 上传图片
            imgurl = OSSUtil.uploadFile2OSS(htmlFile, null, BaseConstants.ossmap.get("WZ"), comid).getString("path");

            // 删除临时文件
            item.delete();
            httpUrl.disconnect();
        }
        // 获取内容
        Elements content = wechartArticlDoc.select(".rich_media_content#js_content");
        String contenttext = content.text();

        // 作者
        String author = wechartArticlDoc.select("#js_name").text();

        // 阅读时长 div img-content
        String time = wechartArticlDoc.select(".rich_media_meta_list").select("rich_media_meta rich_media_meta_text").text();
        int timelength = 0;
        if (StringUtils.isEmpty(time)) {
            timelength = contenttext.length() / 400;
        }

        // Html页面
        wechartArticlDoc.select("h2[id=activity-name]").remove();
        wechartArticlDoc.select("div[id=meta_content]").remove();
        wechartArticlDoc.select("#meta_content").remove();
        wechartArticlDoc.select("#js_pc_qr_code").remove();
        wechartArticlDoc.select("a").attr("href", "#");
        wechartArticlDoc.select("#js_report_article3").remove();

        String htmltext = wechartArticlDoc.html().replace("data-src", "src");

        // 关键字
        List<String> keywordList = HanLP.extractKeyword(contenttext, 5);

        // 数据封装
        // 设置阅读时间
      smartComKjLibwEmpno.setBasttimes(timelength * 60);
        // 设置素材标题
      smartComKjLibwEmpno.setStitle(tittle);
        // 设置素材作者
        // dabtKjLibw.setAutor(author);
        // 设置素材描述
      smartComKjLibwEmpno.setSdesc(sdesc);
        // 设置素材图片
      smartComKjLibwEmpno.setPichttp(imgurl);
        // 设置sno
        String code = "EWZ" + IdWorker.getTimeId();
      smartComKjLibwEmpno.setSno(code);

        String wzurl = uploadhtml(code, htmltext, comid);
      smartComKjLibwEmpno.setConthttp(wzurl);
        return smartComKjLibwEmpno;
    }


    /**
     * OSS 数据迁移 根据url 返回本 OSS 使用的URL
     *
     * @Param: [url]
     * @return: java.lang.String
     * @Author: 张宁
     * @Date: 2019/10/22
     */
    public static String getOSSUrl(String url) throws Exception {
        String comid = JwtTokenUtil.getComid();
        String empno = JwtTokenUtil.getEmpno();
        // 爬取数据
        HttpURLConnection httpUrl = null;
        URL urls = null;
        int len = 0;
        byte[] buff = new byte[1024];
        urls = new URL(url);
        httpUrl = (HttpURLConnection) urls.openConnection();
        httpUrl.connect();
        String contentType = httpUrl.getContentType();
        String textFieldName = "EWZ" + IdWorker.getTimeId() + "." + contentType.split("/")[1];
        // 如果是txt文本 进行转换成html
        if ("text/plain".equals(contentType)) {
            contentType = "text/html";
            textFieldName = "EWZ" + IdWorker.getTimeId() + "." + "html";
        }
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(textFieldName, contentType, true, textFieldName);
        OutputStream os = item.getOutputStream();
        InputStream in = httpUrl.getInputStream();
        while ((len = in.read(buff)) != -1) {
            os.write(buff, 0, len);
        }
        in.close();
        os.close();
        MultipartFile htmlFile = new CommonsMultipartFile(item);
        // 上传oss
        // 上传图片
        String ossURL = OSSUtil.uploadFile2OSS(htmlFile, "WZ", BaseConstants.ossmap.get("WZ"), comid).getString("path");
        // 删除临时文件
        item.delete();
        return ossURL;
    }


    /**
     * 上传html文件
     *
     * @param code  编号
     * @param text  html文本
     * @param comid 渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    public static String uploadhtml(String code, String text, String comid) throws Exception {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = code + ".html";
        FileItem item = factory.createItem(textFieldName, "text/html", true, textFieldName);
        OutputStream os = item.getOutputStream();
        os.write(text.getBytes());
        os.close();
        MultipartFile htmlFile = new CommonsMultipartFile(item);
        // 上传oss
        // 上传图片
        String wzurl = OSSUtil.uploadFile2OSS(htmlFile, null, BaseConstants.ossmap.get("WZ"), comid).getString("path");
        // 删除临时文件
        item.delete();
        return wzurl;
    }
}
