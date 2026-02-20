package com.nh.smart.entity.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 行为时间记录实体
 *
 * @Author: 张宁
 * @Date: 2019/11/5
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserAction{
    // 行为记录id
    private Integer id;
    // 渠道id
    private String comid;
    // 初始时间
    private Date begin;
    // 结束时间
    private Date over;
    // 浏览时间
    private long time;
    // 时间戳
    private String hashnum;

    public void setTimeByBeginAndOver() {
        this.time = (this.over.getTime() - this.begin.getTime()) / 1000;
    }

}
