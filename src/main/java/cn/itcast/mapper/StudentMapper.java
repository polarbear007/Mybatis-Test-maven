package cn.itcast.mapper;

import cn.itcast.entity.Student;

public interface StudentMapper {
	public abstract Student findBysid(Integer sid);
	public abstract void addStudent(Student stu);
}
