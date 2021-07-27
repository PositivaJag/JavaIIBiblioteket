package Printer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.jar.Attributes.Name;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JFileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.biblioteket.Objects.Loan;
import org.biblioteket.Persons.Loantagare;
import java.io.*;

/**
 *
 * @author jenni
 */
public class OLDPrinter {
//
//    public Boolean printLoanReciept(ArrayList<Loan> loans, Loantagare loantagare) {
//        try {
//            int noRows = loans.size();
//            String docName = "Lånelista " + loantagare.getPersonID() + LocalDate.now().toString()+".pdf";
//            String docPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
//            String subTitle1 = "LåntagarID: "+loantagare.getPersonID();
//            String subTitle2 = LocalDate.now().toString();
//            
//            Document reciept = new Document();
//            PdfWriter.getInstance(reciept, new FileOutputStream(docName));
//            reciept.open();
//            
//            PdfPTable table = createLoanRecieptTable();
//            
//            reciept.add(table);
//            reciept.close();
//            
//            PDDocument pdfdoc = PDDocument.load(new File(docName));
//            printPDF();
//            
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(OLDPrinter.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (DocumentException ex) {
//            Logger.getLogger(OLDPrinter.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(OLDPrinter.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return true;
//    }
//
//    private PrintService findPrintService() {
//        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
//        if (service != null) {
//            String printServiceName = service.getName();
//            System.out.println("Print Service Name = " + printServiceName);
//        } else {
//            System.out.println("No default print service found.");
//        }
//        return service;
//    }
//
//    private void printPDF() {
//      
//
//        PrinterJob job = PrinterJob.getPrinterJob();
//        job.setPageable(new PDFPageable(document));
//        job.setPrintService(myPrintService);
//        job.print();
//    }
//
//    private PdfPTable createLoanRecieptTable() {
//        int noCol = 5;
//        PdfPTable table = new PdfPTable(noCol);
//        
//        PdfPCell c1;
//        String[] titles = {"Lånedatum", "Titel", "Återlämnas senas", "Återlämnad", "Försenad"};
//        for (int i = 0; i<noCol; i++){
//            c1 = new PdfPCell(new Phrase(titles[i]));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//
//        }
//        return table;
//    }
}
