package com.tedbj.grace.file.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.IdType;
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
 *
 * </p>
 *
 * @author grace
 * @since 2020-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("grc_file_log")
@ApiModel(value = "文件日志表", description = "文件日志表")
public class GrcFileLog implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -4945153805093370898L;

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
     * 文件ID
     */
    @ApiModelProperty("文件ID")
    @NotNull(message = "文件ID不能为空")
    @Size(min = 1, max = 32, message = "文件ID的长度在1和32之间")
    private String fileId;

    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    @Size(max = 255, message = "文件名的长度在0和255之间")
    private String fileName;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private Double fileSize;

    /**
     * 操作账号
     */
    @ApiModelProperty("操作账号")
    @NotNull(message = "操作账号不能为空")
    @Size(min = 1, max = 32, message = "操作账号的长度在1和32之间")
    private String optAccName;

    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    @Size(max = 64, message = "操作人姓名的长度在0和64之间")
    private String optUserName;

    /**
     * 操作类型
     */
    @ApiModelProperty("操作类型")
    @NotNull(message = "操作类型不能为空")
    @Size(min = 1, max = 8, message = "操作类型的长度在1和8之间")
    private String optType;

    /**
     * 操作时间
     */
    @ApiModelProperty("操作时间")
    @NotNull(message = "操作时间不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime optTime;

    /**
     * 操作结果
     */
    @ApiModelProperty("操作结果")
    @Size(max = 1, message = "操作结果的长度在0和1之间")
    private String optResult;

}
