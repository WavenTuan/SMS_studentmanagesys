#
#  插入用户表的数据
#
delete from iuser;
INSERT INTO iuser VALUES (1,'A','duan','123','段惠文','2022-7-1',1);
INSERT INTO iuser VALUES (2,'A','wang','123','王五','2022-7-2',1);
INSERT INTO iuser VALUES (3,'B','tang','123','唐明','2022-7-3',1);
INSERT INTO iuser VALUES (4,'B','jing','123','金春','2022-7-4',2);
INSERT INTO iuser VALUES (5,'B','chui','123','楚河','2022-7-5',2);
INSERT INTO iuser VALUES (6,'B','Liou','123','刘邦','2022-7-6',3);
#
#  插入年级表的数据
#
delete from grade;
INSERT INTO grade (id,name) VALUES (1,'初一');
INSERT INTO grade (id,name) VALUES (2,'初二');
INSERT INTO grade (id,name) VALUES (3,'初三');
INSERT INTO grade (id,name) VALUES (4,'高一');
INSERT INTO grade (id,name) VALUES (5,'高二');
INSERT INTO grade (id,name) VALUES (6,'高三');
#
#  插入班级表的数据
#
delete from schoolclass;
INSERT INTO schoolclass (id,name,grade_id) VALUES (1,'135',6);
INSERT INTO schoolclass (id,name,grade_id) VALUES (2,'136',5);
INSERT INTO schoolclass (id,name,grade_id) VALUES (3,'137',5);
INSERT INTO schoolclass (id,name,grade_id) VALUES (4,'138',4);
INSERT INTO schoolclass (id,name,grade_id) VALUES (5,'139',4);
INSERT INTO schoolclass (id,name,grade_id) VALUES (6,'140',3);
INSERT INTO schoolclass (id,name,grade_id) VALUES (7,'141',3);
INSERT INTO schoolclass (id,name,grade_id) VALUES (8,'142',2);
INSERT INTO schoolclass (id,name,grade_id) VALUES (9,'143',2);
INSERT INTO schoolclass (id,name,grade_id) VALUES (10,'144',1);
INSERT INTO schoolclass (id,name,grade_id) VALUES (11,'145',1);
INSERT INTO schoolclass (id,name,grade_id) VALUES (12,'146',1);
INSERT INTO schoolclass (id,name,grade_id) VALUES (13,'147',1);
#
#  插入课程表的数据
#
delete from course;
INSERT INTO course (id,name) VALUES (1,'数学');
INSERT INTO course (id,name) VALUES (2,'语文');
INSERT INTO course (id,name) VALUES (3,'政治');
INSERT INTO course (id,name) VALUES (4,'历史');
INSERT INTO course (id,name) VALUES (5,'物理');
INSERT INTO course (id,name) VALUES (6,'化学');
INSERT INTO course (id,name) VALUES (7,'英语');
INSERT INTO course (id,name) VALUES (8,'生物');
INSERT INTO course (id,name) VALUES (9,'体育');
INSERT INTO course (id,name) VALUES (10,'生理卫生');
INSERT INTO course (id,name) VALUES (11,'奥数培训课程');
#
#  插入用户和课程联合表的数据
#
delete from iuser_course;
INSERT INTO iuser_course VALUES (1,1);
INSERT INTO iuser_course VALUES (2,3);
INSERT INTO iuser_course VALUES (2,4);
INSERT INTO iuser_course VALUES (2,10);
#
#  插入考试表的数据
#
delete from exam;
INSERT INTO exam VALUES (1,'段考',1,1,1,'2003-12-11');
INSERT INTO exam VALUES (2,'期考',1,1,1,'2003-12-13');
INSERT INTO exam VALUES (3,'期考',2,2,1,'2003-12-14');
INSERT INTO exam VALUES (4,'测验',2,1,1,'2004-05-14');
INSERT INTO exam VALUES (5,'测验',1,1,1,'2004-05-1');
INSERT INTO exam VALUES (6,'测验',1,1,1,'2004-12-2');
INSERT INTO exam VALUES (7,'测验',2,2,1,'2004-12-3');
INSERT INTO exam VALUES (8,'测验',2,2,1,'2004-12-4');
INSERT INTO exam VALUES (9,'测验',2,2,1,'2004-12-6');
#
#  插入考成绩表的数据
#
delete from studentscore;
insert into studentscore VALUES (1,1,3,111.11);
insert into studentscore VALUES (2,1,4,222.22);
insert into studentscore VALUES (3,1,5,333.33);
insert into studentscore VALUES (4,1,6,444.44);
insert into studentscore VALUES (5,2,3,555.55);
insert into studentscore VALUES (6,2,4,666.66);
insert into studentscore VALUES (7,2,5,777.77);
insert into studentscore VALUES (8,2,6,888.88);
insert into studentscore VALUES (9,3,3,999.99);
insert into studentscore VALUES (10,3,4,100);
insert into studentscore VALUES (11,3,5,110);
insert into studentscore VALUES (12,3,6,120);
