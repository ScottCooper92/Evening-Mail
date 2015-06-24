package com.cooper.nwemail.models;

import com.cooper.nwemail.enums.ContactGroupEnum;

import java.util.List;

/**
 * A ContactMethodType represents a group of contact methods and contains a list
 * of contact methods within it.
 */
public class ContactMethodType {

    private ContactGroupEnum mContactType;

    private String header;
    private List<ContactMethod> methods;

    public ContactMethodType(final ContactGroupEnum type) {
        mContactType = type;
    }

    public ContactGroupEnum getContactType() {
        return mContactType;
    }

    public void setContactType(final ContactGroupEnum mContactType) {
        this.mContactType = mContactType;
    }

    public void setHeader(final String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public List<ContactMethod> getMethods() {
        return methods;
    }

    public void setMethods(final List<ContactMethod> methods) {
        this.methods = methods;
    }
}
