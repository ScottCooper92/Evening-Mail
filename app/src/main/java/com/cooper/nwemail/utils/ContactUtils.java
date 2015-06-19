package com.cooper.nwemail.utils;

import android.content.Context;
import android.content.res.Resources;

import com.cooper.nwemail.enums.ContactGroupEnum;
import com.cooper.nwemail.enums.ContactTypeEnum;
import com.cooper.nwemail.models.ContactMethod;
import com.cooper.nwemail.models.ContactMethodType;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class ContactUtils {


    public static List<ContactMethodType> createContactList(final Context context) {
        final List<ContactMethodType> contactTypes = new ArrayList<>();

        final String packageName = context.getPackageName();
        final Resources resources = context.getResources();

        for (ContactGroupEnum type : ContactGroupEnum.values()) {
            final ContactMethodType contactMethodType = new ContactMethodType(type);
            contactMethodType.setHeader(type.name());

            final String[] titles = resources.getStringArray(
                    resources.getIdentifier(ContactGroupEnum.getTitleArray(type), "array", packageName));
            final String[] subtitles = resources.getStringArray(
                    resources.getIdentifier(ContactGroupEnum.getSubTitleArray(type), "array", packageName));
            final int[] types = resources.getIntArray(
                    resources.getIdentifier(ContactGroupEnum.getTypeArray(type), "array", packageName));


            final List<ContactMethod> methods = createMethodList(titles, subtitles, types);
            contactMethodType.setMethods(methods);
            contactTypes.add(contactMethodType);
        }

        return contactTypes;
    }

    public static List<ContactMethod> createMethodList(final String[] titles, final String[] subtitles, final int[] types) {
        final List<ContactMethod> methods = new ArrayList<>();

        for (int i = 0; i < titles.length; i++) {

            final String title = titles[i];
            String subtitle = "";
            ContactTypeEnum type = ContactTypeEnum.WEBSITE;

            if (subtitles.length > i) {
                subtitle = subtitles[i];
            }
            if (types.length > i) {
                type = ContactTypeEnum.values()[types[i]];
            }

            final ContactMethod method = new ContactMethod(title, subtitle, type);
            methods.add(method);
        }

        return methods;
    }
}
