package com.triconinfotech.WealthWise.service;

import java.util.List;

import com.triconinfotech.WealthWise.entity.Invoice;


/**
 * The Interface InvoiceServiceInterface.
 */
public interface InvoiceServiceInterface {

	/**
	 * Merge invoice.
	 *
	 * @param invoice the invoice
	 * @return the invoice
	 */
	Invoice mergeInvoice(Invoice invoice);
	
	/**
	 * Gets the all invoice.
	 *
	 * @return the all invoice
	 */
	List<Invoice> getAllInvoice();
	
	/**
	 * Gets the invoice by id.
	 *
	 * @param id the id
	 * @return the invoice by id
	 */
	Invoice getInvoiceById(Integer id);
	
	/**
	 * Delete invoice.
	 *
	 * @param id the id
	 */
	void deleteInvoice(Integer id);
}
