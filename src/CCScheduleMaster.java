import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
 * The main class of the program that makes each of the GUIs
 *
 */
public class CCScheduleMaster {
	
	private static JDialog loading;
	private JFrame frame;
	private JLabel idLabel;
	private JTextField id;
	private JButton save;
	private JLabel versionNumLabel;
	private JButton retrieve;
	private JTextField filter;
	private JComboBox<String> filterType;
	private JButton browseCourses;
	private JComboBox assignPoints;
	private PointStrategy distributionStrategy;
	private JButton distribute;
	private JLabel crnLabel;
	private JLabel nameLabel;
	private JLabel rankLabel;
	private JLabel pointsLabel;
	private JLabel course1;
	private JLabel course2;
	private JLabel course3;
	private JLabel course4;
	private JTextField crnB1;
	private JTextField crnB2;
	private JTextField crnB3;
	private JTextField crnB4;
	private JTextField nameB1;
	private JTextField nameB2;
	private JTextField nameB3;
	private JTextField nameB4;
	private JComboBox rankB1;
	private JComboBox rankB2;
	private JComboBox rankB3;
	private JComboBox rankB4;
	private JTextField pointsB1;
	private JTextField pointsB2;
	private JTextField pointsB3;
	private JTextField pointsB4;
	private JLabel pointsRemainingLabel;
	private JLabel pointsLeft;
	private JButton exit;
	private JComboBox versionNum;
	private String versionNumber = "1";
	private GridBagConstraints gc = new GridBagConstraints();	
	private Schedule currentSchedule;
	private ArrayList<Integer> ranking = new ArrayList<Integer>(); 
	private JComboBox userType;
	private JFileChooser fileName;
	private JButton fileSelectButton;
	private static CCScheduleMaster scheduleMaster = new CCScheduleMaster();
	private static DataReader dataReader;
	private JFrame welcomeWindow;
	private static String user = "none";
	private JFrame popUp;
	private boolean toDisplay = false;
	private JButton help;
	private JFileChooser fileChooser = new JFileChooser(".");
	private JButton exitAdmin;
	
