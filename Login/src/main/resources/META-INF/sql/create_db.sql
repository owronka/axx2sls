create table A2S_ACCOUNT
(
	AC_ID integer auto_increment, 
	AC_P_ID integer, 
	AC_NAME varchar (50) not null, 
	AC_PASSWORD varchar (200) not null, 
	AC_SALT varchar (40) not null,
	primary key (AC_ID)
) AUTO_INCREMENT = 100;

alter table A2S_ACCOUNT AUTO_INCREMENT = 100;
