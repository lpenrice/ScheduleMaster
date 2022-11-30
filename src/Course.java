import java.util.regex.Pattern;

/**
 * This is the class for a Course object. 
 *
 */
public class Course {
	
	private String block;
	private String term;
	private String crn;
	private String name;
	private String department;
	private String minPoints;
	private String waitlist;
	private int pointsAssigned;
	
	/**
	 * This is the constructor for a course and takes in all the information about a course.
	 * @param term - term it was taught
	 * @param crn - course registration number
	 * @param name - course name
	 * @param department course is in
	 * @param minPoints - needed to get in
	 * @param waitlist - students on the waitlist
	 */
	public Course(String term, String crn, String name, String department, String minPoints, String waitlist) {
		this.term = term;
		this.crn = crn;
		this.name = name;
		this.department = department;
		this.minPoints = minPoints;
		this.waitlist = waitlist;
	}
	
	public String getCRN() {
		return crn;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		String strippedName = name.strip();
		if(strippedName.length() > 46) 
		{
			strippedName = strippedName.substring(0,46) + "...";
		}
		String strippedDept = department.strip();
		return String.format("%-21s", term) + String.format("%-13s", crn) + String.format("%-50s", strippedName) + 
				"\t\t  " + String.format("%-53s", strippedDept) + String.format("%-40s", minPoints) + waitlist + "\n";
	}

	public void setPoints(int points) {
		pointsAssigned = points;
	}
	
	public int getPoints() {
		return pointsAssigned;
	}
	
	public String getMinPoints() {
		if(minPoints.contains("\t")) {
			minPoints = minPoints.substring(0,minPoints.indexOf("\t"));
		}
		return minPoints;
	}

	public String getDepartment() {
		return department;
	}
	
	public String getTerm() {
		return term;
	}
	
	public String getWaitlist() {
		return waitlist;
	}

	public String getBlock() {
		return block;
	}
	
	public void setBlock(String block) {
		this.block = block;
	}

}
