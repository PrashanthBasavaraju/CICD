package com.triconinfotech.WealthWise.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class Project.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "project")
@SQLDelete(sql = "UPDATE project SET deleted_at = current_timestamp WHERE project_id=?")
@Schema(description = "Projects")
@ApiModel
public class Project extends Auditable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private Integer id;

	/** The name. */
	@Column(name = "name", nullable = false)
	@NotNull(message = "Name is mandatory")
	private String name;

	/** The description. */
	@Column(name = "description", nullable = false)
	private String description;

	/** The start date. */
	@NotNull(message = "Start Date is mandatory")
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	/** The end date. */
	@NotNull(message = "End Date is mandatory")
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	/** The client. */
	@NotNull(message = "Client is mandatory")
	@JsonBackReference
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)
	private Client client;

	/** The employee project relations. */
	@JsonManagedReference
	@OneToMany(mappedBy = "projectEPR", cascade = CascadeType.ALL)
	private List<EmployeeProjectRelation> employeeProjectRelations;

	/** The timesheets. */
	@OneToMany(mappedBy = "projects", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Timelog> timelogs = new ArrayList<>();

}
