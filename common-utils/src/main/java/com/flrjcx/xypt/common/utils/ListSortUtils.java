package com.flrjcx.xypt.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 排序工具类
 *
 * @author Flrjcx
 */
public class ListSortUtils {
    public static List<Map<String, Object>> sortDetails(List<Map<String, Object>> sortData, String sort) {
        if (sortData == null || sortData.isEmpty()) {
            return null;
        }
        if ("DESC".equals(sort)){
            Collections.sort(sortData, new MapComparatorDesc());
        }else if ("ASC".equals(sort)){
            Collections.sort(sortData, new MapComparatorAsc());
        }
        return sortData;
    }

    static class MapComparatorDesc implements Comparator<Map<String, Object>> {
        @Override
        public int compare(Map<String, Object> m1, Map<String, Object> m2) {
            Integer v1 = Integer.valueOf(m1.get("SCORE").toString());
            Integer v2 = Integer.valueOf(m2.get("SCORE").toString());
            if (v2 != null) {
                return v2.compareTo(v1);
            }
            return 0;
        }
    }
    static class MapComparatorAsc implements Comparator<Map<String, Object>> {
        @Override
        public int compare(Map<String, Object> m1, Map<String, Object> m2) {
            Integer v1 = Integer.valueOf(m1.get("SCORE").toString());
            Integer v2 = Integer.valueOf(m2.get("SCORE").toString());
            if(v1 != null){
                return v1.compareTo(v2);
            }
            return 0;
        }
    }

    /**
     * List<Map>根据map字段排序
     *
     * @param list
     * @param field 排序字段
     * @param sortTyp 排序方式 desc-倒序 asc-正序
     * @return
     */
    public static List<LinkedHashMap<String, Object>> sortByField(List<LinkedHashMap<String, Object>> list, String field, String sortTyp) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.sort((m1, m2) -> {
                if (StringUtils.equals(sortTyp,"DESC")) {
                    return Integer.valueOf(m2.get(field).toString()).compareTo(Integer.valueOf(m1.get(field).toString()));
                } else {
                    return Integer.valueOf(m1.get(field).toString()).compareTo(Integer.valueOf(m2.get(field).toString()));
                }
            });
        }

        return list;
    }


//        System.out.println("=======这样子就是以orderid降序，如果ordeid相同再以userid降序==================");
//        orderList.sort(Comparator.comparing(Order::getOrderId).thenComparing(Order::getUserId).reversed());
}
