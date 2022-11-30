import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class initializes and populates the list of courses.
 *
 */
public class CourseSet {

	private ArrayList<Course> courses;
	
	/**
	 * This is the constructor for a set of courses.
	 * @throws SQLException if there is an issue with the database
	 */
	public CourseSet() throws SQLException {
		DataReader dr = DataReader.getHandle();
		this.courses = dr.searchClasses();
	}
	
	public ArrayList<Course> getCourses(){
		return courses;
	}
	
	public ArrayList<Course> getCoursesByDept(String filter) throws Exception {
		ArrayList<Course> filteredCourses = new ArrayList<Course>();
		String keyword = filter.toLowerCase();
		String keyword2 = keyword.substring(0,1).toUpperCase() + keyword.substring(1);
		for(Course course : courses) 
		{
			if(course.getDepartment().contains(keyword) || course.getDepartment().contains(keyword2)) 
			{
				filteredCourses.add(course);
			}
		}
		if(filteredCourses.size() == 0) 
		{
			throw new Exception();
		}
		return filteredCourses;
	}
	
	public ArrayList<Course> getCourseByTerm(String term) throws Exception {
		ArrayList<Course> filteredCourses = new ArrayList<Course>();
		for(Course course: courses) 
		{
			if(course.getTerm().equalsIgnoreCase(term)) 
			{
				filteredCourses.add(course);
			}
		}
		if(filteredCourses.size() == 0) 
		{
			throw new Exception();
		}
		return filteredCourses;
	}
	
	public ArrayList<Course> getCoursesByMinPoints(int points) throws Exception {
		ArrayList<Course> filteredCourses = new ArrayList<Course>();
		for(Course course : courses) 
		{
			if(Integer.parseInt(course.getMinPoints()) == points || Integer.parseInt(course.getMinPoints()) < points) 
			{
				filteredCourses.add(course);
			}
		}
		if(filteredCourses.size() == 0) 
		{
			throw new Exception();
		}
		return filteredCourses;
	}

	public void printCourses() {
		for(Course course : courses) 
		{
			System.out.println(course);
		}
	}
	
	public Course getCourse(String crn) {
		for(Course course : courses) 
		{
			if(course.getCRN().equals(crn)) 
			{
				return course;
			}
		}
		return null;
	}
	
}
