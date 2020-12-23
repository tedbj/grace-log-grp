package com.tedbj.grace.file.service.impl;

import javax.annotation.Resource;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tedbj.grace.basic.exception.BusinessException;
import com.tedbj.grace.file.config.FileConfig;
import com.tedbj.grace.file.entity.GrcFileInfo;
import com.tedbj.grace.file.mapper.FileInfoMapper;
import com.tedbj.grace.file.service.IFileConvertService;
import com.tedbj.grace.file.utils.AsposeOfficeUtil;
import com.tedbj.grace.file.utils.FileNameUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件转换异步实现类
 */
@Service
@Slf4j
@RefreshScope
public class FileConvertServiceImpl implements IFileConvertService {

    /**
     * 文件信息
     */
    @Resource
    private FileInfoMapper fileInfoMapper;

    /**
     * 文件转换
     *
     * @param grcFileInfo 文件信息对象
     */
    @Override
    @Async("asyncServiceExecutor")
    public void officeToPdfAsync(GrcFileInfo grcFileInfo) {
        //NFS处理方式
        if (FileConfig.isNFSStorage()) {
            try {
                String absolutePath = FileConfig.getNfsPath() + grcFileInfo.getFilePath();
                String fileName = FileNameUtil.getNameWithoutFormat(grcFileInfo.getFileName()) + "_" + grcFileInfo.getFileId() + "." + grcFileInfo.getFileFormat();
                AsposeOfficeUtil.officeToPdf(absolutePath, fileName);
                grcFileInfo.setPrevFormat("pdf");
                fileInfoMapper.update(grcFileInfo, new LambdaQueryWrapper<GrcFileInfo>().eq(GrcFileInfo::getFileId, grcFileInfo.getFileId()));
            } catch (BusinessException e) {
                log.error(e.getMessage());
                throw new BusinessException("文件转换失败");
            }
        } else {
            return;
        }
    }

}
