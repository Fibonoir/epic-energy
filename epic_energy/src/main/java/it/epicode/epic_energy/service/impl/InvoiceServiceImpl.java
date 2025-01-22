package it.epicode.epic_energy.service.impl;

import it.epicode.epic_energy.dto.InvoiceCreateDTO;
import it.epicode.epic_energy.dto.InvoiceDTO;
import it.epicode.epic_energy.dto.InvoiceUpdateDTO;
import it.epicode.epic_energy.exceptions.ResourceNotFoundException;
import it.epicode.epic_energy.models.Client;
import it.epicode.epic_energy.models.Invoice;
import it.epicode.epic_energy.models.InvoiceStatus;
import it.epicode.epic_energy.repositories.ClientRepository;
import it.epicode.epic_energy.repositories.InvoiceRepository;
import it.epicode.epic_energy.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(ExternalDataImportServiceImpl.class);

    @Override
    public Page<InvoiceDTO> getAllInvoices(Long clientId, String status, Pageable pageable) {
        Page<Invoice> invoices;

        if (clientId != null && status != null) {
            InvoiceStatus invoiceStatus = InvoiceStatus.valueOf(status.toUpperCase());
            invoices = invoiceRepository.findByClientIdAndStatus(clientId, invoiceStatus, pageable);
        } else if (clientId != null) {
            invoices = invoiceRepository.findByClientId(clientId, pageable);
        } else if (status != null) {
            InvoiceStatus invoiceStatus = InvoiceStatus.valueOf(status.toUpperCase());
            invoices = invoiceRepository.findByStatus(invoiceStatus, pageable);
        } else {
            invoices = invoiceRepository.findAll(pageable);
        }

        return invoices.map(invoice -> modelMapper.map(invoice, InvoiceDTO.class));
    }

    @Override
    public InvoiceDTO createInvoice(InvoiceCreateDTO invoiceCreateDTO) {
        // Fetch Client
        Client client = clientRepository.findById(invoiceCreateDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + invoiceCreateDTO.getClientId()));

        // Map DTO to Entity
        Invoice invoice = modelMapper.map(invoiceCreateDTO, Invoice.class);
        invoice.setClient(client);

        // Save Invoice
        Invoice savedInvoice = invoiceRepository.save(invoice);

        InvoiceDTO invoiceDTO = modelMapper.map(savedInvoice, InvoiceDTO.class);
        invoiceDTO.setClientId(client.getId());

        return invoiceDTO;
    }

    @Override
    public InvoiceDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        return modelMapper.map(invoice, InvoiceDTO.class);
    }

    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceUpdateDTO invoiceUpdateDTO) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));

        // Fetch Client
        Client client = clientRepository.findById(invoiceUpdateDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + invoiceUpdateDTO.getClientId()));

        // Update fields
        existingInvoice.setDate(invoiceUpdateDTO.getDate());
        existingInvoice.setAmount(invoiceUpdateDTO.getAmount());
        existingInvoice.setInvoiceNumber(invoiceUpdateDTO.getInvoiceNumber());
        existingInvoice.setStatus(invoiceUpdateDTO.getStatus());
        existingInvoice.setClient(client);

        // Save Updated Invoice
        Invoice updatedInvoice = invoiceRepository.save(existingInvoice);
        return modelMapper.map(updatedInvoice, InvoiceDTO.class);
    }

    @Override
    public void deleteInvoice(Long id) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        invoiceRepository.delete(existingInvoice);
    }
}
