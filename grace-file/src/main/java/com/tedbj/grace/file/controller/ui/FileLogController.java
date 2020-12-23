package com.tedbj.grace.file.controller.ui;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tedbj.grace.basic.annotation.ApiType;
import com.tedbj.grace.basic.constant.ApiTypeConstant;
import com.tedbj.grace.basic.constant.DefaultValues;
import com.tedbj.grace.basic.format.Result;
import com.tedbj.grace.basic.front.Form;
import com.tedbj.grace.basic.front.Grid;
import com.tedbj.grace.basic.util.CondUtil;
import com.tedbj.grace.basic.util.ConvertUtil;
import com.tedbj.grace.file.entity.GrcFileLog;
import com.tedbj.grace.file.service.IFileLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 前端控制器
 *
 * @author grace
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/file-log-web")
@Api(tags = {"文件日志"}, description = "文件日志")
public class FileLogController {
    /**
     * 服务类
     */
    @Resource
    IFileLogService service;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size 每页条目数
     * @param conditions 查询条件
     *
     * @return 响应结果
     */
    @GetMapping("/query")
    @ApiOperation(value = "分页查询", notes = "分页查询功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", paramType = "query", example = "1", required = true),
            @ApiImplicitParam(name = "size", value = "每页条目数", dataType = "int", paramType = "query", example = DefaultValues.PAGE_SIZE),
            @ApiImplicitParam(name = "conditions", value = "查询条件", dataType = "string", paramType = "query")
    })
    @ApiType(ApiTypeConstant.WEB)
    @ApiOperationSupport(order = 10)
    public Result<Grid<GrcFileLog>> query(@RequestParam int current, @RequestParam(defaultValue = DefaultValues.PAGE_SIZE) int size, @RequestParam(required = false) String conditions) {
        QueryWrapper<GrcFileLog> queryWrapper = CondUtil.convertToQueryWrapper(conditions);
        IPage<GrcFileLog> result = service.selectPage(current, size, queryWrapper);
        Grid<GrcFileLog> grid = ConvertUtil.plusGrid2Grid(result);
        return Result.success(grid);
    }

    /**
     * 根据条件进行查询
     *
     * @param conditions 查询条件
     *
     * @return 响应结果
     */
    @GetMapping("/queryAll")
    @ApiOperation(value = "查询", notes = "查询功能")
    @ApiImplicitParam(name = "conditions", value = "查询条件", dataType = "string", paramType = "query")
    @ApiType(ApiTypeConstant.WEB)
    @ApiOperationSupport(order = 20)
    public Result<Grid<GrcFileLog>> queryAll(@RequestParam(required = false) String conditions) {
        QueryWrapper wrapper = CondUtil.convertToQueryWrapper(conditions);
        List<GrcFileLog> entities = service.list(wrapper);
        Grid<GrcFileLog> grid = ConvertUtil.list2Grid(entities);
        return Result.success(grid);
    }

    /**
     * 根据ID进行查询
     *
     * @param id 主键
     *
     * @return 响应结果
     */
    @GetMapping("/queryById")
    @ApiOperation(value = "根据ID进行查询", notes = "根据ID进行查询功能")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "int", paramType = "query", required = true)
    @ApiType(ApiTypeConstant.WEB)
    @ApiOperationSupport(order = 30)
    public Result<Form<GrcFileLog>> queryById(@RequestParam Integer id) {
        GrcFileLog entity = service.getById(id);
        Form<GrcFileLog> form = new Form();
        form.setData(entity);
        return Result.success(form);
    }

}
