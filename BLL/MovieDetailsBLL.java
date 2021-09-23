package movieCrawlerProject.BLL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import movieCrawlerProject.Crawler.MemberCrawler;
import movieCrawlerProject.Crawler.PositiveCommentCrawler;
import movieCrawlerProject.Enum.Languages;
import movieCrawlerProject.Models.Detail;
import movieCrawlerProject.Models.ImdbMovie;
import movieCrawlerProject.Utils.DocumentUtils;
import movieCrawlerProject.Utils.JSoupUtils;

public class MovieDetailsBLL {
	
	/**
	 * Set participants information in details with multi thread
	 * 
	 * @param movies	movies to be added participants details
	 */
	public static void setMemberDetails(List<ImdbMovie> movies ) {
		
		Set<MemberCrawler> crawler = new HashSet<MemberCrawler>();
		for (ImdbMovie imdbMovie : movies) {
			crawler.add(new MemberCrawler(imdbMovie));
		}
		
		for (MemberCrawler crawl : crawler) {
			try {
				crawl.getThread().join();
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Set a positive comment in movie´s details with multi thread
	 * 
	 * @param movies	movies to be added positive comment
	 */
	public static void setPositiveComment(List<ImdbMovie> movies ) {
		
		Set<PositiveCommentCrawler> crawler = new HashSet<PositiveCommentCrawler>();
		for (ImdbMovie imdbMovie : movies) {
			crawler.add(new PositiveCommentCrawler(imdbMovie));
		}
		
		for (PositiveCommentCrawler crawl : crawler) {
			try {
				crawl.getThread().join();
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	/**
	 * Find informations in the details
	 * 
	 * @param detailsDocument	Document with details
	 * @param detail			Detail from movie
	 */
	public static void organizeDetailInformation(Document detailsDocument, Detail detail) {
		findParticipantsInformation(detailsDocument, detail);
		findCommentsUrl(detailsDocument, detail);
	}

	/**
	 * Find url that references the comments section
	 * 
	 * @param detailsDocument	Document with details
	 * @param detail			Detail from movie
	 */
	
	private static void findCommentsUrl(Document detailsDocument, Detail detail) {
		Elements userReviews = detailsDocument.getElementsByAttributeValue(DocumentUtils.TESTID, DocumentUtils.REVIEWS_CLASS);
		Element aUserRevies = userReviews.select(DocumentUtils.A).first();
		String href = aUserRevies.attr(DocumentUtils.HREF_URL);
		detail.setCommentsUrl(filterUrlConnection(href));
	}

	/**
	 * Find a positive comment in the movie comment section
	 * 
	 * @param detail			Detail from movie
	 * @return					Comment found with another details
	 */
	
	public static Detail findPositiveComment(Detail detail) {

		Document commentDocument = JSoupUtils.getDocument(Languages.ENGLISH, detail.getCommentsUrl());
		Elements ratingValues = commentDocument.getElementsByClass(DocumentUtils.RATING_CLASS);

		for (Element ratingValue : ratingValues) {
			Element value = ratingValue.selectFirst(DocumentUtils.SPAN_FROM_SPAN);
			double rated = Double.parseDouble(value.text());
			if (rated >= 5) {
				Element content = ratingValue.getElementsByClass(DocumentUtils.RATING_CONTENT_CLASS).first();
				detail.setPositiveComment(content.text());
				break;
			}
		}
		return detail;
	}

	/**
	 * Treatment to access positive comments page
	 * 
	 * @param href	Pattern url found
	 * @return		Url to positive comments section
	 */
	private static String filterUrlConnection(String href) {
		return href.replaceAll(href.substring(href.lastIndexOf("?") + 1), DocumentUtils.FILTERED_URL);
	}

	/**
	 * Find participants informations
	 * 
	 * @param detailsDocument	Document with details
	 * @param detail			Existing details		
	 */
	private static void findParticipantsInformation(Document detailsDocument, Detail detail) {

		Element span;
		Elements liDeirectors;
		Element aStars;
		Elements liStars;
		Elements uls;

		uls = detailsDocument.getElementsByAttributeValue(DocumentUtils.TESTID, DocumentUtils.CREDITS_CLASS);

		for (Element ul : uls) {
			span = ul.select(DocumentUtils.SPAN).first();
			if (span != null && (span.text().equals("Directors") || span.text().equals("Director"))) {
				liDeirectors = ul.select(DocumentUtils.LI_FROM_UL);
				for (Element liDirector : liDeirectors) {
					detail.addDirector(liDirector.selectFirst(DocumentUtils.A).text());
				}
			} else {
				aStars = ul.select(DocumentUtils.A).first();
				if (aStars.text().equals("Stars")) {
					liStars = ul.select(DocumentUtils.LI_FROM_UL);
					for (Element liStar : liStars) {
						detail.addMainCast(liStar.text());
					}
					break;
				}
			}

		}
	}
}
