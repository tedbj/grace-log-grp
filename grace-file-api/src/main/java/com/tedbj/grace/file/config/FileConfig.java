package com.tedbj.grace.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import cn.hutool.system.SystemUtil;

/**
 * 文件配置
 */
@Configuration
@RefreshScope
public class FileConfig {

    /**
     * 文件存储方式
     */
    private static String storageType;

    /**
     * NFS方式-文件路径
     */
    private static String nfsPath;

    /**
     * 临时文件路径
     */
    private static String tempFilePath;

    /**
     * 支持预览的格式
     */
    private static String[] supportedPreviewFormats;

    /**
     * 是否是NFS方式
     *
     * @return 结果
     */
    public static boolean isNFSStorage() {
        return "nfs".equalsIgnoreCase(storageType);
    }

    /**
     * 是否是OSS方式
     *
     * @return 结果
     */
    public static boolean isOSSStorage() {
        return "oss".equalsIgnoreCase(storageType);
    }

    /**
     * nfs方式-文件路径
     *
     * @return 文件路径
     */
    public static String getNfsPath() {
        return getPath(nfsPath);
    }

    /**
     * 文件路径的set方法
     *
     * @param nfsPath 文件路径
     */
    @Value("${grace.file.nfs.path:/data/nfs/;Z:\\\\}")
    public void setNfsPath(String nfsPath) {
        FileConfig.nfsPath = nfsPath;
    }

    /**
     * 支持预览的格式的set方法
     *
     * @param formats 支持预览的格式
     */
    @Value("${grace.file.supported-preview-formats:pdf,txt}")
    public void setSupportedPreviewFormats(String formats) {
        FileConfig.supportedPreviewFormats = formats.split(",");
    }

    /**
     * 支持预览的格式
     *
     * @return 支持预览的格式
     */
    public static String[] getSupportedPreviewFormats() {
        return FileConfig.supportedPreviewFormats;
    }

    /**
     * 是否支持预览
     *
     * @param format 格式
     *
     * @return 是或者否
     */
    public static boolean isSupportedPreview(String format) {
        boolean flag = false;
        for (String f : FileConfig.supportedPreviewFormats) {
            if (f.equalsIgnoreCase(format)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 是否不支持预览
     *
     * @param format 格式
     *
     * @return 是或者否
     */
    public static boolean isNotSupportedPreview(String format) {
        return !isSupportedPreview(format);
    }

    /**
     * 获取临时文件绝对路径
     *
     * @return 文件路径
     */
    public static String getTempFilePath() {
        return getPath(tempFilePath);
    }

    /**
     * 临时文件绝对路径
     *
     * @param tempFilePath 临时文件绝对路径
     */
    @Value("${grace.file.temp-file-path:/temp/;D:\\\\}")
    public void setTempFilePath(String tempFilePath) {
        FileConfig.tempFilePath = tempFilePath;
    }

    /**
     * 获取路径
     *
     * @param path 路径
     *
     * @return 路径
     */
    public static String getPath(String path) {
        String[] paths = path.split(";");
        if (SystemUtil.getOsInfo().isWindows()) {
            return paths.length >= 1 ? paths[1] : "C:\\";
        } else {
            return paths[0];
        }
    }

    /**
     * 获取存储类型
     *
     * @return 存储类型
     */
    public static String getStorageType() {
        return storageType;
    }

    /**
     * 文件存储方式的set方法
     *
     * @param storageType 文件存储方式
     */
    @Value("${grace.file.storage-type:nfs}")
    public void setStorageType(String storageType) {
        FileConfig.storageType = storageType;
    }

}
