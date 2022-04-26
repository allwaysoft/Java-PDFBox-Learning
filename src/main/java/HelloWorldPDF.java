
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class HelloWorldPDF {

    public static final String CREATED_PDF = "F://HelloWorld.pdf";

    public static void main(String[] args) {
        try {
            PDDocument pdfDoc = new PDDocument();
            PDPage firstPage = new PDPage();
            // add page to the PDF document
            pdfDoc.addPage(firstPage);
            // For writing to a page content stream
            try ( PDPageContentStream cs = new PDPageContentStream(pdfDoc, firstPage)) {
                cs.beginText();
                // setting font family and font size
                //cs.setFont(PDType1Font.COURIER, 15);
                cs.setFont(PDType0Font.load(pdfDoc, new File("C:\\Windows\\Fonts\\simfang.TTF")), 10);
                // color for the text
                cs.setNonStrokingColor(Color.RED);
                // starting position
                cs.newLineAtOffset(20, 750);
                cs.showText("您好！Hello World PDF created using PDFBox");
                cs.newLineAtOffset(20, 650);
                cs.showText("您好！Hello World PDF created using PDFBox");
                // go to next line
                cs.newLine();
                cs.endText();
            }
            // save PDF document
            pdfDoc.save(CREATED_PDF);
            pdfDoc.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
