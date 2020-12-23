package com.tedbj.grace.file.vo;

import lombok.Data;

@Data
public class FileResult {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 失败原因
     */
    private String failureReason;

    /**
     * 文件操作结果
     */
    public FileResult() {

    }

    /**
     * 构造函数
     *
     * @param success 是否成功
     */
    public FileResult(boolean success) {
        this.success = success;
    }

    /**
     * 构造函数
     *
     * @param success 是否成功
     * @param fileId 文件ID
     */
    public FileResult(boolean success, String fileId) {
        this.success = success;
        this.fileId = fileId;
    }

    /**
     * 成功
     *
     * @param fileId 文件ID
     *
     * @return FileResult对象
     */
    public static FileResult success(String fileId) {
        return new FileResult(true, fileId);
    }

    /**
     * 失败
     *
     * @return FileResult对象
     */
    public static FileResult fail() {
        return new FileResult(false);
    }

}

