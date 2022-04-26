
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

public class SplitPDFExample {

    public static void main(String[] args) throws IOException {
        File file = new File("F:/report.pdf");

        // load pdf file
        PDDocument document = PDDocument.load(file);

        // instantiating Splitter
        Splitter splitter = new Splitter();

        // split the pages of a PDF document
        List<PDDocument> Pages = splitter.split(document);

        // Creating an iterator
        Iterator<PDDocument> iterator = Pages.listIterator();

        // saving splits as pdf
        int i = 0;
        while (iterator.hasNext()) {
            PDDocument pd = iterator.next();
            // provide destination path to the PDF split
            pd.save("sample_part_" + ++i + ".pdf");
            System.out.println("Saved sample_part_" + i + ".pdf");
        }
        System.out.println("Provided PDF has been split into multiple.");
        document.close();
    }

}
