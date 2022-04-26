
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReadPDF {

    public static final String CONTENT_PDF = "F://国库集中支付凭证.pdf";

    public static void main(String[] args) {
        try {
            PDDocument document = PDDocument.load(new File(CONTENT_PDF));
            PDFTextStripper textStripper = new PDFTextStripper();
            // Get total page count of the PDF document
            int numberOfPages = document.getNumberOfPages();
            //set the first page to be extracted
            textStripper.setStartPage(1);
            // set the last page to be extracted
            textStripper.setEndPage(numberOfPages);
            String text = textStripper.getText(document);
            System.out.println(text);
            document.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
