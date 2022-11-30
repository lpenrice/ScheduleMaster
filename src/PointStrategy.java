import java.util.ArrayList;

/**
 * This is the interface for a point distribution strategy.
 *
 */
public interface PointStrategy {

	/**
	 * This method creates a distribution of points for a student's schedule. 
	 * @param schedule of four courses
	 * @return list of points to be allocated in order to each course in a schedule
	 */
	public ArrayList<Integer> distributePoints(Schedule schedule);
	
	public String toString();
	
}
