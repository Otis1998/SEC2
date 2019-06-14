package nju.edu.cinema.data.user;

import nju.edu.cinema.vo.UserInfoForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    /**
     * 创建用户信息
     */
    public int insertUserInfo(@Param("userId") int userId);

    /**
     * 根据用户id获取头像和名字
     */
    public UserInfoForm getUserInfo(@Param("userId") int userId);

    /**
     * 根据用户id更新头像和名字
     */
    public int updateUserInfo(UserInfoForm userInfoForm);
}
