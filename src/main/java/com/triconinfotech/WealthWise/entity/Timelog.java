package com.triconinfotech.WealthWise.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Class Timelog.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "timelogs")
@SQLDelete(sql = "UPDATE timelogs SET deleted_at = current_timestamp WHERE timelog_id=?")
@Schema(description = "Timelogs")
@ApiModel
public class Timelog extends Auditable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "timelog_id")
	private Integer id;
//	@ApiModelProperty(value = "Start time in HH:mm:ss format", example = "09:15:00")
//	@Schema(example = "00:00:00", format = "time")
	@Column(name = "start_time") // optional
	private LocalTime startTime;

	@NotNull(message = "Hours is  mandatory")
	@Column(name = "hours", nullable = false)
	private LocalTime hours;

	/** The description. */
	@Column(name = "description")
	private String description;

	@NotNull(message = "Date is mandatory")
	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private LocalDate date;

	/** The projects. */
	@NotNull(message = "Project is mandatory")
	@JsonBackReference
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
	private Project projects;

	/** The emp id. */
	@NotNull(message = "EmployeeId is mandatory")
	@Column(name = "employee_id", nullable = false)
	private String empId;

}
