package com.nh.smart.entity.material;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * smart_com_muser_card#渠道业务员名片表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_muser_card")
@ApiModel(value = "SmartComMuserCard对象", description = "smart_com_muser_card#渠道业务员名片表")
public class SmartComMuserCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    private String comid;

    @ApiModelProperty(value = "业务员工号")
    @TableId(value = "empno", type = IdType.INPUT)
    private String empno;

    @ApiModelProperty(value = "名片名称")
    private String cardempname;

    @ApiModelProperty(value = "名片性别")
    private String cardsex;

    @ApiModelProperty(value = "名片手机")
    private String cardmobile;

    @ApiModelProperty(value = "工作地址")
    private String workarea;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "自我介绍")
    private String pdesc;

    @ApiModelProperty(value = "微信号码")
    private String wxnumber;

    @ApiModelProperty(value = "微信二维码")
    private String wxewmurl;

    @ApiModelProperty(value = "名片职位")
    private String carddegreeno;

    @ApiModelProperty(value = "更新时间")
    private Date flushtime;

    @ApiModelProperty(value = "名片样式")
    private String cardstyle;

    @ApiModelProperty(value = "微信头像")
    @TableField(exist = false)
    private String headimg;

    @ApiModelProperty(value = "照片集合")
    @TableField(exist = false)
    private List<String> photo;

    @ApiModelProperty(value = "点赞数")
    @TableField(exist = false)
    private Integer dznum;

    @ApiModelProperty(value = "是否点过赞")
    @TableField(exist = false)
    private Boolean isdz;

    @ApiModelProperty(value = "头像")
    @TableField(exist = false)
    private JSONObject headimgArray;

    @ApiModelProperty(value = "用户userid")
    @TableField(exist = false)
    private Integer userid;

    @ApiModelProperty(value = "名片二维码")
    @TableField(exist = false)
    private String mpurl;

    @ApiModelProperty(value = "微官网")
    @TableField(exist = false)
    private String webSite;

    @ApiModelProperty(value = "好友浏览")
    @TableField(exist = false)
    private Integer gwbrowsenum;

    @ApiModelProperty(value = "好友转发")
    @TableField(exist = false)
    private Integer gwforwardnum;

    @ApiModelProperty(value = "好友浏览")
    @TableField(exist = false)
    private Integer mpbrowsenum;

    @ApiModelProperty(value = "好友转发")
    @TableField(exist = false)
    private Integer mpforwardnum;

    @ApiModelProperty(value = "nbs名称")
    @TableField(exist = false)
    private String nbsname;

    @ApiModelProperty(value = "nbs浏览")
    @TableField(exist = false)
    private Integer nbsbrowsenum;

    @ApiModelProperty(value = "nbs转发")
    @TableField(exist = false)
    private Integer nbsforwardnum;
}
