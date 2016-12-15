 CREATE TABLE  ah_yldd(
  ID number primary key not null,
  PHONE VARCHAR2(200) ,
  SPHONE   VARCHAR2(200),
  DTIME   VARCHAR2(200), 
  RESULT   VARCHAR2(200), 
  MSG   VARCHAR2(50) 
)
create sequence ah_yldd_id
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
cache 20;

 CREATE TABLE  ah_yldd_code(
  ID number primary key not null,
  PHONE VARCHAR2(200) ,
  SCODE   VARCHAR2(200),
  STIME   VARCHAR2(200)
)
create sequence ah_yldd_code_id
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
cache 20;



 CREATE TABLE  ah_yldd_sj(
  id number primary key not null,
  sphone   VARCHAR2(200)
)

 CREATE TABLE  ah_yldd_packageno(
  id number primary key not null,
  packageno   VARCHAR2(200),
  status VARCHAR(50)
)