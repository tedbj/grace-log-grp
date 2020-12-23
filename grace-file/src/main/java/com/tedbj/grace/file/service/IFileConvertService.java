package com.tedbj.grace.file.service;

import com.tedbj.grace.file.entity.GrcFileInfo;

/**
 * 文件转换服务类
 */
public interface IFileConvertService {
    /**
     * 文件转换
     *
     * @param grcFileInfo 文件记录
     */
    void officeToPdfAsync(GrcFileInfo grcFileInfo);

}
