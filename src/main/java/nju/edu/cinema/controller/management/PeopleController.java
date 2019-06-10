package nju.edu.cinema.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nju.edu.cinema.bl.management.PeopleService;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.UserForm;

@RestController
public class PeopleController {
	@Autowired
	private PeopleService peopleService;
	
	@RequestMapping(value = "/people/search", method = RequestMethod.GET)
	public ResponseVO searchAllPeople() {
		return peopleService.searchAllPeople();
	}
	
	@RequestMapping(value = "/people/add", method = RequestMethod.POST)
	public ResponseVO addPeople(@RequestBody UserForm userInfo) {
		return peopleService.addPeople(userInfo);
	}
	@RequestMapping(value = "/people/update", method = RequestMethod.POST)
	public ResponseVO updatePeople(@RequestBody UserForm userForm) {
		return peopleService.updatePeople(userForm);
	}
	@RequestMapping(value = "/people/delete", method = RequestMethod.POST)
	public ResponseVO deletePeople(@RequestParam Integer id) {
		return peopleService.deletePeople(id);
	}
	@RequestMapping(value = "/people/getInfo", method = RequestMethod.GET)
	public ResponseVO searchUserById(@RequestParam Integer id) {
		return peopleService.searchUserById(id);
	}
}
