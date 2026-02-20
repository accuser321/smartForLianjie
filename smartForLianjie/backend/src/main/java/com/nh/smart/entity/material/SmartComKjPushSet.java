package com.nh.smart.entity.material;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * smart_com_kj_push_set#渠道推送设置
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_push_set")
@ApiModel(value = "SmartComKjPushSet对象", description = "smart_com_kj_push_set#渠道推送设置")
public class SmartComKjPushSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    private String comid;

    @ApiModelProperty(value = "推送编号")
    @TableId(value = "pushid", type = IdType.INPUT)
    private String pushid;

    @ApiModelProperty(value = "素材类型")
    private String stagcode;

    @ApiModelProperty(value = "素材编号")
    private String sno;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "推送时间")
    private Date pushtime;

    @ApiModelProperty(value = "推送状态")
    private String pushstatus;

    @ApiModelProperty(value = "消息类型：00客服消息，10模版消息")
    private String pushtype;

    @ApiModelProperty(value = "模版编号，模版消息时有数据")
    private String mbnum;

    @ApiModelProperty(value = "标题")
    private String ptitle;

    @ApiModelProperty(value = "描述")
    private String pdesc;

    @ApiModelProperty(value = "内容")
    private String pcontent;

    @ApiModelProperty(value = "关键字1")
    private String keyword1;

    @ApiModelProperty(value = "关键字2")
    private String keyword2;

    @ApiModelProperty(value = "关键字3")
    private String keyword3;

    @ApiModelProperty(value = "关键字4")
    private String keyword4;

    @ApiModelProperty(value = "关键字5")
    private String keyword5;

    @ApiModelProperty(value = "关键字6")
    private String keyword6;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分享图片地址")
    private String pimgurl;

    @ApiModelProperty(value = "链接")
    private String phttpurl;

    @ApiModelProperty(value = "设置人")
    private String operno;

    @ApiModelProperty(value = "设置时间")
    private Date optime;

    @ApiModelProperty(value = "推送范围：ALL内外勤，N内勤，W外勤")
    private String pusharea;

    @ApiModelProperty(value = "推送机构")
    private String pushtjcode;

    @ApiModelProperty(value = "推送梳理")
    @TableField(exist = false)
    private String num;

    @ApiModelProperty(value = "用户工号")
    @TableField(exist = false)
    private String userId;


}