	/**
	 * Main method for the program that reads the data and runs the GUI
	 * @param args
	 */
	public static void main(String[] args) 
	{
		showLoadingMessage();
		try{
		dataReader = DataReader.getHandle();
		dataReader.readData();
		scheduleMaster.welcomeGUI();
		if(user.equals("Student")) {
			scheduleMaster.createStudentGUI();
		}
		else if(user.equals("Administrator")) {
			scheduleMaster.adminGUI();
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeLoadingMessage();
	}

	/**
	 * Displays a loading message for the user to know the program is running
	 * 
	 */
	public static void showLoadingMessage() 
	{
		loading = new JDialog();
		JLabel message = new JLabel("Please wait... System loading", SwingConstants.CENTER);
		loading.add(message);
		loading.setSize(300, 100);
		loading.setLocationRelativeTo(null);
		loading.setVisible(true);
	}
	
	public static void closeLoadingMessage() 
	{
		loading.setVisible(false);
	}
	
	/**
	 * Creates the GUI for the administrator perspective
	 * 
	 */
	public void adminGUI() 
	{
		JFrame adminWindow = new JFrame();
		adminWindow.setSize(700,400);
		adminWindow.setLayout(new GridBagLayout());
		adminWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gc = new GridBagConstraints();	
		
		JLabel uploadMessage = new JLabel("Hi Admin, please enter in the name of .txt file to upload: ");
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy= 0;
		adminWindow.add(uploadMessage, gc);
		fileSelectButton = new JButton("Upload Data");
		gc.gridx = 2;
		gc.gridy= 0;
		fileSelectButton.addActionListener(new FileSelectActionListener());
		adminWindow.add(fileSelectButton,gc);
		help = new JButton("?");
		gc.gridx= 0;
		gc.gridy = 1;
		help.addActionListener(new HelpActionListener());
		adminWindow.add(help, gc);
		
		exitAdmin = new JButton("Exit");
		gc.gridx = 2;
		gc.gridy = 1;
		exitAdmin.addActionListener(new ExitActionListener());
		adminWindow.add(exitAdmin, gc);
		
		adminWindow.setVisible(true);
	}
	
	/**
	 * Creates the GUI for the welcome screen
	 * 
	 */
	public void welcomeGUI() 
	{
		welcomeWindow = new JFrame();
		welcomeWindow.setSize(700,400);
		welcomeWindow.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();	
		JLabel identity = new JLabel("           I am a...");
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy= 0;
		welcomeWindow.add(identity, gc);
		
		userType = new JComboBox();
		userType.addItem("Choose Type:");
		userType.addItem("Student");
		userType.addItem("Administrator");
		userType.addActionListener(new userTypeActionListener());
		gc.gridx = 0;
		gc.gridy= -1;
		
		welcomeWindow.add(userType, gc );	
		help = new JButton("?");
		gc.gridx= 1;
		gc.gridy = 1;
		help.addActionListener(new HelpActionListener());
		welcomeWindow.add(help, gc);
		welcomeWindow.setVisible(true);
	}
	
	/**
	 * Creates the CC student login pop up
	 * 
	 */
	public void studentPopUp() 
	{
		popUp = new JFrame("Enter a CC ID #");
		popUp.setSize(400,100);
		popUp.setLayout(new GridBagLayout());
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 1;
		gc.weighty = 1;
		
		idLabel = new JLabel("CCID:");
		gc.gridx = 2;
		gc.gridy = 0;
		popUp.add(idLabel, gc);

		id = new JTextField();
		gc.gridx = 3;
		gc.gridy = 0;
		id.addActionListener(new CCIDActionListener());
		popUp.add(id, gc);
		popUp.setVisible(true);
	}
	
	/**
	 * Creates the Student GUI and adds all the buttons
	 * 
	 */
	public void createStudentGUI() 
	{
		frame = new JFrame("CC Schedule Master");
		frame.setSize(700,400);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 1;
		gc.weighty = 1;
		
		help = new JButton("?");
		gc.gridx= 1;
		gc.gridy = 0;
		help.addActionListener(new HelpActionListener());
		frame.add(help, gc);

		idLabel = new JLabel("Student: " + id.getText());
		gc.gridx = 3;
		gc.gridy = 0;
		frame.add(idLabel, gc);
		
		filter = new JTextField();
		gc.gridx = 2;
		gc.gridy = 3;
		frame.add(filter, gc);
		
		filterType = new JComboBox<String>();
		gc.gridx = 1;
		gc.gridy = 3;
		filterType.addItem("Filter by:");
		filterType.addItem("Department");
		filterType.addItem("Minimum Points");
		filterType.addItem("Term");
		frame.add(filterType, gc);
		
		browseCourses = new JButton("Browse Courses");
		gc.gridx = 3;
		gc.gridy = 3;
		browseCourses.addActionListener(new BrowseActionListener());
		frame.add(browseCourses, gc);

		assignPoints = new JComboBox();
		assignPoints.addItem("Distribute points:");
		assignPoints.addItem(new EvenStrategy());
		assignPoints.addItem(new OptimalStrategy());
		assignPoints.addItem(new RankedStrategy(ranking));
		gc.gridx = 1;
		gc.gridy = 1;
		frame.add(assignPoints, gc);

		distribute = new JButton("Distribute");
		distribute.addActionListener(new DistributeActionListener());
		gc.gridx = 2;
		gc.gridy = 1;
		frame.add(distribute, gc);

		retrieve = new JButton("Retrieve");
		gc.gridx = 6;
		gc.gridy = 1;
		retrieve.addActionListener(new RetrieveActionListener());
		frame.add(retrieve, gc);

		crnLabel = new JLabel("CRN:");
		gc.gridx = 2;
		gc.gridy = 4;
		frame.add(crnLabel, gc);

		nameLabel = new JLabel("Course Name:");
		gc.gridx = 3;
		gc.gridy = 4;
		frame.add(nameLabel, gc);

		pointsLabel = new JLabel("Points:");
		gc.gridx = 6;
		gc.gridy = 4;
		frame.add(pointsLabel, gc);
		
		course1 = new JLabel("Course 1:");
		gc.gridx = 1;
		gc.gridy = 5;
		frame.add(course1, gc);
		
		course2 = new JLabel("Couese 2:");
		gc.gridx = 1;
		gc.gridy = 6;
		frame.add(course2, gc);
		
		course3 = new JLabel("Course 3:");
		gc.gridx = 1;
		gc.gridy = 7;
		frame.add(course3, gc);
		
		course4 = new JLabel("Course 4:");
		gc.gridx = 1;
		gc.gridy = 8;
		frame.add(course4, gc);
		
		nameB1 = new JTextField();
		gc.gridx = 3;
		gc.gridy = 5;
		gc.gridwidth = 2;
		nameB1.setEditable(false);
		frame.add(nameB1, gc);

		nameB2 = new JTextField();
		gc.gridx = 3;
		gc.gridy = 6;
		nameB2.setEditable(false);
		frame.add(nameB2, gc);

		nameB3 = new JTextField();
		gc.gridx = 3;
		gc.gridy = 7;
		nameB3.setEditable(false);
		frame.add(nameB3, gc);

		nameB4 = new JTextField();
		gc.gridx = 3;
		gc.gridy = 8;
		nameB4.setEditable(false);
		frame.add(nameB4, gc);

		crnB1 = new JTextField();
		gc.gridx = 2;
		gc.gridy = 5;
		gc.gridwidth = 1;
		crnB1.addActionListener(new CRNActionListener(crnB1, nameB1));
		frame.add(crnB1, gc);

		crnB2 = new JTextField();
		gc.gridx = 2;
		gc.gridy = 6;
		crnB2.addActionListener(new CRNActionListener(crnB2, nameB2));
		frame.add(crnB2, gc);

		crnB3 = new JTextField();
		gc.gridx = 2;
		gc.gridy = 7;
		crnB3.addActionListener(new CRNActionListener(crnB3, nameB3));
		frame.add(crnB3, gc);

		crnB4 = new JTextField();
		gc.gridx = 2;
		gc.gridy = 8;
		crnB4.addActionListener(new CRNActionListener(crnB4, nameB4));
		frame.add(crnB4, gc);

		save = new JButton("Save");
		gc.gridx = 6;
		gc.gridy = 0;
		save.addActionListener(new SaveActionListener());
		frame.add(save, gc);

		pointsB1 = new JTextField();
		gc.gridx = 6;
		gc.gridy = 5;
		pointsB1.addActionListener(new PointsActionListener());
		frame.add(pointsB1, gc);
		pointsB1.setText("0");

		pointsB2 = new JTextField();
		gc.gridx = 6;
		gc.gridy = 6;
		pointsB2.addActionListener(new PointsActionListener());
		frame.add(pointsB2, gc);
		pointsB2.setText("0");

		pointsB3 = new JTextField();
		gc.gridx = 6;
		gc.gridy = 7;
		pointsB3.addActionListener(new PointsActionListener());
		frame.add(pointsB3, gc);
		pointsB3.setText("0");

		pointsB4 = new JTextField();
		gc.gridx = 6;
		gc.gridy = 8;
		pointsB4.addActionListener(new PointsActionListener());
		frame.add(pointsB4, gc);
		pointsB4.setText("0");

		pointsRemainingLabel = new JLabel("Points Remaining: ");
		gc.gridx = 5;
		gc.gridy = 9;
		frame.add(pointsRemainingLabel, gc);

		pointsLeft = new JLabel("40");
		gc.gridx = 6;
		gc.gridy = 9;
		frame.add(pointsLeft, gc);

		exit = new JButton("Exit");
		gc.gridx = 1;
		gc.gridy = 9;
		exit.addActionListener(new ExitActionListener());
		frame.add(exit, gc);

		try {
			DataReader dr= DataReader.getHandle();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		versionNumLabel = new JLabel("Version Number:");
		gc.gridx = 3;
		gc.gridy = 1;
		frame.add(versionNumLabel, gc);

		versionNum = new JComboBox();
		versionNum.addItem("Choose Version #");
		int totalTables = dataReader.countStudentTables(id.getText());
		for(int i = 1; i < (totalTables + 1);i++)
		{
			versionNum.addItem(String.valueOf(i));
		}
		gc.gridx = 5;
		gc.gridy = 1;
		versionNum.addActionListener(new VersionNumActionListener());
		frame.add(versionNum, gc);
		
		rankLabel = new JLabel("Rank:");
		gc.gridx = 5;
		gc.gridy = 4;
		frame.add(rankLabel, gc);
		
		rankB1 = new JComboBox();
		rankB1.addItem(1);
		rankB1.addItem(2);
		rankB1.addItem(3);
		rankB1.addItem(4);
		gc.gridx = 5;
		gc.gridy = 5;
		frame.add(rankB1, gc);
		
		rankB2 = new JComboBox();
		rankB2.addItem(1);
		rankB2.addItem(2);
		rankB2.addItem(3);
		rankB2.addItem(4);
		gc.gridx = 5;
		gc.gridy = 6;
		frame.add(rankB2, gc);
		
		rankB3 = new JComboBox();
		rankB3.addItem(1);
		rankB3.addItem(2);
		rankB3.addItem(3);
		rankB3.addItem(4);
		gc.gridx = 5;
		gc.gridy = 7;
		frame.add(rankB3, gc);
		
		
		rankB4 = new JComboBox();
		rankB4.addItem(1);
		rankB4.addItem(2);
		rankB4.addItem(3);
		rankB4.addItem(4);
		gc.gridx = 5;
		gc.gridy = 8;
		frame.add(rankB4, gc);
		
		frame.setVisible(true);
	}
	
	/**
	 * Sets the ranking list to what the user decided to rank there classes as
	 * 
	 */
	public void setRanking() throws Exception 
	{
		ranking.clear();
		ranking.add((int) rankB1.getSelectedItem());
		ranking.add((int) rankB2.getSelectedItem());
		ranking.add((int) rankB3.getSelectedItem());
		ranking.add((int) rankB4.getSelectedItem());
		for (int i=0; i<ranking.size(); i++) 
		{
			for (int j=0; j<ranking.size(); j++) 
			{
				if (i != j && ranking.get(i) == ranking.get(j)) 
				{
					throw new Exception();
				}
			}
		}
	}
	
	/**
	 * This class implements an action listener for the exit button.
	 *
	 */
	private class ExitActionListener implements ActionListener {

		/**
		 * Exits the program 
		 * 
		 */
		public void actionPerformed(ActionEvent e) 
		{
			System.exit(0);	
		}

	}

	/**
	 * This class implements an ActionListener for the save button
	 *
	 */
	private class SaveActionListener implements ActionListener {

		/**
		 * Saves the schedule created when points were distributed across courses to the students account
		 * this means a table with the schedule data is created under the students personal database
		 * schedule is created if one wasn't created (i.e. distribute points function never used)
		 * 
		 */
		public void actionPerformed(ActionEvent e) 
		{
			DataReader dr;
			try {
				dr = DataReader.getHandle();
				if (currentSchedule == null) 
				{
					ScheduleFactory sf = new ScheduleFactory(id.getText());
					ArrayList<String> crnList = new ArrayList<>();
					crnList.add(crnB1.getText());
					crnList.add(crnB2.getText());
					crnList.add(crnB3.getText());
					crnList.add(crnB4.getText());
					currentSchedule = sf.scheduleMaker(crnList);

				}
				ArrayList<Integer> points = new ArrayList<>();
				points.add(Integer.parseInt(pointsB1.getText()));
				points.add(Integer.parseInt(pointsB2.getText()));
				points.add(Integer.parseInt(pointsB3.getText()));
				points.add(Integer.parseInt(pointsB4.getText()));
				currentSchedule.setCoursePoints(points);
				currentSchedule.addSchedule();
				if(dr.countStudentTables(id.getText())>=1) 
				{
					versionNum.addItem(dr.countStudentTables(id.getText()));
					JOptionPane.showMessageDialog(null, "Your schedule was successfully created.");
				} 
				else 
				{
					JOptionPane.showMessageDialog(null, "There was a problem creating your schedule, try again.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * This class implements an Action listener used to take in the the student ID and create 
	 * a database for that id when enter is pressed.
	 *
	 */
	private class CCIDActionListener implements ActionListener {

		/**
		 * Creates database of student if it doesn't already exist
		 */
		public void actionPerformed(ActionEvent e) 
		{
			String ccid = id.getText();
			Student student = new Student(ccid);
			try {
				student.createStudentDB();
				JOptionPane.showMessageDialog(null, "Your database was successfully created.");
				scheduleMaster.createStudentGUI();
				popUp.setVisible(false);
			} 
			catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "There was a problem creating the database.");
			}
		}
		
	}
	
	/**
	 * This class implements an ActionListener which is used to perform the general browse function
	 * for courses and display it in a new frame that the user can scroll through
	 *
	 */
	private class BrowseActionListener implements ActionListener {
		
		/**
		 * Checks to see if the user pressed "Browse" and creates a new JFrame with that information if true
		 * 
		 */
		public void actionPerformed(ActionEvent f) 
		{
			ArrayList<Course> courses = new ArrayList<Course>();
			if(f.getActionCommand().equals("Browse Courses")) 
			{
				try {
					CourseSet cs = new CourseSet();
					if(filter.getText().equals("")) 
					{
						courses = cs.getCourses();
						displayResults(courses);
					}
					else 
					{
						if(filterType.getSelectedItem().equals("Department")) 
						{
							try {
								courses = cs.getCoursesByDept(filter.getText());
								displayResults(courses);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "There are no courses that fit your search criterea");
							} 
						}
						else if(filterType.getSelectedItem().equals("Minimum Points")) 
						{
							try {
								courses = cs.getCoursesByMinPoints(Integer.parseInt(filter.getText()));
								displayResults(courses);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "There are no courses that fit your search criterea");
							}
						}
						else if(filterType.getSelectedItem().equals("Term")) 
						{
							try {
								courses = cs.getCourseByTerm(filter.getText());
								displayResults(courses);
								
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "There are no courses that fit your search criterea\nPlease enter a semester followed by a year\n(ie. Spring 2022)");
							}
						}
						else 
						{
							JOptionPane.showMessageDialog(null, "If searching with a keyword you must select a filter type.");
						}
					}
				} 
				catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Unable to retrieve courses.");
				}
			}			
		}
		
		/**
		 * Helper method to display the courses by filter
		 * @param courses - filtered courses
		 */
		public void displayResults(ArrayList<Course> courses) 
		{
			JFrame browseFrame = new JFrame();
			browseFrame.setLayout(new BorderLayout());
			browseFrame.setTitle("CC Courses:");
			browseFrame.setSize(1400,400);
			browseFrame.setLocation(0,450);

			JLabel columnNames = new JLabel();
			columnNames.setText("Term                               CRN                                              Course Title                                                                Department:                                                            Min Points Req'd                                                  Waitlisted:");
			browseFrame.add(columnNames, BorderLayout.BEFORE_FIRST_LINE);

			JTextArea txt = new JTextArea();
			txt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
				
			for(int i = 0; i < courses.size(); i++) 
			{
				String course = courses.get(i).toString();
				txt.append(course);
			}
	
			JScrollPane scrollArea = new JScrollPane(txt,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			browseFrame.add(scrollArea, BorderLayout.CENTER);
			browseFrame.setVisible(true);
		}
		
	}

	/**
	 * This class implements an action listener used for the auto-fill class name function
	 *
	 */
	private class CRNActionListener implements ActionListener {

		private JTextField crn;
		private JTextField courseName;

		/**
		 * Constructor
		 * @param crn - Course Registration Number
		 * @param courseName - name of the course to be filled in
		 */
		public CRNActionListener(JTextField crn, JTextField courseName) 
		{
			this.crn = crn;
			this.courseName = courseName;
		}

		/**
		 * Fills the course name text field appropriately
		 */
		public void actionPerformed(ActionEvent e) 
		{
			try {
				CourseSet cs = new CourseSet();
				Course course = cs.getCourse(crn.getText());
				if(course != null) 
				{
					courseName.setText(course.getName());
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Course could not be found.");
					crn.setText("");
				}
			} 
			catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Problem retrieving data from database.");
			}
		}
		
	}

	/**
	 * This class is an action listener for the retrieve button. It uses the name convention and version number passed in
	 * through the versionNumActionListener to retrieve the correct schedule 
	 *
	 */
	private class RetrieveActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) 
		{
			ArrayList<String> courses = new ArrayList<String>();
			try 
			{
				DataReader dr = DataReader.getHandle();
				courses = dr.getSchedule(id.getText(), versionNumber);
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}

			crnB1.setText(courses.get(0));
			nameB1.setText(courses.get(1));
			pointsB1.setText(courses.get(2));

			crnB2.setText(courses.get(3));
			nameB2.setText(courses.get(4));
			pointsB2.setText(courses.get(5));

			crnB3.setText(courses.get(6));
			nameB3.setText(courses.get(7));
			pointsB3.setText(courses.get(8));

			crnB4.setText(courses.get(9));
			nameB4.setText(courses.get(10));
			pointsB4.setText(courses.get(11));

		}
		
	}
	
