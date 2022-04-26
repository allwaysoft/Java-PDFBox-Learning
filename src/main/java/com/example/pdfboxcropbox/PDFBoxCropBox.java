package com.example.pdfboxcropbox;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class PDFBoxCropBox {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        File file = new File("F:/rrrrr.pdf");

        PDDocument pdDocument = PDDocument.load(file);
        System.out.println("PDF loaded");
        PDPage page = pdDocument.getPage(0);
        page.setCropBox(new PDRectangle(125f, 150f, 600f, 400f));
        pdDocument.save(new File("F:/rrrrr-edit.pdf"));
    }
}
