package nju.edu.cinema.vo;

import nju.edu.cinema.po.MovieRecentFare;
/**
 * 
 * @author pzq
 *
 */
public class MovieRecentFareVO {
	private Integer movieId;
	private String name;
	/**
	 * 最近票房
	 */
	private double RecentFare;
	public MovieRecentFareVO(MovieRecentFare movieRecentFare) {
		this.movieId=movieRecentFare.getMovieId();
		this.name=movieRecentFare.getName();
		this.RecentFare=movieRecentFare.getRecentFare();
	}
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
