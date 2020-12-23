package com.tedbj.grace.file.utils;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.*;

import org.springframework.core.io.ClassPathResource;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.HeaderFooter;
import com.aspose.words.HeaderFooterType;
import com.aspose.words.License;
import com.aspose.words.Paragraph;
import com.aspose.words.SaveFormat;
import com.aspose.words.Section;
import com.aspose.words.Shape;
import com.aspose.words.ShapeType;
import com.aspose.words.WrapType;
import com.tedbj.grace.basic.enums.ResultEnum;
import com.tedbj.grace.basic.exception.BusinessException;
import com.tedbj.grace.file.enums.OfficeTypeEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * AsposeWords工具类
 */
@Slf4j
public class AsposeOfficeUtil {

    /**
     * 默认水印文本
     */
    private static final String WATER_TEXT = "国家电网总部";

    /**
     * pdf格式
     */
    private static final String FORMAT_PDF = ".pdf";

    /**
     * 反斜杠+点
     */
    private static final String BACKSLASH_POINT = "\\.";

    /**
     * 插入多个水印
     *
     * @param mdoc 文档
     * @param wmText 文本
     * @param left 左边距多少
     * @param top 上边距多少
     *
     * @return Shape
     *
     * @throws Exception 异常处理
     */
    public static Shape shapeMore(Document mdoc, String wmText, double left,
                                  double top) throws Exception {
        Shape waterShape = new Shape(mdoc, ShapeType.TEXT_PLAIN_TEXT);
        // 设置字体、大小
        waterShape.getTextPath().setText(wmText);
        waterShape.getTextPath().setFontFamily("宋体");
        waterShape.setWidth(250);
        waterShape.setHeight(10);
        // 设置倾斜度 文本将从左下角到右上角
        waterShape.setRotation(-20);
        // 绘制水印颜色 // 浅灰色水印
        waterShape.getFill().setColor(Color.LIGHT_GRAY);
        waterShape.setStrokeColor(Color.LIGHT_GRAY);
        // 将水印放置在页面中心
        waterShape.setLeft(left);
        waterShape.setTop(top);
        waterShape.setWrapType(WrapType.NONE);

        return waterShape;
    }

