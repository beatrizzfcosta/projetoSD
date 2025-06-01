package com.example.projetosd.logic;

import com.example.projetosd.model.User;
import com.itextpdf.layout.Style;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

@Service
public class PdfService {
    private static final String PATH = "src/main/java/com/example/projetosd/logic/ficheiro.pdf";

    // Colors
    private static final DeviceRgb PRIMARY_COLOR = new DeviceRgb(33, 37, 41); // Cinza escuro
    private static final DeviceRgb LIGHT_GRAY = new DeviceRgb(248, 249, 250);

    public Document createPdfFiles(ByteArrayOutputStream baos) throws IOException {
        File file = new File(PATH);
        file.getParentFile().mkdirs();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);
        return doc;
    }

    public Table createHeaderFile(InvoiceDTO invoiceDTO) throws IOException {
        // Header
        Table headerTable = new Table(new float[]{1f, 1f})
                .setWidth(UnitValue.createPercentValue(100))
                .setBackgroundColor(PRIMARY_COLOR);

        // Left Side
        Paragraph invoiceInfo = new Paragraph()
                .add(new Text("FATURA").addStyle(PdfStyles.heroTitleStyle()))
                .add("\n")
                .add(new Text("#" + invoiceDTO.getPurchaseId()).addStyle(PdfStyles.invoiceNumberStyle()))
                .add("\n\n")
                .add(new Text("Data de Emissão").addStyle(PdfStyles.labelStyle()))
                .add("\n")
                .add(new Text(invoiceDTO.getDate().toLocalDate().toString()).addStyle(PdfStyles.dateStyle()));

        Cell leftCell = new Cell()
                .add(invoiceInfo)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        // Right Side
        try {
            ImageData imageData = ImageDataFactory.create("src/main/resources/static/images/logo-branco.png");
            Image logo = new Image(imageData)
                    .scaleToFit(115, 115)
                    .setMarginRight(30)
                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);

            Cell rightCell = new Cell()
                    .add(logo)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);

            headerTable.addCell(leftCell);
            headerTable.addCell(rightCell);
        } catch (Exception e) {
            Paragraph brandText = new Paragraph("TAGUS CLASSICS")
                    .addStyle(PdfStyles.brandStyle())
                    .setTextAlignment(TextAlignment.RIGHT);

            Cell rightCell = new Cell()
                    .add(brandText)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);

            headerTable.addCell(leftCell);
            headerTable.addCell(rightCell);
        }

        return headerTable;
    }

    public Table createInfo(InvoiceDTO invoiceDTO) throws IOException {
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                .useAllAvailableWidth()
                .setMarginTop(30)
                .setBorder(Border.NO_BORDER);

        // Client Data
        String nome = invoiceDTO.getCustomerName() != null ? invoiceDTO.getCustomerName() : "";
        String mail = invoiceDTO.getCustomerEmail() != null ? invoiceDTO.getCustomerEmail() : "";
        String nif = invoiceDTO.getCustomerNif() != null ? invoiceDTO.getCustomerNif() : "";
        String telefone = invoiceDTO.getCustomerTelemovel() != null ? invoiceDTO.getCustomerTelemovel() : "";

        Paragraph clientInfo = new Paragraph()
                .add("\n")
                .add(new Text(nome).addStyle(PdfStyles.clientNameStyle()))
                .add("\n")
                .add(new Text("NIF: " + nif).addStyle(PdfStyles.clientDetailStyle()))
                .add("\n")
                .add(new Text("Email: " + mail).addStyle(PdfStyles.clientDetailStyle()))
                .add("\n")
                .add(new Text("Contacto: " + telefone).addStyle(PdfStyles.clientDetailStyle()));

        Cell clientCell = new Cell()
                .add(clientInfo)
                .setBorder(new SolidBorder(PRIMARY_COLOR, 2))
                .setBackgroundColor(LIGHT_GRAY)
                .setPadding(15);

        // Company Data
        Paragraph companyInfo = new Paragraph()
                .add(new Text("TAGUS CLASSICS").addStyle(PdfStyles.companyNameStyle()))
                .add("\n\n")
                .add(new Text("Rua das Flores, 123").addStyle(PdfStyles.companyDetailStyle()))
                .add("\n")
                .add(new Text("1200-000 Lisboa").addStyle(PdfStyles.companyDetailStyle()))
                .add("\n")
                .add(new Text("Telefone: 210 123 456").addStyle(PdfStyles.companyDetailStyle()))
                .add("\n")
                .add(new Text("Email: info@tagusclassics.pt").addStyle(PdfStyles.companyDetailStyle()));

        Cell companyCell = new Cell()
                .add(companyInfo)
                .setBorder(Border.NO_BORDER)
                .setPadding(15)
                .setTextAlignment(TextAlignment.RIGHT);

        infoTable.addCell(clientCell);
        infoTable.addCell(companyCell);

        return infoTable;
    }

    public Table createInvoiceTable(InvoiceDTO invoiceDTO) throws IOException {
        Table itemsTable = new Table(UnitValue.createPercentArray(new float[]{0.8f, 3.5f, 1f, 1.5f, 1.5f}))
                .useAllAvailableWidth()
                .setMarginTop(30)
                .setBorder(Border.NO_BORDER);

        // Top Table
        String[] headers = {"#", "Produto", "Qtd", "Preço Unit.", "Total"};
        for (String header : headers) {
            Cell headerCell = new Cell()
                    .add(new Paragraph(header))
                    .addStyle(PdfStyles.premiumHeaderStyle())
                    .setBorder(Border.NO_BORDER)
                    .setPadding(12);
            itemsTable.addHeaderCell(headerCell);
        }

        // Products Cells
        int id = 1;
        boolean zebra = false;
        BigDecimal subtotal = BigDecimal.ZERO;

        for (InvoiceItemDTO item : invoiceDTO.getItems()) {
            Style rowStyle = zebra ? PdfStyles.premiumZebraRowStyle() : PdfStyles.premiumCellStyle();
            zebra = !zebra;

            String productName = item.getProductName() != null ? item.getProductName() : "—";
            String quantity = item.getQuantity() != null ? String.valueOf(item.getQuantity()) : "—";
            BigDecimal unitPrice = item.getUnitPrice();
            BigDecimal totalPrice = item.getTotalPrice();

            if (totalPrice != null) {
                subtotal = subtotal.add(totalPrice);
            }

            // First Cell
            Cell idCell = new Cell()
                    .add(new Paragraph(String.valueOf(id++)))
                    .addStyle(PdfStyles.idBadgeStyle())
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(8);

            // Product Cell
            Cell productCell = new Cell()
                    .add(new Paragraph(productName))
                    .addStyle(rowStyle)
                    .setFont(PdfStyles.getBoldFont())
                    .setBorder(Border.NO_BORDER)
                    .setPadding(8);

            // Quantity Cell
            Cell quantityCell = new Cell()
                    .add(new Paragraph(quantity))
                    .addStyle(rowStyle)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(8);

            // Unit Price Cell
            Cell unitPriceCell = new Cell()
                    .add(new Paragraph(unitPrice != null ? String.format("%.2f €", unitPrice.doubleValue()) : "—"))
                    .addStyle(rowStyle)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(8);

            // Total Price Cell
            Cell totalCell = new Cell()
                    .add(new Paragraph(totalPrice != null ? String.format("%.2f €", totalPrice.doubleValue()) : "—"))
                    .addStyle(rowStyle)
                    .setFont(PdfStyles.getBoldFont())
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(8);

            itemsTable.addCell(idCell);
            itemsTable.addCell(productCell);
            itemsTable.addCell(quantityCell);
            itemsTable.addCell(unitPriceCell);
            itemsTable.addCell(totalCell);
        }

        return itemsTable;
    }

    public Table createTotal(InvoiceDTO invoiceDTO) throws IOException {
        Table totalTable = new Table(UnitValue.createPercentArray(new float[]{0.3f, 1.8f}))
                .setWidth(UnitValue.createPercentValue(40))
                .setMarginTop(30)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .setBorder(new SolidBorder(PRIMARY_COLOR, 2))
                .setBackgroundColor(LIGHT_GRAY);

        // Subtotal
        totalTable.addCell(new Cell()
                .add(new Paragraph("Subtotal"))
                .addStyle(PdfStyles.summaryLabelStyle())
                .setBorder(Border.NO_BORDER)
                .setPadding(10));

        totalTable.addCell(new Cell()
                .add(new Paragraph(String.format("%.2f €", Double.parseDouble(invoiceDTO.getTotalAmount()))))
                .addStyle(PdfStyles.summaryValueStyle())
                .setBorder(Border.NO_BORDER)
                .setPadding(12)
                .setMinWidth(80)
                .setTextAlignment(TextAlignment.RIGHT));

        // IVA
        double iva = Double.parseDouble(invoiceDTO.getTotalAmount()) * 0.23;
        totalTable.addCell(new Cell()
                .add(new Paragraph("IVA (23%)"))
                .addStyle(PdfStyles.summaryLabelStyle())
                .setBorder(Border.NO_BORDER)
                .setPadding(10));

        totalTable.addCell(new Cell()
                .add(new Paragraph(String.format("%.2f €", iva)))
                .addStyle(PdfStyles.summaryValueStyle())
                .setBorder(Border.NO_BORDER)
                .setPadding(12)
                .setMinWidth(80)
                .setTextAlignment(TextAlignment.RIGHT));

        // Total final
        double totalFinal = Double.parseDouble(invoiceDTO.getTotalAmount()) + iva;

        Cell totalLabelCell = new Cell()
                .add(new Paragraph("TOTAL"))
                .addStyle(PdfStyles.finalTotalLabelStyle())
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(PRIMARY_COLOR)
                .setPadding(15);

        Cell totalValueCell = new Cell()
                .add(new Paragraph(String.format("%.2f €", totalFinal)))
                .addStyle(PdfStyles.finalTotalValueStyle())
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(PRIMARY_COLOR)
                .setPadding(15)
                .setMinWidth(50)
                .setTextAlignment(TextAlignment.RIGHT);

        totalTable.addCell(totalLabelCell);
        totalTable.addCell(totalValueCell);

        return totalTable;
    }

    public ByteArrayOutputStream run(InvoiceDTO invoiceDTO) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document doc = createPdfFiles(baos);
            doc.setMargins(0, 20, 20, 20);

            // Header
            Table header = createHeaderFile(invoiceDTO);
            doc.add(header);

            // Information Client & Company
            Table info = createInfo(invoiceDTO);
            doc.add(info);

            // Products Table
            Table itemsTable = createInvoiceTable(invoiceDTO);
            doc.add(itemsTable);

            // Line
            SolidLine decorativeLine = new SolidLine(3);
            decorativeLine.setColor(PRIMARY_COLOR);
            LineSeparator separator = new LineSeparator(decorativeLine);
            separator.setMarginTop(20);
            doc.add(separator);

            // Total
            Table totalTable = createTotal(invoiceDTO);
            doc.add(totalTable);

            // Footer
            Paragraph footer = new Paragraph()
                    .add(new Text("Obrigado pela sua preferência!").addStyle(PdfStyles.footerStyle()))
                    .add("\n")
                    .add(new Text("Tagus Classics - Excelência em Automóveis Clássicos").addStyle(PdfStyles.footerSubtextStyle()))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(40)
                    .setPadding(20)
                    .setBackgroundColor(LIGHT_GRAY)
                    .setBorder(new SolidBorder(PRIMARY_COLOR, 2));

            doc.add(footer);

            doc.close();
            System.out.println("Invoice created!");

            return baos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}