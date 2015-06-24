package com.cooper.nwemail.models;

import com.cooper.nwemail.enums.ContactTypeEnum;

/**
 * A POJO representing a contact method
 */
public class ContactMethod {
    public String heading;
    public String subheading;
    public ContactTypeEnum mType;

    public ContactMethod(final String heading, final String subheading, final ContactTypeEnum type) {
        this.heading = heading;
        this.subheading = subheading;
        this.mType = type;
    }
}
