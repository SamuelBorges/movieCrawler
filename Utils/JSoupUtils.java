package movieCrawlerProject.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import movieCrawlerProject.Enum.Languages;

public class JSoupUtils {

	/**
	 * Get documented page according to desired language
	 * 
	 * @param language Language in which the document will be retrieved
	 * @param url      Url used to retrieve the document
	 * @return Document retrieved
	 */
	public static Document getDocument(Languages language, String url) {
		Document document = null;

		try {
			Connection connection = Jsoup.connect(url).header(DocumentUtils.HTML_LANGUAGE_HEADER, language.language);
			document = connection.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
}
