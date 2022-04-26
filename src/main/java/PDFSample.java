
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFSample {

    // Page configuration
    private static final PDRectangle PAGE_SIZE = PDRectangle.A4;
    private static final float MARGIN = 90;
    private static final boolean IS_LANDSCAPE = true;

    // Font configuration
    private static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    private static final float FONT_SIZE = 10;

    // Table configuration
    private static final float ROW_HEIGHT = 15;
    private static final float CELL_MARGIN = 2;

    public static void main(String[] args) throws IOException {
        new PDFTableGenerator().generatePDF(createContent());
    }

    private static Table createContent() {
        String title = "支付凭证号：" + "00000000000000";
        // Total size of columns must not be greater than table width.
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("李四", 80));
        columns.add(new Column("LastName", 90));
        columns.add(new Column("Email", 80));
        columns.add(new Column("ZipCode", 83));
        columns.add(new Column("MailOptIn", 80));
        columns.add(new Column("Code", 80));
        columns.add(new Column("Branch", 89));

        String[][] content = {
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},
            {"张三", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334"},};

        float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);

        Table table = new TableBuilder()
                .setTitle(title)
                .setCellMargin(CELL_MARGIN)
                .setColumns(columns)
                .setContent(content)
                .setHeight(tableHeight)
                .setNumberOfRows(content.length)
                .setRowHeight(ROW_HEIGHT)
                .setMargin(MARGIN)
                .setPageSize(PAGE_SIZE)
                .setLandscape(IS_LANDSCAPE)
                .setTextFont(TEXT_FONT)
                .setFontSize(FONT_SIZE)
                .build();
        return table;
    }
}
