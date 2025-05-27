package com.example.projetosd.logic;

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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfService {
    private static final String PATH = "src/main/java/com/example/projetosd/logic/ficheiro.pdf";
    public Document createPdfFiles() throws IOException {
        // Garante que as pastas existem
        File file = new File(PATH);
        file.getParentFile().mkdirs();

        // Criação do PDF
        FileOutputStream fos = new FileOutputStream(file);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);
        return doc;
    }
    public Table createHeaderFile() throws IOException {
        // Create image
        ImageData imageData = ImageDataFactory.create("src/main/resources/static/images/TagusClassics.png");
        Image image = new Image(imageData).scaleToFit(100, 100).setHorizontalAlignment(HorizontalAlignment.RIGHT);

        // Create Table
        Table topo = new Table(new float[]{2, 1});
        topo.setWidth(UnitValue.createPercentValue(100));

        // Left Side Table
        Paragraph titulo = new Paragraph()
                .add(new Text("FATURA #0001").addStyle(PdfStyles.boldStyle(31)))
                .add("\n") // Quebra de linha
                .add(new Text("Data de emissão: ").addStyle(PdfStyles.boldStyle(12)))
                .add(new Text("27-10-2025").addStyle(PdfStyles.normalStyle(12)));

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
    public Table createInfo() throws IOException {
        // Create Table
        Table tabelaLadoALado = new Table(UnitValue.createPercentArray(new float[]{2, 1}))
                .useAllAvailableWidth()
                .setBorder(Border.NO_BORDER);

        // Left Side Table
        Paragraph dadosCliente = new Paragraph()
                .add(new Text("Jorge Rodrigues\n").addStyle(PdfStyles.boldStyle(17)))
                .add(new Text("NIF: ").addStyle(PdfStyles.boldStyle(12)))
                .add(new Text("247445347\n").addStyle(PdfStyles.normalStyle(12)))
                .add(new Text("EMAIL: ").addStyle(PdfStyles.boldStyle(12)))
                .add(new Text("Jorge_Rodrigues@hotmail.com\n").addStyle(PdfStyles.normalStyle(12)))
                .add(new Text("Contacto: ").addStyle(PdfStyles.boldStyle(12)))
                .add(new Text("926873163").addStyle(PdfStyles.normalStyle(12)))
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
    
    public Table createInvoiceTable() throws IOException {
        Table tabelaServicos = new Table(UnitValue.createPercentArray(new float[]{1, 4, 1, 2, 2}))
                .useAllAvailableWidth()
                .setBorder(Border.NO_BORDER);

        // Information
        tabelaServicos
                .addHeaderCell(new Cell().add(new Paragraph("Id"))
                        .addStyle(PdfStyles.headerStyle())
                        .setBorder(Border.NO_BORDER))
                .addHeaderCell(new Cell().add(new Paragraph("Modelo"))
                        .addStyle(PdfStyles.headerStyle())
                        .setBorder(Border.NO_BORDER))
                .addHeaderCell(new Cell().add(new Paragraph("Qnt"))
                        .addStyle(PdfStyles.headerStyle())
                        .setBorder(Border.NO_BORDER))
                .addHeaderCell(new Cell().add(new Paragraph("Preço"))
                        .addStyle(PdfStyles.headerStyle())
                        .setBorder(Border.NO_BORDER))
                .addHeaderCell(new Cell().add(new Paragraph("Total"))
                        .addStyle(PdfStyles.headerStyle())
                        .setBorder(Border.NO_BORDER));

        // Data
        String[][] data = {
                {"1", "Ford Mustang 1965", "1", "30,000 €", "30,000 €"},
                {"2", "Chevrolet Camaro 1969", "2", "28,000 €", "56,000 €"},
                {"3", "Volkswagen Beetle 1960", "3", "15,000 €", "45,000 €"},
                {"4", "Porsche 911 1973", "1", "80,000 €", "80,000 €"}
        };

        for (String[] lines : data) {
            for (String texto : lines) {
                tabelaServicos.addCell(new Cell()
                                .add(new Paragraph(texto))
                                .addStyle(PdfStyles.cellStyle())
                                .setBorder(Border.NO_BORDER))
                        .setTextAlignment(TextAlignment.CENTER);
            }
        }

        return tabelaServicos;
    }
    
    public Table createTotal() throws IOException {
        // Create Table
        Table resumo = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                .setWidth(UnitValue.createPercentValue(25))
                .setMarginTop(20)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);

        resumo.addCell(new Cell().add(new Paragraph("Total")).setBorder(Border.NO_BORDER).addStyle(PdfStyles.totalStyle()));
        resumo.addCell(new Cell().add(new Paragraph("211,000 €")).setBorder(Border.NO_BORDER).addStyle(PdfStyles.totalStyle()));

        return resumo;
    }
    public void run() {
        try {
            // Create PDF Files
            Document doc = createPdfFiles();
            doc.setMargins(10, 10, 10, 10);
            
            // Header File
            Table topo = createHeaderFile();
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
            Table tabelaLadoALado = createInfo();
            doc.add(tabelaLadoALado);
            
            // Space
            doc.add(new Paragraph("").setMarginTop(20));
            
            // Invoice Information
            Table tabelaServicos = createInvoiceTable();
            doc.add(tabelaServicos);
            
            // Final Price Information
            Table resumo = createTotal();
            doc.add(resumo);
            
            // Space
            doc.add(new Paragraph("").setMarginTop(20));
            
            
            Paragraph nota = new Paragraph()
                    .add(new Text("Nota:").addStyle(PdfStyles.boldStyle(12)));

            doc.add(nota);
            
            // Close Document
            doc.close();
            System.out.println("✅ PDF criado com sucesso: " );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
