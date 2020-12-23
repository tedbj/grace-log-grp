package com.tedbj.grace.file.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 响应工具类
 */
@Slf4j
public final class ResponseUtil {

    /**
     * 字节大小
     */
    private static final int BYTE_SIZE = 2048;

    /**
     * 空的构造函数
     */
    private ResponseUtil() {
    }

    /**
     * 显示文本
     *
     * @param response 响应
     * @param text 文本
     */
    public static void writeTextToBrowser(HttpServletResponse response, String text) {
        writeDataToBrowser(response, text, "text/html");
    }

    /**
     * 写入JSON
     *
     * @param response 响应
     * @param obj 对象
     */
    public static void writeJsonToBrowser(HttpServletResponse response, Object obj) {
        writeDataToBrowser(response, JSONUtil.toJsonStr(obj), "application/json");
    }

    /**
     * 写入数据
     *
     * @param response 响应
     * @param text 文本
     * @param format 格式
     */
    private static void writeDataToBrowser(HttpServletResponse response, String text, String format) {
        //这句话的意思，是让浏览器用utf8来解析返回的数据
        response.setHeader("Content-type", format + ";charset=UTF-8");
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter pw = response.getWriter();) {
            pw.write(text);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 写入文件流到response
     *
     * @param response 响应
     * @param fis 输入流
     */
    public static void writeFileInputStream(HttpServletResponse response, InputStream fis) {
        response.setCharacterEncoding("UTF-8");
        byte[] buffer = new byte[BYTE_SIZE];
        try (BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = response.getOutputStream()) {
            int i;
            while ((i = bis.read(buffer)) != -1) {
                //开始导出文件
                os.write(buffer, 0, i);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param response 响应
     * @param file 文件
     * @param fileName 文件名
     */
    public static void downloadFile(File file, HttpServletResponse response, String fileName) {
        int len;
        try {
            String filename = new String(fileName.getBytes("utf-8"), "ISO8859-1");
            //清除首部的空白行
            response.reset();
            //是设置文件类型的，bin这个文件类型是不存在的，浏览器遇到不存在的文件类型就会出现一个下载提示。
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            //读取磁盘上的文件
            InputStream inStream = new FileInputStream(file);
            // 循环取出流中的数据
            byte[] b = new byte[BYTE_SIZE];
            while ((len = inStream.read(b)) > 0) {
                //向客户端响应
                response.getOutputStream().write(b, 0, len);
            }
            //关闭流
            inStream.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param file 文件
     * @param response 响应
     */
    public static void downloadFile(File file, HttpServletResponse response) {
        downloadFile(file, response, file.getName());
    }

    /**
     * 预览文件
     *
     * @param request 请求
     * @param response 响应
     * @param fileFullName 文件全名
     * @param filename 展示文件名
     */
    public static void preview(String fileFullName, String filename, HttpServletRequest request, HttpServletResponse response) {
        File file = new File(fileFullName);
        String fileName = file.getName();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            request.setCharacterEncoding("UTF-8");
            String agent = request.getHeader("User-Agent").toUpperCase();
            if (agent.indexOf("MSIE") <= 0 && (agent.indexOf("RV") == -1 || agent.indexOf("FIREFOX") != -1)) {
                new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }

            response.reset();
            filename = new String(fileName.getBytes("utf-8"), "ISO8859-1");
            byte[] b = new byte[BYTE_SIZE];

            int len;
            while ((len = fis.read(b)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }

            response.flushBuffer();
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

