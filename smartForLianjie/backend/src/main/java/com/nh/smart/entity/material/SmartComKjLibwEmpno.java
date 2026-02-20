package com.nh.smart.entity.material;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * smart_com_kj_libw_empno#渠道业务员素材库
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("smart_com_kj_libw_empno")
@ApiModel(value = "SmartComKjLibwEmpno对象", description = "smart_com_kj_libw_empno#渠道业务员素材库")
public class SmartComKjLibwEmpno implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @TableId(value = "comid", type = IdType.INPUT)
    private String comid;

    @ApiModelProperty(value = "素材类型编码")
    private String btagcode;

    @ApiModelProperty(value = "素材编号")
    private String sno;

    @ApiModelProperty(value = "原始素材编号")
    private String osno;

    @ApiModelProperty(value = "素材标题")
    private String stitle;

    @ApiModelProperty(value = "素材摘要")
    private String smark;

    @ApiModelProperty(value = "素材描述")
    private String sdesc;

    @ApiModelProperty(value = "素材图片")
    private String pichttp;

    @ApiModelProperty(value = "素材地址")
    private String conthttp;

    @ApiModelProperty(value = "作者")
    private String autor;

    @ApiModelProperty(value = "素材分类")
    private String stagcode;

    @ApiModelProperty(value = "个性化设置")
    private String indset;

    @ApiModelProperty(value = "最佳阅读时长")
    private Integer basttimes;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date fbtime;

    @ApiModelProperty(value = "阅读次数")
    private Integer ydnum;

    @ApiModelProperty(value = "分享次数")
    private Integer sharenum;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "操作业务员工号")
    private String empno;

    @ApiModelProperty(value = "操作时间")
    private Date optime;

    @ApiModelProperty(value = "标签集合")
    @TableField(exist = false)
    private List<String> bq;

    @ApiModelProperty(value = "标签集合字符串")
    @TableField(exist = false)
    private String tagcodeStr;

    @ApiModelProperty(value = "是否只读")
    private String readonly;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "报名人数")
    @TableField(exist = false)
    private Integer rennum;

    @ApiModelProperty(value = "好友浏览")
    @TableField(exist = false)
    private Integer browsenum;

    @ApiModelProperty(value = "好友转发")
    @TableField(exist = false)
    private Integer forwardnum;

    @ApiModelProperty(value = "是否有标签，0没有，1有")
    @TableField(exist = false)
    private String isbq;

    @ApiModelProperty(value = "人员类型")
    @TableField(exist = false)
    private String rytype;
}
