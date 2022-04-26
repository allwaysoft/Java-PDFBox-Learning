
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.IOException;

public class MergePDFsExample {

    public static void main(String[] args) throws IOException {
        // load pdf files to be merged
        File file1 = new File("sample_part_1.pdf");
        File file2 = new File("sample_part_2.pdf");
        File file3 = new File("sample_part_3.pdf");

        // instantiatE PDFMergerUtility class
        PDFMergerUtility pdfMerger = new PDFMergerUtility();

        // set destination file path
        pdfMerger.setDestinationFileName("sample_pdf.pdf");

        // add all source files, to be merged, to pdfMerger
        pdfMerger.addSource(file1);
        pdfMerger.addSource(file2);
        pdfMerger.addSource(file3);

        // merge documents
        pdfMerger.mergeDocuments(null);

        System.out.println("PDF Documents merged to a single file");
    }
}
