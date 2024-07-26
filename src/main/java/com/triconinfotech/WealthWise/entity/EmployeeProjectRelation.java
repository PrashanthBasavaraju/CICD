package com.triconinfotech.WealthWise.entity;

import org.hibernate.annotations.SQLDelete;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * The Class EmployeeProjectRelation.
 */
//to do -> sessions 
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "employee_project_relation")
@SQLDelete(sql = "UPDATE employee_project_relation SET deleted_at = current_timestamp WHERE epr_id=?")
@ApiModel
public class EmployeeProjectRelation extends Auditable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "epr_id")
	private Integer id;

	/** The employee id. */
	@NotNull(message = "Employee ID is Mandatory")
	@Column(name = "employee_id", nullable = false)
	private Integer employeeId;

	/** The project EPR. */
	@NotNull(message = "Project is mandatory")
	@JsonBackReference
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "project_id")
	private Project projectEPR;

	/** The weightage. */
	@Column(name = "weightage")
	private String weightage;

	/** The roletype. */

	@NotNull(message = "Role  is mandatory")
	@ManyToOne//(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "role_id",referencedColumnName = "role_id", nullable = false)
	private Role roletype;

}
