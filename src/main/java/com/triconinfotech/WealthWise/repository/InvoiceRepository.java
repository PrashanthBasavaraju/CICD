package com.triconinfotech.WealthWise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.triconinfotech.WealthWise.entity.Invoice;

/**
 * The Interface InvoiceRepository.
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>  {

}
