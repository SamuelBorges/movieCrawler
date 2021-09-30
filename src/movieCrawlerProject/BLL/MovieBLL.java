package movieCrawlerProject.BLL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import movieCrawlerProject.Models.ImdbMovie;
import movieCrawlerProject.Utils.DocumentUtils;
import movieCrawlerProject.Utils.MovieComparator;

public class MovieBLL {

	private static List<ImdbMovie> movies = new ArrayList<ImdbMovie>();
	/**
	 * Get sorted movies according to elements used as reference
	 * 
	 * @param document    Document used to navigate throw elements
	 * @param titleColumn Class expression used in html to refer a title column
	 * @param rating      Class expression used in html to refer a rating column
	 * @return			  Sorted list of movies
	 */
	public static List<ImdbMovie> getMovies(Document document, String titleColumn, String ratingColumn) {
		try {
			Elements validatedTr = getValidTableElements(document, titleColumn);
			findMovies(validatedTr, titleColumn, ratingColumn);
			sortMovies();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return movies;
	}

	/**
	 * Get valid tr elements from movie´s table
	 * 
	 * @param document          Document used to navigate throw elements
	 * @param contentIdentifier Existing class expression in document to search an existing element
	 * @return 					trElements existing in the table
	 * @throws Exception
	 */
	private static Elements getValidTableElements(Document document, String classIdElement) throws Exception {
		Elements tables = document.select(DocumentUtils.TABLE);
		Elements tdTitleElements;
		Elements trElements;

		for (Element table : tables) {
			trElements = table.select(DocumentUtils.TR);
			for (Element tr : trElements) {
				tdTitleElements = tr.getElementsByClass(classIdElement);
				if (tdTitleElements.size() >= 1) {
					return trElements;
				}
			}
		}
		throw new Exception("The specified element {" + classIdElement + "} was not found on the crawl document");
	}

	/**
	 * Get all movies found in the table through valid tr OBS: The mothod does not
	 * take the first ten movies because the site can have changes on default
	 * ordering
	 * 
	 * @param validatedTr  trElements existing in the table
	 * @param titleColumn  Class expression used in html to refer a title column
	 * @param ratingColumn Class expression used in html to refer a rating column
	 * @return
	 */
	private static void findMovies(Elements validatedTr, String titleColumn, String ratingColumn) {
		for (Element tr : validatedTr) {
			Elements tdTitleElements = tr.getElementsByClass(titleColumn);
			Elements tdRatingElements = tr.getElementsByClass(ratingColumn);
			if (tdTitleElements.size() != 0 && tdRatingElements.size() != 0) {
				Element title = tdTitleElements.get(0).select(DocumentUtils.A).first();
				String urlHref = title.attr(DocumentUtils.HREF_URL);
				movies.add(new ImdbMovie(title.text(), Double.parseDouble(tdRatingElements.get(0).text()), urlHref));
			}
		}
	}

	/**
	 * Get the first ten movies ranked by worst rating
	 * 
	 * @param movies 	Movies to be sorted
	 * @return 			Ten sorted movies
	 */
	private static List<ImdbMovie> sortMovies() {
		
		Collections.sort(movies, new MovieComparator());
		movies = movies.stream().limit(10).collect(Collectors.toList());
		return movies;
	}
}
