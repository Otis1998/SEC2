package nju.edu.cinema.blImpl.sales;

import nju.edu.cinema.bl.sales.TicketService;
import nju.edu.cinema.blImpl.management.hall.HallServiceForBl;
import nju.edu.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import nju.edu.cinema.data.management.ScheduleMapper;
import nju.edu.cinema.data.promotion.ActivityMapper;
import nju.edu.cinema.data.promotion.CouponMapper;
import nju.edu.cinema.data.promotion.VIPCardMapper;
import nju.edu.cinema.data.sales.TicketMapper;
import nju.edu.cinema.po.*;
import nju.edu.cinema.vo.*;
import nju.edu.cinema.po.*;
import nju.edu.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by liying on 2019/4/16.
 * Changed by Wang Youxin
 */
@Service
public class TicketServiceImpl implements TicketService {
    private static final String TICKET_STATE_ERROR_MESSAGE = "相应电影票已完成支付或已失效";
    private static final String COUPON_DATE_ERROR_MESSAGE = "不在优惠券可用期间内";
    private static final String BEYOND_PAY_TIME = "超出支付时间";

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    ScheduleMapper scheduleMapper;
    @Autowired
    VIPCardMapper vipCardMapper;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        //TODO:锁座【增加票但状态为未付款】
        try{
            List<Integer> ticketIdList = new ArrayList<>();
            int userId = ticketForm.getUserId();
            int scheduleId = ticketForm.getScheduleId();
            List<SeatForm> seatFormList = ticketForm.getSeats();
            for(SeatForm seatForm:seatFormList){
                Ticket ticket = new Ticket();
                ticket.setUserId(userId);
                ticket.setScheduleId(scheduleId);
                ticket.setColumnIndex(seatForm.getColumnIndex());
                ticket.setRowIndex(seatForm.getRowIndex());
                ticket.setState(0);
                ticket.setTime(new java.sql.Timestamp(new Date().getTime()));
                ticketMapper.insertTicket(ticket);
                ticketIdList.add(ticketMapper.selectMaxId());
            }
            return ResponseVO.buildSuccess(ticketIdList);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeTicket(OrderForm orderForm) {
        //TODO:完成购票【不使用会员卡】流程包括校验优惠券和根据优惠活动赠送优惠券
        try {
            List<Integer> ticketId = orderForm.getTicketId();
            int couponId = orderForm.getCouponId();
            int userId = ticketMapper.selectTicketById(ticketId.get(0)).getUserId();
            int scheduleId = ticketMapper.selectTicketById(ticketId.get(0)).getScheduleId();
            ScheduleItem scheduleItem = scheduleMapper.selectScheduleById(scheduleId);
            int movieId = scheduleItem.getMovieId();
            double originalPrice = scheduleMapper.selectScheduleById(scheduleId).getFare();
            double totalPrice = originalPrice * ticketId.size();
            boolean isBeyondPayTime = false;
            boolean isCouponCanUse = false;
            Date now = new Date();
            if(couponId>0) {
                Coupon coupon = couponMapper.selectById(couponId);
                if (coupon.getStartTime().before(now) && coupon.getEndTime().after(now) && totalPrice >= coupon.getTargetAmount()) {
                    isCouponCanUse = true;
                    totalPrice = totalPrice - coupon.getDiscountAmount();
                }
            }
            for(int id:ticketId){
                Ticket ticket = ticketMapper.selectTicketById(id);
                Date endTime = getAfterTime(ticket.getTime(), 15);//假定等待支付时间为15分钟
                if(endTime.before(new Date())){
                    ticket.setState(2);
                    ticketMapper.updateTicketState(ticket.getId(),ticket.getState());
                    isBeyondPayTime = true;
                }else{
                    ticket.setState(1);
                    ticketMapper.updateTicketState(ticket.getId(),ticket.getState());
                }
            }
            if(isBeyondPayTime){
                return  ResponseVO.buildFailure(BEYOND_PAY_TIME);
            }else{
                if(isCouponCanUse){
                    couponMapper.deleteCouponUser(couponId,userId);
                }
                return ResponseVO.buildSuccess(giveCoupons(movieId, userId));
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 获得一段时间之后的时间
     * @author Wang Youxin
     * @Date 2019/05/14 17:08
     * @Param date
     * @Param time
     */
    Date getAfterTime(Date date, int time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, time);
        Date afterTime = cal.getTime();
        return afterTime;
    }

    /**
     * 根据活动赠送优惠券
     * @author Wang Youxin
     * @Date 2019/05/16 19:50
     * @param movieId
     * @param userId
     * @return
     */
    List<CouponVO> giveCoupons(int movieId, int userId){
        List<Coupon> coupons = new ArrayList<>();
        List<CouponVO> couponVOs=new ArrayList<>();
        List<Activity> activities1 = activityMapper.selectActivitiesWithoutMovie();
        List<Activity> activities2 = activityMapper.selectActivitiesByMovie(movieId);
        for(Activity ac:activities1){
            Date now =new Date();
            if(ac.getStartTime().before(now)&&ac.getEndTime().after(now)){
                coupons.add(ac.getCoupon());
            }
        }

        for(Activity ac:activities2){
            Date now =new Date();
            if(ac.getStartTime().before(now)&&ac.getEndTime().after(now)){
                coupons.add(ac.getCoupon());
            }
        }

        for(Coupon c:coupons){
            couponMapper.insertCouponUser(c.getId(),userId);
            couponVOs.add(new CouponVO(c));
        }

        return couponVOs;
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);
            Hall hall=hallService.getHallById(schedule.getHallId());
            int[][] seats=new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        //TODO:获得用户买过的票
        try{
            List<TicketVO> ticketVOs = getTicketVOList(userId);
            for(Iterator<TicketVO> it = ticketVOs.iterator();it.hasNext();){
                TicketVO t = it.next();
                if(t.getState().equals("0")){
                    it.remove();
                }
            }
            return ResponseVO.buildSuccess(ticketVOs);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(OrderForm orderForm) {
        //TODO:完成购票【使用会员卡】流程包括会员卡扣费、校验优惠券和根据优惠活动赠送优惠券
        try{
            List<Integer> ticketId = orderForm.getTicketId();
            int couponId = orderForm.getCouponId();
            int userId = ticketMapper.selectTicketById(ticketId.get(0)).getUserId();
            int scheduleId = ticketMapper.selectTicketById(ticketId.get(0)).getScheduleId();
            ScheduleItem scheduleItem = scheduleMapper.selectScheduleById(scheduleId);
            int movieId = scheduleItem.getMovieId();
            double originalPrice = scheduleMapper.selectScheduleById(scheduleId).getFare();
            double totalPrice = originalPrice * ticketId.size();
            boolean isBeyondPayTime = false;
            boolean isCouponCanUse = false;

            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(couponId>0) {
                Coupon coupon = couponMapper.selectById(couponId);
                Date now = new Date();
                //判断优惠券是否可用并计算需要支付的金额
                if (coupon.getStartTime().before(now) && coupon.getEndTime().after(now) && totalPrice >= coupon.getTargetAmount()) {
                    isCouponCanUse = true;
                    totalPrice = totalPrice - coupon.getDiscountAmount();
                }
            }
            for(int id:ticketId){
                Ticket ticket = ticketMapper.selectTicketById(id);
                Date endTime = getAfterTime(ticket.getTime(), 15);//假定等待支付时间为15分钟
                if(endTime.before(new Date())){
                    ticket.setState(2);
                    ticketMapper.updateTicketState(ticket.getId(),ticket.getState());
                    isBeyondPayTime = true;
                }else{
                    ticket.setState(1);
                    ticketMapper.updateTicketState(ticket.getId(),ticket.getState());
                }
            }
            if(isBeyondPayTime){
                return  ResponseVO.buildFailure(BEYOND_PAY_TIME);
            }else{
                if(vipCard.getBalance() < totalPrice){
                    return ResponseVO.buildFailure("余额不足");
                }else {
                    vipCard.setBalance(vipCard.getBalance() - totalPrice);
                    vipCardMapper.updateCardBalance(vipCard.getId(), vipCard.getBalance());
                    if (isCouponCanUse) {
                        couponMapper.deleteCouponUser(couponId, userId);
                    }
                    return ResponseVO.buildSuccess(giveCoupons(movieId, userId));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO cancelTicket(List<Integer> ticketId) {
        //TODO:取消锁座（只有状态是"锁定中"的可以取消）
        try{
            for(int id:ticketId) {
                Ticket ticket = ticketMapper.selectTicketById(id);
                if(ticket.getState() == 0){
                    ticketMapper.deleteTicket(id);
                }else{
                    return ResponseVO.buildFailure(TICKET_STATE_ERROR_MESSAGE);
                }
                return ResponseVO.buildSuccess();
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
        return null;
    }

    List<TicketVO> getTicketVOList(int userId){
        List<TicketVO> ticketVOList = new ArrayList<TicketVO>();
        List<Ticket> ticketList = ticketMapper.selectTicketByUser(userId);
        for(Ticket t:ticketList){
            ticketVOList.add(new TicketVO(t));
        }
        return ticketVOList;
    }

}