    /**
     * 插入水印
     *
     * @param doc 文档
     * @param wmText 水印文本
     *
     * @throws Exception 异常处理
     */
    public static void insertWatermarkText(Document doc, String wmText)
            throws Exception {
        Paragraph watermarkPara = new Paragraph(doc);
        int wmWidth = getWatermarkWidth(wmText);
        int width = 1600;
        int height = 2000;
        // 打印的行数（以文字水印的宽为间隔）
        int rowsNumber = height / wmWidth;
        // 每行打印的列数（以文字水印的宽为间隔）
        int columnsNumber = width / wmWidth;
        // 防止图片太小而文字水印太长，所以至少打印一次
        if (rowsNumber < 1) {
            rowsNumber = 1;
        }
        if (columnsNumber < 1) {
            columnsNumber = 1;
        }
        int left;
        int top;
        Shape waterShape;
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                left = j * wmWidth + i * wmWidth;
                top = -j * wmWidth + i * wmWidth;
                waterShape = shapeMore(doc, wmText, left, top);
                watermarkPara.appendChild(waterShape);
                waterShape = shapeMore(doc, wmText, top, left);
                watermarkPara.appendChild(waterShape);
            }
        }
        // 在每个部分中，最多可以有三个不同的标题，因为我们想要出现在所有页面上的水印，插入到所有标题中。
        Section section;
        for (int i = 0; i < doc.getSections().getCount(); i++) {
            section = doc.getSections().get(i);
            // 每个区段可能有多达三个不同的标题，因为我们希望所有页面上都有水印，将所有的头插入。
            insertWatermarkIntoHeader(watermarkPara, section,
                    HeaderFooterType.HEADER_PRIMARY);
        }
    }

    public static int getWatermarkWidth(String watermarkText) {
        String fontType = "宋体";
        Integer fontStyle = Font.PLAIN;
        Integer fontSize = 5;
        Font font = new Font(fontType, fontStyle, fontSize);

        JLabel label = new JLabel(watermarkText);
        FontMetrics metrics = label.getFontMetrics(font);
        // 文字水印的宽
        int waterWidth = metrics.stringWidth(label.getText()) / 2 + 80;
        return waterWidth;
    }

    /**
     * 头部插入水印
     *
     * @param watermarkPara watermarkPara
     * @param section section
     * @param headerType headerType
     */
    public static void insertWatermarkIntoHeader(Paragraph watermarkPara,
                                                 Section section, int headerType) {
        HeaderFooter header = section.getHeadersFooters().get(headerType);
        if (header == null) {
            // 若当前节中没有指定类型的头新建一个
            header = new HeaderFooter(section.getDocument(), headerType);
            section.getHeadersFooters().add(header);
        }
        try {
            // 在头部插入一个水印的克隆
            header.appendChild(watermarkPara.deepClone(true));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 获取aspose-words的license
     *
     * @return 是否成功的标志
     *
     * @throws BusinessException 异常处理
     */
    public static boolean getWordsLicense() throws BusinessException {
        return getLicense("license-words.xml");
    }

    /**
     * 获取aspose-cells的license
     *
     * @return 是否成功的标志
     *
     * @throws BusinessException 异常处理
     */
    public static boolean getCellsLicense() throws BusinessException {
        return getLicense("license-cells.xml");
    }

    /**
     * 获取aspose-slides的License
     *
     * @return 是否成功
     *
     * @throws BusinessException 异常处理
     */
    public static boolean getSlidesLicense() throws BusinessException {
        return getLicense("license-slides.xml");
    }

    /**
     * 获取授权文件
     *
     * @param fileName 文件名
     *
     * @return 结果
     *
     * @throws BusinessException 异常处理
     */
    private static boolean getLicense(String fileName) throws BusinessException {
        boolean result = false;
        InputStream is = null;
        try {
            is = new ClassPathResource(fileName).getInputStream();
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(ResultEnum.FAILURE);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new BusinessException(ResultEnum.FAILURE);
            }
        }
        return result;
    }

    /**
     * word添加水印，支持DOC、DOCX
     *
     * @param path 文件路径
     * @param tempFileName 临时文件名
     *
     * @throws BusinessException 异常处理
     */
    public static void setWordWaterMark(String path, String tempFileName) throws BusinessException {
        // 验证License
        if (!getWordsLicense()) {
            return;
        }
        Document doc;
        String wmText;
        try {
            doc = new Document(path + File.separator + tempFileName);
            wmText = WATER_TEXT;
            insertWatermarkText(doc, wmText);
            doc.save(path + File.separator + tempFileName);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * office转pdf
     *
     * @param path office文件所在路径
     * @param officeFileName office文件名
     * @param officeTypeEnum office类型
     *
     * @throws BusinessException 异常
     */
    public static void officeToPdf(String path, String officeFileName, OfficeTypeEnum officeTypeEnum) throws BusinessException {
        if (officeTypeEnum == OfficeTypeEnum.WORD) {
            wordToPdf(path, officeFileName);
        } else if (officeTypeEnum == OfficeTypeEnum.EXCEL) {
            excelToPdf(path, officeFileName);
        } else if (officeTypeEnum == OfficeTypeEnum.PPT) {
            pptToPdf(path, officeFileName);
        }
    }

    /**
     * office转pdf
     *
     * @param path 路径
     * @param officeFileName 文件名
     */
    public static void officeToPdf(String path, String officeFileName) {
        if (FileNameUtil.isWord(officeFileName)) {
            wordToPdf(path, officeFileName);
        } else if (FileNameUtil.isExcel(officeFileName)) {
            excelToPdf(path, officeFileName);
        } else if (FileNameUtil.isPPT(officeFileName)) {
            pptToPdf(path, officeFileName);
        }
    }

    /**
     * word转pdf，支持DOC、DOCX
     *
     * @param path 路径
     * @param docFileName 文件名
     *
     * @throws BusinessException 异常处理
     */
    public static void wordToPdf(String path, String docFileName) throws BusinessException {
        // 验证License
        if (!getWordsLicense()) {
            return;
        }
        Document doc;
        FileOutputStream os = null;
        try {
            doc = new Document(path + File.separator + docFileName);
            // 创建空的Pdf文档
            String fileName = FileNameUtil.getNameWithoutFormat(docFileName);
            File file = new File(path + File.separator + fileName + FORMAT_PDF);
            os = new FileOutputStream(file);
            // 保存为PDF格式文档
            doc.save(os, com.aspose.words.SaveFormat.PDF);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(ResultEnum.FAILURE);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new BusinessException(ResultEnum.FAILURE);
            }
        }
    }

    /**
     * excel转pdf
     *
     * @param path 文件路径
     * @param xlsFileName 文件名
     *
     * @throws BusinessException 异常处理
     */
    public static void excelToPdf(String path, String xlsFileName) throws BusinessException {
        // 验证License
        if (!getCellsLicense()) {
            return;
        }
        Workbook wb;
        FileOutputStream os = null;
        try {
            wb = new Workbook(path + File.separator + xlsFileName);
            // 创建空的Pdf文档
            String fileName = FileNameUtil.getNameWithoutFormat(xlsFileName);
            File file = new File(path + File.separator + fileName + FORMAT_PDF);
            os = new FileOutputStream(file);
            // 保存为PDF格式文档
            wb.save(os, com.aspose.cells.SaveFormat.PDF);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(ResultEnum.FAILURE);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new BusinessException(ResultEnum.FAILURE);
            }
        }
    }

    /**
     * ppt转pdf
     *
     * @param path 路径
     * @param pptFileName 文件名
     *
     * @throws BusinessException 异常处理
     */
    public static void pptToPdf(String path, String pptFileName) throws BusinessException {
        // 验证License
        if (!getSlidesLicense()) {
            return;
        }
        Presentation pres;
        FileOutputStream os = null;
        try {
            pres = new Presentation(path + File.separator + pptFileName);
            // 创建空的Pdf文档
            String fileName = FileNameUtil.getNameWithoutFormat(pptFileName);
            File file = new File(path + File.separator + fileName + FORMAT_PDF);
            os = new FileOutputStream(file);
            // 保存为PDF格式文档
            pres.save(os, com.aspose.slides.SaveFormat.Pdf);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(ResultEnum.FAILURE);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new BusinessException(ResultEnum.FAILURE);
            }
        }
    }

    /**
     * word添加水印并转为pdf 总结：添加水印并转pdf耗时太长，不建议一起使用
     *
     * @param path 路径
     * @param docFileName 文件名
     *
     * @throws BusinessException 异常处理
     */
    public static void setPdfWaterMark(String path, String docFileName) throws BusinessException {
        // 验证License
        if (!getWordsLicense()) {
            return;
        }
        Document doc;
        String wmText;
        FileOutputStream os = null;

        try {
            doc = new Document(path + File.separator + docFileName);
            wmText = WATER_TEXT;
            insertWatermarkText(doc, wmText);
            String fileName = FileNameUtil.getNameWithoutFormat(docFileName);
            File outPutfile = new File(path + File.separator + fileName + FORMAT_PDF);
            os = new FileOutputStream(outPutfile);
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

}
