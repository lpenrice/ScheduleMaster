import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains tests for the optimal strategy.
 * @author louisapenrice
 *
 */
public class OptimalStrategyTest {
	
	private Course course1;
	private Course course2;
	private Course course3;
	private Course course4;
	private Schedule schedule;
	private PointStrategy optimal;
	
	/**
	 * Creates a schedule and sets the strategy instance to optimal.
	 */
	@Before
	public void setUp() {
		course1 = new Course("Spring 2022", "12345", "Computational Thinking", "Mathematics & Computer Science", "15", "20");
		course2 = new Course("Spring 2022", "12346", "Number Theory", "Mathematics & Computer Science", "0", "0");
		course3 = new Course("Spring 2022", "12347", "Introduction to Human Nutrition", "Human Biology & Kinesiology", "9", "3");
		course4 = new Course("Spring 2022", "12348", "Introduction to Psychology: Bases of Behavior", "Psychology", "20", "33");
		schedule = new Schedule(course1, course2, course3, course4);
		optimal = new OptimalStrategy();
		
	}

	/**
	 * Tests to make sure points are allocated as expected.
	 */
	@Test
	public void test() {
		ArrayList<Integer> expected = new ArrayList<Integer> ();
		expected.add(15);
		expected.add(0);
		expected.add(9);
		expected.add(16);
		assertArrayEquals(expected.toArray(), optimal.distributePoints(schedule).toArray());
	}

}
