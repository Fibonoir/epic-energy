package it.epicode.epic_energy.service;

import it.epicode.epic_energy.exceptions.ResourceNotFoundException;
import it.epicode.epic_energy.models.Invoice;
import it.epicode.epic_energy.models.InvoiceStatus;
import it.epicode.epic_energy.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public Page<Invoice> getAllInvoices(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public Invoice createInvoice(Invoice invoice) {
        // extra logic, e.g., generating unique invoice number
        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Invoice existing = getInvoiceById(id);
        existing.setDate(invoiceDetails.getDate());
        existing.setAmount(invoiceDetails.getAmount());
        existing.setInvoiceNumber(invoiceDetails.getInvoiceNumber());
        existing.setStatus(invoiceDetails.getStatus());
        // ... set other fields as needed
        return invoiceRepository.save(existing);
    }

    public void deleteInvoice(Long id) {
        Invoice invoice = getInvoiceById(id);
        invoiceRepository.delete(invoice);
    }

    // Filter by status
    public Page<Invoice> getInvoicesByStatus(InvoiceStatus status, Pageable pageable) {
        return invoiceRepository.findByStatus(status, pageable);
    }

    // etc.
}