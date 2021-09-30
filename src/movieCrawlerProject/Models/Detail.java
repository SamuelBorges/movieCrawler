package movieCrawlerProject.Models;

import java.util.ArrayList;
import java.util.List;

public class Detail {

	private String detailUrl;
	
	private String commentsUrl;
	
	private List<String> director;
	
	private List<String> mainCast;
	
	private String positiveComment;

	
	public Detail(String urlMovie) {
		this.setDetailUrl(urlMovie);
		this.setDirector(new ArrayList<String>());
		this.setMainCast(new ArrayList<String>());
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getCommentsUrl() {
		return commentsUrl;
	}

	public void setCommentsUrl(String commentsUrl) {
		this.commentsUrl = commentsUrl;
	}

	public List<String> getDirector() {
		return director;
	}
	
	public void setDirector(List<String> director) {
		this.director = director;
	}	

	public void addDirector(String director) {
		this.director.add(director);
	}

	public List<String> getMainCast() {
		return mainCast;
	}

	public void setMainCast(List<String> mainCast) {
		this.mainCast = mainCast;
	}
	
	public void addMainCast(String mainCast) {
		this.mainCast.add(mainCast);
	}

	public String getPositiveComment() {
		return positiveComment;
	}

	public void setPositiveComment(String positiveComment) {
		this.positiveComment = positiveComment;
	}
	
	
	
}
