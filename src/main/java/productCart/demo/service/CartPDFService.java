package productCart.demo.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
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
import java.util.Optional;

@Service
public class CartPDFService {
    public void exportToPdf(final Optional<Cart> cart) {

        Document document = new Document();

        try {
            OutputStream outputStream = new FileOutputStream("Cart.pdf");

            PdfWriter.getInstance(document, outputStream);

            document.open();

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

            List<Product> products = cart.get().getProducts();

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
}
