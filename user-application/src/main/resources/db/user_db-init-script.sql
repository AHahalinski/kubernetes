CREATE TABLE user_account(
   id 				SERIAL 			PRIMARY KEY,
   username 		VARCHAR (255) 	UNIQUE NOT NULL,
   amountOfposts	VARCHAR (255)
);