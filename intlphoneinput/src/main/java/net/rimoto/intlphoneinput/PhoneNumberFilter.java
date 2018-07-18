package net.rimoto.intlphoneinput;

import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class PhoneNumberFilter implements InputFilter {

    private final PhoneNumberUtil phoneNumberUtil;
    private final IntlPhoneInput intlPhoneInput;

    public PhoneNumberFilter(PhoneNumberUtil phoneNumberUtil, IntlPhoneInput intlPhoneInput) {
        this.phoneNumberUtil = phoneNumberUtil;
        this.intlPhoneInput = intlPhoneInput;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        CharSequence ret;
        CharSequence filteredSource = filterNonDigits(source);

        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(source.toString(), null);
            if(phoneNumberUtil.isValidNumber(phoneNumber)) {
                String regionCode = phoneNumberUtil.getRegionCodeForNumber(phoneNumber);
                if(regionCode != null) {
                    intlPhoneInput.setEmptyDefault(regionCode);
                }
                return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            }
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        ret = filteredSource;
//        Log.d("PhoneNumberFilter", "ret: " + ret + ", source: " + source + "[" + start + "," + end + "], dst: " + dest + "[" + dstart + "," + dend + "]");
        return ret;
    }

    static CharSequence filterNonDigits(@Nullable CharSequence source) {
        StringBuilder sb = new StringBuilder();
        if(source != null) {
            for (int i = 0; i < source.length(); i++) {
                char c = source.charAt(i);
                if(Character.isDigit(c)) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
