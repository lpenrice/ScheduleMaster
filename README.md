
# Schedule Master

The CC Schedule Master was created as a group final project in the course Software Design. Its goal is to help streamline Colorado College's 
course pre-registration process for students and administrators. 

The college uses a point system to determine which students get a spot in 
each course. Students are given 40 points each semester to 
distribute amongst their desired courses. When deciding how many points 
to allocate towards a given course, students typically take 2 factors into 
consideration: how high demand the given course has been in the past and how 
important it is to their academic progress that they obtain a spot.

Our program creates a MySQL database of 4 semesters worth of data about the minimum points that were required to get into each course.

The student view asks users to first enter an ID number. Students are able to 
browse the list of courses, see the minimum points that have required in the past, and create drafts of their schedule. To help students 
decide how to best allocate their points, we implemented 3 point distribution strategies. Students can select to have the program either 
distribute their points evenly, distribute them based on a ranking of importance given by the student, or distribute them by prioritizing 
the courses that require the most points. Using the student's ID number, these drafts are saved and can be retrieved from another MySQL 
database personal to each student that has engaged with the program. 

The administrative perspective allows administrators to upload PDFs of point data from additional semesters and therefore increase the 
level to which our point distribution strategies can provide students with their desired outcome. 


## Features

- Student:
	- Create profile
	- Select classes
	- Select a point distribution strategy
	- Browse all classes
	- Filter list of classes
	- Save schedule drafts
	- Retrieve previous schedule drafts
	- Edit previous schedule drafts
	- Rank classes in schedule based on importance
- Administrator:
	- Upload more point data

## Goals for Future Development
- Add an advisor view that would allow them to: 
	- View the schedule drafts of their advisees
	- Accept or reject schedule drafts
    - Provide feedback on drafts
	- Give advisees their pre-registration code
- Add a professor view that would allow them to:
	- Add contextual notes to accompany their courses to provide students with more information as they as registering
## Authors

- Louisa Penrice
- Graham Whiting
- Andrew Little
- Jack Dresser

