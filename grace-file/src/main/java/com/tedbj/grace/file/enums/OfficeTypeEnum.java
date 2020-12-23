package com.tedbj.grace.file.enums;

/**
 * office类型枚举
 */
public enum OfficeTypeEnum {
    /**
     * word
     */
    WORD("word"),

    /**
     * excel
     */
    EXCEL("excel"),

    /**
     * ppt
     */
    PPT("ppt");
    /**
     * 值
     */
    private String value;

    /**
     * 构造函数
     *
     * @param value 值
     */
    OfficeTypeEnum(String value) {
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
