import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class is used to represent students as they interact with the program
 * It is used to create new student profiles in a unique database
 * 
 */
public class Student {
	
	private String ccID;
	
	/**
	 * Constructor for the class
	 * @param ccID
	 */
	public Student(String ccID) {
		this.ccID = ccID;
	}
	
	/**
	 * This method creates the student database with the relevant CC student ID number
	 * @throws SQLException
	 */
	public void createStudentDB() throws SQLException {
		DataReader dr = DataReader.getHandle();
		dr.databaseCreator(8889);
		dr.databaseCreator(8889, ccID);
	}

}
