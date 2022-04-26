
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class AddingPDF {

    public static final String DEST = "F:\\HelloWorld.pdf";

    public static void main(String[] args) {
        try {
            // load exiting PDF
            PDDocument pdDoc = PDDocument.load(new File(DEST));
            PDPage page = new PDPage();
            // add page to the document
            pdDoc.addPage(page);
            // write to a page content stream
            try ( PDPageContentStream cs = new PDPageContentStream(pdDoc, page)) {
                cs.beginText();
                // setting font family and font size
                cs.setFont(PDType1Font.HELVETICA, 14);
                // Text color in PDF
                cs.setNonStrokingColor(Color.BLUE);
                // set offset from where content starts in PDF
                cs.newLineAtOffset(20, 750);
                cs.showText("Adding content to an existing PDF");
                cs.newLine();
                cs.endText();
            }
            // save and close PDF document
            pdDoc.save(new File("F:\\Changed.pdf"));
            pdDoc.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
