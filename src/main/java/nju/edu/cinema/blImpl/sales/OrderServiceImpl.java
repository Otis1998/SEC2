package nju.edu.cinema.blImpl.sales;

import nju.edu.cinema.bl.sales.OrderService;
import nju.edu.cinema.blImpl.management.schedule.MovieServiceForBl;
import nju.edu.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import nju.edu.cinema.data.sales.OrderMapper;
import nju.edu.cinema.po.Cumulative;
import nju.edu.cinema.po.Movie;
import nju.edu.cinema.po.Order;
import nju.edu.cinema.po.ScheduleItem;
import nju.edu.cinema.po.Ticket;
import nju.edu.cinema.vo.CumulativeVO;
import nju.edu.cinema.vo.OrderVO;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.SeatForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String AFTER_THE_START_TIME = "当前时间晚于电影开始时间";

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ScheduleServiceForBl scheduleServiceForBl;
    @Autowired
    MovieServiceForBl movieServiceForBl;
    /**
     * TODO:完成退票（根据购票的订单号进行退票）
     * @author Wang Youxin
     * @Date 2019/06/08
     * @param orderId
     * @return
     */
    @Override
    public ResponseVO refundTicket(int orderId){
        try{
            Order order = orderMapper.selectByOrderId(orderId);
            ResponseVO responseVO = preCheck(order);
            if(responseVO.getSuccess()){

            }
            return responseVO;
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getOrderByUser(int userId){
        try{
            List<Order> orders = orderMapper.selectByUserId(userId);
            for(Iterator<Order> it=orders.iterator();it.hasNext();){
                preCheck(it.next());
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
        int movieId = orders.get(0).getMovieId();
        for(Iterator<Order> it=orders.iterator();it.hasNext();){
            OrderVO orderVO = new OrderVO();
            Movie movie = movieServiceForBl.getMovieById(movieId);
            orderVO.setMovieName(movie.getName());
            orderVO.setPosterURL(movie.getPosterUrl());
            String[] ticketsId = it.next().getTicketsId().split("&");
            TicketServiceImpl ticketService = new TicketServiceImpl();
            int scheduleId = ticketService.getTicketById(Integer.parseInt(ticketsId[0])).getScheduleId();
            ScheduleItem scheduleItem = scheduleServiceForBl.getScheduleItemById(scheduleId);
            orderVO.setHallId(scheduleItem.getHallId());
            orderVO.setStartTime(scheduleItem.getStartTime());
            orderVO.setEndTime(scheduleItem.getEndTime());
            int numOfTicket = ticketsId.length;
            orderVO.setNumOfTicket(numOfTicket);
            orderVO.setCost(it.next().getCost());
            List<SeatForm> seatForms = new ArrayList<>();
            for(int i = 0;i<ticketsId.length;i++){
                SeatForm seatForm = new SeatForm();
                Ticket ticket = ticketService.getTicketById(Integer.parseInt(ticketsId[i]));
                seatForm.setColumnIndex(ticket.getColumnIndex());
                seatForm.setRowIndex(ticket.getRowIndex());
                int state = ticket.getState();
                if(state > 0 && state < 3){
                    orderVO.setState(0);
                }else{
                    orderVO.setState(1);
                }
                seatForms.add(seatForm);
            }
            orderVO.setSeatFormList(seatForms);
        }
        return orderVOList;
    }

    /**
     * 退票的前置检查
     * @param order
     * @return
     */
    ResponseVO preCheck(Order order){
        List<Integer> movieIds = new ArrayList<>();
        movieIds.add(order.getMovieId());
        List<ScheduleItem> scheduleItems = scheduleServiceForBl.getScheduleByMovieIdList(movieIds);
        String[] ticketIds = order.getTicketsId().split("&");
        Date now = new Date();
        ResponseVO responseVO = ResponseVO.buildSuccess();
        if(scheduleItems.get(0).getStartTime().before(now)){
            for(int i = 0; i < ticketIds.length; i++){
                Ticket t = new Ticket();
                t.setId(Integer.parseInt(ticketIds[i]));
                t.setState(3);
                TicketServiceImpl ticketService = new TicketServiceImpl();
                ticketService.updateTicket(t.getId(),t.getState());
            }
            responseVO = ResponseVO.buildFailure(AFTER_THE_START_TIME);
        }
        return responseVO;
    }

    @Override
    public void addOrder(Order order) {
        orderMapper.insertOrder(order);
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
}
