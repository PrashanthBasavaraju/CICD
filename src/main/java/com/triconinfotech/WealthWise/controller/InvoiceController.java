package com.triconinfotech.WealthWise.controller;

import com.triconinfotech.WealthWise.entity.Invoice;
import com.triconinfotech.WealthWise.entity.Timelog;
import com.triconinfotech.WealthWise.service.InvoiceServiceInterface;
import com.triconinfotech.WealthWise.service.TimeLogServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Class InvoiceController.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/invoices")
@Tag(name = "Invoice")
public class InvoiceController {

	/** The invoice service. */
	private final InvoiceServiceInterface invoiceService;
	
	private TimeLogServiceImpl timelogService;

	/**
	 * Gets the all invoices.
	 *
	 * @return the all invoices
	 */
	@GetMapping
	public ResponseEntity<List<Invoice>> getAllInvoices() {
		List<Invoice> invoices = invoiceService.getAllInvoice();
		return new ResponseEntity<>(invoices, HttpStatus.OK);
	}

	/**
	 * Gets the invoice by id.
	 *
	 * @param id the id
	 * @return the invoice by id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> getInvoiceById(@PathVariable Integer id) {
		try {
			Invoice invoice = invoiceService.getInvoiceById(id);
			return new ResponseEntity<>(invoice, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Adds the invoice.
	 *
	 * @param invoice the invoice
	 * @return the response entity
	 */
	@PostMapping
	public ResponseEntity<Object> addInvoice(@RequestBody Invoice invoice) {
		Invoice createdInvoice = invoiceService.mergeInvoice(invoice);
		return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);

	}

	/**
	 * Update invoice.
	 *
	 * @param invoice the invoice
	 * @return the response entity
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateInvoice(@PathVariable Integer id, @RequestBody Invoice invoice) {
		Invoice updatedInvoice = invoiceService.mergeInvoice(invoice);
		return new ResponseEntity<>(updatedInvoice, HttpStatus.OK);

	}

	/**
	 * Delete invoice.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteInvoice(@PathVariable Integer id) {
		try {
			invoiceService.deleteInvoice(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/timelog")
	public List<Timelog> getTimesheet(@RequestParam("projectId") Integer project_id, @RequestParam("empId") Integer emp_id) {
	    return timelogService.getTimelogByProjectAndEmployee(project_id, emp_id);
	}

}
