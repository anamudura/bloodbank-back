package com.example.bloodbank.pdf;

import com.example.bloodbank.appuser.BankStatistics;
import com.example.bloodbank.repo.AppointmentRepository;
import com.example.bloodbank.service.BankService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;


@AllArgsConstructor
@Component
public class PdfGenerator {
    private final AppointmentRepository appointmentRepository;
    private final BankService bankService;
    public byte[] generatePdf(Long id) {
        try {

            BankStatistics bankPDF = bankService.calculateStats(id);
            // Create a new document
            Document document = new Document();

            // Create a ByteArrayOutputStream to hold the PDF content
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Create a PdfWriter to write the document to the ByteArrayOutputStream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            // Open the document
            document.open();

            // Add content to the document
            document.add(new Paragraph("Bank Statistics"));
            document.add(new Paragraph("Number of Liters: " + bankPDF.getNumberOfLiters()));
            document.add(new Paragraph("Number of Total Appointments: " + bankPDF.getNumberOfTotalAppointments()));
            document.add(new Paragraph("Number of Confirmed Appointments: " + bankPDF.getNumberOfConfirmedAppointments()));
            document.add(new Paragraph("O: " + bankPDF.getO()));
            document.add(new Paragraph("A: " + bankPDF.getA()));
            document.add(new Paragraph("B: " + bankPDF.getB()));
            document.add(new Paragraph("AB: " + bankPDF.getAB()));

            // Close the document
            document.close();

            // Get the PDF content from the ByteArrayOutputStream

            return outputStream.toByteArray();
        } catch (DocumentException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }

        return null;
    }
}
