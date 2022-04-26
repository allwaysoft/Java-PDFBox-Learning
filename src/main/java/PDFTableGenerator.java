
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class PDFTableGenerator {

    // Generates document from Table object
    public void generatePDF(Table table) throws IOException {
        PDDocument doc = null;
        try {
            doc = new PDDocument();
            drawTable(doc, table);
            doc.save("F:\\sample.pdf");
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

    // Configures basic setup for the table and draws it page by page
    public void drawTable(PDDocument doc, Table table) throws IOException {
        // Calculate pagination
        Integer rowsPerPage = new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - 1; // subtract
        Integer numberOfPages = new Double(Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage)).intValue();

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page = generatePage(doc, table);
            PDPageContentStream contentStream = generateContentStream(doc, page, table);
            if (pageCount == 0) {
                //写标题
                contentStream.beginText();
                // 文字位置
                contentStream.setFont(PDType0Font.load(doc, new File("C:\\Windows\\Fonts\\simfang.TTF")), 18);
                contentStream.newLineAtOffset(table.getWidth() / 2 + 20, table.getHeight() + table.getMargin() + 40);
                // 插入文本
                contentStream.showText(table.getTitle());
                contentStream.endText();

                //导出时间
                contentStream.beginText();
                contentStream.setFont(PDType0Font.load(doc, new File("C:\\Windows\\Fonts\\simfang.TTF")), 8);
                contentStream.newLineAtOffset(table.getWidth() - 20, table.getHeight() + table.getMargin() + 10);
                // 插入文本
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                contentStream.showText("批单导出日期:" + format.format(new Date()));
                contentStream.endText();

                contentStream.setFont(PDType0Font.load(doc, new File("C:\\Windows\\Fonts\\simfang.TTF")), 10);
                String[][] currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
                drawCurrentPage(table, currentPageContent, contentStream);
            } else {
                String[][] currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
                drawCurrentPage(table, currentPageContent, contentStream);
            }
        }
    }

    // Draws current page table grid and border lines and content
    private void drawCurrentPage(Table table, String[][] currentPageContent, PDPageContentStream contentStream)
            throws IOException {
        float tableTopY = table.isLandscape() ? table.getPageSize().getWidth() - table.getMargin() : table.getPageSize().getHeight() - table.getMargin();

        // Draws grid and borders
        drawTableGrid(table, currentPageContent, contentStream, tableTopY);

        // Position cursor to start drawing content
        float nextTextX = table.getMargin() + table.getCellMargin();
        // Calculate center alignment for text in cell considering font height
        float nextTextY = tableTopY - (table.getRowHeight() / 2)
                - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table.getFontSize()) / 4);

        // Write column headers
        writeContentLine(table.getColumnsNamesAsArray(), contentStream, nextTextX, nextTextY, table);
        nextTextY -= table.getRowHeight();
        nextTextX = table.getMargin() + table.getCellMargin();

        // Write content
        for (int i = 0; i < currentPageContent.length; i++) {
            writeContentLine(currentPageContent[i], contentStream, nextTextX, nextTextY, table);
            nextTextY -= table.getRowHeight();
            nextTextX = table.getMargin() + table.getCellMargin();
        }

        contentStream.close();
    }

    // Writes the content for one line
    private void writeContentLine(String[] lineContent, PDPageContentStream contentStream, float nextTextX, float nextTextY,
            Table table) throws IOException {
        Integer numberOfColumns = table.getNumberOfColumns();
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            String text = lineContent[i];
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(nextTextX, nextTextY);
            contentStream.drawString(text != null ? text : "");
            contentStream.endText();
            nextTextX += table.getColumns().get(i).getWidth();
        }
    }

    private void drawTableGrid(Table table, String[][] currentPageContent, PDPageContentStream contentStream, float tableTopY)
            throws IOException {
        // Draw row lines
        float nextY = tableTopY;
        for (int i = 0; i <= currentPageContent.length + 1; i++) {
            contentStream.drawLine(table.getMargin(), nextY, table.getMargin() + table.getWidth(), nextY);
            nextY -= table.getRowHeight();
        }

        // Draw column lines
        final float tableYLength = table.getRowHeight() + (table.getRowHeight() * currentPageContent.length);
        final float tableBottomY = tableTopY - tableYLength;
        float nextX = table.getMargin();
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
            nextX += table.getColumns().get(i).getWidth();
        }
        contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
    }

    private String[][] getContentForCurrentPage(Table table, Integer rowsPerPage, int pageCount) {
        int startRange = pageCount * rowsPerPage;
        int endRange = (pageCount * rowsPerPage) + rowsPerPage;
        if (endRange > table.getNumberOfRows()) {
            endRange = table.getNumberOfRows();
        }
        return Arrays.copyOfRange(table.getContent(), startRange, endRange);
    }

    private PDPage generatePage(PDDocument doc, Table table) {
        PDPage page = new PDPage();
        page.setMediaBox(table.getPageSize());
        page.setRotation(table.isLandscape() ? 90 : 0);
        doc.addPage(page);
        return page;
    }

    private PDPageContentStream generateContentStream(PDDocument doc, PDPage page, Table table) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, false, false);
        // User transformation matrix to change the reference when drawing.
        // This is necessary for the landscape position to draw correctly
        if (table.isLandscape()) {
            contentStream.concatenate2CTM(0, 1, -1, 0, table.getPageSize().getWidth(), 0);
        }
        contentStream.setFont(PDType0Font.load(doc, new File("C:\\Windows\\Fonts\\simfang.TTF")), 10);
        return contentStream;
    }
}
