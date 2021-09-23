package movieCrawlerProject.Utils;

import java.util.Comparator;

import movieCrawlerProject.Models.ImdbMovie;

public class MovieComparator implements Comparator<ImdbMovie>{

	@Override
	public int compare(ImdbMovie movie, ImdbMovie anotherMovie) {
		return movie.getRatedRanking() < anotherMovie.getRatedRanking() ? -1 : movie.getRatedRanking() == anotherMovie.getRatedRanking() ? 0 : 1;
	}
}
