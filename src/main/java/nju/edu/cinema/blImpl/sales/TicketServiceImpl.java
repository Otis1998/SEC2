package nju.edu.cinema.blImpl.sales;

import com.sun.org.apache.xpath.internal.operations.Or;
import nju.edu.cinema.bl.sales.OrderService;
import nju.edu.cinema.bl.sales.TicketService;
import nju.edu.cinema.blImpl.management.hall.HallServiceForBl;
import nju.edu.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import nju.edu.cinema.data.promotion.VIPCardMapper;
import nju.edu.cinema.data.sales.OrderMapper;
import nju.edu.cinema.data.sales.TicketMapper;
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
public class TicketServiceImpl implements TicketService, TicketServiceForBl {
    private static final String TICKET_STATE_ERROR_MESSAGE = "相应电影票已完成支付或已失效";
    private static final String COUPON_DATE_ERROR_MESSAGE = "不在优惠券可用期间内";
    private static final String BEYOND_PAY_TIME = "超出支付时间";

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    CouponServiceForBl couponServiceForBl;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    ActivityServiceForBl activityServiceForBl;
    @Autowired
    ScheduleServiceForBl scheduleServiceForBl;
    @Autowired
    VIPCardMapper vipCardMapper;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
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
        try {
            ResponseVO responseVO = buyTicket(orderForm,0);
            return responseVO;
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    String getTicketsId(List<Integer> ticketsId){
        StringBuilder s = new StringBuilder();
        for(Iterator<Integer> it = ticketsId.iterator();it.hasNext();){
            s.append(String.valueOf(it.next())).append("&");
        }
        String result = s.toString();
        return s.substring(0,s.length()-1);
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
        List<Activity> activities1 = activityServiceForBl.selectActivitiesWithoutMovie();
        List<Activity> activities2 = activityServiceForBl.selectActivitiesByMovie(movieId);
        activities1.addAll(activities2);
        for(Activity ac:activities1){
            Date now =new Date();
            if(ac.getStartTime().before(now)&&ac.getEndTime().after(now)){
                coupons.add(ac.getCoupon());
            }
        }


        for(Coupon c:coupons){
            couponServiceForBl.insertCoupon(c.getId(),userId);
            couponVOs.add(new CouponVO(c));
        }

        return couponVOs;
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);
            HallVO hall=hallService.getHallById(schedule.getHallId());
            int[][] seats=hall.getSeats();
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
        try{
            ResponseVO responseVO = buyTicket(orderForm,1);
            return responseVO;
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO cancelTicket(List<Integer> ticketId) {
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

    @Override
    public Ticket getTicketByIdForBl(int ticketId) {
        return ticketMapper.selectTicketById(ticketId);
    }

    @Override
    public void updateTicketState(int ticketId, int state) {
        ticketMapper.updateTicketState(ticketId,state);
    }

    @Override
    public void deleteTicket(int ticketId) {
        ticketMapper.deleteTicket(ticketId);
    }

    private ResponseVO buyTicket(OrderForm orderForm, int method){
        boolean isBuySuccess = false;
        List<Integer> ticketId = orderForm.getTicketId();
        int couponId = orderForm.getCouponId();
        String ticketsId = getTicketsId(ticketId);
        int userId = ticketMapper.selectTicketById(ticketId.get(0)).getUserId();
        int scheduleId = ticketMapper.selectTicketById(ticketId.get(0)).getScheduleId();
        ScheduleItem scheduleItem = scheduleServiceForBl.getScheduleItemById(scheduleId);
        int movieId = scheduleItem.getMovieId();
        double originalPrice = scheduleItem.getFare();
        double totalPrice = originalPrice * ticketId.size();
        boolean isBeyondPayTime = false;
        boolean isCouponCanUse = false;

        if(couponId>0) {
            Coupon coupon = couponServiceForBl.selectCouponById(couponId);
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
            if(method == 0){
                if(isCouponCanUse){
                    couponServiceForBl.deleteCoupon(couponId,userId);
                }
                isBuySuccess = true;
            }
            if(method == 1) {
                VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
                if (vipCard.getBalance() < totalPrice) {
                    return ResponseVO.buildFailure("余额不足");
                } else {
                    vipCard.setBalance(vipCard.getBalance() - totalPrice);
                    vipCardMapper.updateCardBalance(vipCard.getId(), vipCard.getBalance());
                    if (isCouponCanUse) {
                        couponServiceForBl.deleteCoupon(couponId, userId);
                    }
                    isBuySuccess = true;
                }
            }

            if(isBuySuccess) {
                //添加Order对象
                Order order = new Order();
                order.setUserId(userId);
                order.setMovieId(movieId);
                order.setTicketsId(ticketsId);
                order.setCost(totalPrice);
                order.setPaymentMode(1);
                order.setState(0);
                orderMapper.insertOrder(order);
            }
            return ResponseVO.buildSuccess(giveCoupons(movieId, userId));
        }
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
