
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author @title PdfAddSealUtil
 * @Description:
 * @date 2020/8/5
 */
public class PdfAddSealUtil {

    private static Logger logger = Logger.getLogger(PdfAddSealUtil.class);

    public static void main(String args[]) {

        new PdfAddSealUtil().addSeal("F:/sample.pdf", "F:/seal.pdf", "中国", "文书", "2020", "办公室", "30年", "numb");

    }

    public boolean addSeal(String inPath, String outPath, String qz, String lb, String year, String dep, String deadline, String numb) {
        try {
            File file = new File(inPath);
            if (!file.exists()) {
                logger.debug("找不到文件：" + inPath);
            }
            // 文件夹不存在新建文件夹
            String outDir = outPath.substring(0, outPath.lastIndexOf("/"));
            File outDirFile = new File(outDir);
            if (!outDirFile.exists()) {
                outDirFile.mkdirs();
                logger.debug("归档章，新建文件夹成功：" + outDir);
            }

            PDDocument doc = PDDocument.load(file);
            // 导入楷体
            //PDFont font = PDType0Font.load(doc, this.getClass().getResourceAsStream("C:\\Windows\\Fonts\\simfang.TTF"));
            PDFont font = PDType0Font.load(doc, new File("C:\\Windows\\Fonts\\simfang.ttf"));
            // PDFont font = PDType0Font.load(doc, this.getClass().getResourceAsStream("C:\\Windows\\Fonts\\simfang.TTF"));

            PDPage page = doc.getPage(0);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.PREPEND, false, false);
            float width = page.getMediaBox().getWidth();
            float height = page.getMediaBox().getLowerLeftY() + page.getMediaBox().getHeight();

            // 表格起始x,y坐标;印章距离右上角各5mm
            float x = width - countMM(95);
            float y = height - countMM(12);
            // 所有表格高度mm转point
            float allTableheight1 = countMM(8);
            float allTableheight2 = countMM(8);
            // 上层左起第一个表格
            float topOneTableWidth = countMM(75);// 宽度

            // 上层左起第二个表格
            float topTwoTableWidth = countMM(15);
            float topTwoTableX = x + topOneTableWidth;

            float bottomTableY = y - allTableheight2;
            // 下层左起第一个表格
            float bottomOneTableWidth = countMM(11);

            // 下层左起第二个表格
            float bottomTwoTableWidth = countMM(53);
            float bottomTwoTableX = x + bottomOneTableWidth;

            float bottomThreeTableWidth = countMM(11);
            float bottomThreeTableX = bottomTwoTableX + bottomTwoTableWidth;

            float bottomFourTableWidth = countMM(15);
            float bottomFourTableX = bottomThreeTableX + bottomThreeTableWidth;

            // 画表格
            drawTable(contentStream, x, y, topOneTableWidth, allTableheight1);
            drawTable(contentStream, topTwoTableX, y, topTwoTableWidth, allTableheight1);
            drawTable(contentStream, x, bottomTableY, bottomOneTableWidth, allTableheight2);
            drawTable(contentStream, bottomTwoTableX, bottomTableY, bottomTwoTableWidth, allTableheight2);
            drawTable(contentStream, bottomThreeTableX, bottomTableY, bottomThreeTableWidth, allTableheight2);
            drawTable(contentStream, bottomFourTableX, bottomTableY, bottomFourTableWidth, allTableheight2);

            // 文字上移2毫米,居中
            float move2mm = countMM(3);
            int fontSize = 12;
            writeText(contentStream, font, fontSize, 0, 0, "测试");
            writeText(contentStream, font, fontSize, x + countMM(1), y + move2mm, qz);
            writeText(contentStream, font, fontSize, topTwoTableX + countMM(1), y + move2mm, lb);
            writeText(contentStream, font, fontSize, x + countMM(1), bottomTableY + countMM(3), year);
            if (dep.length() > 12) {
                int length1 = dep.length();
                if (length1 > 18) {
                    length1 = 18;
                    String str1 = dep.substring(0, length1);
                    writeText(contentStream, font, 8, bottomTwoTableX + countMM(1), bottomTableY + countMM(4), str1);
                    String str2 = dep.substring(18);
                    writeText(contentStream, font, 8, bottomTwoTableX + countMM(1), bottomTableY + countMM(1), str2);
                } else {
                    String str1 = dep.substring(0, length1);
                    writeText(contentStream, font, 8, bottomTwoTableX + countMM(1), bottomTableY + countMM(4), str1);
                }

            } else {
                writeText(contentStream, font, fontSize, bottomTwoTableX + countMM(1), bottomTableY + countMM(3), dep);
            }
            writeText(contentStream, font, fontSize, bottomThreeTableX + countMM(1), bottomTableY + countMM(3), deadline);
            writeText(contentStream, font, fontSize, bottomFourTableX + countMM(1), bottomTableY + countMM(3), numb);
            contentStream.close();
            doc.save(outPath);
            doc.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void drawTable(PDPageContentStream contentStream, float x, float y, float tableWidth, float tableHeight) {
        try {
            contentStream.setStrokingColor(Color.red);
            drawLine(contentStream, x, y, x, y + tableHeight);
            drawLine(contentStream, x, y + tableHeight, x + tableWidth, y + tableHeight);
            drawLine(contentStream, x + tableWidth, y + tableHeight, x + tableWidth, y);
            drawLine(contentStream, x + tableWidth, y, x, y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawLine(PDPageContentStream contentStream, float startX, float startY, float endX, float endY) {
        try {
            contentStream.moveTo(startX, startY);
            contentStream.lineTo(endX, endY);
            contentStream.stroke();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float countMM(float point) {
        return (float) (point * 72 / 25.4);
    }

    private void writeText(PDPageContentStream contentStream, PDFont font, float fontSize, float startX, float startY, String txt) throws IOException {
        if (StringUtils.isEmpty(txt)) {
            return;
        }
        contentStream.beginText();
        // 文字位置
        contentStream.newLineAtOffset(startX, startY);
        // 设置字体type,size
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(0, 0, 0);
        // 插入文本
        contentStream.showText(txt);
        contentStream.endText();
    }
}
