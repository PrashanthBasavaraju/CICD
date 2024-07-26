package com.triconinfotech.WealthWise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class Role.
 */
@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "roles")
public class Role extends Auditable {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private Integer id;
    
    /** The name. */
    @NotNull(message = "Role Name is mandatory ")
	@Column(name = "type",unique = true)
	private String name;

}
