package movieCrawlerProject.Models;

public class ImdbMovie {

	private String title;
	
	private double ratedRanking;
	
	private Detail detail;
	
	private String urlMovie;
	

	public ImdbMovie(String title, double ratedRanking, String urlMovie) {
		this.setTitle(title);
		this.setRatedRanking(ratedRanking);
		this.setUrlMovie(urlMovie);
		this.setDetail(new Detail(urlMovie));
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getRatedRanking() {
		return ratedRanking;
	}

	public void setRatedRanking(double ratedRanking) {
		this.ratedRanking = ratedRanking;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	public String getUrlMovie() {
		return urlMovie;
	}

	public void setUrlMovie(String urlMovie) {
		this.urlMovie = urlMovie;
	}
	
}
