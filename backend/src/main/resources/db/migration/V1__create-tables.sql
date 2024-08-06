CREATE TABLE user(
	id varchar(36) primary key unique not null,
	name varchar(100) not null,
	email varchar(150) not null unique,
	password varchar(255) not null,
	active boolean not null,
	admin boolean not null
);

CREATE TABLE meeting(
	id varchar(36) primary key unique not null,
	topic varchar(50) not null,
	agenda varchar(50) not null,
	start_time datetime not null,
	duration_minutes int not null,
	join_url varchar(250),
	requester varchar(36),
	participants JSON,
	meeting_id int unique
);

CREATE TABLE attachment(
	id varchar(36) primary key unique not null,
	filename varchar(120) not null,
	filetype varchar(5) not null,
	filelenght int not null,
	url varchar(150) not null,
	meeting_id varchar(36),
	FOREIGN KEY(meeting_id) references meeting(id)
);