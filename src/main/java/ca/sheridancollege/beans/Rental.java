package ca.sheridancollege.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import ca.sheridancollege.enums.RentalState;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity

@NamedQuery(name="Rental.byID", query="from Rental where id=:id")
@NamedQuery(name="Rental.all", query="from Rental")
@NamedQuery(name="Rental.active", query="from Rental where returnedDate is null")
@NamedQuery(name="Rental.archive", query="from Rental where returnedDate is not null")
public class Rental implements Serializable {
	
	@Id
	@GeneratedValue
	private int id;
	private LocalDate signOutDate;
	private LocalDate dueDate;
	private LocalDate returnedDate;
	@ManyToOne
	@JoinColumn(name="CUSTOMER_ID")
	private Customer customer;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Bike bike;
	private String comment;
	
	
	public Rental(LocalDate signOutDate, LocalDate dueDate, LocalDate returnedDate, Customer customer, Bike bike, String comment) {
		this.signOutDate = signOutDate;
		this.dueDate = dueDate;
		this.returnedDate = returnedDate;
		this.customer = customer;
		this.bike = bike;
		this.comment = comment;
	}
	
	public String getRentalState() {
		// all rentals should have signOutDate and dueDate
		if(this.signOutDate != null && this.dueDate != null) {
			if(this.returnedDate == null) {
				return (this.dueDate.isAfter(LocalDate.now())) ? "Active" : "Late";
			} else {
				return (this.returnedDate.isBefore(this.dueDate)) ? "Returned" : "Returned Late";
			}
		}
		return "Invalid";
	}
}
