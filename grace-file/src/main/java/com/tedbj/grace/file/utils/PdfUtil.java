package com.tedbj.grace.file.utils;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.tedbj.grace.basic.enums.ResultEnum;
import com.tedbj.grace.basic.exception.BusinessException;

/**
 * pdf工具类
 *
 * @author tyy
 * @version 1.0
 * @date
 **/
public class PdfUtil {

    /**
     * 透明度
     */
    private static final float FILL_OPACITY = 0.3f;

    /**
     * 透明度
     */
    private static final float STROKE_OPACITY = 0.4f;

    /**
     * 宽度
     */
    private static final int WIDTH = 1600;

    /**
     * 高度
     */
    private static final int HEIGHT = 2000;

    /**
     * 字体大小
     */
    private static final int FONT_SIZE = 12;

    /**
     * 倾斜角度
     */
    private static final int ROTATION = 30;

    /**
     * 基础宽度
     */
    private static final int BASE_WIDTH = 40;

    /**
     * pdf添加水印
     *
     * @param inputFile 输入文件
     * @param outputFile 输出文件
     * @param waterMarkName 水印名称
     *
     * @throws BusinessException 异常处理
     */
    public static void addPdfWaterMark(String inputFile, String outputFile, String waterMarkName) throws BusinessException {
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(inputFile);
            stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            // 使用系统字体
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            PdfGState gs = new PdfGState();
            // 设置透明度
            gs.setFillOpacity(FILL_OPACITY);
            gs.setStrokeOpacity(STROKE_OPACITY);
            JLabel label = new JLabel();
            label.setText(waterMarkName);
            FontMetrics metrics = label.getFontMetrics(label.getFont());
            int wmWidth = metrics.stringWidth(label.getText()) / 2 + BASE_WIDTH;

            // 打印的行数（以文字水印的宽为间隔）
            int rowsNumber = HEIGHT / wmWidth;
            // 每行打印的列数（以文字水印的宽为间隔）
            int columnsNumber = WIDTH / wmWidth;
            // 防止图片太小而文字水印太长，所以至少打印一次
            if (rowsNumber < 1) {
                rowsNumber = 1;
            }
            if (columnsNumber < 1) {
                columnsNumber = 1;
            }
            int left;
            int top;
            int total = reader.getNumberOfPages() + 1;
            for (int i = 1; i < total; i++) {
                PdfContentByte under = stamper.getOverContent(i);
                under.saveState();
                under.setGState(gs);
                under.beginText();
                // 设置字体
                under.setFontAndSize(baseFont, FONT_SIZE);
                // 设置水印颜色
                under.setColorFill(BaseColor.GRAY);
                // 添加水印
                for (int m = 0; m < rowsNumber; m++) {
                    for (int n = 0; n < columnsNumber; n++) {
                        left = n * wmWidth + m * wmWidth;
                        top = -n * wmWidth + m * wmWidth;
                        // 水印文字成30度角倾斜
                        under.showTextAligned(Element.ALIGN_LEFT, waterMarkName, left, top, ROTATION);
                        under.showTextAligned(Element.ALIGN_LEFT, waterMarkName, top, left, ROTATION);
                    }
                }
                under.endText();
            }
        } catch (DocumentException | IOException e) {
            throw new BusinessException(ResultEnum.FAILURE);
        } finally {
            try {
                if (stamper != null) {
                    stamper.close();
                }
            } catch (DocumentException | IOException e) {
                throw new BusinessException(ResultEnum.FAILURE);
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

}
