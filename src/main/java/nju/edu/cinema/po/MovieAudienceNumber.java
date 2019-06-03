package nju.edu.cinema.po;

public class MovieAudienceNumber {
    private Integer movieId;
    /**
     * 观众人数
     */
    private Integer audienceNumber;
    private String name;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getAudienceNumber() {
        return audienceNumber;
    }

    public void setAudienceNumber(Integer audienceNumber) {
        this.audienceNumber = audienceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
