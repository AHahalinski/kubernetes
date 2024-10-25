CREATE TABLE post(
   id 				SERIAL 			PRIMARY KEY,
   author_id 		INT 			NOT NULL,
   text 			VARCHAR (255) 	NOT NULL,
   posted_at		TIMESTAMPTZ		NOT NULL
);