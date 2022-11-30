import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class will be communicating with the CourseSet class to format and set student schedules.
 * Schedules will also be able to be saved here.
 * 
 */
public class Schedule {
	
	private String id;
	private Course course1;
	private Course course2;
	private Course course3;
	private Course course4;
	private ArrayList<Course> courses = new ArrayList<Course>();

	/**
	 * Constructor, sets id of the schedule
	 * @param id
	 */
	public Schedule(String id) {
		this.id = id;
	}
	
	/**
	 * Set a schedule for testing purposes
	 * @param course1 - first course in Schedule
	 * @param course2 - second course in Schedule
	 * @param course3 - third course in Schedule
	 * @param course4 - fourth course in Schedule
	 */
	public Schedule(Course course1, Course course2, Course course3, Course course4) {
		this.course1 = course1;
		this.course2 = course2;
		this.course3 = course3;
		this.course4 = course4;
		courses.add(course1);
		courses.add(course2);
		courses.add(course3);
		courses.add(course4);
	}
	
	public String getID() {
		return id;
	}

	/**
	 * Adds courses to this schedule
	 * @param coursestoAdd - courses being added to this schedule
	 */
	public void makeSchedule(ArrayList<String> coursestoAdd) {
		try {
			DataReader dr = DataReader.getHandle();
			course1 = dr.getCourse(coursestoAdd.get(0));
			course1.setBlock("1");
			course2 = dr.getCourse(coursestoAdd.get(1));
			course2.setBlock("2");
			course3 = dr.getCourse(coursestoAdd.get(2));
			course3.setBlock("3");
			course4 = dr.getCourse(coursestoAdd.get(3));
			course4.setBlock("4");
			courses.add(course1);
			courses.add(course2);
			courses.add(course3);
			courses.add(course4);
		} 
		catch (SQLException e) {
			System.out.println("error querying courses");
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the schedule to the database using DataReader methods
	 * 
	 */
	public void addSchedule() {
		try {
			DataReader dr = DataReader.getHandle();
			dr.makeStudentTable(id);
			int tableNumber = dr.countStudentTables(id);
			dr.insertStudentTable(this, tableNumber); 
		}
		catch (SQLException e) {
			System.out.println("error querying courses");
			e.printStackTrace();
		}
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	/**
	 * Sets the points assigned to each class in this schedule
	 * @param pointsList
	 */
	public void setCoursePoints(ArrayList<Integer> pointsList) {
		int i = 0;
		for (Course course : courses) 
		{
			course.setPoints(pointsList.get(i));
			i++;
		}
	}
	
}
