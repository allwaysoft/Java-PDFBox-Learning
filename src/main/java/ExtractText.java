
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ExtractText {

    public static void main(String[] args) {
        try {
            PDDocument doc = PDDocument.load(new File("F:/sample.pdf"));
            String text = new PDFTextStripper().getText(doc);;
            System.out.println("Text in PDF\n---------------------------------");
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
