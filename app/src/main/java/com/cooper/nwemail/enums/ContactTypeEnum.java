package com.cooper.nwemail.enums;

import android.content.Intent;
import android.net.Uri;

import com.cooper.nwemail.R;
import com.cooper.nwemail.models.ContactMethod;

/**
 * TODO
 */
public enum ContactTypeEnum {

    ADDRESS,
    EMAIL,
    PHONE,
    WEBSITE,
    FACEBOOK,
    TWITTER;

    public static int getIcon(final ContactTypeEnum type) {
        switch (type) {
            case ADDRESS:
                return R.drawable.ic_place_red_a700_48dp;
            case PHONE:
                return R.drawable.ic_phone_red_a700_48dp;
            case EMAIL:
                return R.drawable.ic_email_red_a700_48dp;
            case WEBSITE:
                return R.drawable.ic_web_48dp;
            case FACEBOOK:
                return R.drawable.ic_facebook_48;
            case TWITTER:
                return R.drawable.ic_twitter_48;
            default:
                return R.drawable.ic_email_red_a700_48dp;
        }
    }

    public static Intent createIntent(final ContactMethod method) {
        Intent intent = new Intent();
        switch (method.mType) {
            case ADDRESS:
                final Uri uri = Uri.parse("geo:0,0?q=" + method.heading + ", " + method.subheading);
                intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                break;
            case PHONE:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + method.subheading));
                break;
            case EMAIL:
                intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{method.subheading});
                break;
            case WEBSITE:
            case FACEBOOK:
            case TWITTER:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(method.subheading));
                break;
        }
        return intent;
    }

}
