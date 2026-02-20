package com.nh.smart.entity.material;

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
import java.time.LocalDateTime;
import java.util.List;

/**
 * smart_com_kj_libw#素材库
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName("smart_com_kj_libw")
@ApiModel(value = "SmartComKjLibw对象", description = "smart_com_kj_libw#素材库")
public class SmartComKjLibw implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    private String comid;

    @ApiModelProperty(value = "素材类型编码")
    private String btagcode;

    @ApiModelProperty(value = "素材编号")
    @TableId(value = "sno", type = IdType.INPUT)
    private String sno;

    @ApiModelProperty(value = "素材标题")
    private String stitle;

    @ApiModelProperty(value = "素材摘要")
    private String smark;

    @ApiModelProperty(value = "素材描述")
    private String sdesc;

    @ApiModelProperty(value = "素材图片")
    private String pichttp;

    @ApiModelProperty(value = "素材大图，素材库展示使用")
    private String bpichttp;

    @ApiModelProperty(value = "素材地址")
    private String conthttp;

    @ApiModelProperty(value = "作者")
    private String autor;

    @ApiModelProperty(value = "是否只读")
    private String readonly;

    @ApiModelProperty(value = "素材分类")
    private String stagcode;

    @ApiModelProperty(value = "个性化设置,图片上的头像位置等")
    private String indset;

    @ApiModelProperty(value = "最佳阅读时长")
    private Integer basttimes;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime fbtime;

    @ApiModelProperty(value = "阅读次数")
    private Integer ydnum;

    @ApiModelProperty(value = "分享次数")
    private Integer sharenum;

    @ApiModelProperty(value = "状态:0有效，1失效")
    private String status;

    @ApiModelProperty(value = "操作人")
    private String operno;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime optime;

    @ApiModelProperty(value = "批次号")
    private String pcnum;

    @ApiModelProperty(value = "标签集合")
    @TableField(exist = false)
    private List<String> bq;

    @ApiModelProperty(value = "最新标志")
    @TableField(exist = false)
    private String zx;
}
