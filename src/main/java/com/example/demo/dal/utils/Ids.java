package com.example.demo.dal.utils;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author lt 2013-6-19下午7:46:21
 * @version 1.0.0
 */
public class Ids {

    /**
     * 验证ID的有效性
     *
     * @param id
     * @return
     */
    public static boolean verifyId(final Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 验证ID的有效性
     *
     * @param id
     * @return
     */
    public static boolean verifyId(final Integer id) {
        if (id == null || id <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 验证ID列表的有效性
     *
     * @param ids
     * @return
     */
    public static boolean verifyIds(final String ids) {
        if (Strings.isNullOrEmpty(ids)) {
            return false;
        }
        return transformIds(ids).stream().allMatch(id -> verifyId(id));
    }

    /**
     * 比较两个整形数是否相等（空指针安全）
     *
     * @param c1
     * @param c2
     * @return
     */
    public static boolean eq(final Integer c1, final Integer c2) {
        return (compare(c1, c2) == 0);
    }

    /**
     * 比较两个长整形数是否相等（空指针安全）
     *
     * @param c1
     * @param c2
     * @return
     */
    public static boolean eq(final Long c1, final Long c2) {
        return (compare(c1, c2) == 0);
    }

    /**
     * 比较两个整形数的大小（空指针安全）
     *
     * @param c1
     * @param c2
     * @return
     */
    public static int compare(final Integer c1, final Integer c2) {
        return _compare(c1, c2);
    }

    /**
     * 比较两个长整形数的大小（空指针安全）
     *
     * @param c1
     * @param c2
     * @return
     */
    public static int compare(final Long c1, final Long c2) {
        return _compare(c1, c2);
    }

    /**
     * 比较两个字符串的大小（空指针安全）
     *
     * @param c1
     * @param c2
     * @return
     */
    public static int compare(final String c1, final String c2) {
        return _compare(c1, c2);
    }

    /**
     * 通用比较器，只要实现了Comparable接口都可以比较，但是两个参数类型必需相同。
     *
     * @param c1
     * @param c2
     * @param <T>
     * @return
     */
    private static <T extends Comparable> int _compare(final T c1, final T c2) {

        if (c1 == null && c2 == null) {
            return 0;
        }

        if (c1 != null && c2 == null) {
            return 1;
        }

        if (c1 == null && c2 != null) {
            return -1;
        }

        if (!c1.getClass().equals(c2.getClass())) {
            throw new RuntimeException(String.format("参数1类型为：%s,参数2类型为：%s。两个比较参数类型不一致。", c1.getClass(), c2.getClass()));
        }

        return c1.compareTo(c2);
    }

    /**
     * 把Strig的List转换成Long的List
     *
     * @param idList
     * @return
     */
    public static List<Long> transformIds(final List<String> idList) {
        return Lists.transform(idList, new Function<String, Long>() {
            @Override
            public Long apply(final String id) {
                return Long.valueOf(id);
            }
        });
    }

    /**
     * 分割id字符串(默认以半角逗号分割)
     *
     * @param ids
     * @return
     */
    public static Set<Long> transformIds(final String ids) {
        return transformIds(ids, ",");
    }

    /**
     * 分割id字符串
     *
     * @param ids
     * @param split
     * @return
     */
    public static Set<Long> transformIds(final String ids, String split) {
        if (!Strings.isNullOrEmpty(ids)) {
            final Iterable<String> splitIds = Splitter.on(split).omitEmptyStrings().split(ids);
            final Iterable<Long> longIds = Iterables.transform(splitIds, new Function<String, Long>() {
                @Override
                public Long apply(final String input) {
                    return Long.valueOf(input);
                }
            });
            return Sets.newLinkedHashSet(longIds);
        } else {
            return Sets.newLinkedHashSet();
        }

    }

    /**
     * 检测是否存在列表中
     *
     * @param list
     * @param enums
     * @param <E>
     * @return
     */
    public static <E> boolean inArray(final Collection<E> list, final E... enums) {
        if (enums == null || enums.length == 0) {
            return false;
        }
        return Lists.newArrayList(enums).parallelStream().anyMatch(e -> list.contains(e));
    }

    /**
     * 检测是否不存在列表中
     *
     * @param list
     * @param enums
     * @param <E>
     * @return
     */
    public static <E> boolean notInArray(final Collection<E> list, final E... enums) {
        return !inArray(list, enums);
    }

    /**
     * 判断列表是否为空（null不抛异常）
     *
     * @param list
     * @param <E>
     * @return
     */
    public static <E> boolean isNotEmpty(final Collection<E> list) {
        return (list != null && !list.isEmpty());
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {

        System.out.println(Ids.compare(100, 2200));
        System.out.println(Ids.compare(0, 0));
        System.out.println(Ids.compare(10L, 3L));
        System.out.println(Ids.compare("asdf", "asdfasdf"));
        System.out.println(Ids.compare("ddeadf", "afe"));

        Long c1 = null, c2 = null;
        System.out.println(Ids.compare(c1, c2));
        System.out.println(Ids.compare("asdf", null));
        System.out.println(Ids.compare(null, "asdfasdf"));

    }
}
