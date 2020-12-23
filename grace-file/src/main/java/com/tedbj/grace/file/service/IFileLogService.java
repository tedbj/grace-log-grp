package com.tedbj.grace.file.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tedbj.grace.file.entity.GrcFileLog;

/**
 * 服务类
 *
 * @author grace
 * @since 2020-11-07
 */
public interface IFileLogService extends IService<GrcFileLog> {
    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size 每页条目数
     * @param queryWrapper 查询条件
     *
     * @return 分页查询结果
     */
    IPage<GrcFileLog> selectPage(int current, int size, QueryWrapper<GrcFileLog> queryWrapper);

}
