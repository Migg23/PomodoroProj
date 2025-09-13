# Pomodoro Project
This is my first project that uses React as the frontend and has a Java web server. This project is based on the Pomodoro studying technique, where a student studies for an amount of time and then takes a short break, where this cycle continues, increasing the break time with longer study sessions. The Pomodoro timer is designed to have different timer sessions where a student either has to study or is able to take a break. This project is not finished and needs features such as a fully stopped time.





# How It's Made:
Tech used: React.js, CSS, Java, HTML, JavaScript

This project was started to gain a deeper understanding of the connection between the client and server. I started with the backend using Java, as it was a language that I used for school in one of my classes. I used SpringBoot for its auto-configuration of the necessary components needed to run the app. The server is able to decide which session and how the time that needed to be displayed. Each method returns a map of the status, time, and starts/stops a session. 
The React frontend then pulls the desired information from the web server and displays the time, status, and any information. 
