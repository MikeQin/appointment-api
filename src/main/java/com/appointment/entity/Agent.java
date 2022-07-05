package com.appointment.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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
	@OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Appointment> appointments;
	
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
		if (source.getAppointments() != null && !source.getAppointments().isEmpty()) target.setAppointments(source.getAppointments());
		
	}
}
