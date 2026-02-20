package com.nh.smart.dao.record;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nh.smart.entity.record.SmartKjCustomEmpship;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * smart_com_kj_custom_empship#客户与业务员关系表 Dao接口
 */
@Repository
public interface SmartKjCustomEmpshipDao extends BaseMapper<SmartKjCustomEmpship> {

    /**
     * 添加客户与业务员关系
     *
     * @Param: [smartComKjCustomEmpship]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    Integer insertCustomEmpship(@Param("smartKjCustomEmpship") SmartKjCustomEmpship smartComKjCustomEmpship);

    /**
     * 查看业务员拓客数
     *
     * @Param: [comid, empno, userid]
     * @return: int
     * @Author: 张宁
     * @Date: 2019/11/6
     */
    int selectCustomEmpship(@Param("comid") String comid, @Param("empno") String empno);

    /**
     * 客户挖掘列表（通讯录/最近访客/准客户/朋友）
     */
    IPage<Map<String, Object>> selectCustomPage(Page page,
                                                @Param("comid") String comid,
                                                @Param("empno") String empno,
                                                @Param("name") String name,
                                                @Param("labids") List<String> labids,
                                                @Param("mode") String mode);
}
