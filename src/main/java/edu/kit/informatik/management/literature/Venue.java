package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author David Oberacker
 */
public abstract class Venue implements Entity {
    private final String title;

    private TreeSet<String> keywords;

    public Venue(final String title) throws IllegalArgumentException {
        if (title != null && PatternHolder.NAMEPATTERN.matcher(title).matches()) {
            this.title = title;
        } else {
            throw new IllegalArgumentException("The title of a venue"
                    + " can only be a sequence of the chars [a-zA-Z]");
        }

        this.keywords = new TreeSet<>();
    }

    public String getTitle() {
        return this.title;
    }

    public TreeSet<String> getKeywordsTree() {
        return this.keywords;
    }

    @Override
    public SortedSet<String> getKeywords() {
        return this.getKeywordsTree().descendingSet();
    }

    public abstract SortedSet<Article> getArticles();

}
