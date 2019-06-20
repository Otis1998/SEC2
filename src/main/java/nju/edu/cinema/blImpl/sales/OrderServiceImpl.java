package nju.edu.cinema.blImpl.sales;

import nju.edu.cinema.bl.sales.OrderService;
import nju.edu.cinema.blImpl.management.schedule.MovieServiceForBl;
import nju.edu.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import nju.edu.cinema.data.sales.OrderMapper;
import nju.edu.cinema.data.sales.TicketMapper;
import nju.edu.cinema.po.*;
import nju.edu.cinema.vo.CumulativeVO;
import nju.edu.cinema.vo.OrderVO;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.SeatForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String AFTER_THE_START_TIME = "当前时间晚于电影开始时间";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleServiceForBl;
    @Autowired
    MovieServiceForBl movieServiceForBl;
    @Autowired
    RefundServiceForBl refundServiceForBl;
    @Autowired
    VIPServiceForBl vipServiceForBl;


    /**
     * TODO:完成退票（根据购票的订单号进行退票）
     * @author Wang Youxin
     * @Date 2019/06/08
     * @param orderId
     * @return
     */
    @Override
    public ResponseVO cancelOrder(int orderId){
        try{
            Order order = orderMapper.selectByOrderId(orderId);
            RefundStrategy refundStrategy = refundServiceForBl.getCurrentStrategyForBl();
            int availableHour = refundStrategy.getAvailableHour();
            ResponseVO responseVO = preCheck(order,availableHour);
            if(responseVO.getSuccess()){
                double refundedMoney = order.getCost() - refundStrategy.getCharge();
                List<Integer> ticketsIdList = order.getTicketsIdList();
                for(Iterator<Integer> it = ticketsIdList.iterator();it.hasNext();){
                    ticketMapper.deleteTicket(it.next());
                }
                orderMapper.deleteOrderById(orderId);
                int userId = order.getUserId();
                if(order.getPaymentMode()==1){
                    vipServiceForBl.updateVIPCardBalance(userId,refundedMoney);
                }
            }
            return responseVO;
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    Date getAfterTime(Date date, int time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, time);
        Date afterTime = cal.getTime();
        return afterTime;
    }

    @Override
    public ResponseVO getOrderByUser(int userId){
        try{
            List<Order> orders = orderMapper.selectByUserId(userId);
            RefundStrategy refundStrategy = refundServiceForBl.getCurrentStrategyForBl();
            int availableHour = refundStrategy.getAvailableHour();
            for(Iterator<Order> it=orders.iterator();it.hasNext();){
                preCheck(it.next(),availableHour);
            }
            List<OrderVO> orderVOs = getOrderVOList(userId);
            return ResponseVO.buildSuccess(orderVOs);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    List<OrderVO> getOrderVOList(int userId){
        List<OrderVO> orderVOList = new ArrayList<OrderVO>();
        List<Order> orders = orderMapper.selectByUserId(userId);
        if(orders.size() > 0) {
            for (Iterator<Order> it = orders.iterator(); it.hasNext(); ) {
                Order order = it.next();
                int movieId = order.getMovieId();
                OrderVO orderVO = new OrderVO();
                Movie movie = movieServiceForBl.getMovieById(movieId);
                orderVO.setMovieName(movie.getName());
                orderVO.setPosterURL(movie.getPosterUrl());
                String[] ticketsId = order.getTicketsId().split("&");
                int scheduleId = ticketMapper.selectTicketById(Integer.parseInt(ticketsId[0])).getScheduleId();
                ScheduleItem scheduleItem = scheduleServiceForBl.getScheduleItemById(scheduleId);
                orderVO.setHallId(scheduleItem.getHallId());
                orderVO.setStartTime(scheduleItem.getStartTime());
                orderVO.setEndTime(scheduleItem.getEndTime());
                int numOfTicket = ticketsId.length;
                orderVO.setNumOfTicket(numOfTicket);
                orderVO.setOrderId(order.getOrderId());
                orderVO.setCost(order.getCost());
                List<SeatForm> seatForms = new ArrayList<>();
                for (int i = 0; i < ticketsId.length; i++) {
                    SeatForm seatForm = new SeatForm();
                    Ticket ticket = ticketMapper.selectTicketById(Integer.parseInt(ticketsId[i]));
                    seatForm.setColumnIndex(ticket.getColumnIndex());
                    seatForm.setRowIndex(ticket.getRowIndex());
                    seatForms.add(seatForm);
                }
                orderVO.setState(order.getState());
                orderVO.setSeatFormList(seatForms);
                orderVOList.add(orderVO);
            }
        }
        return orderVOList;
    }

    /**
     * 退票的前置检查
     * @param order
     * @return
     */
    ResponseVO preCheck(Order order,int availableHour){
        List<Integer> ticketsId = order.getTicketsIdList();
        Ticket ticket = ticketMapper.selectTicketById(ticketsId.get(0));
        ScheduleItem scheduleItem = scheduleServiceForBl.getScheduleItemById(ticket.getScheduleId());
        Date now = new Date();
        ResponseVO responseVO = ResponseVO.buildSuccess();
        Date targetDate = getAfterTime(now,availableHour);
        if(scheduleItem.getStartTime().before(targetDate)){
            for(int i = 0; i < ticketsId.size(); i++){
                Ticket t = new Ticket();
                t.setId(ticketsId.get(i));
                t.setState(3);
                ticketMapper.updateTicketState(t.getId(),t.getState());
            }
            order.setState(1);
            orderMapper.updateOrderState(order.getOrderId(),order.getState());
            responseVO = ResponseVO.buildFailure(AFTER_THE_START_TIME);
        }
        return responseVO;
    }


	@Override
	public ResponseVO getTargetUserByCumulative(double cumulative) {
		try{
            List<Cumulative> cumulatives = orderMapper.selectTargetUserByCumulative(cumulative);
            List<CumulativeVO> cumulativeVOs=new ArrayList<>();
            for(Cumulative cum:cumulatives) {
            	cumulativeVOs.add(new CumulativeVO(cum));
            }
            return ResponseVO.buildSuccess(cumulativeVOs);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}
	@Override
    public ResponseVO printOrder(int orderId){
        Order order = orderMapper.selectByOrderId(orderId);
        order.setState(1);
        orderMapper.updateOrderState(order.getOrderId(),order.getState());
        List<Integer> ticketsId = order.getTicketsIdList();
        for(Iterator<Integer> it =ticketsId.iterator();it.hasNext(); ){
            Ticket t = ticketMapper.selectTicketById(it.next());
            t.setState(3);
            ticketMapper.updateTicketState(t.getId(),t.getState());
        }
        return ResponseVO.buildSuccess();
    }
}
