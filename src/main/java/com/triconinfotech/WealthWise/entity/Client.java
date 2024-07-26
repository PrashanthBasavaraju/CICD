package com.triconinfotech.WealthWise.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import java.util.List;


/**
 * The Class Client.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "client")
@SQLDelete(sql = "UPDATE client SET deleted_at = current_timestamp WHERE client_id=?")
@ApiModel
public class Client extends Auditable {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_id")
	private Integer id;
	
	/** The name. */
	@NotNull(message = "Name is mandatory")
	@Column(name = "name", nullable = false)
	private String name;
	
	/** The email. */
	@NotNull(message = "Email is mandatory")
	@Column(name = "email", nullable = false)
	private String email;
	
	/** The location. */
	@NotNull(message = "Location is mandatory")
	@Column(name = "location", nullable = false)
	private String location;
	
	/** The phone. */
	@NotNull(message = "Phone is mandatory")
	@Column(name = "phone", nullable = false)
	private String phone;

/** The projects. */
//    @JsonIgnore
	@JsonManagedReference
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	// @NotNull(message = "Project ID is mandatory")
	private List<Project> projects;
}
