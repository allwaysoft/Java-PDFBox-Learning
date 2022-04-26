
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class CreatePDF {

    public static final String CREATED_PDF = "F://Content.pdf";

    public static void main(String[] args) {
        try {
            PDDocument pdfDoc = new PDDocument();
            PDPage firstPage = new PDPage();
            // add page to the PDF document
            pdfDoc.addPage(firstPage);
            // For writing to a page content stream
            try ( PDPageContentStream cs = new PDPageContentStream(pdfDoc, firstPage)) {
                cs.beginText();
                cs.setFont(PDType1Font.COURIER, 15);
                cs.newLineAtOffset(20, 750);
                cs.setLeading(12);
                cs.showText("Hello World PDF created using PDFBox");
                cs.newLine();
                String text = "This text spans multiple lines and it is added to the PDF dcoument generated using PDFBox";
                showMultiLineText(text, 20, 762, 580, firstPage, cs, PDType1Font.COURIER, 15);
                cs.setFont(PDType1Font.TIMES_BOLD, 15);
                cs.setNonStrokingColor(Color.RED);
                cs.showText("While adding this line font and color settings are changed.");
                cs.newLine();
                cs.endText();
            }
            pdfDoc.save(CREATED_PDF);
            pdfDoc.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void showMultiLineText(String text, int x, int y, int allowedWidth, PDPage page, PDPageContentStream contentStream, PDFont font, int fontSize) throws IOException {
        List<String> lines = new ArrayList<String>();
        String line = "";
        // split the text on spaces
        String[] words = text.split(" ");
        for (String word : words) {
            if (!line.isEmpty()) {
                line += " ";
            }
            // check if adding the word to the line surpasses the width of the page
            int size = (int) (fontSize * font.getStringWidth(line + word) / 1000);
            if (size > allowedWidth) {
                // if line + word surpasses the width of the page, add the line without the current word
                lines.add(line);
                // start new line with the current word
                line = word;
            } else {
                // if line + word fits the page width, add the current word to the line
                line += word;
            }
        }
        lines.add(line);
        for (String ln : lines) {
            System.out.println("Line- " + ln);
            contentStream.showText(ln);
            contentStream.newLine();
        }
    }
}
