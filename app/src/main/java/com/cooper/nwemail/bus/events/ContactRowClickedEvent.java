package com.cooper.nwemail.bus.events;

import com.cooper.nwemail.enums.ContactGroupEnum;
import com.cooper.nwemail.models.ContactMethod;

/**
 * An Object representing a Contact click event, lets listeners know the contact method and the type
 */
public class ContactRowClickedEvent {

    private final ContactGroupEnum mType;
    private final ContactMethod mMethods;

    public ContactRowClickedEvent(final ContactGroupEnum type, final ContactMethod methods) {
        mType = type;
        this.mMethods = methods;
    }

    public ContactMethod getMethods() {
        return mMethods;
    }

    public ContactGroupEnum getType() {
        return mType;
    }
}
