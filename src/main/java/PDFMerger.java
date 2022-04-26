
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class PDFMerger {

    public static final String MERGED_PDF = "F://Merged.pdf";

    public static void main(String[] args) {
        // Source PDFs as a list
        List<String> fileList = Arrays.asList("F://HelloWorldTTF.pdf", "F://Content.pdf");
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        pdfMerger.setDestinationFileName(MERGED_PDF);
        try {
            // iterate list and add files to PDFMergerUtility
            for (String filePath : fileList) {
                pdfMerger.addSource(filePath);
            }
            // Merge documents
            pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
