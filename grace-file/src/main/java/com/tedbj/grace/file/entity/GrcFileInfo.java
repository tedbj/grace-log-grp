package com.tedbj.grace.file.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件记录表
 * </p>
 *
 * @author grace
 * @since 2020-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("grc_file_info")
@ApiModel(value = "文件记录表", description = "文件记录表")
public class GrcFileInfo implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -3104872592678430805L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "主键不能为空")
    @Min(value = -2147483648, message = "主键的最小值为-2147483648")
    @Max(value = 2147483647, message = "主键的最大值为2147483647")
    private Integer id = 0;

    /**
     * 租户编码
     */
    @ApiModelProperty("租户编码")
    @NotNull(message = "租户编码不能为空")
    @Size(min = 1, max = 255, message = "租户编码的长度在1和255之间")
    private String tenantCode;

    /**
     * 文件ID
     */
    @ApiModelProperty("文件ID")
    @NotNull(message = "文件ID不能为空")
    @Size(min = 1, max = 32, message = "文件ID的长度在1和32之间")
    private String fileId;

    /**
     * 存储类型
     */
    @ApiModelProperty("存储类型")
    @NotNull(message = "存储类型不能为空")
    @Size(min = 1, max = 32, message = "存储类型的长度在1和32之间")
    private String storeType;

    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    @Size(max = 255, message = "文件路径的长度在0和255之间")
    private String filePath;

    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    @NotNull(message = "文件名不能为空")
    @Size(min = 1, max = 64, message = "文件名的长度在1和64之间")
    private String fileName;

    /**
     * 文件格式
     */
    @ApiModelProperty("文件格式")
    @Size(max = 32, message = "文件格式的长度在0和32之间")
    private String fileFormat;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    @TableField("file_size")
    @NotNull(message = "文件大小不能为空")
    @Min(value = -2147483648, message = "文件大小的最小值为-2147483648")
    @Max(value = 2147483647, message = "文件大小的最大值为2147483647")
    private Integer fileSize;

    /**
     * 上传账号
     */
    @ApiModelProperty("上传账号")
    @NotNull(message = "上传账号不能为空")
    @Size(min = 1, max = 32, message = "上传账号的长度在1和32之间")
    private String uploadAccName;

    /**
     * 上传人姓名
     */
    @ApiModelProperty("上传人姓名")
    @Size(max = 255, message = "上传人姓名的长度在0和255之间")
    private String uploadUserName;

    /**
     * 上传时间
     */
    @ApiModelProperty("上传时间")
    @NotNull(message = "上传时间不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @NotNull(message = "更新时间不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 预览文件格式
     */
    @ApiModelProperty("预览文件格式")
    @Size(max = 32, message = "预览文件格式的长度在0和32之间")
    private String prevFormat;

}
