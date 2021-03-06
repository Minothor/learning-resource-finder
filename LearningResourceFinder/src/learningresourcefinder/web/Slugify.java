package learningresourcefinder.web;

/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
import java.text.Normalizer;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

public class Slugify {
	public static String slugify(String input) {
		String ret = StringUtils.trim(input);
		if (StringUtils.isBlank(input)) {
			return "";
		}
		ret = Jsoup.parse(ret).text();  // In case the string contains some html, such as <em>.
		ret = normalize(ret);
		ret = removeSmallWords(ret);
		ret = removeDuplicateWhiteSpaces(ret);
		ret = StringUtils.stripAccents(ret);  // It seems to be bad practice to have accents in slugs: ê --> e
		return ret.replace(" ", "-").toLowerCase();
	}

	private static String normalize(String input) {
		String ret = StringUtils.trim(input);
		if (StringUtils.isBlank(ret)) {
			return "";
		}
		ret = ret.replace("ß", "ss");
		ret = Normalizer.normalize(ret, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "")
				.replaceAll("[^a-zA-Z0-9 ]", "");
		return ret;
	}

	private static String removeDuplicateWhiteSpaces(String input) {
		String ret = StringUtils.trim(input);
		if (StringUtils.isBlank(ret)) {
			return "";
		}

		return ret.replaceAll("\\s+", " ");
	}

	private static String removeSmallWords(String string) {
		String[] smallWords = { "les", "la", "de", "le", "un", "une", "des",
				"ce", "ces", "cette" };
		for (String smallWord : smallWords) {
			string = string.toLowerCase().replaceAll("\\b" + smallWord + "\\b",
					"");
		}

		return string;
	}

}
