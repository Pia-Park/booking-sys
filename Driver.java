package ca.booking.system;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

import ca.booking.db.connection.DbConstants;

public class Driver {
	
	private static final String ALL_ST_QUERY = "SELECT * FROM STUDENT";
	private static final String ALL_T_QUERY = "SELECT * FROM TEACHER";
	private static final String ALL_C_QUERY = "SELECT * FROM COURSE";
	private static final String ALL_R_QUERY = "SELECT * FROM RESERVATION";
	private static final String ADD_R_QUERY = "INSERT INTO RESERVATION(r_date, course_id, student_id) VALUES (?,?,?)";
	private static final String ADD_S_QUERY = "INSERT INTO STUDENT(student_fname, student_lname, student_pnum, student_email) VALUES (?,?,?,?)";
	private static final String ADD_T_QUERY = "INSERT INTO TEACHER(teacher_fname, teacher_lname, teacher_pnum, teacher_email) VALUES (?,?,?,?)";
	private static final String ADD_C_QUERY = "INSERT INTO COURSE(course_name, teacher_id) VALUES (?,?)";
	private static final String UPDATE_R_QUERY = 
			"UPDATE RESERVATION SET r_date = ? WHERE course_id = ?";
	private static final String JOIN_R_QUERY = "SELECT s.student_fname, s.student_lname, c.course_name, s.student_id, r.course_id, r.r_date\n"
			+ "FROM STUDENT s \n"
			+ "INNER JOIN RESERVATION r\n"
			+ "ON r.student_id = s.student_id\n"
			+ "INNER JOIN COURSE c\n"
			+ "ON r.course_id = c.course_id;";
	
	public static Connection getConnection() throws SQLException {		
		Connection conn = DriverManager.
				getConnection(DbConstants.CONN_STRING, DbConstants.USER, DbConstants.PASSWORD);
		return conn;		
	}
	
	public static ResultSet getDB(Connection conn, String query) throws SQLException  {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs;		
	}
		
	public static void addStudent(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		PreparedStatement prestmt = null;
		prestmt = conn.prepareStatement(ADD_S_QUERY);
		
		System.out.println("Enter the first name of student: ");
		String fname = input.nextLine();
		prestmt.setString(1, fname);
		
		System.out.println("Enter the last name of student: ");
		String lname = input.nextLine();
		prestmt.setString(2, lname);
		
		System.out.println("Enter the phone number: ");
		String pnum = input.nextLine();
		prestmt.setString(3, pnum);
		
		System.out.println("Enter the email: ");
		String email = input.nextLine();
		prestmt.setString(4, email);
		
		prestmt.executeUpdate();
		System.out.println(fname + " " + lname + "has been added as a student.");
	}
	
	
	public static void addTeacher(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		PreparedStatement prestmt = null;
		prestmt = conn.prepareStatement(ADD_T_QUERY);
		
		System.out.println("Enter the first name of teacher: ");
		String fname = input.nextLine();
		prestmt.setString(1, fname);
		
		System.out.println("Enter the last name of teacher: ");
		String lname = input.nextLine();
		prestmt.setString(2, lname);
		
		System.out.println("Enter the phone number: ");
		String pnum = input.nextLine();
		prestmt.setString(3, pnum);
		
		System.out.println("Enter the email: ");
		String email = input.nextLine();
		prestmt.setString(4, email);
		
		prestmt.executeUpdate();
		System.out.println(fname + " " + lname + "has been added as a teacher.");

	}
	
	public static void addCourse(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		PreparedStatement prestmt = null;
		prestmt = conn.prepareStatement(ADD_C_QUERY);
		
		System.out.println("Enter the course name: ");
		String cName = input.nextLine();
		prestmt.setString(1, cName);
		
		System.out.println("Enter the ID of teacher: ");
		int tID = input.nextInt();
		prestmt.setInt(2, tID);
			
		prestmt.executeUpdate();
		System.out.println(cName + " has been added as a course.");

	}
	
	public static void makeReservation(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		PreparedStatement prestmt = null;
		prestmt = conn.prepareStatement(ADD_R_QUERY);
		
		System.out.println("Enter the date you want to reservation: ");
		String date = input.nextLine();
		prestmt.setDate(1, Date.valueOf(date));
		
		System.out.println("Enter the ID of course: ");
		int cID = input.nextInt();
		prestmt.setInt(2, cID);
		
		System.out.println("Enter the ID of student: ");
		int sID = input.nextInt();
		prestmt.setInt(3, sID);
		
		prestmt.executeUpdate();
		
		ResultSet rs = getDB(conn, ALL_ST_QUERY);
		
		while(rs.next()) {
			if(sID == rs.getInt("student_id")) {
				System.out.println("Reservation Student name: " + rs.getString("student_fname") + " " + rs.getString("student_lname"));
				
			}
		}
		
		ResultSet rs1 = getDB(conn, ALL_C_QUERY);
		
		while(rs1.next()) {
			if(cID == rs1.getInt("course_id")) {
				System.out.println("Reservation course Name: " + rs1.getString("course_name"));
			}
		}		

	}
	
	public static void printCourseReservation(Connection conn) throws SQLException {
		
		Scanner input = new Scanner(System.in);
		PreparedStatement prestmt = null;
		prestmt = conn.prepareStatement(ALL_R_QUERY);
		
		System.out.println("Enter the course ID that you want to see: ");
		int cID = input.nextInt();
		
		ResultSet rs = prestmt.executeQuery(JOIN_R_QUERY);
				
		while(rs.next()) {
			if(cID == rs.getInt("course_id")) {
				System.out.println("Reservation Course: " + rs.getString("course_name") + "\nReservation date: " + rs.getString("r_date") + 
						"\nReservation Student name: " + rs.getString("student_fname") + " " + rs.getString("student_lname"));
			}
		}
						
	}

	public static void main(String[] args) throws SQLException {
		
		Scanner input = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = getConnection();
			
//			addStudent(conn);
//			addTeacher(conn);
//			addCourse(conn);
//			makeReservation(conn);
			printCourseReservation(conn);
			
//			System.out.println("List of 0000");
//			ResultSet rs = getDB(conn, ALL_C_QUERY);
//			while(rs.next()) {
//				System.out.println("Course name: " + rs.getString("course_name") + "\nteacher ID: " + rs.getString("teacher_id"));
//			}
			
//			System.out.println("--------------------------------");
//			System.out.println("1.Reservation\n2.add a student\n3.add a teacher\n4.add a course\n5.quit");
//			System.out.println("--------------------------------");
//			int answer = input.nextInt();
//			
//			while(true) {
//				
//				switch (answer) {
//				case 1:
//					makeReservation(conn);
//					ResultSet rs = getDB(conn, ALL_R_QUERY);
//					System.out.println("Reservation date: " + rs.getString("r_date") + "\nCourse ID: " + rs.getString("course_id"));
//					break;
//				case 2:
//					addStudent(conn);
//					break;
//
//				default:
//					break;
//				}
//				
//			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {	
		
		if(stmt != null) {
			stmt.close();
		}				
		if(conn != null) {
			conn.close();
		}								
	}

		
		
		

	}

}