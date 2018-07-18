package net.rimoto.intlphoneinput;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.ArrayMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CountriesFetcher {
    private static CountryList sCountries;

    /**
     * Fetch JSON from RAW resource
     *
     * @param context  Context
     * @param resource Resource int of the RAW file
     * @return JSON
     */
    private static String getJsonFromRaw(Context context, int resource) {
        String json;
        try {
            InputStream inputStream = context.getResources().openRawResource(resource);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static CountryList getCountries(Context context, @Nullable String[] countryCodes, boolean showRemainingCountries) {
        ArrayMap<String, Country> countryArrayMap = null;
        List<Country> remainingCountries = null;
        if(countryCodes != null) {
            countryArrayMap = new ArrayMap<>();
            if(showRemainingCountries) {
                remainingCountries = new ArrayList<>();
            }
        }
        sCountries = new CountryList();
        try {
            JSONArray countries = new JSONArray(getJsonFromRaw(context, R.raw.countries));
            for (int i = 0; i < countries.length(); i++) {
                try {
                    JSONObject country = (JSONObject) countries.get(i);
                    Country countryObj = new Country(country.getString("name"), country.getString("iso2"), country.getInt("dialCode"));
                    if(countryCodes == null) {
                        sCountries.add(countryObj);
                    } else {
                        countryArrayMap.put(countryObj.getIso(), countryObj);
                        if(remainingCountries != null) {
                            remainingCountries.add(countryObj);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(countryCodes != null) {
            for (String countryCode : countryCodes) {
                Country country = countryArrayMap.get(countryCode);
                if(country != null) {
                    sCountries.add(country);
                    if(remainingCountries != null) {
                        remainingCountries.remove(country);
                    }
                }
            }
            if(remainingCountries != null) {
                sCountries.addAll(remainingCountries);
            }
        }

        return sCountries;
    }


    public static class CountryList extends ArrayList<Country> {
        /**
         * Fetch item index on the list by iso
         *
         * @param iso Country's iso2
         * @return index of the item in the list
         */
        public int indexOfIso(String iso) {
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getIso().toUpperCase().equals(iso.toUpperCase())) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Fetch item index on the list by dial coder
         *
         * @param dialCode Country's dial code prefix
         * @return index of the item in the list
         */
        @SuppressWarnings("unused")
        public int indexOfDialCode(int dialCode) {
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getDialCode() == dialCode) {
                    return i;
                }
            }
            return -1;
        }
    }
}
