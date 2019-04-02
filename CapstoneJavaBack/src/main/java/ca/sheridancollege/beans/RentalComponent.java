package ca.sheridancollege.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class RentalComponent {
	@Id
	@GeneratedValue
	private int id;
	private String notes;
	
	public RentalComponent(String notes) {
		this.notes = notes;
	}
}
