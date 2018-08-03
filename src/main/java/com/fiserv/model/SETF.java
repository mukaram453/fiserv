package com.fiserv.model;

import java.util.ArrayList;
import java.util.List;

public class SETF {

	private String sheet;
	private String dealerNumber;
	private String ssn1;
	private String ssn2;
	private String ssn3;
	private String newUsed;
	private String dob1;
	private String dob2;
	private String dob3;
	private String fName;
	private String lName;
	private String streetNum;
	private String streetName;
	private String streetType;
	private String Apt;
	private String zipCode;
	private String city;
	private String state;
	private String years = "5";
	private String months = "4";
	private String addressStatus = "Rent";
	private String employer;
	private String yearsMonths = "5";
	private String year;
	private String make;
	private String model;
	private String msrp;
	private String invoice;
	private String bookValue = "1234";
	private List<String> errors = new ArrayList<String>();

	public String getfName() {
		return fName;
	}

	public String getDealerNumber() {
		return dealerNumber;
	}

	public void setDealerNumber(String dealerNumber) {
		this.dealerNumber = (dealerNumber == null || "".equals(dealerNumber)) ? "09159" : dealerNumber;
	}

	public String getSsn1() {
		return ssn1;
	}

	public void setSsn1(String ssn) {
		this.ssn1 = ssn;
	}

	public String getSsn2() {
		return ssn2;
	}

	public void setSsn2(String ssn) {
		this.ssn2 = ssn;
	}

	public String getSsn3() {
		return ssn3;
	}

	public void setSsn3(String ssn) {
		this.ssn3 = ssn;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getDob() {
		return getDob1() + getDob2() + getDob3();
	}

	public String getAddressLine1() {
		return getApt() + getStreetNum() + getStreetName() + getStreetType();
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getYears() {
		return years;
	}

	public String getMonths() {
		return months;
	}

	public String getAddressStatus() {
		return addressStatus;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMsrp() {
		return msrp;
	}

	public void setMsrp(String msrp) {
		this.msrp = msrp;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getBookValue() {
		return bookValue;
	}

	public String getDob2() {
		return dob2;
	}

	public void setDob2(String dob2) {
		this.dob2 = dob2;
	}

	public String getDob1() {
		return dob1;
	}

	public void setDob1(String dob1) {
		this.dob1 = dob1;
	}

	public String getDob3() {
		return dob3;
	}

	public void setDob3(String dob3) {
		this.dob3 = dob3;
	}

	public String getStreetType() {
		return streetType + "";
	}

	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}

	public String getStreetNum() {
		return streetNum + " ";
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public String getStreetName() {
		return streetName + " ";
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getNewUsed() {
		return newUsed;
	}

	public void setNewUsed(String newUsed) {
		this.newUsed = newUsed;
	}

	public String getSsn() {
		return getSsn1() + getSsn2() + getSsn3();
	}

	public String getYearsMonths() {
		return yearsMonths;
	}

	public String getSheet() {
		return sheet;
	}

	public void setSheet(String sheet) {
		this.sheet = sheet;
	}

	public String getApt() {
		return Apt + " ";
	}

	public void setApt(String apt) {
		this.Apt = apt.trim();
	}

	public void reset() {
		dealerNumber = null;
		ssn1 = null;
		ssn2 = null;
		ssn3 = null;
		newUsed = null;
		dob1 = null;
		dob2 = null;
		dob3 = null;
		fName = null;
		lName = null;
		streetNum = null;
		streetName = null;
		streetType = null;
		zipCode = null;
		city = null;
		state = null;
		employer = null;
		year = null;
		make = null;
		model = null;
		msrp = null;
		invoice = null;
		errors.clear();
	}

	@Override
	public String toString() {
		String setf = sheet + " [dealerNumber -> " + dealerNumber + ", ssn1 -> " + ssn1 + ", ssn2 -> " + ssn2
				+ ", ssn3 -> " + ssn3 + ", newUsed -> " + newUsed + ", dob1 -> " + dob1 + ", dob2 -> " + dob2
				+ ", dob3 -> " + dob3 + ", fName -> " + fName + ", lName -> " + lName + ", streetNum -> " + streetNum
				+ ", streetName -> " + streetName + ", streetType -> " + streetType + ", Apt -> " + Apt
				+ ", zipCode -> " + zipCode + ", city -> " + city + ", state -> " + state + ", years -> " + years
				+ ", months -> " + months + ", addressStatus -> " + addressStatus + ", employer -> " + employer
				+ ", yearsMonths -> " + yearsMonths + ", year -> " + year + ", make -> " + make + ", model -> " + model
				+ ", msrp -> " + msrp + ", invoice -> " + invoice + ", bookValue -> " + bookValue + "]";
		if (!errors.isEmpty())
			setf += "Errors -> " + errors;
		return setf;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors.addAll(errors);
	}

}
