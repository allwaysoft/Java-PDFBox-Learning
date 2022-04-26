
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;

public class TrueType {

    public static void main(String[] args) throws IOException {

        File file = new File("F:/report-edit.pdf");
        PDDocument pd = PDDocument.load(file);
        // 需要的字体文件
        Map<COSName, PDFont> oldfont = new HashMap<COSName, PDFont>();
        COSName fontName = null;
        PDType0Font targetfont = PDType0Font.load(pd, new File("C:\\Windows\\Fonts\\simfang.ttf"));
        for (PDPage page : pd.getPages()) {
            PDFStreamParser pdfsp = new PDFStreamParser(page);
            pdfsp.parse();
            List<Object> tokens = pdfsp.getTokens();
            for (int j = 0; j < tokens.size(); j++) {
                //创建一个object对象去接收标记
                Object next = tokens.get(j);
                //instanceof判断其左边对象是否为其右边类的实例
                if (next instanceof COSName) {
                    fontName = (COSName) next;
                    if (!oldfont.containsKey(fontName)) {
                        oldfont.put(fontName, page.getResources().getFont(fontName));
                    }
                } else if (next instanceof COSString) {
                    COSString previous = (COSString) next;
                    try ( InputStream in = new ByteArrayInputStream(previous.getBytes())) {
                        StringBuffer sb = new StringBuffer();
                        while (in.available() > 0) {
                            int rc = oldfont.get(fontName).readCode(in);
                            sb.append(oldfont.get(fontName).toUnicode(rc));
                        }
                        //重置COSString对象
                        sb.append("例");
                        System.out.println("--Tj----" + sb.toString());
                        previous.setValue(targetfont.encode(sb.toString()));
                    }
                } else if (next instanceof COSArray) {
                    //PDF中的字符串
                    byte[] pstring = {};
                    int prej = 0;
                    COSArray previous = (COSArray) next;
                    //循环previous
                    for (int k = 0; k < previous.size(); k++) {
                        Object arrElement = previous.getObject(k);
                        if (arrElement instanceof COSString) {
                            //COSString对象>>创建java字符串的一个新的文本字符串。
                            COSString cosString = (COSString) arrElement;
                            //将此字符串的内容作为PDF文本字符串返回。
                            if (j == prej) {
                                byte[] thisbyte = cosString.getBytes();
                                byte[] temp = new byte[pstring.length + thisbyte.length];
                                System.arraycopy(pstring, 0, temp, 0, pstring.length);
                                System.arraycopy(thisbyte, 0, temp, pstring.length, thisbyte.length);
                                pstring = temp;
                            } else {
                                prej = j;
                                pstring = cosString.getBytes();
                            }
                        }
                    }
                    try ( InputStream in = new ByteArrayInputStream(pstring)) {
                        StringBuffer sb = new StringBuffer();
                        while (in.available() > 0) {
                            int rc = oldfont.get(fontName).readCode(in);
                            sb.append(oldfont.get(fontName).toUnicode(rc));
                        }
                        sb.append("例");
                        System.out.println("TJ----" + sb.toString());
                        COSString cosString2 = (COSString) previous.getObject(0);
                        cosString2.setValue(targetfont.encode(sb.toString()));
                    }
                    int total = previous.size() - 1;
                    for (int k = total; k > 0; k--) {
                        previous.remove(k);
                    }
                }
            }
            PDStream updatedStream = new PDStream(pd);
            OutputStream out = updatedStream.createOutputStream();
            ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
            tokenWriter.writeTokens(tokens);
            out.close();
            oldfont.forEach((k, v) -> {
                page.getResources().put(k, targetfont);
            });
            page.setContents(updatedStream);
        }
        pd.save("d:/1.pdf");
        pd.close();
    }

}
