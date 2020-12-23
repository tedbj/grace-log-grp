package com.tedbj.grace.file.api;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tedbj.grace.file.config.FileConfig;
import com.tedbj.grace.file.utils.FileNameUtil;
import com.tedbj.grace.file.utils.ResponseUtil;
import com.tedbj.grace.file.vo.FileInfo;
import com.tedbj.grace.file.vo.FileResult;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件服务类
 */
@Slf4j
@Service
public class FileApi implements IFileApi {

    /**
     * 文件ID的长度
     */
    private static final int FILE_ID_LENGTH = 20;

    /**
     * 文件Feign接口
     */
    @Resource
    IFileFeignApi fileFeignApi;

    /**
     * 校验checkFileInfo
     *
     * @param fileInfo 文件信息
     * @param fileResult 结果对象
     *
     * @return 是或者否
     */
    private static boolean checkFileInfo(FileInfo fileInfo, FileResult fileResult) {
        if (fileInfo == null || fileInfo.getFileName() == null) {
            fileResult.setSuccess(false);
            fileResult.setFailureReason("获取文件信息失败");
            return false;
        }

        return true;
    }

    /**
     * 上传
     *
     * @param tenantCode 租户编码
     * @param file 文件
     *
     * @return FileResult对象
     */
    public FileResult upload(String tenantCode, MultipartFile file) {
        FileResult fileResult = new FileResult();
        FileInfo fileInfo = new FileInfo();
        if (StrUtil.isBlank(tenantCode) || file == null) {
            fileResult.setSuccess(false);
            fileResult.setFailureReason("租户编码为空或文件为空");
        } else {
            fileInfo.setTenantCode(tenantCode);
            if (FileConfig.isNFSStorage()) {
                fileInfo.setStoreType(FileConfig.getStorageType());
                try {
                    //生成文件ID
                    String fileId = RandomUtil.randomStringUpper(FILE_ID_LENGTH);
                    fileInfo.setFileId(fileId);
                    //获取日期，格式为yyy/MM/dd
                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    String text = date.format(formatters);
                    //文件路径
                    String path = FileConfig.getNfsPath() + File.separator + tenantCode + File.separator + text;
                    fileInfo.setFilePath(tenantCode + "/" + text);
                    //创建路径
                    FileUtil.mkdir(path);
                    //新的文件名，“文件名称_文件ID.格式”
                    String fileStorageName = FileNameUtil.getNameWithoutFormat(file.getOriginalFilename()) + "_" + fileId + "." + FileNameUtil.getFormat(file.getOriginalFilename());
                    fileInfo.setFileName(file.getOriginalFilename());
                    fileInfo.setFileFormat(FileNameUtil.getFormat(file.getOriginalFilename()));
                    //上传文件
                    File fileUpload = new File(path + File.separator + fileStorageName);
                    fileInfo.setUploadTime(LocalDateTime.now());
                    file.transferTo(fileUpload);
                    boolean result = fileFeignApi.addInfoAndConvert(fileInfo);
                    if (result) {
                        fileInfo.setFileSize(Integer.parseInt(StrUtil.toString(file.getSize())));
                        //写入FileResult对象
                        fileResult.setSuccess(true);
                        fileResult.setFileId(fileId);
                    } else {
                        FileUtil.del(fileUpload);
                        //写入FileResult对象
                        fileResult.setSuccess(false);
                        fileResult.setFailureReason("文件信息记录失败");
                    }
                } catch (IOException e) {
                    log.error(e.getMessage());
                    fileResult.setSuccess(false);
                    fileResult.setFailureReason(e.getMessage());
                }
            } else if (FileConfig.isOSSStorage()) {
                fileResult.setSuccess(false);
                fileResult.setFailureReason("暂未实现");
            }
        }
        return fileResult;
    }

    /**
     * 下载文件
     *
     * @param fileId 文件Id
     * @param response 响应
     */
    @Override
    public void download(String fileId, HttpServletResponse response) {
        FileInfo fileInfo = fileFeignApi.getFileInfo(fileId);
        if (fileInfo != null) {
            ResponseUtil.downloadFile(new File(FileConfig.getNfsPath() + fileInfo.getFilePath() + "/" + FileNameUtil.getNameWithoutFormat(fileInfo.getFileName()) + "_" + fileInfo.getFileId() + "." + FileNameUtil.getFormat(fileInfo.getFileName())),
                    response,
                    fileInfo.getFileName());
        } else {
            ResponseUtil.writeJsonToBrowser(response, "文件下载失败");
        }
    }

