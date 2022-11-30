import java.util.ArrayList;

/**
 * This class in an implementation of the point strategy interface for an even distribution.
 *
 */
public class EvenStrategy implements PointStrategy {
	

	/**
	 * This method evenly distributes points across a student's schedule.
	 * @param student schedule
	 * @return list of points in order
	 */
	public ArrayList<Integer> distributePoints(Schedule schedule) {
		ArrayList<Integer> distribution = new ArrayList<Integer>();
		distribution.add(10);
		distribution.add(10);
		distribution.add(10);
		distribution.add(10);
		return distribution;
	}
	
	public String toString(){
		return "Evenly";
	}

}
