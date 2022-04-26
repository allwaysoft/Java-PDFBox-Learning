
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class PDFBoxExample {

    public static void main(String[] args) throws IOException {
        try ( PDDocument doc = new PDDocument()) {
            PDPage pdPage = new PDPage();
            doc.addPage(pdPage);

            float pageHeight = pdPage.getMediaBox().getHeight();

            try ( PDPageContentStream stream = new PDPageContentStream(doc, pdPage)) {
                PDFont font = PDType1Font.HELVETICA;

                float padding = 5f;
                float x = 100f;
                float y = pageHeight - 100f;

                stream.setNonStrokingColor(200, 200, 200);
                stream.addRect(x, y, 100f, 30f);
                stream.fill();

                stream.beginText();
                stream.setNonStrokingColor(0, 0, 0);
                //stream.setFont(font, 24);
                stream.setFont(PDType0Font.load(doc, new File("C:\\Windows\\Fonts\\simfang.TTF")), 24);
                stream.newLineAtOffset(x + padding, y + padding);
                stream.showText("世界你好");
                stream.endText();

            }

            doc.save(new File("F:/Example.pdf"));
        }

    }

}