	/**
	 * This class provides an action listener for the JComboBox that allows the user to select which of their 
	 * schedules they want to retrieve.
	 *
	 */
	private class VersionNumActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) 
		{
			versionNumber = versionNum.getSelectedItem().toString();
		}
		
	}

	/**
	 * This class implements an action listener for the distribute button.
	 *
	 */
	private class DistributeActionListener implements ActionListener {
		
		/**
		 * Allocates and fills points for each course depending on the chosen strategy.
		 * 
		 */
		public void actionPerformed(ActionEvent e) 
		{
			distributionStrategy =  (PointStrategy) assignPoints.getSelectedItem();
			if (distributionStrategy instanceof RankedStrategy) 
			{
				try {
					setRanking();
				} catch (Exception e2){
					JOptionPane.showMessageDialog(null, "All courses must have a different rank");
				}
			}
			String idString = id.getText();
			
			ArrayList<String> crnList = new ArrayList<>();
			crnList.add(crnB1.getText());
			crnList.add(crnB2.getText());
			crnList.add(crnB3.getText());
			crnList.add(crnB4.getText());

			ScheduleFactory sf = new ScheduleFactory(idString);
			currentSchedule = sf.scheduleMaker(crnList);
			
			ArrayList<Integer> points = distributionStrategy.distributePoints(currentSchedule);
			pointsB1.setText(points.get(0) + "");
			pointsB2.setText(points.get(1) + "");
			pointsB3.setText(points.get(2) + "");
			pointsB4.setText(points.get(3) + "");
			
			int pointsRemaining = 40 - (points.get(0) + points.get(1) + points.get(2) + points.get(3));
			pointsLeft.setText(pointsRemaining + "");
		}
		
	}
	
	/**
	 * This class provides an ActionListener for the JComboBox that allows the user to select which of their schedules
	 * they want to retrieve
	 * 
	 */
	private class userTypeActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) 
		{
			String user = userType.getSelectedItem().toString();
			
			if(user.equals("Student")) 
			{
				welcomeWindow.setVisible(false);
				scheduleMaster.studentPopUp();
			}
			else if(user.equals("Administrator")) 
			{
				scheduleMaster.adminGUI();
				welcomeWindow.setVisible(false);
			}
		}
		
	}
	
	/**
	 * Updates the points remaining when a user manually enters points for a class
	 * 
	 */
	private class PointsActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			int points1 = Integer.parseInt(pointsB1.getText());
			int points2 = Integer.parseInt(pointsB2.getText());
			int points3 = Integer.parseInt(pointsB3.getText());
			int points4 = Integer.parseInt(pointsB4.getText());
			pointsLeft.setText(40 - points1 - points2 - points3 - points4 + "");
		}
		
	}

	/**
	 * Creates the help page
	 *
	 */
	private class HelpActionListener implements ActionListener {

		private JFrame helpWindow;
		private JTextArea content;
		
		public void actionPerformed(ActionEvent e) 
		{
			try {
				BufferedReader br = new BufferedReader(new FileReader("help.txt"));
				String fileContent = "";
				String line;
				while((line = br.readLine()) != null)
				{
					fileContent += (line + "\n");
				}
				helpWindow = new JFrame("Help");
				content = new JTextArea();
				content.setText(fileContent);
				helpWindow.add(content);
				helpWindow.pack();
				helpWindow.setVisible(true);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Used to select a txt file with more course data being added to the CCCourses table
	 *
	 */
	private class FileSelectActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) 
		{
			int returnVal = fileChooser.showOpenDialog((Component)e.getSource());
		    if (returnVal == JFileChooser.APPROVE_OPTION) 
		    {
		        File file = fileChooser.getSelectedFile();
		        try {
		           String fileName = file.getName();
		           System.out.println(fileName);
		           DataReader dr = DataReader.getHandle();
		           dr.dataParser(fileName);
		           dr.tableMaker("CCCourses");
		           dr.popTable();
		           JOptionPane.showMessageDialog(null, "Data Successfully uploaded");
		        } 
		        catch (SQLException e1) {
		        	JOptionPane.showMessageDialog(null, "There was a problem adding this data. Please select another file.");
		        }
		        catch (Exception ex) {
		        	JOptionPane.showMessageDialog(null, "problem accessing file"+file.getAbsolutePath());
		        }
		    }       
		}
		
	}
	
}
