package com.tedbj.grace.file.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "文件信息", description = "文件信息")
public class FileInfo implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -3124872592678430805L;

    /**
     * 租户编码
     */
    @ApiModelProperty("租户编码")
    private String tenantCode;

    /**
     * 文件ID
     */
    @ApiModelProperty("文件ID")
    private String fileId;

    /**
     * 存储类型
     */
    @ApiModelProperty("存储类型")
    private String storeType;

    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    private String filePath;

    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    private String fileName;

    /**
     * 文件格式
     */
    @ApiModelProperty("文件格式")
    private String fileFormat;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private Integer fileSize;

    /**
     * 上传账号
     */
    @ApiModelProperty("上传账号")
    private String uploadAccName;

    /**
     * 上传人姓名
     */
    @ApiModelProperty("上传人姓名")
    private String uploadUserName;

    /**
     * 上传时间
     */
    @ApiModelProperty("上传时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 预览文件格式
     */
    @ApiModelProperty("预览文件格式")
    private String prevFormat;

}
