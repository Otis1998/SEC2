package nju.edu.cinema.bl.management;

import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.UserForm;
import nju.edu.cinema.vo.UserInfoForm;

public interface PeopleService {
	/**
	 * 搜索所有人员信息
	 * @return
	 */
	ResponseVO searchAllPeople();
	/**
	 * 添加人员信息
	 * @userInfo
	 * @return
	 */
	ResponseVO addPeople(UserForm userInfo);
	/**
	 * 修改人员信息
	 * @userInfo
	 * @return
	 */
	ResponseVO updatePeople(UserInfoForm userInfoForm);
	/**
	 * 删除人员信息
	 * @id
	 * @return
	 */
	ResponseVO deletePeople(Integer id);
	/**
	 * 根据id搜索人员信息
	 * @id
	 * @return
	 */
	ResponseVO searchUserById(Integer id);
}
