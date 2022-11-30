import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * This is the schedule factory and is responsible for making instances of schedules
 *
 */
public class ScheduleFactory {
	
	private String ccID;
	private ArrayList<String> crn;
	
	/**
	 * Constructor for the class
	 * @param ccID
	 */
	public ScheduleFactory(String ccID) {
		this.ccID = ccID;
	}
	
	/**
	 * This method is responsible for creating the new schedule objects and passing the course information to them so they can be 
	 * created in the individual student tables.
	 * @param crn
	 * @return schedule instance
	 */
	public Schedule scheduleMaker(ArrayList<String> crn) {
		Schedule schedule =  new Schedule(ccID);
		schedule.makeSchedule(crn);
		return schedule;
	}
	
}
