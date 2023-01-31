package com.example.BankAcc.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.Length;



@Entity
@Indexed
@Table(name = "cardtbl1")
public class Cardtbl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	 @Field  
	@Column(name = "name")
	@Pattern(regexp="^[a-zA-Z]*", message = "please enter alphabate")
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	 @Field  
	 @Column(name = "gender")
	 @NotBlank(message = "choose the gender")
	private String gender;
	 
	 @Field  
	 @Column(name = "dob")
	 @NotBlank(message = "choose the date")	 
	 private String dob;
	
	 @Field  
	 @Column(name = "address")
	 @NotBlank(message = "Address is mandatory")
	private String address;
	
	
	 @Field  
	 @Column(name = "cardno")
	 @Pattern(regexp="^[0-9]*", message = "please enter digit")
	 @NotBlank(message = "uniquenumber is mandatory")
	 @Length(min = 16,max = 16, message = "please enter 16 digit number")
	private String cardno;
	 
	 @Field  
	 @Column(name = "profession")
	 @NotBlank(message = "profession is mandatory")
	private String profession;
	 
	 @Field  
	 @Column(name = "citizenship")
	 @NotBlank(message = "citizenship is mandatory")
	private String citizenship;
	 
	 @Field  
	 @Column(name = "accno")	 
	 private String accno;
	 
	 @Field  
	 @Column(name = "openingbalance")
	 @Pattern(regexp="^[0-9]+(.[0-9]{1,2})?%?", message = "please enter digit")
	 @NotBlank(message = "opening balance is mandatory")
		private String openingbalance;
	 
	 @Column(name = "status")
	 private long status;
	
	
	 
	 
	 //counstocutor
	public Cardtbl() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	//parameterize counstocutor
	public Cardtbl(long id, String name, String gender, String dob, String address, String cardno, String profession, String citizenship, String accno, String openingbalance ,long status) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
		this.cardno = cardno;
		this.profession = profession;
		this.citizenship = citizenship;
		this.accno = accno;
		this.openingbalance = openingbalance;
		this.status = status;
	}




	//getter setter method
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getOpeningbalance() {
		return openingbalance;
	}

	public void setOpeningbalance(String openingbalance) {
		this.openingbalance = openingbalance;
	}
	
	

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	//to string method
	@Override
	public String toString() {
		return "Cardtbl [id=" + id + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", address=" + address
				+ ", cardno=" + cardno + ", profession=" + profession + ", citizenship=" + citizenship + ", accno="
				+ accno + ", openingbalance=" + openingbalance + ", status=" + status + "]";
	}

	


	

	
}
