package learningresourcefinder.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import learningresourcefinder.util.DateUtil;

import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<Date>{

	@Override
	public String print(Date date, Locale locale) {
		return DateUtil.formatyyyyMMdd(date);
	}

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
			return DateUtil.parseyyyyMMddWithParseException(text);
	}

}
