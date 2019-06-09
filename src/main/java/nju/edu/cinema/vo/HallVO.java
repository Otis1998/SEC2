package nju.edu.cinema.vo;

import nju.edu.cinema.po.Hall;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
public class HallVO {
    private Integer id;
    private String name;
    private int[][] seats;

    public HallVO(Hall hall, int[][] seats){
        this.id = hall.getId();
        this.name = hall.getName();
        this.seats = seats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRow() {
        return seats.length;
    }

    public Integer getColumn() {
        return seats[0].length;
    }

    public int[][] getSeats() {
        return seats;
    }

    public void setSeats(int[][] seats) {
        this.seats = seats;
    }
}
