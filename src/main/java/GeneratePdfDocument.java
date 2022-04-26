
import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class GeneratePdfDocument {

    public static void drawTriangle(PDPageContentStream pDPageContentStream, float x, float y, float width, float height) throws IOException {
        pDPageContentStream.appendRawCommands("q\n");
//        pDPageContentStream.setLineCapStyle(0);
        pDPageContentStream.setLineCapStyle(1);
        pDPageContentStream.setStrokingColor(Color.BLACK);
        pDPageContentStream.moveTo(x, y);
        pDPageContentStream.addLine(x, y, x + width, y);
        pDPageContentStream.addLine(x + width, y, x + width, y + width);
        pDPageContentStream.addLine(x + width, y + width, x, y);
        pDPageContentStream.appendRawCommands("B*\n");
        pDPageContentStream.appendRawCommands("Q\n");
    }

    public static void main(String[] agrgs) throws IOException {
        String shapePdfFilePath = "F:/triangle.pdf";

        PDDocument pDDocument = new PDDocument();
        PDPage page1 = new PDPage();
        pDDocument.addPage(page1);
        PDPageContentStream pDPageContentStream = new PDPageContentStream(pDDocument, page1);
        drawTriangle(pDPageContentStream, 100, 500, 100, 100);
        pDPageContentStream.close();
        pDDocument.save(shapePdfFilePath);
        pDDocument.close();

    }
}
