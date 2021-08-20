package Printer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biblioteket.Controllers;
import org.biblioteket.Objects.Loan;
import org.biblioteket.Persons.Loantagare;

/**
 * Prints recipt on loans.
 *
 * @author jenni
 */
public class Printer {

    Loantagare loantagare;
    ArrayList<Loan> loans;

    Font rubrik1 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    Font rubrik2 = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
    Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);

    Document document;
    String docName = "Temp.pdf";

    /**
     * Creates and opens a PDF that the user can choose to print or save.
     *
     * @param loans
     * @param loantagare
     */
    public void createLoanRecipet(ArrayList<Loan> loans, Loantagare loantagare) {

        this.loantagare = loantagare;
        this.loans = loans;
        createPDF();
        openPdf();  //Open in default pdf reader 

    }

    /**
     * Creates the pdf that is sent to pdf reader.
     */
    private void createPDF() {
        try {
            //Create document
            document = new Document();
            //Get writer instance and choose file
            PdfWriter.getInstance(document, new FileOutputStream(docName));

            //Write general text
            document.open();
            Paragraph preface = new Paragraph();
            preface.add(new Paragraph("Kvitto lån " + LocalDate.now().toString(), rubrik1));
            preface.add(new Paragraph("\nLåntagarID: " + loantagare.getPersonID(), rubrik2));
            preface.add(new Paragraph(" "));
            document.add(preface);

            //create and add table of loans and close document.
            document.add(createTable());
            document.close();

        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates tabel with all new loans.
     *
     * @return
     */
    private PdfPTable createTable() {

        //New table
        PdfPTable table = new PdfPTable(4);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell c1 = new PdfPCell(new Phrase("Titel", rubrik2));

        //Set column headers
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Streckkod", rubrik2));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Lånedatum", rubrik2));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Återlämnas", rubrik2));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        table.setHeaderRows(1);

        //Add data from loans
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);
            table.addCell(new Phrase(loan.getTitel(), font));
            table.addCell(new Phrase(Integer.toString(loan.getStreckkod()), font));
            table.addCell(new Phrase(loan.getLoanDate().toString(), font));
            table.addCell(new Phrase(loan.getLatestReturnDate().toString(), font));
        }
        return table;
    }

    private void openPdf() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(docName);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                Controllers.simpleErrorAlert("pdf-filen kunde inte öppnas\n"
                        + "Kontrollera att en pdf-läsare finns på datorn");
            }
        }
    }
}
