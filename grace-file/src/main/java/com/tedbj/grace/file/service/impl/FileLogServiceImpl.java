package com.tedbj.grace.file.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tedbj.grace.file.entity.GrcFileLog;
import com.tedbj.grace.file.mapper.FileLogMapper;
import com.tedbj.grace.file.service.IFileLogService;

/**
 * 服务实现类
 *
 * @author grace
 * @since 2020-11-07
 */
@Service
public class FileLogServiceImpl extends ServiceImpl<FileLogMapper, GrcFileLog> implements IFileLogService {
    /**
     * Mapper
     */
    @Resource
    FileLogMapper mapper;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size 每页条目数
     * @param queryWrapper 查询条件
     *
     * @return 分页查询结果
     */
    public IPage<GrcFileLog> selectPage(int current, int size, QueryWrapper<GrcFileLog> queryWrapper) {
        return mapper.selectPage(new Page<>(current, size), queryWrapper);
    }

}
