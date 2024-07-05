CREATE TABLE user(
	id varchar(36) primary key unique not null,
	name varchar(100) not null,
	email varchar(150) not null unique,
	password varchar(255) not null,
	allow_level tinyint not null,
	active boolean not null,
	admin boolean not null
);

CREATE TABLE meeting(
	id varchar(36) primary key unique not null,
	title varchar(50) not null,
	meeting varchar(50) not null,
	start_time datetime not null,
	duration_minutes int not null,
	join_url varchar(250),
	requester varchar(36),
	participants JSON,
	meeting_minutes varchar(10) not null,
	FOREIGN KEY(requester) REFERENCES user(id)
);

CREATE TABLE attachment(
	id varchar(36) primary key unique not null,
	filename varchar(120) not null,
	filetype varchar(5) not null,
	filelenght int not null,
	url varchar(150) not null,
	meetingId varchar(36),
	FOREIGN KEY(id) references meeting(id)
);