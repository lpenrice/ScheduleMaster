import java.util.ArrayList;
import java.util.Collections;

/**
 * This class implements the point strategy interface for an optimal distribution strategy.
 *
 */
public class OptimalStrategy implements PointStrategy {

	/**
	 * This method allocates as many points, in order by course, as it can towards the minimum points 
	 * needed for each course in a student's schedule until it has run out of points to allocate.
	 * @param student schedule
	 * @return list of points in order
	 */
	public ArrayList<Integer> distributePoints(Schedule schedule) {
		ArrayList<Integer> distribution = new ArrayList<Integer>();
		int pointsLeft = 40;
		ArrayList<Integer> minPoints = new ArrayList<Integer>();
		for(Course course : schedule.getCourses() )
		{
			String temp = course.getMinPoints();
			String cleaned = "";
			for (int i = 0; i<temp.length(); i++) {
				String toCheck = "";
				toCheck += temp.charAt(i);
				if (toCheck.isBlank()) {
					break;
				}
				cleaned += temp.charAt(i);
			}
			minPoints.add(Integer.parseInt(cleaned));
		}
		for(Integer i : minPoints)
		{
			if(pointsLeft > 0) 
			{
				if(i <= pointsLeft)
				{
					distribution.add(i);
					pointsLeft -= i;
				}
				else
				{
					distribution.add(pointsLeft);
					pointsLeft -= pointsLeft;
				}
			}
			else
				distribution.add(0);
		}
		return distribution;
	}

	public String toString() {
		return "Optimally";
	}
	
}
