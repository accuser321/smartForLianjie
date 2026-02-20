package com.nh.smart.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 树形机构节点实体
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Tree {

    // 节点id
    @TableField(exist = false)
    private String id;

    // 节点名称
    @TableField(exist = false)

    private String name;
    // 路径编码
    @TableField(exist = false)
    private String path;

    // 路径名称
    @TableField(exist = false)
    private String pathname;

    // 父节点id
    @TableField(exist = false)
    private String parentId;

    // 孩子集合
    @TableField(exist = false)
    private List<?> children;

    // 状态
    @TableField(exist = false)
    private String state;

    // 属性
    @TableField(exist = false)
    private String attributes;

    // 文本
    @TableField(exist = false)
    private String text;

    // 图表地址
    @TableField(exist = false)
    private String iconCls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
