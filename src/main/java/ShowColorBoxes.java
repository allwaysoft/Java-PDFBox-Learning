
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class ShowColorBoxes {

    private static void drawCircle(PDPageContentStream contentStream, int cx, int cy, int r, int red, int green, int blue) throws IOException {
        final float k = 0.552284749831f;
        contentStream.setNonStrokingColor(red, green, blue);
        contentStream.moveTo(cx - r, cy);
        contentStream.curveTo(cx - r, cy + k * r, cx - k * r, cy + r, cx, cy + r);
        contentStream.curveTo(cx + k * r, cy + r, cx + r, cy + k * r, cx + r, cy);
        contentStream.curveTo(cx + r, cy - k * r, cx + k * r, cy - r, cx, cy - r);
        contentStream.curveTo(cx - k * r, cy - r, cx - r, cy - k * r, cx - r, cy);
        contentStream.fill();
    }

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

    public static void main(String args[]) throws Exception {

        //Loading an existing document
        File file = new File("F:/sample.pdf");
        PDDocument document = PDDocument.load(file);

        //Retrieving a page of the PDF Document
        PDPage page = document.getPage(0);

        //Instantiating the PDPageContentStream class
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        //Setting the non stroking color
        contentStream.setNonStrokingColor(Color.DARK_GRAY);

        //Drawing a rectangle
        contentStream.addRect(200, 650, 100, 100);

        //Drawing a rectangle
        contentStream.fill();

        System.out.println("rectangle added");
        drawCircle(contentStream, 250, 700, 50, 255, 10, 10);

        drawTriangle(contentStream, 100, 500, 100, 100);
        //contentStream.drawLine(start.getX(), start.getY(), end.getX(),end.getY());
        contentStream.drawLine(100, 100, 200, 100);
        contentStream.drawLine(100, 200, 200, 200);
        contentStream.drawLine(100, 100, 100, 200);
        contentStream.drawLine(200, 100, 200, 200);
        //Closing the ContentStream object
        contentStream.close();

        //Saving the document
        File file1 = new File("F:/colorbox.pdf");
        document.save(file1);

        //Closing the document
        document.close();
    }
}
