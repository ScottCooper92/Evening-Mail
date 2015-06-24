package com.cooper.nwemail.enums;

/**
 * The ContactGroupEnum represents the different groups of contact methods available.
 * <p/>
 * Contains util methods for finding String Arrays associated with each enum.
 */
public enum ContactGroupEnum {

    ADDRESS,
    PHONE,
    EMAIL,
    SOCIAL,
    COMPLAINTS;

    public static String getTitleArray(final ContactGroupEnum type) {
        switch (type) {
            case ADDRESS:
                return "addressTitles";
            case PHONE:
                return "phoneTitles";
            case EMAIL:
                return "emailTitles";
            case SOCIAL:
                return "socialTitles";
            case COMPLAINTS:
                return "complaintTitles";
            default:
                return "";
        }
    }

    public static String getSubTitleArray(final ContactGroupEnum type) {
        switch (type) {
            case ADDRESS:
                return "addressSubtitles";
            case PHONE:
                return "phoneSubtitles";
            case EMAIL:
                return "emailSubtitles";
            case SOCIAL:
                return "socialSubtitles";
            case COMPLAINTS:
                return "complaintSubtitles";
            default:
                return "";
        }
    }

    public static String getTypeArray(ContactGroupEnum type) {
        switch (type) {
            case ADDRESS:
                return "addressTypes";
            case PHONE:
                return "phoneTypes";
            case EMAIL:
                return "emailTypes";
            case SOCIAL:
                return "socialTypes";
            case COMPLAINTS:
                return "complaintTypes";
            default:
                return "";
        }
    }
}
