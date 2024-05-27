# java-final-proj
This is a final project in "java advanced" course, done with a partnerץ

Functional Design:
Ticket Booking System for Performances.
The user enters the site, selects an artist, performance location, and date.
Then, they enter a new site where they can book either marked or unmarked tickets. They choose the option they prefer.
They can select the number of unmarked seats.
They can see which marked seats are available and choose accordingly.
Afterwards, they enter some personal details, and the booking is confirmed.
From the main page, managers can access a login page. There they can add performances and view ticket sales.
The main site also has an option to add reviews based on a specific artist.
Additionally, management options are available. There, new performances can be added to the system, and the number of tickets sold can be viewed.

Class Design:
XHTML Pages:
Index.xhtml: The main page where performances are selected.
Review.xhtml: Accessed through the main page. Reviews can be added based on the artist.
admin.xhtml: Admin login page, accessed through the main page.
adminSettings.xhtml: After logging in, performances can be added and sales viewed here.
order.xhtml: After selecting the performance, navigate to this page.
userDetails.xhtml: After selecting tickets, the user enters their personal details here.

Beans Classes (connecting between the DB and the front end):
menuBean: Responsible for managing the homepage, receiving the user’s selections, and updating accordingly.
orderBean: After the user selects the performance, it is passed to orderBean, where the actual booking is handled – the number of tickets and type of tickets.
adminBean: Responsible for all management matters: adding performances and viewing sales.
userBean: Responsible for the data the user enters to complete their booking.

Classes Representing SQL Tables:
Artist, Show, Tickets, Orders, Pictures, Review, WebUser
Runner: A class that aggregates all the required functions for database communication.

SQL Tables:
Artist – Primary Key: name. Another field: id.
Show – Primary Key: id. Other fields: time, date, location. Foreign Key: artist name.
Tickets – Primary Key: id. Other fields: Unmarked_Seats, Unmarked_left, 25 variables for marked seats: Seat<row>_<seat>. Foreign Key: show id.
Orders – Primary Key: id. Other fields: which_Ticket (can be um<number> or seat<row>_<seat>). Foreign Keys: Web_User_ID, Show_ID, Tickets_ID.
Web_user – Primary Key: id. Other fields: first name, last name, email, phone, is_manager – Boolean.
Review – Primary Key: id. Other fields: Content. Foreign Key: Artist_Name.
Pictures – Primary Key: id. Other fields: Content (in bytes), Pic_name. Foreign Key: artist name.

CSS Pages:
Styles.css – General CSS file for all pages.
Login.css – Specific CSS file for the login page.
