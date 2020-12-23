package com.tedbj.grace.file.controller.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tedbj.grace.basic.annotation.ApiType;
import com.tedbj.grace.basic.constant.ApiTypeConstant;
import com.tedbj.grace.file.service.IFileInfoService;
import com.tedbj.grace.file.vo.FileInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 文件记录表前端控制器
 */
@RestController
@RequestMapping("/file-api")
@Api(tags = {"文件操作API"}, description = "文件操作API")
@ApiIgnore
public class FileApiController {

    /**
     * 文件记录
     */
    @Resource
    IFileInfoService fileInfoService;

    /**
     * 新增
     *
     * @param fileInfo 实体对象
     *
     * @return 是否成功
     */
    @PostMapping("/addInfoAndConvert")
    @ApiOperation(value = "增加文件记录", notes = "增加文件记录")
    @ApiType(ApiTypeConstant.API)
    @ApiOperationSupport(order = 10)
    public boolean addInfoAndConvert(@RequestBody FileInfo fileInfo) {
        return fileInfoService.addInfoAndConvert(fileInfo);
    }

    /**
     * 根据文件ID获取文件属性
     *
     * @param fileId 文件ID
     *
     * @return 文件全路径
     */
    @GetMapping("/getFileInfo")
    @ApiType(ApiTypeConstant.API)
    @ApiOperation(value = "根据文件ID获取文件全路径", notes = "根据文件ID获取文件全路径")
    @ApiImplicitParam(name = "fileId", value = "文件ID", dataType = "string", paramType = "query", required = true)
    @ApiOperationSupport(order = 20)
    FileInfo getFileInfo(@RequestParam("fileId") String fileId) {
        return fileInfoService.getFileInfo(fileId);
    }

    /**
     * 删除文件信息
     *
     * @param fileId 文件ID
     *
     * @return 是否成功
     */
    @GetMapping("/deleteFileInfo")
    @ApiOperation(value = "删除文件信息", notes = "删除文件信息")
    @ApiType(ApiTypeConstant.API)
    @ApiOperationSupport(order = 30)
    boolean deleteFileInfo(@RequestParam("fileId") String fileId) {
        return fileInfoService.deleteFileInfo(fileId);
    }

    /**
     * 更新文件信息
     *
     * @param fileInfo 文件信息
     * @param oldPdfFileFullName 旧的文件全名
     *
     * @return 是否成功
     */
    @PostMapping("/updateFileInfoAndConvert")
    @ApiOperation(value = "更新文件信息", notes = "更新文件信息")
    @ApiType(ApiTypeConstant.API)
    @ApiOperationSupport(order = 40)
    boolean updateFileInfoAndConvert(@RequestBody FileInfo fileInfo, @RequestParam("oldPdfFileFullName") String oldPdfFileFullName) {
        return fileInfoService.updateFileInfoAndConvert(fileInfo, oldPdfFileFullName);
    }

}
