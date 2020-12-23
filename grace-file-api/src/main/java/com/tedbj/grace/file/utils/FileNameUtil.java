package com.tedbj.grace.file.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 文件名工具类
 */
public class FileNameUtil {

    /**
     * 点
     */
    private static final String POINT = ".";

    /**
     * 获取文件的根路径
     *
     * @param fullFileName 文件全名含路径
     *
     * @return 结果
     */
    public static String getPath(String fullFileName) {
        if (StrUtil.isBlank(fullFileName) || !fullFileName.contains("/")) {
            return "";
        } else {
            return fullFileName.substring(0, fullFileName.lastIndexOf("/"));
        }
    }

    /**
     * 获取文件名
     *
     * @param fullFileName 文件全名含路径
     *
     * @return 文件名
     */
//    public static String getName(String fullFileName) {
//        String result;
//        if (StringUtil.isBlank(fullFileName)) {
//            //如果为空，则返回空
//            result = "";
//        } else if (!fullFileName.contains("/")) {
//            result = fullFileName;
//        } else if (fullFileName.contains(POINT)) {
//            result = fullFileName.substring(0, fullFileName.lastIndexOf(POINT));
//        } else {
//            result = fullFileName.substring(0, fullFileName.lastIndexOf(POINT));
//        }
//        return result;
//    }

    /**
     * 获取格式
     *
     * @param fileName 文件名
     *
     * @return 格式名称
     */
    public static String getFormat(String fileName) {
        String result;
        if (StrUtil.isBlank(fileName)) {
            //如果为空，则返回空
            result = "";
        } else if (fileName.contains(".")) {
            if (fileName.length() == fileName.lastIndexOf(".") + 1) {
                result = "";
            } else {
                //如果含有.则获取.后面的字符串
                result = fileName.substring(fileName.lastIndexOf(".") + 1);
            }
        } else {
            result = "";
        }
        return result;
    }

    /**
     * 获取没有格式的文件名
     *
     * @param fileName 文件名
     *
     * @return 没有格式的文件名
     */
    public static String getNameWithoutFormat(String fileName) {
        String result;
        if (StrUtil.isBlank(fileName)) {
            //如果为空，则返回空
            result = "";
        } else if (fileName.contains(".")) {
            result = fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            result = "";
        }
        return result;
    }

    /**
     * 是否是word
     *
     * @param fileName 文件名
     *
     * @return 是或者否
     */
    public static boolean isWord(String fileName) {
        return "doc".equals(getFormat(fileName)) || "docx".equals(getFormat(fileName));
    }

    /**
     * 是否是PPT
     *
     * @param fileName 文件名
     *
     * @return 是或者否
     */
    public static boolean isPPT(String fileName) {
        return "ppt".equals(getFormat(fileName)) || "pptx".equals(getFormat(fileName));
    }

    /**
     * 是否是word
     *
     * @param fileName 文件名
     *
     * @return 是或者否
     */
    public static boolean isExcel(String fileName) {
        return "xls".equals(getFormat(fileName)) || "xlsx".equals(getFormat(fileName));
    }

    /**
     * 是否为office文档
     *
     * @param fileName 文件名
     *
     * @return 是或者否
     */
    public static boolean isOffice(String fileName) {
        return isWord(fileName) || isPPT(fileName) || isExcel(fileName);
    }

}
