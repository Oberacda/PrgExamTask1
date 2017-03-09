package edu.kit.informatik.management.literature.interfaces;

import edu.kit.informatik.management.literature.Publication;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * A venue has articles and can return them.
 * <p>
 * A venue makes sure that a class can return
 * the articles published by it or its managed
 * subclasses (not meant in the java inheritance model,
 * more like the
 * {@link edu.kit.informatik.management.literature.Conference}
 * and the
 * {@link edu.kit.informatik.management.literature.ConferenceSeries}).
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public interface Venue {

    /**
     * Returns a stream of all publications published by the publisher.
     * <p>
     * This stream can contain no elements if nothing was published.
     * </p>
     *
     * @return Sorted stream of publications.
     */
    Stream<Publication> getPublications();


    /**
     * Returns a article form a conference form this series.
     *
     * @param id
     *         the id of the desired article.
     * @return article from the conference with this id.
     */
    Optional<Publication> getPublication(String id);

}
