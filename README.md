# TV Show Bucket List

## build up your own taste

This application can **collect** TV shows of every individual users' 
choices, show the list by *category*, **delete** or mark a 
TV show as **completed**. 

Everyone who likes to watch TV shows can use this application.

This project is interesting to me because I like to watch TV shows to release 
stress or just spend time, but it always takes me a lot of time to find 
the one attracts me on the Internet. With a TV show bucket list, when I 
really want to watch something, I can quickly search one from the collections that I have saved before.

## User Stories 
- As a user, I want to be able to add a TV show to the list
- As a user, I want to be able to delete a TV show
- As a user, I want to be able to see all saved TV shows of selected *categories*
- As a user, I want to be able to mark a TV shows as **watched**
- As a user, I want to be able to save my TV show bucket list to file
- As a user, I want to be able to optionally load my TV show bucket list from the file when the program starts

# Instructions for Grader
- You can generate the first required event(**add Xs to Y**) by first selecting "a" in the command line and then
  clicking the "Add a show" button in the popped out GUI window after entering the name and category of a show
- You can generate the second required event(**remove Xs from Y**) by first selecting "r" in the command line and 
  then clicking the "Remove this show" button after selecting any show in the menu list
- You can trigger my **audio** component by clicking any button
- You can **save** the state of my application by clicking the "Save the shows" button
- The state of the application **automatically reloads** because I think it makes more sense

# Phase 4: Task 2
- **Task completed**: test and design a class that is robust
- **Classes and methods involved**: removeShow and showByCategory in ShowList class, their corresponding tests in the 
  ShowListTest test class, and showByCategory in the ShowListUI class (the removeShow method is not affected by the
  additional exception because it has been moved to the GUI part)
- **Exception added**: ShowCannotBeFoundException

# Phase 4: Task 3
- **Change 1**: At first I had an inner class of MyActionListener inside ShowGUI, but gradually I found that the ShowGUI
            class became quite long and had two different main tasks(set up for both GUI and the ActionListener). So I 
            decided to extract MyActionListener into a separate class from ShowGUI to improve cohesion. Although I 
            noticed that MyActionListener still has some calls on ShowGUI(remaining some coupling between these two
            classes), I believe the code is more organized from what it was in Phase 3.
- **Change 2**: Since I need to have TV shows listed in both the console and GUI, it makes sense to display the 
            information using the same format. Before I just have two strings under each class (one to print in the 
            console, and one to display on the GUI panel), but it exposes to future mistakes that when I want to change 
            how the TV shows are displayed, I may change the format in one place but forget the other. In order to keep 
            the consistency and duplicated codes in the project, I added a display() method in the TVShow class which 
            can be called from both ShowListUI and ShowGUI.
- **Change 3**: In MyActionListener classes, extracted many small helper methods to make the code more readable