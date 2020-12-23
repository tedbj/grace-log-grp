package com.tedbj.grace.file.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tedbj.grace.file.entity.GrcFileInfo;
import com.tedbj.grace.file.mapper.FileInfoMapper;
import com.tedbj.grace.file.service.IFileConvertService;
import com.tedbj.grace.file.service.IFileInfoService;
import com.tedbj.grace.file.utils.FileNameUtil;
import com.tedbj.grace.file.vo.FileInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 文件记录表服务实现类
 *
 * @author grace
 * @since 2020-11-07
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, GrcFileInfo> implements IFileInfoService {

    /**
     * 文件记录表Mapper
     */
    @Resource
    FileInfoMapper mapper;

    /**
     * 文件转换服务
     */
    @Resource
    IFileConvertService fileConvertService;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size 每页条目数
     * @param queryWrapper 查询条件
     *
     * @return 分页查询结果
     */
    public IPage<GrcFileInfo> selectPage(int current, int size, QueryWrapper<GrcFileInfo> queryWrapper) {
        return mapper.selectPage(new Page<>(current, size), queryWrapper);
    }

    /**
     * 增加文件信息
     *
     * @param fileInfo 文件信息
     *
     * @return 是否成功
     */
    @Override
    public boolean addInfoAndConvert(FileInfo fileInfo) {
        GrcFileInfo grcFileInfo = BeanUtil.toBean(fileInfo, GrcFileInfo.class);
        //如果是office文档，则进行转换pdf
        if (FileNameUtil.isOffice(fileInfo.getFileName())) {
            fileConvertService.officeToPdfAsync(grcFileInfo);
        }
        return mapper.insert(grcFileInfo) > 0;
    }

    /**
     * 根据文件ID获取文件全路径
     *
     * @param fileId 文件ID
     *
     * @return 文件全路径
     */
    @Override
    public FileInfo getFileInfo(String fileId) {
        FileInfo fileInfo = new FileInfo();
        GrcFileInfo grcFileInfo = mapper.selectOne(new LambdaQueryWrapper<GrcFileInfo>().eq(GrcFileInfo::getFileId, fileId));
        if (grcFileInfo != null) {
            fileInfo = BeanUtil.toBean(grcFileInfo, FileInfo.class);
            return fileInfo;
        } else {
            return null;
        }

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
        return mapper.delete(new LambdaQueryWrapper<GrcFileInfo>().eq(GrcFileInfo::getFileId, fileId)) > 0;
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
        GrcFileInfo grcFileInfo = BeanUtil.toBean(fileInfo, GrcFileInfo.class);
        if (StrUtil.isNotBlank(grcFileInfo.getPrevFormat())) {
            FileUtil.del(oldPdfFileFullName);
            grcFileInfo.setPrevFormat(null);
        }
        int update = mapper.update(grcFileInfo, new LambdaQueryWrapper<GrcFileInfo>().eq(GrcFileInfo::getFileId, fileInfo.getFileId()));
        if (FileNameUtil.isOffice(grcFileInfo.getFileName())) {
            fileConvertService.officeToPdfAsync(grcFileInfo);
        }
        return update > 0;
    }

}
