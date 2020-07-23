package org.HotelRoomBooking;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

public class BddOutils {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hotel";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	private static IDataSet readDataSet(String filename) throws Exception {
		return new FlatXmlDataSetBuilder().build(new File(filename));
	}

	public static void insertData(String path_to_file) throws Exception {
		IDataSet dataset = readDataSet(path_to_file);
		IDatabaseTester databaseTester = new JdbcDatabaseTester(DRIVER, JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataset);
		databaseTester.onSetup();
	}

	public static void deleteAllData(String path_to_file) throws Exception {
		IDataSet dataset = readDataSet(path_to_file);
		IDatabaseTester databaseTester = new JdbcDatabaseTester(DRIVER, JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
		databaseTester.setDataSet(dataset);
		databaseTester.onSetup();
	}

	public static void compareData(String table, String path_to_file, String... col) throws SQLException, Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(DRIVER, JDBC_URL, USER, PASSWORD);
		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable(table);
		IDataSet expectedDataSet = readDataSet(path_to_file);
		ITable expectedTable = expectedDataSet.getTable(table);
		String tab[] = col;
		Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, tab);
	}

	public static IDataSet dateUpdate(String path_to_file) throws Exception {
		IDataSet data = readDataSet(path_to_file);
		Calendar date = new GregorianCalendar();
		ReplacementDataSet rData = new ReplacementDataSet(data);
		rData.addReplacementObject("[start_date]", date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1)
				+ "-" + date.get(Calendar.DAY_OF_MONTH) + " 12:00:00");
		Calendar end_date = date;
		end_date.add(Calendar.DAY_OF_MONTH, 1);
		rData.addReplacementObject("[end_date]", end_date.get(Calendar.YEAR) + "-" + (end_date.get(Calendar.MONTH) + 1)
				+ "-" + end_date.get(Calendar.DAY_OF_MONTH) + " 12:00:00");
		return rData;
	}
	
	public static void insertDataCurrentDate(String path_to_file) throws Exception {
		IDataSet dataset = dateUpdate(path_to_file);
		IDatabaseTester databaseTester = new JdbcDatabaseTester(DRIVER, JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataset);
		databaseTester.onSetup();
	}
}