package com.triconinfotech.WealthWise.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;


/**
 * The Class Auditable.
 */
@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

	/** The created by. */

	@CreatedBy
	@Column(name = "created_by")
	@JsonIgnore
	private Long createdBy;

	/** The modified by. */

	@LastModifiedBy
	@Column(name = "modified_by")
	@JsonIgnore
	private Long modifiedBy;

	/** The created at. */
	@JsonIgnore
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	/** The updated at. */
	@JsonIgnore
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	/** The deleted at. */
	@JsonIgnore
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

}
