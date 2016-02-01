package uk.co.dashery.clothingquery.clothing;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.CharMatcher;
import com.google.common.collect.Sets;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.dashery.clothingquery.clothing.tag.Tag;

import java.util.*;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class Clothing {

    public static final CharMatcher PUNCTUATION_MATCHER = CharMatcher.anyOf(",. \n\t\\\"'][#*:()-`’");
    public static final int NAME_SCORE_MULTIPLIER = 10;
    public static final int SEARCHABLE_TEXT_SCORE_MULTIPLIER = 1;

    @Id
    private String id;
    private String brand;
    private String name;
    private int price;
    private String link;
    private String imageLink;
    private Set<Tag> tags;

    public Clothing() {
        this(null, null, null, 0, null, null, (String) null);
    }

    public Clothing(String id) {
        this(id, null, null, 0, null, null, (String) null);
    }

    public Clothing(String id, String brand, String name, int price, String link, String imageLink,
                    String searchableText) {
        this.tags = new HashSet<>();

        setId(id);
        setBrand(brand);
        setName(name);
        setPrice(price);
        setLink(link);
        setImageLink(imageLink);
        setSearchableText(searchableText);
    }

    @PersistenceConstructor
    protected Clothing(String id, String brand, String name, int price, String link, String
            imageLink, Set<Tag> tags) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.link = link;
        this.imageLink = imageLink;
        this.tags = tags;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonProperty
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("brand")
    public String getBrand() {
        return brand;
    }

    @JsonProperty("merchant")
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
        addNewTags(name, NAME_SCORE_MULTIPLIER);
    }

    @JsonIgnore
    public Set<Tag> getTags() {
        return tags;
    }

    @JsonProperty("description")
    public void setSearchableText(String description) {
        addNewTags(description, SEARCHABLE_TEXT_SCORE_MULTIPLIER);
    }

    private void addNewTags(String text, int multiplier) {
        if (text != null) {
            List<String> words = getWordsFromText(text);
            HashSet<String> wordSet = Sets.newHashSet(words);
            wordSet.forEach(word -> addTag(word, words, multiplier));
        }
    }

    private List<String> getWordsFromText(String text) {
        String unescapedText = Parser.unescapeEntities(text, true);
        String parsedHtml = Jsoup.parse(unescapedText).text();
        String lowerCaseParsedHtml = parsedHtml.toLowerCase();
        String[] untrimmedWords = lowerCaseParsedHtml.split(" ");
        return Arrays.stream(untrimmedWords)
                .map(untrimmedWord -> PUNCTUATION_MATCHER.trimFrom(untrimmedWord))
                .collect(Collectors.toList());
    }

    private void addTag(String value, List<String> values, int multiplier) {
        int occurrences = Collections.frequency(values, value);
        int score = occurrences * multiplier;

        addScoreToTag(value, score);
    }

    private void addScoreToTag(String value, int score) {
        Optional<Tag> existingTag = this.tags.stream()
                .filter(tag -> tag.getValue().equals(value))
                .findFirst();

        if (existingTag.isPresent()) {
            existingTag.get().addToScore(score);
        } else {
            this.tags.add(new Tag(value, score));
        }
    }

    public int compareRelevance(Clothing otherClothing, String... tags) {
        int relevance = getRelevance(tags);
        int otherRelevance = otherClothing.getRelevance(tags);

        if (relevance == otherRelevance) {
            return 0;
        } else if (relevance < otherRelevance) {
            return -1;
        } else {
            return 1;
        }
    }

    private int getRelevance(String... tags) {
        return this.tags.stream()
                .filter(tag -> Arrays.asList(tags).contains(tag.getValue()))
                .mapToInt(Tag::getScore)
                .sum();
    }
}
