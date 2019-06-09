package nju.edu.cinema.vo;

import java.util.List;

public class HallForm {

    private String name;
    private List<SeatForm> seats;
    private Integer column;
    private Integer row;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SeatForm> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatForm> seats) {
        this.seats = seats;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public void setRow(Integer row) {
        this.row = row;
    }
}
