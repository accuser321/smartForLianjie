package com.nh.smart.entity.record;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * smart_com_wxmsg#消息记录表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_wxmsg")
@ApiModel(value = "SmartComWxmsg对象", description = "smart_com_wxmsg#消息记录表")
public class SmartComWxmsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    private String comid;

    @ApiModelProperty(value = "消息id，自增一")
    @TableId(value = "msgid", type = IdType.AUTO)
    private Integer msgid;

    @ApiModelProperty(value = "消息触发人")
    private Integer fuserid;

    @ApiModelProperty(value = "消息接收人")
    private Integer tuserid;

    @ApiModelProperty(value = "消息类型")
    private String msgtype;

    @ApiModelProperty(value = "素材类型编码")
    private String btagcode;

    @ApiModelProperty(value = "来源分类：转发、浏览")
    private String otype;

    @ApiModelProperty(value = "素材编号")
    private String sno;

    @ApiModelProperty(value = "消息标题")
    private String mtitle;

    @ApiModelProperty(value = "消息描述")
    private String mdesc;

    @ApiModelProperty(value = "消息内容")
    private String mcontent;

    @ApiModelProperty(value = "消息链接")
    private String mhttpurl;

    @ApiModelProperty(value = "发送时间")
    private Date pushtime;

    @ApiModelProperty(value = "阅读时间")
    private Date readtime;

    @ApiModelProperty(value = "状态")
    private String status;


}
