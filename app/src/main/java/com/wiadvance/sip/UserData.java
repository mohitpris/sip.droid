package com.wiadvance.sip;

import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiadvance.sip.model.Contact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserData {

    private static final String PREF_NAME = "name";
    private static final String PREF_EMAIL = "email";
    private static final String PREF_SIP = "sip";
    private static final String PREF_DOMAIN = "domain";
    private static final String PREF_PASSWORD = "password";
    private static final String PREF_REGISTRATION_OK = "registration_ok";
    private static final String PREF_RECENT_CONTACT = "recent_contact";
    private static final String PREF_FAVORATE_CONTACT = "favorate_contact";


    public static HashBiMap<String, String> sEmailtoSipBiMap = HashBiMap.create();
    public static HashBiMap<String, String> sEmailtoPhoneBiMap = HashBiMap.create();
    public static List<Contact> sCompanyContactList = new ArrayList<>();
    public static List<Contact> sPhoneContactList = new ArrayList<>();


    public static String getName(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_NAME, null);
    }

    public static String getEmail(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_EMAIL, null);
    }

    public static String getSip(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_SIP, null);
    }

    public static String getDomain(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_DOMAIN, null);
    }

    public static String getPassword(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_PASSWORD, null);
    }

    public static boolean getRegistrationStatus(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_REGISTRATION_OK, false);
    }

    public static List<Contact> getRecentContactList(Context context) {
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_RECENT_CONTACT, new Gson().toJson(new ArrayList<Contact>()));
        return gson.fromJson(json, new TypeToken<List<Contact>>(){}.getType());
    }

    public static List<Contact> getFavorateContactList(Context context) {
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_FAVORATE_CONTACT, new Gson().toJson(new ArrayList<Contact>()));
        return gson.fromJson(json, new TypeToken<List<Contact>>(){}.getType());
    }


    public static void setName(Context context, String name) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_NAME, name).apply();
    }

    public static void setEmail(Context context, String email) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_EMAIL, email).apply();
    }

    public static void setSip(Context context, String sip) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_SIP, sip).apply();
    }

    public static void setDomain(Context context, String domain) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_DOMAIN, domain).apply();
    }

    public static void setPassword(Context context, String password) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_PASSWORD, password).apply();
    }

    public static void setRegistrationStatus(Context context, boolean isOk) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREF_REGISTRATION_OK, isOk).apply();
    }

    public static void setRecentContactList(Context context, List<Contact> contacts) {
        Gson gson = new Gson();
        String json = gson.toJson(contacts);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_RECENT_CONTACT, json).apply();
    }

    public static void setFavorateContactList(Context context, List<Contact> contacts){
        Gson gson = new Gson();
        String json = gson.toJson(contacts);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_FAVORATE_CONTACT, json).apply();
    }

    public static void clean(Context context) {
        setName(context, null);
        setEmail(context, null);
        setSip(context, null);
        setDomain(context, null);
        setPassword(context, null);
        setRegistrationStatus(context, false);
        setRecentContactList(context, new ArrayList<Contact>());

        sEmailtoSipBiMap.clear();
        sEmailtoPhoneBiMap.clear();

        sCompanyContactList.clear();
        sPhoneContactList.clear();
    }

    public static void updateRecentContact(Context context, Contact contact) {
        List<Contact> list = getRecentContactList(context);

        Iterator<Contact> i = list.iterator();
        while (i.hasNext()) {
            Contact c = i.next(); // must be called before you can call i.remove()
            if(c.equals(contact)){
                i.remove();
            }
        }

        list.add(0, contact);
        setRecentContactList(context, list);
    }

    public static void addFavoriteContact(Context context, Contact contact){
        List<Contact> list = getFavorateContactList(context);
        boolean isExist = false;

        Iterator<Contact> i = list.iterator();
        while (i.hasNext()) {
            Contact c = i.next(); // must be called before you can call i.remove()
            if(c.equals(contact)){
                isExist = true;
                break;
            }
        }

        if(!isExist){
            list.add(contact);
            setFavorateContactList(context, list);
        }
        Toast.makeText(context, "已將" + contact.getName() + "加入我的最愛", Toast.LENGTH_SHORT).show();
    }

    public static void removeFavoriteContact(Context context, Contact contact){
        List<Contact> list = getFavorateContactList(context);

        Iterator<Contact> i = list.iterator();
        while (i.hasNext()) {
            Contact c = i.next(); // must be called before you can call i.remove()
            if(c.equals(contact)){
                i.remove();
            }
        }

        setFavorateContactList(context, list);
        Toast.makeText(context, "已將" + contact.getName() + "移出我的最愛", Toast.LENGTH_SHORT).show();
    }

    public static boolean isFavoriteContact(Context context, Contact contact){
        List<Contact> list = getFavorateContactList(context);

        Iterator<Contact> i = list.iterator();
        while (i.hasNext()) {
            Contact c = i.next(); // must be called before you can call i.remove()
            if(c.equals(contact)){
                return true;
            }
        }

        return false;
    }
}
