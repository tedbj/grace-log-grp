package com.tedbj.grace.file.fallback;

import org.springframework.stereotype.Component;

import com.tedbj.grace.file.api.IFileFeignApi;
import com.tedbj.grace.file.vo.FileInfo;

/**
 * 示例fallback
 */
@Component
public class IFileFeignFallback implements IFileFeignApi {
    /**
     * 增加文件信息
     *
     * @param fileInfo 文件信息
     *
     * @return
     */
    @Override
    public boolean addInfoAndConvert(FileInfo fileInfo) {
        return false;
    }

    /**
     * 获取文件全名
     *
     * @param fileId 文件ID
     *
     * @return
     */
    @Override
    public FileInfo getFileInfo(String fileId) {
        return null;
    }

    /**
     * 删除文件信息
     *
     * @param fileId 文件ID
     *
     * @return 是否成功
     */
    @Override
    public boolean deleteFileInfo(String fileId) {
        return false;
    }

    /**
     * 更新文件信息
     *
     * @param fileInfo 文件信息
     *
     * @return 是否成功
     */
    @Override
    public boolean updateFileInfoAndConvert(FileInfo fileInfo, String oldPdfFileFullName) {
        return false;
    }

}
