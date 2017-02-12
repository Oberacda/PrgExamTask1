package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.*;

/**
 * @author David Oberacker
 */

public class Conference implements Entity {

    //=================Fields==========================

    private final int year;

    private final String location;

    private TreeMap<String, Article> conferencePublications;

    private TreeSet<String> keywordsList;

    //=================Constructor======================

    public Conference(final int year, final String location, final SortedSet<String> keywords) {
        this.year = year;
        this.location = location;
        this.conferencePublications = new TreeMap<>(String::compareTo);
        this.keywordsList = new TreeSet<>(keywords);
    }

    //=================Getter===========================

    public int getYear() {
        return this.year;
    }

    public String getLocation() {
        return this.location;
    }

    public Article getArticle(final String id)
            throws NoSuchElementException {
        if (this.conferencePublications.containsKey(id)) {
            return this.conferencePublications.get(id);
        } else {
            throw new NoSuchElementException("there is no article"
                    + " with this id!");
        }
    }

    //=================Methods==========================

    public void addArticle(final String id, final String title)
            throws IllegalArgumentException {
        if (this.conferencePublications.containsKey(id)) {
            throw new IllegalArgumentException("there already is a article with this id!");
        } else {
            this.conferencePublications.put(id, new Article(id, title,
                    this.getYear(),
                    this.keywordsList.descendingSet()));
        }
    }

    //=================Override Methods=================

    @Override
    public void addKeyword(final String keyword) throws IllegalArgumentException {
        if (PatternHolder.KEYWORDPATTERN.matcher(keyword).matches()) {
            this.keywordsList.add(keyword);

            for (Article a:this.conferencePublications.values()) {
                a.addKeyword(keyword);
            }
        } else {
            throw new IllegalArgumentException(String.format("keyword does not match"
                            + " requirements : %s !",
                    PatternHolder.KEYWORDPATTERN.pattern()));
        }
    }

    @Override
    public SortedSet<String> getKeywords() {
        return this.keywordsList.descendingSet();
    }
}

