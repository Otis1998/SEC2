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
	public ResponseVO searchPresentById(int id) {
		try {
			List<Present> present=rechargePresentMapper.selectPresentById(id);
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
	public ResponseVO publishPresent(PresentForm presentForm) {
		try {
			rechargePresentMapper.insertOnePresent(presentForm);
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
            rechargePresentMapper.updatePresent(presentForm);
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
	
}
