package cn.com.dhw.sms.model;

import java.util.HashSet;
import java.util.Set;

public class Teacher extends AbstractUser{
	private Set<Course> courses = new HashSet<Course>();

    public Set<Course> getCourses() { return courses; }
    public void setCourses(Set<Course> courses) {this.courses = courses; }

    public void addCourse(Course course) {courses.add(course); }
    public void removeCourse(Course course) {courses.remove(course);}

    public void clearCourses() {courses.clear();}

    public boolean isCourse(Course course){return courses.contains(course);}
}
