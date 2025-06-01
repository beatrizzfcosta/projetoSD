package com.example.projetosd.logic;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;

public class PdfStyles {
    private static final String FONT_REGULAR_PATH = "src/main/resources/static/font/Montserrat-Regular.ttf";
    private static final String FONT_BOLD_PATH = "src/main/resources/static/font/Montserrat-Bold.ttf";

    // Colors
    private static final DeviceRgb PRIMARY_COLOR = new DeviceRgb(33, 37, 41);      // Cinza escuro
    private static final DeviceRgb ACCENT_COLOR = new DeviceRgb(255, 193, 7);       // Dourado
    private static final DeviceRgb LIGHT_COLOR = new DeviceRgb(248, 249, 250);      // Cinza claro
    private static final DeviceRgb WHITE = new DeviceRgb(255, 255, 255);            // Branco
    private static final DeviceRgb ZEBRA_COLOR = new DeviceRgb(200, 200, 200);      // Zebra suave


    // ========== FONT STYLE ==========
    public static PdfFont getRegularFont() throws IOException {
        try {
            PdfFontFactory.register(FONT_REGULAR_PATH);
            return PdfFontFactory.createRegisteredFont("Montserrat", PdfEncodings.IDENTITY_H);
        } catch (Exception e) {
            return PdfFontFactory.createFont();
        }
    }

    public static PdfFont getBoldFont() throws IOException {
        try {
            PdfFontFactory.register(FONT_BOLD_PATH);
            return PdfFontFactory.createRegisteredFont("Montserrat-Bold", PdfEncodings.IDENTITY_H);
        } catch (Exception e) {
            return PdfFontFactory.createFont();
        }
    }

    // ========== HEADER STYLE ==========

    public static Style heroTitleStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(18)
                .setFontColor(WHITE)
                .setMarginLeft(20)
                .setTextAlignment(TextAlignment.LEFT);
    }

    public static Style invoiceNumberStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(16)
                .setMarginLeft(20)
                .setFontColor(ACCENT_COLOR)
                .setTextAlignment(TextAlignment.LEFT);
    }

    public static Style labelStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(11)
                .setMarginLeft(20)
                .setFontColor(LIGHT_COLOR)
                .setTextAlignment(TextAlignment.LEFT);
    }

    public static Style dateStyle() throws IOException {
        return new Style()
                .setFont(getRegularFont())
                .setFontSize(10)
                .setMarginLeft(20)
                .setMarginBottom(15)
                .setFontColor(WHITE)
                .setTextAlignment(TextAlignment.LEFT);
    }

    public static Style brandStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(10)
                .setFontColor(ACCENT_COLOR)
                .setTextAlignment(TextAlignment.RIGHT);
    }

    // ========== INFORMATION STYLE ==========

    public static Style sectionHeaderStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(11)
                .setFontColor(PRIMARY_COLOR)
                .setTextAlignment(TextAlignment.LEFT);
    }

    public static Style clientNameStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(13)
                .setFontColor(PRIMARY_COLOR)
                .setTextAlignment(TextAlignment.LEFT);
    }

    public static Style clientDetailStyle() throws IOException {
        return new Style()
                .setFont(getRegularFont())
                .setFontSize(8)
                .setFontColor(PRIMARY_COLOR)
                .setTextAlignment(TextAlignment.LEFT);
    }

    public static Style companyNameStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(14)
                .setFontColor(PRIMARY_COLOR)
                .setTextAlignment(TextAlignment.RIGHT);
    }

    public static Style companyDetailStyle() throws IOException {
        return new Style()
                .setFont(getRegularFont())
                .setFontSize(8)
                .setFontColor(PRIMARY_COLOR)
                .setTextAlignment(TextAlignment.RIGHT);
    }

    // ========== TABLE STYLE ==========

    public static Style premiumHeaderStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(8)
                .setFontColor(WHITE)
                .setBackgroundColor(PRIMARY_COLOR)
                .setTextAlignment(TextAlignment.CENTER);
    }

    public static Style premiumCellStyle() throws IOException {
        return new Style()
                .setFont(getRegularFont())
                .setFontSize(8)
                .setFontColor(PRIMARY_COLOR)
                .setBackgroundColor(WHITE)
                .setBorderBottom(new SolidBorder(LIGHT_COLOR, 1));
    }

    public static Style premiumZebraRowStyle() throws IOException {
        return new Style()
                .setFont(getRegularFont())
                .setFontSize(8)
                .setFontColor(PRIMARY_COLOR)
                .setBackgroundColor(ZEBRA_COLOR)
                .setBorderBottom(new SolidBorder(LIGHT_COLOR, 1));
    }

    public static Style idBadgeStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(10)
                .setFontColor(PRIMARY_COLOR)
                .setBackgroundColor(ACCENT_COLOR);
    }

    // ========== RESUME/TOTAL STYLE ==========

    public static Style summaryLabelStyle() throws IOException {
        return new Style()
                .setFont(getRegularFont())
                .setFontSize(8)
                .setFontColor(PRIMARY_COLOR);
    }

    public static Style summaryValueStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(8)
                .setFontColor(PRIMARY_COLOR);
    }

    public static Style finalTotalLabelStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(8)
                .setFontColor(WHITE);
    }

    public static Style finalTotalValueStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(10)
                .setFontColor(ACCENT_COLOR);
    }

    // ========== FOOTER STYLE ==========

    public static Style footerStyle() throws IOException {
        return new Style()
                .setFont(getBoldFont())
                .setFontSize(10)
                .setFontColor(PRIMARY_COLOR);
    }

    public static Style footerSubtextStyle() throws IOException {
        return new Style()
                .setFont(getRegularFont())
                .setFontSize(7)
                .setFontColor(PRIMARY_COLOR);
    }

}