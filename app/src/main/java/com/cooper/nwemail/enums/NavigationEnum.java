package com.cooper.nwemail.enums;

import com.cooper.nwemail.R;
import com.cooper.nwemail.constants.Constants;

/**
 * An Enum representing each item in the Navigation Drawer, also includes static methods for getting
 * information associated with each item.
 * <p/>
 * Note that the position of the enum in the list denotes its corresponding items position in the nav drawer
 */
public enum NavigationEnum {

    NEWS,
    BARROW_AFC,
    BARROW_RAIDERS,
    FOOTBALL,
    RUGBY_LEAGUE,
    CRICKET,
    JUNIOR_SPORT,
    OTHER_SPORT,
    CONTACT,
    ABOUT,
    SETTINGS;

    /**
     * Returns the String ID for this Navigation Enum
     */
    public static int getName(final NavigationEnum navigationEnum) {

        int name = R.string.label_news;

        switch (navigationEnum) {
            case NEWS:
                name = R.string.label_news;
                break;
            case BARROW_AFC:
                name = R.string.label_afc;
                break;
            case BARROW_RAIDERS:
                name = R.string.label_raiders;
                break;
            case FOOTBALL:
                name = R.string.label_football;
                break;
            case RUGBY_LEAGUE:
                name = R.string.label_rugby;
                break;
            case CRICKET:
                name = R.string.label_cricket;
                break;
            case JUNIOR_SPORT:
                name = R.string.label_junior_sport;
                break;
            case OTHER_SPORT:
                name = R.string.label_other_sport;
        }

        return name;
    }

    /**
     * Returns the URL associated with this item
     */
    public static String getFeedId(final NavigationEnum navigationEnum) {

        String id = "";

        switch (navigationEnum) {
            case NEWS:
                id = Constants.URL_NEWS;
                break;
            case BARROW_AFC:
                id = Constants.URL_AFC;
                break;
            case BARROW_RAIDERS:
                id = Constants.URL_RAIDERS;
                break;
            case FOOTBALL:
                id = Constants.URL_FOOTBALL;
                break;
            case RUGBY_LEAGUE:
                id = Constants.URL_LEAGUE;
                break;
            case CRICKET:
                id = Constants.URL_CRICKET;
                break;
            case JUNIOR_SPORT:
                id = Constants.URL_JUNIOR;
                break;
            case OTHER_SPORT:
                id = Constants.URL_OTHER;
                break;
        }

        return id;
    }
}
