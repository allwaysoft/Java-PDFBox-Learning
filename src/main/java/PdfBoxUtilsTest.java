
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class PdfBoxUtilsTest {

    private PDRectangle pageSize = PDRectangle.A4;

    private Integer marginX = 50;
    private Integer marginY = 50;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        PDDocument document = new PDDocument();
        PDType0Font font = PDType0Font.load(document, new FileInputStream(new File("C:\\Windows\\Fonts\\simfang.TTF")));
        PdfBoxUtilsTest pbut = new PdfBoxUtilsTest();
        pbut.drawFirstPage(document, font);
        // drawSecondPage(document, font);
        document.save(new FileOutputStream(new File("F:\\test2.pdf")));
        document.close();
    }

    private void drawFirstPage(PDDocument document, PDType0Font font) throws IOException {
        PDPage pdPage = new PDPage(pageSize);
        document.addPage(pdPage);
        PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);

        PdfBoxUtils.beginTextSteam(contentStream, 20f, marginX.floatValue(), pageSize.getHeight() - (2 * marginY));
        // 书写信息
        PdfBoxUtils.drawParagraph(contentStream, "物流单摘要", font, 18);
        PdfBoxUtils.createEmptyParagraph(contentStream, 2);

        contentStream.setFont(font, 13);
        PdfBoxUtils.drawParagraph(contentStream, "物流单号：2022099");
        PdfBoxUtils.drawParagraph(contentStream, "结算时间段：从20200909到20200807");
        PdfBoxUtils.drawParagraph(contentStream, "商品总数量(件)：100000");
        PdfBoxUtils.drawParagraph(contentStream, "商品总价格(元)：100000000000");
        PdfBoxUtils.drawParagraph(contentStream, "买卖人名称：李白");
        PdfBoxUtils.createEmptyParagraph(contentStream, 4);

        PdfBoxUtils.drawParagraph(contentStream, "公司(盖章)：");
        PdfBoxUtils.createEmptyParagraph(contentStream, 2);
        contentStream.showText("日期：");

        PdfBoxUtils.createEmptyParagraph(contentStream, 16);
        contentStream.newLineAtOffset(195, 0);
        PdfBoxUtils.drawParagraph(contentStream, "小熊猫超级防伪码", font, 12);

        PdfBoxUtils.endTextSteam(contentStream);

        // 划线
        PdfBoxUtils.drawLine(contentStream, marginX, 545, PDRectangle.A4.getWidth() - marginX, 545);
        PdfBoxUtils.drawLine(contentStream, marginX, 410, PDRectangle.A4.getWidth() - marginX, 410);

        // 贴图
        PdfBoxUtils.drawImage(document, contentStream, new File("F:\\test.png"),
                (pageSize.getWidth() / 2) - 80, 150, 160, 160);

        contentStream.close();
    }
}
