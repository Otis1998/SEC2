package nju.edu.cinema.data.management;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import nju.edu.cinema.po.Present;
import nju.edu.cinema.vo.PresentForm;
@Mapper
public interface RechargePresentMapper {
	/**
	 * 查询所有充值优惠信息
	 * @return
	 */
	List<Present> selectAllPresent();
	/**
	 * 插入一条充值优惠信息
	 * @return
	 */
	int insertOnePresent(PresentForm presentForm);
	/**
	 * 更新充值优惠信息
	 * @return
	 */
	int updatePresent(PresentForm presentForm);
	/**
	 * 删除充值优惠信息
	 * @return
	 */
	int deleteOnePresent(@Param("id") int id);
	/**
	 * 根据id查找充值优惠信息
	 * @return
	 */
	List<Present> selectPresentById(@Param("id") int id);
}
