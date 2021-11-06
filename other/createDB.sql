
/* Drop Tables */

DROP TABLE IF EXISTS holiday_management;
DROP TABLE IF EXISTS reservation_status;
DROP TABLE IF EXISTS meeting_rooms;
DROP TABLE IF EXISTS users;




/* Create Tables */

CREATE TABLE holiday_management
(
	holiday_id varchar(15) NOT NULL UNIQUE,
	holiday_date date NOT NULL,
	holiday_name varchar(15) NOT NULL,
	delete_fg smallint NOT NULL,
	PRIMARY KEY (holiday_id)
) WITHOUT OIDS;


CREATE TABLE meeting_rooms
(
	room_id varchar(15) NOT NULL UNIQUE,
	room_name varchar(30) NOT NULL,
	create_dt timestamp NOT NULL,
	update_dt timestamp NOT NULL,
	delete_fg smallint NOT NULL,
	PRIMARY KEY (room_id)
) WITHOUT OIDS;


CREATE TABLE reservation_status
(
	room_id varchar(15) NOT NULL UNIQUE,
	user_id varchar(15) NOT NULL UNIQUE,
	user_name varchar(30) NOT NULL,
	use_start_time timestamp NOT NULL,
	use_end_time timestamp,
	create_dt timestamp NOT NULL,
	update_dt timestamp NOT NULL,
	delete_fg smallint NOT NULL
) WITHOUT OIDS;


CREATE TABLE users
(
	user_id varchar(15) NOT NULL UNIQUE,
	user_name varchar(30) NOT NULL,
	sex char(1) DEFAULT '''0''' NOT NULL,
	create_dt timestamp NOT NULL,
	update_dt timestamp NOT NULL,
	delete_fg smallint NOT NULL,
	PRIMARY KEY (user_id)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE reservation_status
	ADD FOREIGN KEY (room_id)
	REFERENCES meeting_rooms (room_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reservation_status
	ADD FOREIGN KEY (user_id)
	REFERENCES users (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;
