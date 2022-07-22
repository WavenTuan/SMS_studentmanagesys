#
#  �����û���
#
DROP TABLE IF EXISTS iuser;
CREATE TABLE iuser (
  Id bigint(20) unsigned NOT NULL auto_increment,
  type char(1) ,
  userId varchar(10) ,
  password varchar(20) ,
  name varchar(20),
  latestOnline datetime,
  schoolclass_id bigint(20),
  PRIMARY KEY  (Id)
)DEFAULT CHARSET=utf8;
#
#  �����༶��
#
DROP TABLE IF EXISTS schoolclass;
CREATE TABLE schoolclass (
  Id bigint(20) unsigned NOT NULL auto_increment,
  name varchar(20),
  grade_id bigint(20),
  PRIMARY KEY  (Id)
)DEFAULT CHARSET=utf8;
#
#  �����꼶��
#
DROP TABLE IF EXISTS grade;
CREATE TABLE grade (
  Id bigint(20) unsigned NOT NULL auto_increment,
  name varchar(20),
  PRIMARY KEY  (Id)
)DEFAULT CHARSET=utf8;
#
#  �����γ̱�
#
DROP TABLE IF EXISTS course;
CREATE TABLE course (
  Id bigint(20) unsigned NOT NULL auto_increment,
  name varchar(20),
  PRIMARY KEY  (Id)
)DEFAULT CHARSET=utf8;
#
#  ������ʦ�Ϳγ̵����ϱ�
#
DROP TABLE IF EXISTS iuser_course;
CREATE TABLE iuser_course (
  iuser_id bigint(20) unsigned NOT NULL,
  course_id bigint(20) unsigned NOT NULL
)DEFAULT CHARSET=utf8;
#
#  �������Ա�
#
DROP TABLE IF EXISTS exam;
CREATE TABLE exam (
  Id bigint(20) unsigned NOT NULL auto_increment,
  name varchar(50),
  iuser_id bigint(20) ,
  course_id bigint(20) ,
  schoolclass_id bigint(20) ,
  date datetime,
  PRIMARY KEY  (Id)
)DEFAULT CHARSET=utf8;
#
#  �����ɼ���
#
DROP TABLE IF EXISTS studentscore;
CREATE TABLE studentscore (
  Id bigint(20) unsigned NOT NULL auto_increment,
  exam_id bigint(20),
  iuser_id bigint(20) ,
  score float(5,2) ,
  PRIMARY KEY  (Id)
)DEFAULT CHARSET=utf8;
