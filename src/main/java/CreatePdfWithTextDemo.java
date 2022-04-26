
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Creates a sample.pdf document and write a message at an offset with
 * HELVETICA_BOLD font style.
 */
public class CreatePdfWithTextDemo {

    public static void main(String[] args) throws IOException {
        String filename = "F:/sample.pdf";
        String message = "This is a sample PDF document created using PDFBox.";

        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 10);
            contents.newLineAtOffset(50, 700);
            contents.showText(message);
            contents.setFont(PDType0Font.load(doc, new File("C:\\Windows\\Fonts\\simfang.TTF")), 10);
            // color for the text
            contents.setNonStrokingColor(Color.RED);
            // starting position
            contents.newLineAtOffset(100, 300);
            contents.showText("您好！Hello World PDF created using PDFBox");
            contents.endText();
            contents.close();

            doc.save(filename);
        } finally {
            doc.close();
        }
    }
}
