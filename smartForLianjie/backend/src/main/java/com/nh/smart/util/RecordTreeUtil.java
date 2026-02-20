package com.nh.smart.util;


import com.nh.smart.entity.record.SmartComKjActionRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 根据集合,返回其层次结构
 *
 * @author zn
 * @date 2019-09-19 10:20
 */
public class RecordTreeUtil {

    /**
     * 递归生成EasyUI所需的Tree结构
     * List<User> user   0
     */
    public static Map<String, Object> buildTree(Map<String, Object> map, List<?> list, Integer parent_id, Integer userid) {
        List<Object> treelist = new ArrayList<Object>();
        if (list.size() == 0) {
            return map;
        }
        for (Object x : list) {
            Map<String, Object> relationship = new HashMap<>();
            Map<String, Object> message = new HashMap<>();
            SmartComKjActionRecord y = (SmartComKjActionRecord) x;
            if(y.getParentId().intValue() == parent_id.intValue() && y.getTreeid().intValue() != userid.intValue()){
                y.setChildren((List<?>)buildTree(map, list, y.getTreeid(), userid).get("treelist"));
                if(y.getChildren().size() > 0 ){
                    relationship.put("target", y.getTreeid());
                    relationship.put("source", y.getParentId());
                    message.put("userid", y.getTreeid());
                    message.put("name", y.getKhname());
                    message.put("headimg", y.getHeadimg());
                    ((List<Map<String, Object>>)map.get("message")).add(message);
                    ((List<Map<String, Object>>)map.get("relationship")).add(relationship);
                    treelist.add(y);
                }
            }
            if(y.getTreeid().intValue() == userid.intValue() && y.getParentId().intValue() == parent_id.intValue()){
                y.setChildren(null);
                treelist.add(y);
                relationship.put("target", y.getTreeid());
                relationship.put("source", y.getParentId());
                message.put("userid", y.getTreeid());
                message.put("name", y.getKhname());
                message.put("headimg", y.getHeadimg());
                ((List<Map<String, Object>>)map.get("message")).add(message);
                ((List<Map<String, Object>>)map.get("relationship")).add(relationship);
                map.put("treelist", treelist);
                return map;
            }
        }
        map.put("treelist", treelist);
        return map;
    }

}
