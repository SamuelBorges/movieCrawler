package movieCrawlerProject.Crawler;

import movieCrawlerProject.BLL.MovieDetailsBLL;
import movieCrawlerProject.Models.Detail;
import movieCrawlerProject.Models.ImdbMovie;

public class PositiveCommentCrawler implements Runnable{
	private Thread thread;
	private ImdbMovie movie;
	
	public PositiveCommentCrawler(ImdbMovie movie) {
		this.movie = movie;
		this.newThread();
	}
	
	private void newThread() {
		thread = new Thread(this);
		thread.start();
	}

	public Thread getThread() {
		return thread;
	}
	@Override
	public void run() {
		movie.setDetails(MovieDetailsBLL.findPositiveComment(movie.getDetail()));
	}

}
