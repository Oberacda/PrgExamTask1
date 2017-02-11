package edu.kit.informatik.management.literature;

import java.util.*;

/**
 * @author David Oberacker
 */
public class ConferenceSeries extends Venue  {
    private TreeSet<Conference> conferenceList;

    public ConferenceSeries(final String title) {
        super(title);
        this.conferenceList = new TreeSet<>(Comparator.comparingInt(Conference::getYear));
    }

    public void addConference(final int year, final String location)
            throws IllegalArgumentException{
        for (Conference c : this.conferenceList) {
            if(c.getYear() == year) {
                throw new IllegalArgumentException(String.format("There is already"
                        + " a conference in the year %4d", year));
            }
        }
        this.conferenceList.add(new Conference(year, location));
    }

    @Override
    public SortedSet<Article> getArticles() {
        return null;
    }

    private class Conference {
        private final int year;

        private final String location;

        public Conference(final int year, final String location) {
            this.year = year;
            this.location = location;
        }

        public int getYear() {
            return this.year;
        }

        public String getLocation() {
            return this.location;
        }
    }
}
