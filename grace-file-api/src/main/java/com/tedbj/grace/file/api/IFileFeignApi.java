package com.tedbj.grace.file.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.tedbj.grace.file.fallback.IFileFeignFallback;
import com.tedbj.grace.file.vo.FileInfo;

import io.swagger.annotations.ApiOperation;

/**
 * 实例接口
 */
@FeignClient(name = "grace-file", path = "/file-api", fallback = IFileFeignFallback.class)
public interface IFileFeignApi {
    /**
     * 增加文件记录
     *
     * @param fileInfo 文件信息
     *
     * @return 是否成功
     */
    @PostMapping("/addInfoAndConvert")
    @ApiOperation(value = "增加文件记录", notes = "增加文件记录")
    boolean addInfoAndConvert(@RequestBody FileInfo fileInfo);

    /**
     * 根据文件ID获取文件全路径
     *
     * @param fileId 文件ID
     *
     * @return 文件全路径
     */
    @GetMapping("/getFileInfo")
    @ApiOperation(value = "根据文件ID获取文件全路径", notes = "根据文件ID获取文件全路径")
    FileInfo getFileInfo(@RequestParam("fileId") String fileId);

    /**
     * 删除文件信息
     *
     * @param fileId 文件ID
     *
     * @return 是否成功
     */
    @GetMapping("/deleteFileInfo")
    @ApiOperation(value = "删除文件信息", notes = "删除文件信息")
    boolean deleteFileInfo(@RequestParam("fileId") String fileId);

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
    boolean updateFileInfoAndConvert(@RequestBody FileInfo fileInfo, @RequestParam("oldPdfFileFullName") String oldPdfFileFullName);

}
