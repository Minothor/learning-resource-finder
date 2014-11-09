package learningresourcefinder.search;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.charfilter.MappingCharFilter;
import org.apache.lucene.analysis.charfilter.NormalizeCharMap;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

public final class MyAnalyzer extends StopwordAnalyzerBase {
    private int maxTokenLength = 255;
    public static final CharArraySet ENGLISH_STOP_WORDS_SET;
    static {
        final List<String> stopWords = Arrays.asList("a", "an", "and", "are",
                "as", "at", "be", "but", "by", "for", "if", "in", "into", "is",
                "it", "no", "not", "of", "on", "or", "such", "that", "the",
                "their", "then", "there", "these", "they", "this", "to", "was",
                "will", "with", "http", "https", "www");
        final CharArraySet stopSet = new CharArraySet(Version.LUCENE_40,
                stopWords, false);
        ENGLISH_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
    }

    public MyAnalyzer() {
        super(Version.LUCENE_40, ENGLISH_STOP_WORDS_SET);
    }

    @Override
    protected TokenStreamComponents createComponents(final String fieldName,
            final Reader reader) {
        final StandardTokenizer src = new StandardTokenizer(Version.LUCENE_40,
                reader);
        src.setMaxTokenLength(maxTokenLength);
        TokenStream tok = new StandardFilter(Version.LUCENE_40, src);
        //this filter makes the search case insensitive.
        tok = new LowerCaseFilter(Version.LUCENE_40, tok);
        //this filter makes the search return no results for the words contained in the "stopwords" list.
        tok = new StopFilter(Version.LUCENE_40, tok, stopwords);
        //this filter makes the search accent insensitive.
        tok = new ASCIIFoldingFilter(tok);

        return new TokenStreamComponents(src, tok) {
            @Override
            protected void setReader(final Reader reader) throws IOException {
                src.setMaxTokenLength(MyAnalyzer.this.maxTokenLength);
                super.setReader(reader);
            }
        };
    }
    //Converts periods and underscores to spaces so composed words separated by dots (such as URL) or underscores can be tokenized.
    @Override
    protected Reader initReader(String fieldName, Reader reader) {
        NormalizeCharMap.Builder builder = new NormalizeCharMap.Builder();
        builder.add(".", " ");
        builder.add("_", " ");
        NormalizeCharMap normMap = builder.build();
        return new MappingCharFilter(normMap, reader);
    }
}