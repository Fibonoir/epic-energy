package it.epicode.epic_energy.contorllers;

import it.epicode.epic_energy.dto.InvoiceCreateDTO;
import it.epicode.epic_energy.dto.InvoiceDTO;
import it.epicode.epic_energy.dto.InvoiceUpdateDTO;
import it.epicode.epic_energy.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Validated
public class InvoiceController {

    private final InvoiceService invoiceService;
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);


    @GetMapping
    public ResponseEntity<Page<InvoiceDTO>> getAllInvoices(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<InvoiceDTO> invoices = invoiceService.getAllInvoices(clientId, status, pageable);
        return ResponseEntity.ok(invoices);
    }

    @PostMapping
    public ResponseEntity<InvoiceDTO> createInvoice(@Valid @RequestBody InvoiceCreateDTO invoiceCreateDTO) {
        logger.debug("Received request to create invoice: {}", invoiceCreateDTO.getInvoiceNumber());
        InvoiceDTO createdInvoice = invoiceService.createInvoice(invoiceCreateDTO);
        logger.debug("Invoice created with ID: {}", createdInvoice.getId());
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Long id) {
        InvoiceDTO invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable Long id, @Valid @RequestBody InvoiceUpdateDTO invoiceUpdateDTO) {
        InvoiceDTO updatedInvoice = invoiceService.updateInvoice(id, invoiceUpdateDTO);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}