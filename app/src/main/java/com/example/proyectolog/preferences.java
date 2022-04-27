package com.example.proyectolog;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class preferences
{
    private static final String Data_login = "status_login", DATA_AS = "As";
    private static final String Data_User = "status_user";

    private static SharedPreferences getSharedPReferences(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataAs(Context context, String data)
    {
        SharedPreferences.Editor  editor = getSharedPReferences(context).edit();
        editor.putString(DATA_AS,data);
        editor.apply();
    }

    public static String getDataAs(Context context)
    {
        return getSharedPReferences(context).getString(DATA_AS,"");
    }



    public static void setDataLogin(Context context, boolean status)
    {
        SharedPreferences.Editor  editor = getSharedPReferences(context).edit();
        editor.putBoolean(Data_login, status);
        editor.apply();
    }
    public static boolean getData_login(Context context)
    {
        return getSharedPReferences(context).getBoolean(Data_login, false);
    }


    public static void setData_User(Context context, String data)
    {
        SharedPreferences.Editor  editor = getSharedPReferences(context).edit();
        editor.putString(Data_User,data);
        editor.apply();
    }

    public static String getData_User(Context context)
    {
        return getSharedPReferences(context).getString(Data_User,"");
    }

    public static void clearData (Context context)
    {
        SharedPreferences.Editor editor= getSharedPReferences(context).edit();
        editor.remove(DATA_AS);
        editor.remove(Data_login);
        editor.remove(Data_User);
        editor.apply();
    }


}
