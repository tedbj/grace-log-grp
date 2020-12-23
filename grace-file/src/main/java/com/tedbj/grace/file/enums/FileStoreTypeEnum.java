package com.tedbj.grace.file.enums;

/**
 * 文件存储类型美剧
 */
public enum FileStoreTypeEnum {

    /**
     * nfs方式
     */
    NFS("nfs"),

    /**
     * oss方式
     */
    OSS("oss");

    /**
     * 值
     */
    private String value;

    /**
     * 构造函数
     *
     * @param value 值
     */
    FileStoreTypeEnum(String value) {
        this.value = value;
    }

    /**
     * get方法
     *
     * @return 值
     */
    public String getValue() {
        return this.value;
    }

}
