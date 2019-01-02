package cn.itcast.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.itcast.entity.Student;
import cn.itcast.entity.VO;

public interface StudentMapper {
	public abstract Student findBysid(Integer sid);
	public abstract void addStudent(Student stu);
	public abstract void addStudents(Student[] stus);
	public abstract void addStudentCollection(@Param("col")Collection<Student> stuCollection);
	public abstract void addStudentList(List<Student> stuList);
	public abstract void updateByMap(Map<String, Object> map);
	public abstract void addStudentVO(VO vo);
	public abstract void updateStudent1(Integer sid, String sname, Integer age);
	public abstract void updateStudent2(@Param("sid")Integer sid, @Param("sname")String sname, @Param("age")Integer age);
}
