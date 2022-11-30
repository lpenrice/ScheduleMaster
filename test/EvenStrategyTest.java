import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the point allocation of the even strategy
 * @author jacksondresser
 *
 */
public class EvenStrategyTest {
	
	private static final int POINTS_AVAILABLE = 40;
	private Course course1;
	private Course course2;
	private Course course3;
	private Course course4;
	private Schedule schedule;
	
	/**
	 * Sets up courses and a schedule for testing
	 */
	@Before
	public void setUp() {
		course1 = new Course("Spring 2022", "12345",
				"Course 1", "Department", "3", "0");
		course2 = new Course("Spring 2022", "12346",
				"Course 2", "Department", "3", "0");
		course3 = new Course("Spring 2022", "12347",
				"Course 3", "Department", "3", "0");
		course4 = new Course("Spring 2022", "12348",
				"Course 4", "Department", "3", "0");
		schedule = new Schedule(course1, course2, course3, course4);
	}

	/**
	 * Compares ArrayList of int from distributePoints method to an Arraylist of
	 * point values distributed evenly
	 */
	@Test
	public void testEvenPointDistribution() {
		ArrayList<Integer> pointDistribution = new ArrayList<Integer>();
		int pointsPerClass = POINTS_AVAILABLE / schedule.getCourses().size();
		for(int i = 0; i < schedule.getCourses().size(); i++) {
			pointDistribution.add(pointsPerClass);
		}
		EvenStrategy evenly = new EvenStrategy();
		Object[] expectedDist = pointDistribution.toArray();
		Object[] computerDistributed = evenly.distributePoints(schedule).toArray();
		assertArrayEquals(expectedDist, computerDistributed);
	}

}
