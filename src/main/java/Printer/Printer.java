/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Printer;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.biblioteket.Objects.Loan;
import org.biblioteket.Persons.Loantagare;

/**
 *
 * @author jenni
 */
public class Printer {

    public void createLoanRecipet(ArrayList<Loan> loans, Loantagare loantagare) {

        try {
            File file = createNewFile("Temp.txt");

            FileWriter writer = new FileWriter(file);
            
            writer.write("Kvitto lån\n");   //Rubrik
            writer.write("LåntagarID: " + loantagare.getPersonID() + "\n");
            writer.write(LocalDate.now().toString() + "\n\n");
            writer.write("Titel\nLånedatum\tÅterlämnas \tFörsenad\n\n");
            //Skriv alla lån.
            for (int i = 0; i < loans.size(); i++) {
                Loan loan = loans.get(i);
                String toWriter = loan.getTitel() + "\n" + loan.getLoanDate().toString()
                        + "\t" + loan.getLatestReturnDate().toString() + "\t" 
                        + getLateStatus(loan)+"\n\n";
                writer.write(toWriter);
            }
            writer.close();
            
            PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintService(findPrintService());
        job.print();
        
        } catch (IOException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PrinterException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
}

    private File createNewFile(String name) {
        try {
            //Create temporary file for reciept
            File file = new File(name);
            if (file.exists()) //Delete file if it already exists. 
            {
                file.delete();
            }
            file.createNewFile();   //Create new, empty file. 
            return file;
        } catch (IOException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String getActualReturnDate(Loan loan) {
        if (loan.getActualReturnDate() == null) {
            return "";
        } else {
            return loan.getActualReturnDate().toString();
        }

    }
    
    private String getLateStatus(Loan loan){
        LocalDate latestreturn = loan.getLatestReturnDate();
        
        if (latestreturn.isBefore(LocalDate.now())){
            return ChronoUnit.DAYS.between(latestreturn, LocalDate.now()) +" dagar sen";        
            }
        else
            return "";
    }
    
      private PrintService findPrintService() {
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        if (service != null) {
            String printServiceName = service.getName();
            System.out.println("Print Service Name = " + printServiceName);
        } else {
            System.out.println("No default print service found.");
        }
        return service;
    }

    private void printFile() {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintService(findPrintService());
            job.print();
        } catch (PrinterException ex) {
            Logger.getLogger(OLDPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createPDF(){
        Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));

document.open();
Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
Chunk chunk = new Chunk("Hello World", font);

    }
}
