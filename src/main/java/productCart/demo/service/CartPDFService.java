package productCart.demo.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import productCart.demo.model.Cart;
import productCart.demo.model.Product;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class CartPDFService {
    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static final Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);

    public void exportToPdf(Cart cart) {

        Document document = new Document();

        try {
            OutputStream outputStream = new FileOutputStream("Cart.pdf");

            PdfWriter.getInstance(document, outputStream);

            document.open();
            Anchor anchor = new Anchor("First Chapter", catFont);
            anchor.setName("CART");

            // Second parameter is the number of the chapter;
            Paragraph paragraph = new Paragraph("Cart", subFont);
            paragraph.add(new Paragraph(cart.getStatus().toString()));
            paragraph.add(new Paragraph(cart.getDateOfOrder().toString()));

            document.add(paragraph);

            paragraph = new Paragraph();
            addEmptyLine(paragraph, 2);
            document.add(paragraph);

            //Sukuriame pdf langelius
            PdfPCell cell1 = new PdfPCell(new Paragraph("Id"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Name"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Amount"));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Price"));

            PdfPTable pdfPTable = new PdfPTable(4);
            pdfPTable.addCell(cell1);
            pdfPTable.addCell(cell2);
            pdfPTable.addCell(cell3);
            pdfPTable.addCell(cell4);

            List<Product> products = cart.getProducts();

            for (Product product : products) {
                PdfPCell productCell1 = new PdfPCell(new Paragraph(product.getId().toString()));
                pdfPTable.addCell(productCell1);
                PdfPCell productCell2 = new PdfPCell(new Paragraph(product.getName()));
                pdfPTable.addCell(productCell2);
                PdfPCell productCell3 = new PdfPCell(new Paragraph(product.getAmount().toString()));
                pdfPTable.addCell(productCell3);
                PdfPCell productCell4 = new PdfPCell(new Paragraph(product.getPrice().toString()));
                pdfPTable.addCell(productCell4);
            }

            document.add(pdfPTable);

            document.close();
            outputStream.close();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
