package com.tedbj.grace.file.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tedbj.grace.file.entity.GrcFileLog;

/**
 * 文件日志Mapper接口
 *
 * @author grace
 * @since 2020-11-07
 */
@Mapper
public interface FileLogMapper extends BaseMapper<GrcFileLog> {

}
