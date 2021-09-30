package movieCrawlerProject.Crawler;

import org.jsoup.nodes.Document;

import movieCrawlerProject.BLL.MovieDetailsBLL;
import movieCrawlerProject.Enum.Languages;
import movieCrawlerProject.Models.Detail;
import movieCrawlerProject.Models.ImdbMovie;
import movieCrawlerProject.Utils.JSoupUtils;

public class MemberCrawler implements Runnable {

	private Thread thread;
	private ImdbMovie movie;
	private static Detail details;

	public MemberCrawler(ImdbMovie movie) {
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
		details = getMembersDetails(movie.getDetail());
		movie.setDetail(details);
	}

	private static Detail getMembersDetails(Detail detail) {
		Document detailsDocument = JSoupUtils.getDocument(Languages.ENGLISH, detail.getDetailUrl());
		MovieDetailsBLL.organizeDetailInformation(detailsDocument, detail);
		return detail;
	}

}
