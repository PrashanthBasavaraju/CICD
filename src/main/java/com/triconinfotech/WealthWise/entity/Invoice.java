package com.triconinfotech.WealthWise.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * The Class Invoice.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "invoice")
public class Invoice {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private Integer id;
	
	/** The duration. */
	@Column(name = "duration")
	private LocalTime duration;
	
	/** The amount. */
	@Column(name = "amount")
	private Double amount;
	
	/** The created at. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	/** The updated at. */
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
