package cn.itcast.entity;

import java.io.Serializable;

public class Student implements Serializable {
	private static final long serialVersionUID = -5304386891883937131L;
	private Integer sid;
	private String sname;
	private Integer age;

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student [sid=" + sid + ", sname=" + sname + ", age=" + age + "]";
	}

	
}
