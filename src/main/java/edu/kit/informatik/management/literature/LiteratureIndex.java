package edu.kit.informatik.management.literature;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Manages the litrature index of a publication.
 * <p>
 * This class manages a collection of articles cited in the
 * article its a filed of.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class LiteratureIndex {

    private TreeSet<Article> litratureIndex;

    public LiteratureIndex() {
        this.litratureIndex = new TreeSet<>(new Comparator<Article>() {
            @Override
            public int compare(final Article o1, final Article o2) {
                Iterator<Author> iterO1 = o1.getAuthors().iterator(),
                        iterO2 = o2.getAuthors().iterator();
                while (iterO1.hasNext() && iterO2.hasNext()) {
                    Author a = iterO1.next();
                    Author b = iterO2.next();

                    if (a.compareTo(b) == 0) {
                        continue;
                    } else {
                        return a.compareTo(b);
                    }
                }
                if (iterO1.hasNext()) {
                    return -1;
                }
                if (iterO2.hasNext()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    /**
     * Adds a citation to the list.
     *
     * @param citedArticle
     *         the article that was cited.
     */
    public void addCitation(final Article citedArticle) {
        if (citedArticle.isComplete())
    }
}
