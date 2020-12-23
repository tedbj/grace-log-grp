package com.tedbj.grace.file.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.tedbj.grace.file.vo.FileResult;

/**
 * 文件服务
 */
public interface IFileApi {

    /**
     * 上传
     *
     * @param tenantCode 租户编码
     * @param file 文件
     *
     * @return FileResult对象
     */
    FileResult upload(String tenantCode, MultipartFile file);

    /**
     * 下载
     *
     * @param fileId 文件Id
     * @param response 响应
     */
    void download(String fileId, HttpServletResponse response);

    /**
     * 更新文件
     *
     * @param fileId 文件ID
     * @param file 文件
     *
     * @return FileResult对象
     */
    FileResult updateFile(String fileId, MultipartFile file);

    /**
     * 删除文件
     *
     * @param fileId 文件Id
     *
     * @return FileResult对象
     */
    FileResult delete(String fileId);

    /**
     * 预览
     *
     * @param response 响应
     * @param fileId 文件ID
     */
    void preview(String fileId, HttpServletResponse response, HttpServletRequest request);

}
