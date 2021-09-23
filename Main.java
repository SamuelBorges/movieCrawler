package movieCrawlerProject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;

import movieCrawlerProject.BLL.MovieBLL;
import movieCrawlerProject.BLL.MovieDetailsBLL;
import movieCrawlerProject.Enum.Languages;
import movieCrawlerProject.Models.Detail;
import movieCrawlerProject.Models.ImdbMovie;
import movieCrawlerProject.Utils.DocumentUtils;
import movieCrawlerProject.Utils.JSoupUtils;

public class Main {

	public static void main(String[] args) {

		String url;
		Document document;
		List<ImdbMovie> movies = new ArrayList<ImdbMovie>();
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		
		//Param for rules
		url = "https://www.imdb.com/chart/bottom";

		
		document = JSoupUtils.getDocument(Languages.ENGLISH, url);
		movies = MovieBLL.getMovies(document, DocumentUtils.TITLE_COLUMN, DocumentUtils.IMDB_RATING);
		
		MovieDetailsBLL.setMemberDetails(movies);
		MovieDetailsBLL.setPositiveComment(movies);
		
		
		int index = 1;
		for (ImdbMovie movie : movies) {
			Detail detail = movie.getDetail();
			
			System.out.println("Posição: " + index++ + "° lugar");
			System.out.println("Nome do Filme: " + movie.getTitle());
			System.out.println("Nota: " + decimalFormat.format(movie.getRatedRanking()));
			System.out.println("Diretor(es): " + detail.getDirector());
			System.out.println("Elenco principal: " + detail.getMainCast());
			System.out.println("Comentário positivo: " + detail.getPositiveComment());
			System.out.println("");
			System.out.println("---------------------------------------------------------");
			System.out.println("");
		}

	}

}
