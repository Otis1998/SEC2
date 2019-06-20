package nju.edu.cinema.blImpl.sales;

import nju.edu.cinema.po.Ticket;

/**
 * Created by Wang Yuxiao on 2019/6/20.
 */
public interface TicketServiceForBl {
    /**
     * 根据电影票id获取电影票
     * @param ticketId
     * @return
     */
    Ticket getTicketByIdForBl(int ticketId);

    /**
     * 更新电影票状态
     * @param ticketId
     * @param state
     */
    void updateTicketState(int ticketId,int state);

    /**
     * 删除电影票
     * @param ticketId
     */
    void deleteTicket(int ticketId);
}
