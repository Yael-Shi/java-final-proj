

CREATE Table Web_user(
    Web_User_ID varchar(40),
    First_Name varchar(15), 
    Last_Name varchar(20),
    Email_address varchar(30),
    Phone_Number varchar(10) NOT NULL,
    Is_Manager boolean,
    Primary Key(Web_User_ID)
);

Create Table Artist(
    Artist_ID varchar(40),
    Artist_Name varchar(30) NOT NULL,
    Primary Key(Artist_Name)
);


Create Table Show(
    Show_ID varchar(40),
    Show_Time TIME,
    Show_date DATE,
    Show_Location varchar(30) NOT NULL,
    Art_Name varchar(40) NOT NULL,
    Primary Key(Show_ID),
    CONSTRAINT fk_artName
    Foreign Key(Art_Name) 
        REFERENCES Artist(Artist_Name) 
            ON DELETE CASCADE ON UPDATE CASCADE
);  
 

Create Table Tickets(
    Tickets_ID varchar(40),
    Show_ID varchar(40),
    Unmarked_Seats int,
    Unmarked_left int,
    Seat1_1 boolean,
    Seat1_2 boolean,
    Seat1_3 boolean,
    Seat1_4 boolean,
    Seat1_5 boolean,
    Seat2_1 boolean,
    Seat2_2 boolean,
    Seat2_3 boolean,
    Seat2_4 boolean,
    Seat2_5 boolean,
    Seat3_1 boolean,
    Seat3_2 boolean,
    Seat3_3 boolean,
    Seat3_4 boolean,
    Seat3_5 boolean,
    Seat4_1 boolean,
    Seat4_2 boolean,
    Seat4_3 boolean,
    Seat4_4 boolean,
    Seat4_5 boolean,
    Seat5_1 boolean,
    Seat5_2 boolean,
    Seat5_3 boolean,
    Seat5_4 boolean,
    Seat5_5 boolean,
    Primary Key(Tickets_ID),
    CONSTRAINT fk_showid 
    Foreign Key (Show_ID)  
        REFERENCES Show(Show_ID) 
            ON DELETE CASCADE ON UPDATE CASCADE
)

Create Table Orders(
    Order_ID varchar(40) NOT NULL,
    Web_User_ID varchar(40) NOT NULL,
    Show_ID varchar(40) NOT NULL,
    Tickets_ID varchar(40) NOT NULL,
    Which_Ticket varchar(5) NOT NULL,
    Primary Key(Order_ID),
    CONSTRAINT fk_userid 
    Foreign Key (Web_User_ID) 
        REFERENCES Web_User(Web_User_ID) 
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_showid 
    Foreign Key (Show_ID)  
        REFERENCES Show(Show_ID) 
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ticket
    Foreign Key (Tickets_ID)  
        REFERENCES Tickets(Tickets_ID) 
            ON DELETE CASCADE ON UPDATE CASCADE
);

Create Table Review(
    Review_ID varchar(40),
    Content varchar(500),
    Artist_Name varchar(40),
    Primary Key(Review_ID),
    CONSTRAINT fk_artistid Foreign Key (Artist_Name) REFERENCES Artist(Artist_Name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Pictures (
      Pic_id VARCHAR(40),
      Content BYTEA,
      Pic_name VARCHAR(255),
      Art_Name varchar(40) NOT NULL,
      Primary Key(pic_id),
      CONSTRAINT fk_artName
          Foreign Key(Art_Name)
              REFERENCES Artist(Artist_Name)
              ON DELETE CASCADE ON UPDATE CASCADE
);
