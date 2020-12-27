package com.tedbj.grace.file.api;

import com.tedbj.grace.file.fallback.IFileFeignFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 实例接口
 */
@FeignClient(name = "grace-file", path = "/file-api-api", fallback = IFileFeignFallback.class)
public interface IFileFeignTestApi {
    /**
     * 测试
     *
     * @param param 参数
     */
    @GetMapping("/test")
    @ApiOperation(value = "测试", notes = "测试")
    String test(@RequestParam("param") String param);
}
