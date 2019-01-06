package cn.itcast.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.itcast.entity.Student;
import cn.itcast.entity.VO;
import cn.itcast.entity.VO2;

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
	
	public abstract void addBatch1(List<Student> stuList);
	public abstract void addBatch2(List<Student> stuList);
	
	public abstract void updateBatch(List<Student> stuList);
	public abstract void deleteBatch(List<Student> stuList);
	public abstract void test();
	
	public abstract List<Student> findAll();
	
	public abstract List<Student> testProcedure1();
	public abstract List<Student> testProcedure2(@Param("start_id")Integer start_id, @Param("end_id")Integer end_id);
	public abstract void testProcedure3(@Param("stu_count")Integer stu_count, @Param("age_avg")Double age_avg);
	public abstract void testProcedure4(VO2 vo);
}
