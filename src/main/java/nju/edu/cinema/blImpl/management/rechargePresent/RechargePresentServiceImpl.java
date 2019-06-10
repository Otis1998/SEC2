package nju.edu.cinema.blImpl.management.rechargePresent;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.edu.cinema.bl.management.RechargePresentService;
import nju.edu.cinema.data.management.RechargePresentMapper;
import nju.edu.cinema.po.Present;
import nju.edu.cinema.vo.PresentForm;
import nju.edu.cinema.vo.PresentVO;
import nju.edu.cinema.vo.ResponseVO;

@Service
public class RechargePresentServiceImpl implements RechargePresentService,RechargePresentServiceForBl{
	@Autowired
	private RechargePresentMapper rechargePresentMapper;

	@Override
	public ResponseVO publishPresent(PresentForm presentForm) {
		try {
			Present present=new Present();
			present.setTargetAmount(presentForm.getTargetAmount());
			present.setPresentAmount(presentForm.getPresentAmount());
			rechargePresentMapper.insertOnePresent(present);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}

	@Override
	public ResponseVO deletePresent(int id) {
		try {
            rechargePresentMapper.deleteOnePresent(id);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}

	@Override
	public ResponseVO updatePresent(PresentForm presentForm) {
		try {
			Present present=new Present();
			present.setId(presentForm.getId());
			present.setTargetAmount(presentForm.getTargetAmount());
			present.setPresentAmount(presentForm.getPresentAmount());
            rechargePresentMapper.updatePresent(present);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}

	@Override
	public ResponseVO searchAllPresents() {
		try {
			List<Present> present=rechargePresentMapper.selectAllPresent();
			List<PresentVO> presentVO=new ArrayList<>();
			for(Present pr:present) {
				presentVO.add(new PresentVO(pr));
			}
            return ResponseVO.buildSuccess(presentVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}

	@Override
	public String getPresentDescription() {
		String description="";
		List<Present> present=rechargePresentMapper.selectAllPresent();
		for(Present pr:present) {
			description=description+"满"+String.format("%.2f", pr.getTargetAmount())+"送"+String.format("%.2f",pr.getPresentAmount())+"<br />";
		}
		return description;
	}

	@Override
	public double calculate(int amount) {
		double balance=0;
		double Amount=amount;
		List<Present> presents=rechargePresentMapper.selectAllPresent();
		for(Present present:presents) {
			if(amount>=present.getTargetAmount()) {
				while (Amount>=present.getTargetAmount()) {
					Amount=Amount-present.getTargetAmount();
					balance=balance+present.getPresentAmount();
				}
			}
		}
		return balance+amount;
	}
	
}
