package cn.itcast.entity;

import java.util.List;
import java.util.Map;

public class VO {
	private Student stu;
	private Student[] stus;
	private List<Student> stuList;
	private Map<String, Student> stuMap;
	

	public Student getStu() {
		return stu;
	}

	public void setStu(Student stu) {
		this.stu = stu;
	}

	public List<Student> getStuList() {
		return stuList;
	}

	public void setStuList(List<Student> stuList) {
		this.stuList = stuList;
	}

	public Map<String, Student> getStuMap() {
		return stuMap;
	}

	public void setStuMap(Map<String, Student> stuMap) {
		this.stuMap = stuMap;
	}

	public Student[] getStus() {
		return stus;
	}

	public void setStus(Student[] stus) {
		this.stus = stus;
	}

}
