package nju.edu.cinema.po;
/**
 * 
 * @author pzq
 *
 */
public class MovieRecentFare {
	private Integer movieId;
	private String name;
	/**
	 * 最近票房
	 */
	private double RecentFare;
	public Integer getMovieId() {
		return movieId;
	}
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getRecentFare() {
		return RecentFare;
	}
	public void setRecentFare(double recentFare) {
		RecentFare = recentFare;
	}
	
}
