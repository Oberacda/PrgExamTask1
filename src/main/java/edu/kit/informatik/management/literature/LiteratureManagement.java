package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresent;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author David Oberacker
 */
public class LiteratureManagement {
    private ArrayList<ConferenceSeries> conferenceSeriesList;

    private ArrayList<Journal> journalsList;

    private ArrayList<Author> authorsList;

    public LiteratureManagement() {
        this.authorsList = new ArrayList<>();
        this.journalsList = new ArrayList<>();
        this.conferenceSeriesList = new ArrayList<>();
    }

    public void addJournal(final String name, final String publisher)
            throws ElementAlreadyPresent {
        Journal newJournal = new Journal(name, publisher);
        if (!(this.journalsList.contains(newJournal))) {
            this.journalsList.add(newJournal);
        } else {
            throw new ElementAlreadyPresent("This journal is already present!");
        }
    }

    public void addConferenceToSeries(final String conferenceSeriesTitle,
                                      final String conferenceLocation,
                                      final int conferenceYear)
            throws NoSuchElementException, ElementAlreadyPresent {
        Optional<ConferenceSeries> conferenceSeriesOptional
                = this.getConferenceSeries(conferenceSeriesTitle);

        if (!(conferenceSeriesOptional.isPresent())) {
            throw new NoSuchElementException("There is no conference series with this title!");
        }

        ConferenceSeries conferenceSeries = conferenceSeriesOptional.get();
        if (!(conferenceSeries.getConference(conferenceYear).isPresent())) {
            conferenceSeries.addConference(conferenceYear, conferenceLocation);
        } else {
            throw new ElementAlreadyPresent(String.format("There already is a conference"
                    + " in the year %4d in this series!", conferenceYear));
        }
    }

    public void addConferenceSeries(final String title)
    throws ElementAlreadyPresent {
        ConferenceSeries newConferenceSeries = new ConferenceSeries(title);
        if (!(this.conferenceSeriesList
                .contains(newConferenceSeries))) {
            this.conferenceSeriesList.add(newConferenceSeries);
        } else {
            throw new ElementAlreadyPresent("This conference series is already present!");
        }
    }

    private Optional<ConferenceSeries> getConferenceSeries(final String title) {
        return this.conferenceSeriesList.stream().filter(conferenceSeries ->
                title.equals(conferenceSeries.getTitle())).findFirst();
    }

    private Optional<Journal> getJournal(final String title) {
        return this.journalsList.stream().filter(journal ->
                title.equals(journal.getTitle())).findFirst();
    }

    private Optional<Conference> getConferenceFromSeries(final String seriesTitle,
                                                         final int conferenceYear) {
        Optional<ConferenceSeries> seriesOptional = this.getConferenceSeries(seriesTitle);
        if (seriesOptional.isPresent()) {
            return  seriesOptional.get().getConference(conferenceYear);
        } else {
            return Optional.empty();
        }

    }
}
