package cn.itcast.entity;

import java.io.Serializable;

public class VO2 implements Serializable {
	private static final long serialVersionUID = -5974897949796592L;
	private Integer stu_count;
	private Double age_avg;

	public Integer getStu_count() {
		return stu_count;
	}

	public void setStu_count(Integer stu_count) {
		this.stu_count = stu_count;
	}

	public Double getAge_avg() {
		return age_avg;
	}

	public void setAge_avg(Double age_avg) {
		this.age_avg = age_avg;
	}

}
