package com.example.projetosd.logic;

import com.example.projetosd.model.User;
import com.itextpdf.layout.Style;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
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
    public Document createPdfFiles(ByteArrayOutputStream baos) throws IOException {
        // Garante que as pastas existem
        File file = new File(PATH);
        file.getParentFile().mkdirs();

        // Criação do PDF
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);
        return doc;
    }
    public Table createHeaderFile(InvoiceDTO invoiceDTO) throws IOException {
        // Create image
        ImageData imageData = ImageDataFactory.create("src/main/resources/static/images/TagusClassics.png");
        Image image = new Image(imageData).scaleToFit(100, 100).setHorizontalAlignment(HorizontalAlignment.RIGHT);

        // Create Table
        Table topo = new Table(new float[]{2, 1})
                .setWidth(UnitValue.createPercentValue(100));

        // Left Side Table
        Paragraph titulo = new Paragraph()
                .add(new Text("FATURA #"+ invoiceDTO.getPurchaseId()).addStyle(PdfStyles.boldStyle(31)))
                .add("\n") // Quebra de linha
                .add(new Text("Data de emissão: ").addStyle(PdfStyles.boldStyle(12)))
                .add(new Text(invoiceDTO.getDate().toLocalDate().toString()).addStyle(PdfStyles.normalStyle(12)));

        Cell cellTexto = new Cell().add(titulo)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        topo.addCell(cellTexto);

        // Right Side Table
        Cell cellImagem = new Cell().add(image)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        topo.addCell(cellImagem);
        
        return topo;
        
    }
    public Table createInfo(InvoiceDTO invoiceDTO) throws IOException {
        // Create Table
        Table tabelaLadoALado = new Table(UnitValue.createPercentArray(new float[]{2, 1}))
                .useAllAvailableWidth()
                .setBorder(Border.NO_BORDER);

        // Obter dados do DTO e tratar null
        String nome = invoiceDTO.getCustomerName() != null ? invoiceDTO.getCustomerName() : "";
        String mail = invoiceDTO.getCustomerEmail() != null ? invoiceDTO.getCustomerEmail() : "";
        String nif = invoiceDTO.getCustomerNif() != null ? invoiceDTO.getCustomerNif() : "";
        String telefone = invoiceDTO.getCustomerTelemovel() != null ? invoiceDTO.getCustomerTelemovel() : "";

        // Left Side Table
        Paragraph dadosCliente = new Paragraph()
                .add(new Text(nome + "\n").addStyle(PdfStyles.boldStyle(17)))
                .add(new Text("NIF: ").addStyle(PdfStyles.boldStyle(12)))
                .add(new Text(nif + "\n").addStyle(PdfStyles.normalStyle(12)))
                .add(new Text("EMAIL: ").addStyle(PdfStyles.boldStyle(12)))
                .add(new Text(mail + "\n").addStyle(PdfStyles.normalStyle(12)))
                .add(new Text("Contacto: ").addStyle(PdfStyles.boldStyle(12)))
                .add(new Text(telefone).addStyle(PdfStyles.normalStyle(12)))
                .setTextAlignment(TextAlignment.LEFT);
        tabelaLadoALado.addCell(new Cell().add(dadosCliente)
                        .setBorder(Border.NO_BORDER))
                .setTextAlignment(TextAlignment.LEFT);

        // Right Side Table 
        Paragraph dadosEmpresa = new Paragraph()
                .add(new Text("Tagus Classics\n").addStyle(PdfStyles.boldStyle(17)))
                .add(new Text("Morada: ").addStyle(PdfStyles.boldStyle(12)))
                .add(new Text("Rua das Flores, 123, Lisboa\n").addStyle(PdfStyles.normalStyle(12)))
                .add(new Text("Telefone: ").addStyle(PdfStyles.boldStyle(12)))
                .add(new Text("210 123 456\n").addStyle(PdfStyles.normalStyle(12)))
                .setTextAlignment(TextAlignment.LEFT);
        tabelaLadoALado.addCell(new Cell().add(dadosEmpresa)
                        .setBorder(Border.NO_BORDER))
                .setTextAlignment(TextAlignment.LEFT);
        
        return tabelaLadoALado;
    }
    
    public Table createInvoiceTable(InvoiceDTO invoiceDTO) throws IOException {
        Table tabelaServicos = new Table(UnitValue.createPercentArray(new float[]{1, 4, 1, 2, 2}))
                .useAllAvailableWidth()
                .setBorder(Border.NO_BORDER);


        // Data
        tabelaServicos
                .addHeaderCell(new Cell()
                        .add(new Paragraph("Id"))
                        .addStyle(PdfStyles.headerStyle())
                        .setBorder(Border.NO_BORDER))

                .addHeaderCell(new Cell()
                        .add(new Paragraph("Produto"))
                        .addStyle(PdfStyles.headerStyle())
                        .setBorder(Border.NO_BORDER))

                .addHeaderCell(new Cell()
                        .add(new Paragraph("Qnt"))
                        .addStyle(PdfStyles.headerStyle())
                        .setBorder(Border.NO_BORDER))

                .addHeaderCell(new Cell()
                        .add(new Paragraph("Preço Unit."))
                        .addStyle(PdfStyles.headerStyle())
                        .setBorder(Border.NO_BORDER))

                .addHeaderCell(new Cell()
                        .add(new Paragraph("Total"))
                        .addStyle(PdfStyles.headerStyle())
                        .setBorder(Border.NO_BORDER));



        int id = 1;
        boolean zebra = false;
        for (InvoiceItemDTO item : invoiceDTO.getItems()) {
            Style rowStyle = zebra ? PdfStyles.zebraRowStyle() : PdfStyles.cellStyle();
            zebra = !zebra;

            String productName = item.getProductName() != null ? item.getProductName() : "—";
            String quantity = item.getQuantity() != null ? String.valueOf(item.getQuantity()) : "—";

            BigDecimal unitPrice = item.getUnitPrice();
            BigDecimal totalPrice = item.getTotalPrice();

            tabelaServicos.addCell(new Cell().add(new Paragraph(String.valueOf(id++)))
                    .addStyle(rowStyle)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER));

            tabelaServicos.addCell(new Cell().add(new Paragraph(productName))
                    .addStyle(rowStyle)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER));

            tabelaServicos.addCell(new Cell().add(new Paragraph(String.valueOf(quantity)))
                    .addStyle(rowStyle)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER));

            tabelaServicos.addCell(new Cell().add(new Paragraph(unitPrice != null ? String.format("%.2f €", unitPrice.doubleValue()) : "—"))
                    .addStyle(rowStyle)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT));

            tabelaServicos.addCell(new Cell().add(new Paragraph(totalPrice != null ? String.format("%.2f €", totalPrice.doubleValue()) : "—"))
                    .addStyle(rowStyle)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT));
        }


        return tabelaServicos;
    }
    
    public Table createTotal(InvoiceDTO invoiceDTO) throws IOException {
        // Create Table
        Table resumo = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                .setWidth(UnitValue.createPercentValue(25))
                .setMarginTop(20)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);

        resumo.addCell(new Cell()
                .add(new Paragraph("Total:"))
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(PdfStyles.totalStyle()));

        resumo.addCell(new Cell()
                .add(new Paragraph(String.valueOf(Double.parseDouble(invoiceDTO.getTotalAmount())) + " €"))
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(PdfStyles.totalStyle()));

        return resumo;
    }
    public ByteArrayOutputStream run(InvoiceDTO invoiceDTO) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // Create PDF Files
            Document doc = createPdfFiles(baos);
            doc.setMargins(10, 10, 10, 10);

            // Header File
            Table topo = createHeaderFile(invoiceDTO);
            doc.add(topo);

            //Space
            doc.add(new Paragraph("").setMarginTop(5));

            // Line
            SolidLine solidLine = new SolidLine(2);
            solidLine.setColor(ColorConstants.BLACK);

            LineSeparator linha = new LineSeparator(solidLine);

            doc.add(linha);

            // Space
            doc.add(new Paragraph("").setMarginTop(20));

            // Client and Company Information 
            Table tabelaLadoALado = createInfo(invoiceDTO);
            doc.add(tabelaLadoALado);

            // Space
            doc.add(new Paragraph("").setMarginTop(20));

            // Invoice Information
            Table tabelaServicos = createInvoiceTable(invoiceDTO);
            doc.add(tabelaServicos);

            // Final Price Information
            Table resumo = createTotal(invoiceDTO);
            doc.add(resumo);

            Paragraph footer = new Paragraph("Obrigado pela sua preferência!")
                    .addStyle(PdfStyles.boldStyle(10))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(40);

            doc.add(footer);


            // Close Document
            doc.close();
            System.out.println("✅ PDF criado com sucesso: ");

            return baos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
