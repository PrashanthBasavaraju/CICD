package com.triconinfotech.WealthWise.service;

import java.util.List;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.triconinfotech.WealthWise.entity.Invoice;
import com.triconinfotech.WealthWise.repository.InvoiceRepository;

import lombok.AllArgsConstructor;

/**
 * The Class InvoiceServiceImpl.
 */
@AllArgsConstructor
@Service
public class InvoiceServiceImpl implements InvoiceServiceInterface {

	/** The invoice repo. */
	private final InvoiceRepository invoiceRepo;

	/**
	 * Merge invoice.
	 *
	 * @param invoice the invoice
	 * @return the invoice
	 */
	@Override
	public Invoice mergeInvoice(Invoice invoice) {
		return invoiceRepo.save(invoice);

	}

	/**
	 * Gets the all invoice.
	 *
	 * @return the all invoice
	 */
	@Override
	public List<Invoice> getAllInvoice() {

		return invoiceRepo.findAll();
	}

	/**
	 * Gets the invoice by id.
	 *
	 * @param id the id
	 * @return the invoice by id
	 * @throws RuntimeException the runtime exception
	 */
	@Override
	public Invoice getInvoiceById(Integer id) throws RuntimeException {
		Optional<Invoice> invoiceOptional = invoiceRepo.findById(id);
		return invoiceOptional.orElseThrow(() -> new RuntimeException("Invoice not found"));
	}

	/**
	 * Delete invoice.
	 *
	 * @param id the id
	 */
	@Override
	public void deleteInvoice(Integer id) {
		if (invoiceRepo.existsById(id)) {
			invoiceRepo.deleteById(id);
			return;
		}

		throw new RuntimeException("Invoice not found");
	}

}
