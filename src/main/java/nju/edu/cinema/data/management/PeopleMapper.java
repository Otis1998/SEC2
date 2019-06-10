package nju.edu.cinema.data.management;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import nju.edu.cinema.po.User;



@Mapper
public interface PeopleMapper {
	/**
	 * 查询所有人员信息
	 * @return
	 */
	List<User> selectAllPeople();
	/**
	 * 插入一条人员信息
	 * @param userInfo
	 * @return
	 */
	int insertOneUser(User userInfo);
	/**
	 * 删除一条人员信息
	 * @param id
	 * @return
	 */
	int deleteOneUser(@Param("id") Integer id);
	/**
	 * 修改人员信息
	 * @param userInfo
	 * @return
	 */
	int updatePeople(User userForm);
	/**
	 * 根据id查找人员信息
	 * @param id
	 * @return
	 */
	List<User> selectUserById(@Param("id") Integer id);
}
