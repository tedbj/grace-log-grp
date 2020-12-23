package com.tedbj.grace.file.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tedbj.grace.file.entity.GrcFileInfo;
import com.tedbj.grace.file.vo.FileInfo;

/**
 * 文件记录表服务类
 *
 * @author grace
 * @since 2020-11-07
 */
public interface IFileInfoService extends IService<GrcFileInfo> {
    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size 每页条目数
     * @param queryWrapper 查询条件
     *
     * @return 分页查询结果
     */
    IPage<GrcFileInfo> selectPage(int current, int size, QueryWrapper<GrcFileInfo> queryWrapper);

    /**
     * 增加文件记录
     *
     * @param fileInfo 文件信息
     *
     * @return 是否成功
     */
    boolean addInfoAndConvert(FileInfo fileInfo);

    /**
     * 根据文件ID获取文件全路径
     *
     * @param fileId 文件ID
     *
     * @return 文件属性
     */
    FileInfo getFileInfo(String fileId);

    /**
     * 删除文件信息
     *
     * @param fileId 文件ID
     *
     * @return 是否成功
     */
    boolean deleteFileInfo(String fileId);

    /**
     * 更新文件信息
     *
     * @param fileInfo 文件信息
     * @param oldPdfFileFullName 旧的文件名
     *
     * @return 是否成功
     */
    boolean updateFileInfoAndConvert(FileInfo fileInfo, String oldPdfFileFullName);

}