    /**
     * 更新文件
     *
     * @param fileId 文件ID
     * @param file 文件
     *
     * @return FileResult对象
     */
    @Override
    public FileResult updateFile(String fileId, MultipartFile file) {
        FileResult fileResult = new FileResult();
        fileResult.setFileId(fileId);
        FileInfo fileInfoOld = fileFeignApi.getFileInfo(fileId);
        boolean b = checkFileInfo(fileInfoOld, fileResult);
        if (b) {
            try {
                FileUtil.del(FileConfig.getNfsPath() + fileInfoOld.getFilePath() + "/" + FileNameUtil.getNameWithoutFormat(fileInfoOld.getFileName()) + "_" + fileInfoOld.getFileId() + "." + FileNameUtil.getFormat(fileInfoOld.getFileName()));
                String newFileFullName =
                        FileConfig.getNfsPath() + fileInfoOld.getFilePath()
                                + "/" + FileNameUtil.getNameWithoutFormat(file.getOriginalFilename()) + "_" + fileId + "." + FileNameUtil.getFormat(file.getOriginalFilename());
                File newFile = new File(newFileFullName);
                file.transferTo(newFile);
                String pdfPath =
                        FileConfig.getNfsPath() + fileInfoOld.getFilePath() + "/" + FileNameUtil.getNameWithoutFormat(fileInfoOld.getFileName()) + "_" + fileId + "." + fileInfoOld.getPrevFormat();
                fileInfoOld.setFileId(fileId);
                fileInfoOld.setFileName(file.getOriginalFilename());
                fileInfoOld.setFileSize(Integer.parseInt(StrUtil.toString(file.getSize())));
                fileInfoOld.setUpdateTime(LocalDateTime.now());
                boolean flag = fileFeignApi.updateFileInfoAndConvert(fileInfoOld,
                        pdfPath);
                if (flag) {
                    fileResult.setSuccess(true);
                } else {
                    fileResult.setSuccess(false);
                    fileResult.setFailureReason("文件信息更新失败");
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                fileResult.setSuccess(false);
                fileResult.setFailureReason(e.getMessage());
            }
        }

        return fileResult;
    }

    /**
     * 删除文件
     *
     * @param fileId 文件Id
     *
     * @return FileResult对象
     */
    @Override
    public FileResult delete(String fileId) {
        FileResult fileResult = new FileResult();
        FileInfo fileInfo = fileFeignApi.getFileInfo(fileId);
        boolean b = checkFileInfo(fileInfo, fileResult);
        if (b) {
            if (StrUtil.isNotBlank(fileInfo.getPrevFormat())) {
                String prevFullName =
                        FileConfig.getNfsPath() + fileInfo.getFilePath() + "/" + FileNameUtil.getNameWithoutFormat(fileInfo.getFileName()) + "_" + fileId + "." + fileInfo.getPrevFormat();
                FileUtil.del(prevFullName);
            }
            boolean del =
                    FileUtil.del(FileConfig.getNfsPath() + fileInfo.getFilePath() + "/" + FileNameUtil.getNameWithoutFormat(fileInfo.getFileName()) + "_" + fileInfo.getFileId() + "." + FileNameUtil.getFormat(fileInfo.getFileName()));
            if (del) {
                boolean delFlag = fileFeignApi.deleteFileInfo(fileId);
                if (delFlag) {
                    fileResult.setSuccess(true);
                    fileResult.setFileId(fileId);
                } else {
                    fileResult.setSuccess(false);
                    fileResult.setFileId(fileId);
                    fileResult.setFailureReason("文件信息删除失败");
                }
            } else {
                fileResult.setSuccess(false);
                fileResult.setFileId(fileId);
                fileResult.setFailureReason("文件删除失败");
            }
        }
        return fileResult;
    }

    /**
     * 预览
     *
     * @param fileId 文件ID
     * @param response 响应
     */
    @Override
    public void preview(String fileId, HttpServletResponse response, HttpServletRequest request) {
        FileInfo fileInfo = fileFeignApi.getFileInfo(fileId);
        if (fileInfo == null || StrUtil.isBlank(fileInfo.getFileName())) {
            ResponseUtil.writeTextToBrowser(response, "文件不存在");
        } else {
            String format = fileInfo.getFileFormat();
            if (FileConfig.isNotSupportedPreview(format)) {
                ResponseUtil.writeTextToBrowser(response, "不支持此格式的预览");
                return;
            }
            if (StrUtil.isNotBlank(fileInfo.getPrevFormat())) {
                String fullName =
                        FileConfig.getNfsPath() + fileInfo.getFilePath() + "/" + FileNameUtil.getNameWithoutFormat(fileInfo.getFileName()) + "_" + fileInfo.getFileId() + "." + fileInfo.getPrevFormat();
                ResponseUtil.preview(fullName, fileInfo.getFileName(), request, response);
            } else {
                String fullName =
                        FileConfig.getNfsPath() + fileInfo.getFilePath() + "/" + FileNameUtil.getNameWithoutFormat(fileInfo.getFileName()) + "_" + fileInfo.getFileId() + "." + FileNameUtil.getFormat(fileInfo.getFileName());
                ResponseUtil.preview(fullName, fileInfo.getFileName(), request, response);
            }
        }
    }

}
