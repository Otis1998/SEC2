package nju.edu.cinema.vo;

public class MoviePlacingRateVO {
    private Integer movieId;
    /**
     * 上座率
     */
    private Double placingRate;
    private String name;

    public MoviePlacingRateVO(Integer movieId, Double placingRate, String name){
        this.movieId = movieId;
        this.placingRate = placingRate;
        this.name = name;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Double getPlacingRate() { return placingRate; }

    public void setPlacingRate(Double placingRate) {
        this.placingRate = placingRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
