
import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFWithImage {

    public static final String DEST = "F:\\image.pdf";

    public static void main(String[] args) {
        try {
            PDDocument pdDoc = new PDDocument();
            PDPage page = new PDPage();
            // add page to the document
            pdDoc.addPage(page);
            // Create image object using the image location
            PDImageXObject image = PDImageXObject.createFromFile("F:\\test.png", pdDoc);
            // write to a page content stream
            try ( PDPageContentStream cs = new PDPageContentStream(pdDoc, page)) {
                cs.beginText();
                // setting font family and font size
                cs.setFont(PDType1Font.HELVETICA, 14);
                // Text color in PDF
                cs.setNonStrokingColor(Color.BLUE);
                // set offset from where content starts in PDF
                cs.newLineAtOffset(20, 750);
                cs.showText("Adding image to PDF Example");
                cs.newLine();
                cs.endText();
                cs.drawImage(image, 20, 450, 350, 200);
            }
            // save and close PDF document
            pdDoc.save(DEST);
            pdDoc.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
