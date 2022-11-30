import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class is responsible for reading data from a txt file that contains course information. The data is stripped and 
 * formatted with the use of several helper methods. Once the data is in ArrayList<String>, we create and connect to a MYSQL 
 * database for this course information. Several helper methods format insert, select, and create SQL queries we can execute 
 * after establishing the connection. We also have the ability to create course objects which can be easily passed to CCScheduleMaster 
 * for GUI display.
 * 
 */
public class DataReader {

	private static DataReader dr;
	private String fileName;
	private static ArrayList<String> CRN = new ArrayList<String>();
	private static ArrayList<String> Name = new ArrayList<String>();
	private static ArrayList<String> Waitlist = new ArrayList<String>();
	private static ArrayList<String> MinPoints = new ArrayList<String>();
	private static ArrayList<String> MidPointsFinal = new ArrayList<String>();
	private static ArrayList<String> Department = new ArrayList<String>();
	private static ArrayList<String> Term = new ArrayList<String>();
	private static String fileTerm;
	public static final String PORT_NUMBER = "8889";
	private Statement stmt;
	private Statement stmt2;
	private Statement stmt3;
	private Connection conn1;
	private Connection conn2;
	private Connection conn3;

	private DataReader() throws SQLException {
		initialSet();
		secondSet();
	}

	/**
	 * Singleton pattern method to retrieve the instance of DataReader
	 * @return DataReader instance
	 */
	public static DataReader getHandle() throws SQLException{
		if (dr == null) 
		{
			dr = new DataReader();
		}
		return dr;
	}
	
