import java.util.ArrayList;

/**
 * This class implements the point strategy interface for a strategy that takes ranking of importance into account.
 *
 */
public class RankedStrategy implements PointStrategy {
	
	private ArrayList<Integer> ranking;

	/**
	 * This is the constructor for a ranked strategy.
	 * @param ranking list in order of courses in a student's schedule
	 */
	public RankedStrategy(ArrayList<Integer> ranking) {
		this.ranking = ranking;
	}
	
	/**
	 * This method creates a distribution of points for a student's ranked schedule. 
	 * @param schedule of four courses
	 * @return list of points to be allocated in order to each course in a schedule
	 */
	public ArrayList<Integer> distributePoints(Schedule schedule){
		ArrayList<Course> initialCourses = schedule.getCourses(); //courses ordered by initial input
		ArrayList<Course> courses = new ArrayList<Course>(); //courses ordered by rank
		for(int i : ranking)
		{
			courses.add(initialCourses.get(i-1));
			
		}
		
		ArrayList<Integer> distribution = new ArrayList<Integer>();
		int pointsLeft = 40;
		for (Course c : courses)
		{
			if(pointsLeft > 0) 
			{
				if(Integer.parseInt(c.getMinPoints()) <= pointsLeft)
				{
					c.setPoints(Integer.parseInt(c.getMinPoints()));
					pointsLeft -= Integer.parseInt(c.getMinPoints());
				}
				else
				{
					c.setPoints(pointsLeft);
					pointsLeft -= pointsLeft;
				}
			}
			else
				c.setPoints(0);
		}
		for (Course course: initialCourses) 
		{
			distribution.add(course.getPoints());
		}
		return distribution;
	}
	
	public String toString() {
		return "By Ranking";
	}
	
}
