package nju.edu.cinema.po;

public class Seat {
    private Integer hallId;
    private Integer row;
    private Integer column;

    public Seat(){}

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public Integer getHallId() {
        return hallId;
    }
}
