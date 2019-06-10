package nju.edu.cinema.blImpl.management.people;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nju.edu.cinema.bl.management.PeopleService;
import nju.edu.cinema.data.management.PeopleMapper;
import nju.edu.cinema.po.User;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.UserForm;
import nju.edu.cinema.vo.UserVO;

@Service
public class PeopleServiceImpl implements PeopleService{
	@Autowired
	private PeopleMapper peopleMapper;
	@Override
	public ResponseVO searchAllPeople() {
		try {
            return ResponseVO.buildSuccess(peopleList2PeopleVOList(peopleMapper.selectAllPeople()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}

	@Override
	public ResponseVO addPeople(UserForm userInfo) {
		try {
			User user=new User();
			user.setIdentity(userInfo.getIdentity());
			user.setUsername(userInfo.getUsername());
			user.setPassword(userInfo.getPassword());
            peopleMapper.insertOneUser(user);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}

	@Override
	public ResponseVO updatePeople(UserForm userForm) {
		try {
			User user=new User();
			user.setId(userForm.getId());
			user.setIdentity(userForm.getIdentity());
			user.setUsername(userForm.getUsername());
			user.setPassword(userForm.getPassword());
            peopleMapper.updatePeople(user);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}

	@Override
	public ResponseVO deletePeople(Integer id) {
		try { 
            peopleMapper.deleteOneUser(id);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}
	
    private List<UserVO> peopleList2PeopleVOList(List<User> peopleList){
        List<UserVO> peopleVOList = new ArrayList<>();
        for(User user : peopleList){
        	peopleVOList.add(new UserVO(user));
        }
        return peopleVOList;
    }

	@Override
	public ResponseVO searchUserById(Integer id) {
		try {
			return ResponseVO.buildSuccess(peopleList2PeopleVOList(peopleMapper.selectUserById(id)));
		} catch (Exception e) {
			e.printStackTrace();
            return ResponseVO.buildFailure("失败");
		}
	}
}
