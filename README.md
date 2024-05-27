# java-final-proj
This is a final project in "java advanced" course, done with a partner.<br>
<br>
Functional Design:<br>
Ticket Booking System for Concerts, baseed on jsf and jpa.<br>
The user enters the site, selects an artist, performance location, and date.<br>
Then, they enter a new site where they can book either marked or unmarked tickets. They choose the option they prefer.<br>
They can select the number of unmarked seats.<br>
They can see which marked seats are available and choose accordingly.<br>
Afterwards, they enter some personal details, and the booking is confirmed.<br>
From the main page, managers can access a login page. There they can add performances and view ticket sales.<br>
The main site also has an option to add reviews based on a specific artist.<br>
Additionally, management options are available. There, new performances can be added to the system, and the number of tickets sold can be viewed.<br>
<br>
Classes Design: <br>
XHTML Pages: <br>
Index.xhtml: The main page where performances are selected.<br>
Review.xhtml: Accessed through the main page. Reviews can be added based on the artist.<br>
admin.xhtml: Admin login page, accessed through the main page.<br>
adminSettings.xhtml: After logging in, performances can be added and sales viewed here.<br>
order.xhtml: After selecting the performance, navigate to this page.<br>
userDetails.xhtml: After selecting tickets, the user enters their personal details here.<br>
<br>
Beans Classes (connecting between the DB and the front end):<br>
menuBean: Responsible for managing the homepage, receiving the user’s selections, and updating accordingly.<br>
orderBean: After the user selects the performance, it is passed to orderBean, where the actual booking is handled – the number of tickets and type of tickets.<br>
adminBean: Responsible for all management matters: adding performances and viewing sales.<br>
userBean: Responsible for the data the user enters to complete their booking.<br>
<br>
Classes Representing SQL Tables:<br>
Artist, Show, Tickets, Orders, Pictures, Review, WebUser<br>
Runner: A class that aggregates all the required functions for database communication.<br>
<br>
SQL Tables:<br>
Artist – Primary Key: name. Another field: id.<br>
Show – Primary Key: id. Other fields: time, date, location. Foreign Key: artist name.<br>
Tickets – Primary Key: id. Other fields: Unmarked_Seats, Unmarked_left, 25 variables for marked seats: Seat<row>_<seat>. Foreign Key: show id.<br>
Orders – Primary Key: id. Other fields: which_Ticket (can be um<number> or seat<row>_<seat>). Foreign Keys: Web_User_ID, Show_ID, Tickets_ID.<br>
Web_user – Primary Key: id. Other fields: first name, last name, email, phone, is_manager – Boolean.<br>
Review – Primary Key: id. Other fields: Content. Foreign Key: Artist_Name.<br>
Pictures – Primary Key: id. Other fields: Content (in bytes), Pic_name. Foreign Key: artist name.<br>
<br>
CSS Pages:<br>
Styles.css – General CSS file for all pages.<br>
Login.css – Specific CSS file for the login page.<br>