	/**
	 * This method takes in a txt file name and uses if/else logic to sort the txt lines into appropriate ArrayLists
	 * @param fileName
	 */
	public void dataParser(String fileName){
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) 
			{
				if(line.contains("Course Point Distribution for") && (line.contains("Spring") || line.contains("Fall"))) {
					Term.add(line);
				}
				else if(line.contains("CRN:"))
				{
					CRN.add(line);
				}
				else if(line.contains("Department:")) 
				{
					Department.add(line);
				}
				else if(line.contains("Demand:")) 
				{
					Name.add(line);
				}
				else if(line.contains("Waitlisted:")) 
				{
					Waitlist.add(line);
				}
			}
			CRN = listCleaner(CRN, "CRN");
			Name = listCleaner(Name, "Name");
			MinPoints = listCleaner(Department, "Points");
			Department = listCleaner(Department, "Department");
			Term = listCleaner(Term, "Course Point Distribution for");
			fileTerm = Term.get(0);
			Waitlist = listCleaner(Waitlist, "Waitlist");
			MidPointsFinal = pointsHelper(MinPoints);
			databaseCreator(8889);
			tableMaker("CCCourses");
			popTable();
			listReset();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException f) {
			f.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is a helper method that removes unnecessary information from the now organized lines from the txt file
	 * @param list, depending on which of the previously generated ArrayLists, we'll be cleaning
	 * @param listName, 
	 * @return returns a cleaned ArrayList
	 */
	public ArrayList<String> listCleaner(ArrayList<String> list, String listName) {
		ArrayList<String> listToReturn = new ArrayList<>();
		String toCheck = "";
		switch(listName) {
		case "Course Point Distribution for":
			for(String s: list) 
			{
				String term = s.substring(s.indexOf("for")+4);
				term = term.strip();
				listToReturn.add(term);
			}
			return listToReturn;
		case "CRN":
			for(String s: list) 
			{
				String crn = s.substring(5,10);
				listToReturn.add(crn);
			}
			return listToReturn;
		case "Waitlist":
			for(String s: list) 
			{
				String waitlist = s.substring(18,20);
				listToReturn.add(waitlist);
			}
			return listToReturn;
		case "Name":
			for(String s: list) 
			{
				String [] temp = new String[2];
				temp = s.split("\\s{2,4}");
				String CourseName = temp[0];
				if(CourseName.contains("'")) 
				{
					String CourseNametemp = CourseName.replace("'", "");
					listToReturn.add(CourseNametemp);
				}
				else 
				{
					listToReturn.add(CourseName);
				}
			}
			return listToReturn;
		case "Points":
			for(String s: list) 
			{
				if(s.contains("Min")) 
				{
					String points = s.substring(s.lastIndexOf(":")+2);
					if(points == "") 
					{
						listToReturn.add("0");
					}
					else 
					{
						listToReturn.add(points);
					}
				}
				else 
				{
					listToReturn.add("0");
				}
			}
			return listToReturn;
		case "Department":
			for(String s: list) 
			{
				String crn = s.substring(12,s.length());
				String [] temp = new String[2];
				temp = crn.split("\\s{2,4}");
				String deptName = temp[0];
				if(deptName.contains("Econ")) 
				{
					deptName = "Economics & Business";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Chemistry")) 
				{
					deptName = "Chemistry & Biochemistry";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Comparative")) 
				{
					deptName = "Comparative Literature";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Environmental")) 
				{
					deptName = "Environmental Studies";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Feminist")) 
				{
					deptName = "Feminist & Gender Studies";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Film and Media")) 
				{
					deptName = "Film and Media Studies";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Film and New Media")) 
				{
					deptName = "Film and New Media Studies";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("German")) 
				{
					deptName = "Chinese, German, Italian, Japanese, and Russian";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Human")) 
				{
					deptName = "Human Biology & Kinesiology";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Math")) 
				{
					deptName = "Mathematics & Computer Science";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Oganismal")) 
				{
					deptName = "Organismal Biology & Ecology";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Race")) 
				{
					deptName = "Race, Ethnicity, and Migration Studies";
					listToReturn.add(deptName);
				}
				else if(deptName.contains("Spanish")) 
				{
					deptName = "Spanish & Portuguese";
					listToReturn.add(deptName);

				}
				else 
				{
					listToReturn.add(deptName);
				}
			}
			return listToReturn;
		default:
			return null;
		}
	}
	
	/**
	 * This is another helper method that helps to populate missing point information in the minPoints ArrayList
	 * @param minPoints ArrayList, 
	 * @return updated ArrayList with missing points filled in
	 */
	public ArrayList<String> pointsHelper(ArrayList<String> minPoints) {
		ArrayList<String> toReturn = new ArrayList<String>();
		for(String s: minPoints) 
		{
			if(s.isBlank()) 
			{
				String toAdd = "0";
				toReturn.add(toAdd);
			}
			else if(s.length()>3) 
			{
				String toAdd = s.substring(0,2);
				toReturn.add(toAdd);
			}
			else 
			{
				toReturn.add(s);
			}
		}
		return toReturn;
	}
	
	/**
	 * This method is used to create the Course List database.
	 * @param PORT_NUMBER
	 */
	public void databaseCreator(int PORT_NUMBER) {
		try  {
			String sql = "create database IF NOT EXISTS CCScheduleMaster";
			stmt.execute(sql);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This method sets the initial statement used to create the database
	 * @throws SQLException
	 */
	public void initialSet() throws SQLException {
		conn1 = DriverManager.getConnection("jdbc:mysql://localhost:" + PORT_NUMBER + "/?serverTimezone=UTC", 
				"root", "root");
		stmt = conn1.createStatement();
		databaseCreator(Integer.parseInt(PORT_NUMBER));
	}
	
	/**
	 * This method gets the statement from initialSet()
	 * @return statement from initialSet()
	 */
	public Statement initialGet() {
		return stmt;
	}

	/**
	 * Overridden DatabaseCreator() method, used to create a database for a CC student
	 * @param PORT_NUMBER
	 * @param ccid
	 * @throws SQLException
	 */
	public void databaseCreator(int PORT_NUMBER, String ccid) throws SQLException {
		String sql = "create database IF NOT EXISTS CC" + ccid;
		stmt.execute(sql);
		thirdSet(ccid);

	}
	
	/**
	 * This method is used to format a CREATE table_name SQL query and execute that query
	 * @param tableName
	 * @throws SQLException
	 */
	public void tableMaker(String tableName) throws SQLException {
		secondSet();
		stmt2 = secondGet();
		String sql = "CREATE TABLE IF NOT EXISTS "+ tableName + " (Term varchar(255), CRN varchar(255), Name varchar(255), Department varchar(255), MinPoints varchar(255), Waitlist varchar(255));";
		stmt2.execute(sql);
	}
	
	/**
	 * This method is used to format a INSERT INTO table_name SQL queries and execute that query
	 * @param tableName
	 * @throws SQLException
	 */
	public void insertToTable(String term, String crn, String Name, String department, String points, String waitlist) throws SQLException {
		if (stmt2 == null) {
			secondSet();
		}
		stmt2 = secondGet();
		String sql = "INSERT INTO CCCourses (Term, CRN, Name, Department, MinPoints, Waitlist) VALUES ('" + term + "', '" + crn + "', '" + Name + "', '" + department + "', '" + points + "', '" + waitlist + "');";
		stmt2.execute(sql);
	}
	
	/**
	 * This is a helper method to populate the term ArrayList
	 * @param toAdd
	 * @return the Term ArrayList
	 */
	public ArrayList<String> termHelper(String toAdd) {
		ArrayList<String>toReturn = new ArrayList<String>();
		for(int i=0; i < 767; i++) 
		{
			toReturn.add(toAdd);
		}
		return toReturn;
	}
	
	/**
	 * This method is used to clean older data sets with different formatting as a part of iteration 3
	 * @param demand
	 * @param limit
	 * @param waitlist
	 * @param minPoints
	 * @return MinPoints final list
	 */
	public ArrayList<String> dataHelper(ArrayList<String> demand, ArrayList<String> limit, ArrayList<String> waitlist, ArrayList<String> minPoints) {
		ArrayList<String> points = new ArrayList<>();
		for(int i=0; i < demand.size(); i++) 
		{
			int demand2 = Integer.parseInt(demand.get(i));
			int limit2 = Integer.parseInt(limit.get(i));
			int waitlist2 = Integer.parseInt(waitlist.get(i));
			int minPoints2 = Integer.parseInt(minPoints.get(i));
			if(limit2-demand2<0 && waitlist2==0 && minPoints2==-1) 
			{
				points.add("0");
			}
		}
		return points;
	}
	
	/**
	 * This method uses a loop to populate the newly made courses table with courses and calls the insertToTable() method to do this
	 * @throws SQLException
	 */
	public void popTable() throws SQLException  
	{
		String sql = "SELECT * FROM CCCourses where Term = '"+ fileTerm + "'";		ResultSet rs=  stmt2.executeQuery(sql);
		String alreadyExists = ""; 
		if(!rs.isBeforeFirst()){
			for(int i = 0; i<CRN.size();i++) 
			{
				String term = Term.get(i);
				String crn = CRN.get(i);
				String name = Name.get(i);
				String dept = Department.get(i);
				String points = MidPointsFinal.get(i);
				String waitlist = Waitlist.get(i);
				
				String Crn = crn;
				if (!alreadyExists.equals(crn)) {
					insertToTable(term, crn, name, dept, points, waitlist);
				}
				alreadyExists = crn;
			}
		}
	}
	
	/**
	 * This method is used by CCScheduleMaster to populate course information when searching by CRN
	 * @param crn #
	 * @return The course matching the given CRN
	 * @throws SQLException
	 */
	public Course getCourse(String crn) throws SQLException {
		stmt2 = secondGet();
		ArrayList<Course> courses = new ArrayList<Course>(); 
		String sql = "SELECT * FROM CCCourses WHERE CRN = '" + crn +"';";
		stmt2.execute(sql);
		ResultSet rs = stmt2.executeQuery(sql);
		while(rs.next()) 
		{
			String term = rs.getString("Term");
			String cRN = rs.getString("CRN");
			String name = rs.getString("Name");
			String dept = rs.getString("Department");
			String minPoints = rs.getString("MinPoints");
			String waitlist = rs.getString("Waitlist");
			Course course = new Course(term, cRN, name, dept, minPoints, waitlist);
			courses.add(course);
		}
		return courses.get(0);
	}

	/**
	 * Inserts the course data of a schedule into a table within the students individual database
	 * @param schedule - the schedule being recorded
	 * @param tableNumber - the number of the schedule this is, (i.e. 1 if it is the first one created)
	 */
	public void insertStudentTable(Schedule schedule, int tableNumber) {
		try {
			ArrayList<Course> courses = schedule.getCourses();
			for (Course course:courses)
			{
				String sql = "INSERT INTO schedule"+tableNumber+" (Block, CRN, CourseName, Points) VALUES ('" + course.getBlock()+ "', '" + course.getCRN() + "', '" +course.getName()+ "', '" +course.getPoints()+"');";
				stmt3.execute(sql);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Makes an empty schedule table in the students individual database
	 * @param id
	 * @throws SQLException
	 */
	public void makeStudentTable(String id) throws SQLException  
	{
		int numTables = countStudentTables(id);
		int thisTable = numTables + 1;
		try {
			//Create next student schedule in database
			String sql = "CREATE TABLE schedule"+ thisTable + " (Block varchar(255), CRN varchar(255), CourseName varchar(255), Points varchar(255));";
			stmt3.execute(sql);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method counts all the schedules a student has made
	 * @param id
	 * @return # of student schedules
	 */
	public int countStudentTables(String id) {
		try {
			//Find number of tables in student database
			String sql = "SELECT count(*) AS TOTALNUMBEROFTABLES FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'CC"+id+ "';";
			ResultSet rs = stmt3.executeQuery(sql);
			int numTables = 0;
			while (rs.next())
			{
				numTables = rs.getInt("TOTALNUMBEROFTABLES");
			}
			return numTables;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * This method is used to retrieve past student schedules once their database has been created.
	 * @param ccID
	 * @param versionNum
	 * @throws SQLException
	 */
	public ArrayList<String> getSchedule(String ccID, String versionNum) throws SQLException {
		ArrayList<String> toshow = new ArrayList<String>(); 

		String sql = "SELECT CRN, CourseName, Points FROM schedule"+versionNum;
		ResultSet rs = stmt3.executeQuery(sql);
		while(rs.next()) 
		{
			String Crn= rs.getString("CRN");
			toshow.add(Crn);
			String name= rs.getString("CourseName");
			toshow.add(name);
			String points = rs.getString("Points");
			toshow.add(points);

		}
		return toshow;
	}

	/**
	 * This method is called when a general search for classes is performed it creates course objects from the lines 
	 * returned by the SQL query that are then added to an ArrayList<Course>.
	 * @return all courses
	 * @throws SQLException
	 */
	public ArrayList<Course> searchClasses() throws SQLException {
		secondSet();
		ArrayList<Course> courses = new ArrayList<Course>(); 
		String sql = "SELECT * FROM CCCourses";
		ResultSet rs = secondGet().executeQuery(sql);
		while(rs.next()) 
		{
			String term = rs.getString("Term");
			String cRN = rs.getString("CRN");
			String name = rs.getString("Name");
			String dept = rs.getString("Department");
			String minPoints = rs.getString("MinPoints");
			String waitlist = rs.getString("Waitlist");
			Course course = new Course(term, cRN, name, dept, minPoints, waitlist);
			courses.add(course);

		}
		return courses;
	}
	
	/**
	 * This method is used to create the second connection once the CC course database has been created so tables can now be made
	 * @throws SQLException
	 */
	public void secondSet() throws SQLException {
		conn2 = DriverManager.getConnection(
				"jdbc:mysql://localhost:" + PORT_NUMBER + "/CCScheduleMaster?user=root&password=root&serverTimezone=UTC"); // MySQL
		stmt2 = conn2.createStatement();
	}
	
	/**
	 * This method returns the second statement created
	 * @return the second Statement
	 */
	public Statement secondGet() {
		return stmt2;
	}
	
	/**
	 * This method is used to create the third connection once the IndividualStudent database has been created so tables can now be made
	 * @param ccID
	 * @throws SQLException
	 */
	public void thirdSet(String ccID) throws SQLException {
		conn3 = DriverManager.getConnection(
				"jdbc:mysql://localhost:" + PORT_NUMBER + "/CC"+ ccID +"?user=root&password=root&serverTimezone=UTC"); // MySQL
		stmt3 = conn3.createStatement();
	}
	
	/**
	 * Clears the lists of course information used to populate the CCCourses table in the ScheduleMaster database
	 * 
	 */
	public void listReset() {
		CRN.clear();
		Name.clear();
		Department.clear();
		MinPoints.clear();
		Waitlist.clear();
		MidPointsFinal.clear();
		Term.clear();
	}

	/**
	 * Reads in all the data, called in CCScheduleMaster
	 */
	public void readData() {
		try {
			initialSet();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		dataParser("testdata.txt");
	}
	
}
