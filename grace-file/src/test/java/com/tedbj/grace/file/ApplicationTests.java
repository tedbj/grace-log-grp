package com.tedbj.grace.file;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tedbj.grace.file.entity.GrcFileInfo;
import com.tedbj.grace.file.service.IFileConvertService;
import com.tedbj.grace.file.service.IFileInfoService;
import com.tedbj.grace.file.utils.AsposeOfficeUtil;
import com.tedbj.grace.file.utils.FileNameUtil;
import com.tedbj.grace.file.vo.FileInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Console;

@SpringBootTest
class ApplicationTests {

    /**
     * 文件记录表服务类
     */
    @Resource
    IFileInfoService service;

    @Resource
    IFileConvertService fileConvertService;

    /**
     * 判断结果是否为空
     */
    @Test
    public void test1() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileSize(1233);
        GrcFileInfo grcFileInfo = BeanUtil.toBean(fileInfo, GrcFileInfo.class);
        service.save(grcFileInfo);
    }

    @Test
    public void test2() {
        Console.log(FileNameUtil.getNameWithoutFormat("附件6.问卷网操作说明.docx"));
    }

    @Test
    public void test3() {
        AsposeOfficeUtil.officeToPdf("D:\\林悦文件", "全员绩效管理工作成效考核评价常态化评估说明.docx");
    }

    @Test
    public void test4() {
        GrcFileInfo grcFileInfo = service.getOne(new LambdaQueryWrapper<GrcFileInfo>().eq(GrcFileInfo::getFileId, "ZUIC3STR6I7UYV1E8W06"));
        fileConvertService.officeToPdfAsync(grcFileInfo);
    }

}
