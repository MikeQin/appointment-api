package com.appointment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="AGENT")
public class Agent {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$")
	private String email;
	@Lob
	private byte[] profilePhoto;
	
	// relationship mapping
	// Since an agent might have too many appointments, 
	// we don't do OneToMany mapping here for better performance.
	// For example purpose, if we do mapping...
	// ------------------------------------------------------------
	// @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	// private List<Appointment> appointments;
	
	public String getName() {
		return this.firstName + " " + this.lastName;
	}
	
	public static void copy(Agent source, Agent target) {
		if (source == null || target == null) return;
		
		if (source.getId() > 0) target.setId(source.getId());
		if (!source.getFirstName().isBlank()) target.setFirstName(source.getFirstName());
		if (!source.getLastName().isBlank()) target.setLastName(source.getLastName());
		if (!source.getEmail().isBlank()) target.setEmail(source.getEmail());
		if (source.getProfilePhoto().length > 0) target.setProfilePhoto(source.getProfilePhoto());
	}
}
