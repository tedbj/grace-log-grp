package com.tedbj.grace.file.controller.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tedbj.grace.basic.annotation.ApiType;
import com.tedbj.grace.basic.constant.ApiTypeConstant;
import com.tedbj.grace.file.api.IFileApi;
import com.tedbj.grace.file.service.IFileConvertService;
import com.tedbj.grace.file.vo.FileResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 文件记录表前端控制器
 */
@RestController
@RequestMapping("/file-test-api")
@Api(tags = {"文件操作测试"}, description = "文件操作测试")
public class FileApiTestController {

    /**
     * 文件转换
     */
    @Resource
    IFileConvertService fileConvertService;

    /**
     * 文件上传
     */
    @Resource
    IFileApi fileApi;

    /**
     * 文件上传
     *
     * @param tenantCode 租户编码
     * @param file 文件
     *
     * @return 结果
     */
    @PostMapping("/upload")
    @ApiOperation("上传文件")
    @ApiType(ApiTypeConstant.API)
    @ApiImplicitParam(name = "tenantCode", value = "租户编码", dataType = "string", paramType = "query", required = true)
    @ApiOperationSupport(order = 10)
    public FileResult upLoadFile(@RequestParam String tenantCode, @RequestPart MultipartFile file) {
        return fileApi.upload(tenantCode, file);
    }

    /**
     * 下载文件
     *
     * @param fileId 文件ID
     * @param response 响应
     */
    @GetMapping("/download")
    @ApiType(ApiTypeConstant.API)
    @ApiOperation(value = "下载文件", notes = "下载文件")
    @ApiOperationSupport(order = 20)
    void download(@RequestParam("fileId") String fileId, HttpServletResponse response) {
        fileApi.download(fileId, response);
    }

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     *
     * @return FileResult 操作结果
     */
    @GetMapping("/delete")
    @ApiType(ApiTypeConstant.API)
    @ApiOperation(value = "删除文件", notes = "删除文件")
    @ApiOperationSupport(order = 30)
    FileResult delete(@RequestParam("fileId") String fileId) {
        return fileApi.delete(fileId);
    }

    /**
     * 更新文件
     *
     * @param fileId 文件ID
     * @param file 文件
     *
     * @return FileResult 操作结果
     */
    @PostMapping("/update")
    @ApiType(ApiTypeConstant.API)
    @ApiOperation(value = "更新文件", notes = "更新文件")
    @ApiOperationSupport(order = 40)
    FileResult update(@RequestParam("fileId") String fileId, MultipartFile file) {
        return fileApi.updateFile(fileId, file);
    }

    /**
     * 预览文件
     *
     * @param fileId 文件ID
     * @param response 响应
     */
    @GetMapping("/preview")
    @ApiType(ApiTypeConstant.API)
    @ApiOperation(value = "预览文件", notes = "预览文件")
    @ApiOperationSupport(order = 50)
    void preview(@RequestParam("fileId") String fileId, HttpServletResponse response, HttpServletRequest request) {
        fileApi.preview(fileId, response, request);
    }

    //pdf加水印

    //图片压缩

    //生成二维码

}
