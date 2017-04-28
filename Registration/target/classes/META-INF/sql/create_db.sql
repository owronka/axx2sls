create table A2S_MASTERDATA
(
	M_ID integer auto_increment, 
	M_TYPE varchar (20) not null, 
	M_CODE varchar (20) not null, 
	M_TEXT_DE varchar (20) not null, 
	M_TEXT_EN varchar (20) not null, 
	primary key (M_ID)
);

create table A2S_PERSON     
(
	P_ID integer auto_increment, 
	P_SALUTATION varchar (10), 
	P_FIRST_NAME varchar (50) not null, 
	P_LAST_NAME varchar (50) not null, 
	P_EMAIL varchar (80) not null,
	P_ACCOUNT_NAME varchar (50) not null,
	primary key (P_ID)
);

create table A2S_ADDRESS
(
	AD_ID integer auto_increment, 
	AD_P_ID integer, 
	AD_TYPE varchar (20) not null, 
	AD_STREET varchar (255) not null, 
	AD_HOUSENUMBER varchar (10), 
	AD_ZIP varchar (10) not null, 
	AD_CITY varchar (255) not null, 
	AD_COUNTRY varchar (20) not null,
	primary key (AD_ID)
);

alter table A2S_ADDRESS add constraint FK_AD_P_ID  foreign key (AD_P_ID) references A2S_PERSON(P_ID);
alter table A2S_ADDRESS AUTO_INCREMENT = 100;
alter table A2S_MASTERDATA AUTO_INCREMENT = 100;
alter table A2S_PERSON AUTO_INCREMENT = 100;

alter table A2S_PERSON add unique index  IDX_P_ACCOUNT_NAME (P_ACCOUNT_NAME);