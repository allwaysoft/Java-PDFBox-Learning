
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFGenerator {

    public static final String DEST = "F:\\MultiLine.pdf";

    public static void main(String[] args) {
        try {
            PDDocument pdDoc = new PDDocument();
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
                // required when using newLine()
                cs.setLeading(12);
                cs.showText("First line added to the PDF");
                cs.newLine();
                String text = "This is a long text which spans multiple lines, this text checks if the line is changed as per the allotted width in the PDF or not."
                        + "The Apache PDFBox library is an open source tool written in Java for working with PDF documents. ";
                divideTextIntoMultipleLines(text, 580, page, cs, PDType1Font.HELVETICA, 14);
                cs.endText();
            }
            // save and close PDF document
            pdDoc.save(DEST);
            pdDoc.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void divideTextIntoMultipleLines(String text, int allowedWidth, PDPage page, PDPageContentStream contentStream, PDFont font, int fontSize) throws IOException {
        List<String> lines = new ArrayList<String>();
        String line = "";
        // split the text one or more spaces
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (!line.isEmpty()) {
                line += " ";
            }
            // check for width boundaries
            int size = (int) (fontSize * font.getStringWidth(line + word) / 1000);
            if (size > allowedWidth) {
                // if line + new word > page width, add the line to the list without the word
                lines.add(line);
                // start new line with the current word
                line = word;
            } else {
                // if line + word < page width, append the word to the line
                line += word;
            }
        }
        lines.add(line);
        // write lines to Content stream
        for (String ln : lines) {
            contentStream.showText(ln);
            contentStream.newLine();
        }
    }
}
