package nju.edu.cinema.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nju.edu.cinema.bl.management.RechargePresentService;
import nju.edu.cinema.vo.PresentForm;
import nju.edu.cinema.vo.ResponseVO;

@RestController
public class RechargePresentController {
	@Autowired
	private RechargePresentService rechargePresentService;
	@RequestMapping(value = "/present/search", method = RequestMethod.GET)
	public ResponseVO searchAllPeople() {
		return rechargePresentService.searchAllPresents();
	}
	
	@RequestMapping(value = "/present/add", method = RequestMethod.POST)
	public ResponseVO addPeople(@RequestBody PresentForm presentForm) {
		return rechargePresentService.publishPresent(presentForm);
	}
	@RequestMapping(value = "/present/update", method = RequestMethod.POST)
	public ResponseVO updatePeople(@RequestBody PresentForm presentForm) {
		return rechargePresentService.updatePresent(presentForm);
	}
	@RequestMapping(value = "/present/delete", method = RequestMethod.POST)
	public ResponseVO deletePeople(@RequestParam Integer id) {
		return rechargePresentService.deletePresent(id);
	}
}
