package nju.edu.cinema.data.user;

import nju.edu.cinema.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Mapper
public interface AccountMapper {

    /**
     * 创建一个新的账号
     * @param username
     * @param password
     * @return
     */
    public int createNewAccount(@Param("identity")int identity,@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名查找账号
     * @param username
     * @return
     */
    public User getAccountByName(@Param("username") String username);

    /**
     * 根据用户id查找帐号
     * @param userId
     * @return
     */
    public User getAccountById(@Param("userId") int userId);

    /**
     * 根据用户id更新密码
     */
    public void updatePassword(@Param("userId") int userId, @Param("password") String password);
}
