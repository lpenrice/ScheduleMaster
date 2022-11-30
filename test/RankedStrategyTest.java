import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the points allocations of the ranked strategy
 * @author louisapenrice
 *
 */
public class RankedStrategyTest {

	private Course course1;
	private Course course2;
	private Course course3;
	private Course course4;
	private Schedule schedule;
	private ArrayList<Integer> ranking = new ArrayList<Integer>();
	private PointStrategy ranked;
	
	/**
	 * Sets up courses, a schedule, and a ranking for testing
	 */
	@Before
	public void setUp() {
		course1 = new Course("Spring 2022", "12345", "Computational Thinking", "Mathematics & Computer Science", "15", "20");
		course2 = new Course("Spring 2022", "12346", "Number Theory", "Mathematics & Computer Science", "0", "0");
		course3 = new Course("Spring 2022", "12347", "Introduction to Human Nutrition", "Human Biology & Kinesiology", "9", "3");
		course4 = new Course("Spring 2022", "12348", "Introduction to Psychology: Bases of Behavior", "Psychology", "20", "33");
		schedule = new Schedule(course1, course2, course3, course4);
		ranking.add(3);
		ranking.add(4);
		ranking.add(1);
		ranking.add(2);
		ranked = new RankedStrategy(ranking);
	}
	
	/**
	 * Tests to make sure points were allocated by ranking, but remain in order of courses
	 */
	@Test
	public void test() {
		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.add(11);
		expected.add(0);
		expected.add(9);
		expected.add(20);
		assertArrayEquals(expected.toArray(), ranked.distributePoints(schedule).toArray());
	}

}
