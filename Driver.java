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
	private static final String DELETE_R_QUERY = "DELETE FROM RESERVATION WHERE student_id = ? AND r_id = ?";
	private static final String UPDATE_R_QUERY = 
			"UPDATE RESERVATION SET r_date = ? WHERE course_id = ?";
	private static final String JOIN_R_QUERY = "SELECT s.student_fname, s.student_lname, c.course_name, s.student_id, r.course_id, r.r_date, r.r_id\n"
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
		System.out.println("*｡ ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ ˖° ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ *｡");
		System.out.println(fname + " " + lname + " has been added as a student.");
		System.out.println("*｡ ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ ˖° ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ *｡");
		System.out.println("-----------** Student List **------------");
		
		ResultSet rs = prestmt.executeQuery(ALL_ST_QUERY);
		while(rs.next()) {
			System.out.println("=========================================");
			System.out.println("Student ID: " + rs.getInt("student_id") + "\nStudent Name: " + rs.getString("student_fname") + " " + rs.getString("student_lname"));
			System.out.println("=========================================");
		}
		

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
		System.out.println("*｡ ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ ˖° ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ *｡");
		System.out.println(fname + " " + lname + " has been added as a teacher.");
		System.out.println("*｡ ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ ˖° ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ *｡");
		System.out.println("-----------** Teacher List **------------");

		ResultSet rs = prestmt.executeQuery(ALL_T_QUERY);
		while(rs.next()) {
			System.out.println("=========================================");
			System.out.println("Teacher ID: " + rs.getInt("teacher_id") + "\nTeacher Name: " + rs.getString("teacher_fname") + " " + rs.getString("teacher_lname"));
			System.out.println("=========================================");
		}

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
		System.out.println("*｡ ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ ˖° ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ *｡");
		System.out.println(cName + " has been added as a course.");
		System.out.println("*｡ ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ ˖° ☾ ⋆⁺₊⋆ ♡̷̷̷ ‧₊˚ ♡ *｡");
		System.out.println("------------** Course List **------------");
		
		ResultSet rs = prestmt.executeQuery(ALL_C_QUERY);
		while(rs.next()) {
			System.out.println("=========================================");
			System.out.println("Course ID: " + rs.getInt("course_id") + "\nCourse Name: " + rs.getString("course_name"));
			System.out.println("=========================================");
		}

	}
	
	public static int howMany(Connection conn, String date, int courseID) throws SQLException {
		Scanner input = new Scanner(System.in);
		PreparedStatement prestmt = null;
		prestmt = conn.prepareStatement(ALL_R_QUERY);
		ResultSet rs = prestmt.executeQuery();
		int count = 0;
		while(rs.next()) {
			if(rs.getDate("r_date").equals(Date.valueOf(date)) && rs.getInt("course_id") == courseID) {
				count += 1;
			}
		}
		return count;
	
	}
	
	public static void makeReservation(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		try {
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
			
			if(howMany(conn, date, cID) == 6) {
				System.out.println("(｡•́ - •̀｡)  (｡•́ - •̀｡)  (｡•́ - •̀｡)  (｡•́ - •̀｡) ");
				System.out.println("    This course is already full. \n    You can not make a reservation.");
				System.out.println("(｡•́ - •̀｡)  (｡•́ - •̀｡)  (｡•́ - •̀｡)  (｡•́ - •̀｡) ");

			} else {
				prestmt.executeUpdate();
				
				ResultSet rs = getDB(conn, JOIN_R_QUERY);
				System.out.println("-------** Reservation Successful**-------");
				System.out.println("----------** Resevation List**-----------");
				while(rs.next()) {
					if(sID == rs.getInt("student_id")) {
						System.out.println("=========================================\nReservation Number: " + rs.getInt("r_id") + "\nReservation course name: " + rs.getString("course_name") + "\nReservation date: " + rs.getString("r_date") + 
								"\nReservation Student name: " + rs.getString("student_fname") + " " + rs.getString("student_lname")+"\n=========================================");				
					}
				}		
				
			}			
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public static void printCourseReservation(Connection conn) throws SQLException {
		
		Scanner input = new Scanner(System.in);
		try {
			PreparedStatement prestmt = null;
			prestmt = conn.prepareStatement(ALL_R_QUERY);
			
			System.out.println("Enter the course ID that you want to see: ");
			int cID = input.nextInt();
			System.out.println("------------** Course List**-------------");
			ResultSet rs = prestmt.executeQuery(JOIN_R_QUERY);
			Boolean nothing = false;
			while(rs.next()) {
				if(cID == rs.getInt("course_id")) {
					System.out.println("=========================================\nReservation Number: " + rs.getInt("r_id") + "\nReservation Course: " + rs.getString("course_name") + "\nReservation date: " + rs.getString("r_date") + 
							"\nReservation Student name: " + rs.getString("student_fname") + " " + rs.getString("student_lname") + "\n=========================================");
					nothing = true;
				}
			}	
			if(nothing == false) {
				System.out.println("** This course doesn't have any reservation. **");
				teacherMenu(conn);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
		
	public static void printStudentReservation(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		try {
			PreparedStatement prestmt = null;
			prestmt = conn.prepareStatement(ALL_R_QUERY);
			System.out.println("Enter the your Student ID: ");
			int sID = input.nextInt();
			System.out.println("----------** Resevation List**-----------");
			ResultSet rs = prestmt.executeQuery(JOIN_R_QUERY);
			Boolean nothing = false;
			while(rs.next()) {
				if(sID == rs.getInt("student_id")) {
					System.out.println("=========================================\nReservation Number: " +rs.getInt("r_id")+ "\nReservation Course: " + rs.getString("course_name") + "\nReservation date: " + rs.getString("r_date") + 
							"\nReservation Student name: " + rs.getString("student_fname") + " " + rs.getString("student_lname")+"\n=========================================");
					nothing = true;
				} 
			} 
			if(nothing == false) {
				System.out.println("**** You don't have any reservation ****");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	public static void printStudentReservation1(Connection conn, int sID) throws SQLException {
		Scanner input = new Scanner(System.in);
		try {
			PreparedStatement prestmt = null;
			prestmt = conn.prepareStatement(ALL_R_QUERY);
			
			ResultSet rs = prestmt.executeQuery(JOIN_R_QUERY);
			Boolean nothing = false;
			while(rs.next()) {
				if(sID == rs.getInt("student_id")) {
					System.out.println("=========================================\nReservation Number: " +rs.getInt("r_id")+ "\nReservation Course: " + rs.getString("course_name") + "\nReservation date: " + rs.getString("r_date") + 
							"\nReservation Student name: " + rs.getString("student_fname") + " " + rs.getString("student_lname")+"\n=========================================");
					nothing = true;
				} 
			} 
			if(nothing == false) {
				System.out.println("**** You don't have any reservation ****");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
		
	
	public static void cancelReservation(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		try {
			System.out.println("Enter the your student ID: ");
			int sID = input.nextInt();
			System.out.println("---** Here is your Reservation List **---");
			printStudentReservation1(conn, sID);
			System.out.println("Enter the Reservation Number that you want to cancel: ");
			int rId = input.nextInt();
			PreparedStatement prestmt = null;
			prestmt = conn.prepareStatement(DELETE_R_QUERY);
			prestmt.setInt(1, sID);
			prestmt.setInt(2, rId);
			prestmt.executeUpdate();
			
			ResultSet rs = prestmt.executeQuery(JOIN_R_QUERY);
			System.out.println("----------** Cancel Successful**---------");
			System.out.println("----------** Resevation List**-----------");
			Boolean nothing = false;
			while(rs.next()) {
				if(sID == rs.getInt("student_id")) {
					System.out.println("=========================================\nReservation Number: " +rs.getInt("r_id")+ "\nReservation Course: " + rs.getString("course_name") + "\nReservation date: " + rs.getString("r_date") + 
							"\nReservation Student name: " + rs.getString("student_fname") + " " + rs.getString("student_lname")+"\n=========================================");
					nothing = true;
				} 
			} 
			if(nothing == false) {
				System.out.println("**** You don't have any reservation ****");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
		
	public static void findTeacher(Connection conn) throws SQLException{
		Scanner input = new Scanner(System.in);
					
		try {
			PreparedStatement prestmt = null;
			prestmt = conn.prepareStatement(ALL_T_QUERY);			
			ResultSet rs = prestmt.executeQuery(ALL_T_QUERY);
			System.out.println("Enter the Teacher ID: ");
			int tID = input.nextInt();
			Boolean nothing = false;
			while(rs.next()) {
				if(tID == rs.getInt("teacher_id")) {
					System.out.println("ෆ˟̑*̑˚̑*̑˟̑ෆ.₊̣̇.ෆ˟̑*̑˚̑*̑˟̑ෆ.₊̣̇.ෆ˟̑*̑˚̑*̑˟̑ෆ.₊̣̇.ෆ˟̑*̑˚̑*̑˟̑ෆ");
					System.out.println("ʕ•ᴥ•ʔ нёllo, " + rs.getString("teacher_fname") + " " + rs.getString("teacher_lname") + " teacher ♡");
					nothing = true;
				} 
			} 
			if(nothing == false) {
				System.out.println("Wrong Information. Please try again.");
				findTeacher(conn);	
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}			
	}
			
	public static void findStudent(Connection conn) throws SQLException{
		Scanner input = new Scanner(System.in);
		
		try {
			PreparedStatement prestmt = null;
			prestmt = conn.prepareStatement(ALL_ST_QUERY);				
			
			ResultSet rs = prestmt.executeQuery(ALL_ST_QUERY);
			System.out.println("Enter the Student ID: ");
			int sID = input.nextInt();
			Boolean nothing = false;
			while(rs.next()) {
				if(sID == rs.getInt("student_id")) {
					System.out.println("ෆ˟̑*̑˚̑*̑˟̑ෆ.₊̣̇.ෆ˟̑*̑˚̑*̑˟̑ෆ.₊̣̇.ෆ˟̑*̑˚̑*̑˟̑ෆ.₊̣̇.ෆ˟̑*̑˚̑*̑˟̑ෆ");
					System.out.println("ʕ•ᴥ•ʔ нёllo, " + rs.getString("student_fname") + " " + rs.getString("student_lname") + ".");
					nothing = true;
				} 
			} 
			if(nothing == false) {
				System.out.println("Wrong Information. Please try again.");
				findStudent(conn);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}			
	}
	
	public static String answer() {
		Scanner input = new Scanner(System.in);
		System.out.println("☆..:*・☆.。.:*・°☆.。.:*・°☆.。.:*・°☆..: *");
		System.out.println("=========================================");
		System.out.println("\n ⎝⎛♥‿♥⎞⎠ Welcome to Cornerstone!⎝⎛♥‿♥⎞⎠\n");
		System.out.println("=========================================");
		System.out.println("☆..:*・☆.。.:*・°☆.。.:*・°☆.。.:*・°☆..: *");
		System.out.println("    If you are a teacher press 't', \n    if you are a student press 's': ");
		String answer = input.nextLine();
		
		while(!answer.equalsIgnoreCase("t") && !answer.equalsIgnoreCase("s")) {
			System.err.println("Wrong Information. Please try again.");
			System.out.println("Enter the 't' or 's'");
			answer = input.nextLine();
		} return answer;
	}
	
	public static void teacherMenu(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		
		System.out.println("================= MENU ==================");
		System.out.println("      1. Check the Reservation\n      2. Add a Student\n      3. Add a Teacher\n      4. Add a Course\n      5. Quit");
		System.out.println("=========================================");
		System.out.println("Choose your option in the menu: ");

		int option = input.nextInt();
								
			switch (option) {
			case 1:
				printCourseReservation(conn);
				teacherMenu(conn);
				break;
			case 2:
				addStudent(conn);
				teacherMenu(conn);
				break;
			case 3:
				addTeacher(conn);
				teacherMenu(conn);
				break;
			case 4:
				addCourse(conn);
				teacherMenu(conn);
				break;
			case 5:
				System.out.println("☆..:*・☆.。.:*・°☆.。.:*・°☆.。.:*・°☆..: *");
				System.out.println("　   ∧＿∧ \n"
						+ "   (｡•ㅅ•｡)    Good bye!\n"
						+ "   / つ(⌒⌒)   Have a nice day!\n"
						+ "   しー ＼／");
				System.out.println("☆..:*・☆.。.:*・°☆.。.:*・°☆.。.:*・°☆..: *");
				break;
			default:
				teacherMenu(conn);
				break;			
			} 		
	}
	
	public static void studentMenu(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		
		System.out.println("================= MENU ==================");
		System.out.println("      1. Make a Reservation\n      2. Check the reservation\n      3. Cancel the reservation\n      4. Quit");
		System.out.println("=========================================");
		System.out.println("Choose your option in the menu:");
		String numB = input.nextLine();
		switch (numB) {
		case "1":
			makeReservation(conn);
			studentMenu(conn);
			break;
		case "2":	
			printStudentReservation(conn);
			studentMenu(conn);
			break;
		case "3":
			cancelReservation(conn);
			studentMenu(conn);
			break;
		case "4":
			System.out.println("☆..:*・☆.。.:*・°☆.。.:*・°☆.。.:*・°☆..: *");
			System.out.println("　   ∧＿∧ \n"
					+ "   (｡•ㅅ•｡)    Good bye!\n"
					+ "   / つ(⌒⌒)   Have a nice day!\n"
					+ "   しー ＼／");
			System.out.println("☆..:*・☆.。.:*・°☆.。.:*・°☆.。.:*・°☆..: *");
			break;			
		default:
			studentMenu(conn);
			break;
		}
			


		
	}

	public static void main(String[] args) throws SQLException {
		
		Scanner input = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
				
		try {
			conn = getConnection();	
				String answer = answer();
				if(answer.equalsIgnoreCase("t")) {
					findTeacher(conn);
					System.out.println("How can I help you today?");
					teacherMenu(conn);					
				} else if(answer.equalsIgnoreCase("s")){
					findStudent(conn);
					System.out.println("How can I help you today?");
					studentMenu(conn);					
				} else {
					System.err.println("Wrong Information!");
				}							

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
