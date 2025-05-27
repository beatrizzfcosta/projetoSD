package com.example.projetosd.logic;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.layout.Style;
import java.io.IOException;

public class PdfStyles {

    private static final String FONT_REGULAR_PATH = "src/main/resources/static/font/Montserrat-Regular.ttf";
    private static final String FONT_BOLD_PATH = "src/main/resources/static/font/Montserrat-Bold.ttf";

    public static PdfFont getRegularFont() throws IOException {
        PdfFontFactory.register(FONT_REGULAR_PATH);
        return PdfFontFactory.createRegisteredFont("Montserrat", PdfEncodings.IDENTITY_H);
    }

    public static PdfFont getBoldFont() throws IOException {
        PdfFontFactory.register(FONT_BOLD_PATH);
        return PdfFontFactory.createRegisteredFont("Montserrat-Bold", PdfEncodings.IDENTITY_H);
    }

    public static Style normalStyle(int size) throws IOException {
        return new Style()
                .setFont(getRegularFont())
                .setFontSize(size);
    }

    public static Style boldStyle(int size) throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(size);
    }

    public static Style headerStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(12)
                .setFontColor(ColorConstants.WHITE)
                .setBackgroundColor(ColorConstants.RED);
    }

    public static Style cellStyle() throws IOException {
        return new Style()
                .setFont(getRegularFont())
                .setFontSize(12);
    }

    public static Style totalStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(12);
    }
}
