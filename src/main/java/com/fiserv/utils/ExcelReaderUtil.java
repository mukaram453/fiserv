package com.fiserv.utils;

import java.io.File;

import javax.swing.JPanel;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.fiserv.model.SETF;
import com.fiserv.tests.TestRunner;

public class ExcelReaderUtil {

	static final char QUOTES = '"';
	public static SETF setf = new SETF();
	public static Connection connection;
	public static Recordset recordset;
	final JPanel panel = new JPanel();
	String file;

	public ExcelReaderUtil(String file) throws Exception {
		this.file = file;
		Fillo fillo = new Fillo();
		try {
			connection = fillo.getConnection(file);
		} catch (FilloException e) {
			TestRunner.line = "Could not open excel file " + new File(file).getAbsolutePath();
			System.err.println(TestRunner.line);
			TestRunner.line = e.getMessage();
			System.err.println(TestRunner.line);
			throw new Exception();
		}
	}

	public Recordset readSheet(String sheet) throws Exception {
		if (recordset != null) {
			return recordset;
		}
		try {
			String query = "Select * From %s";
			recordset = connection.executeQuery(String.format(query, QUOTES + sheet + QUOTES)).where("Processed='N'");
			setf.setSheet(sheet);
			recordset.moveFirst();
		} catch (Exception e) {
			TestRunner.line = "Could not read sheet " + sheet;
			System.err.println(TestRunner.line);
			System.err.println(TestRunner.line);
			TestRunner.line = e.getMessage();
			System.err.println(TestRunner.line);
			throw new Exception();
		}
		return recordset;
	}

	public static void updateSheet(String status) {
		String updateQuery = String.format(
				"Update %s Set Processed='%s' where Social1='%s' and Social2='%s' and Social3='%s'",
				QUOTES + setf.getSheet() + QUOTES, status, setf.getSsn1(), setf.getSsn2(), setf.getSsn3());
		try {
			connection.executeUpdate(updateQuery);
		} catch (FilloException e) {
			TestRunner.line = "Cannot update record.....";
			System.out.println(TestRunner.line);
		}
	}

	public static void closeExcel() {
		try {
			if (recordset != null) {
				recordset.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {

		}
	}

	public static void moveToNextRow() {
		setf.reset();
		try {
			recordset.next();
		} catch (FilloException e) {
			TestRunner.line = "Error move to next record..... ";
			System.out.println(TestRunner.line);
			System.err.println(TestRunner.line);
			TestRunner.line = e.getMessage();
			System.err.println(TestRunner.line);
		}

	}

	public static SETF setFields() {
		try {
			setf.setNewUsed(recordset.getField("NewUsed"));
			setf.setDealerNumber(recordset.getField("DealerNum"));
			setf.setfName(recordset.getField("First"));
			setf.setlName(recordset.getField("Last"));
			setf.setSsn1(recordset.getField("Social1"));
			setf.setSsn2(recordset.getField("Social2"));
			setf.setSsn3(recordset.getField("Social3"));
			setf.setDob1(recordset.getField("DOBmm"));
			setf.setDob2(recordset.getField("DOBdd"));
			setf.setDob3(recordset.getField("DOByyyy"));
			setf.setStreetNum(recordset.getField("StreetNum"));
			setf.setStreetName(recordset.getField("StreetName"));
			setf.setStreetType(recordset.getField("StreetType"));
			setf.setApt(recordset.getField("Apt"));
			setf.setCity(recordset.getField("City"));
			setf.setState(recordset.getField("State"));
			setf.setZipCode(recordset.getField("Zip"));
			setf.setEmployer(recordset.getField("EmplBy"));
			setf.setYear(recordset.getField("VehYr"));
			setf.setMake(recordset.getField("VehMake"));
			setf.setModel(recordset.getField("VehModel"));
			setf.setInvoice(recordset.getField("Invoice"));
			setf.setMsrp(recordset.getField("MSRP"));
		} catch (FilloException e) {
			System.out.println("Error setting records..... ");
			System.err.println(TestRunner.line);
			TestRunner.line = e.getMessage();
			System.err.println(TestRunner.line);
		}
		return setf;
	}
}
