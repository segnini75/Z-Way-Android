/*
 * Z-Way for Android is a UI for Z-Way server
 *
 * Created by Ivan Platonov on 07.07.14 18:36.
 * Copyright (c) 2014 Z-Wave.Me
 *
 * All rights reserved
 * info@z-wave.me
 * Z-Way for Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Z-Way for Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Z-Way for Android.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.z_wave.android.dataModel;

import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.List;

import me.z_wave.android.database.tables.ProfileTable;

/**
 * Created by Ivan PL on 07.07.2014.
 */
public class LocalProfile {

    public int id;
    public int serverId;
    public String name;
    public String indoorServer;
    public String login;
    public String password;
    public double latitude;
    public double longitude;
    public boolean active;

    public LocalProfile() {
    }

    public LocalProfile(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        serverId = cursor.getInt(cursor.getColumnIndex(ProfileTable.P_SERVER_ID));
        name = cursor.getString(cursor.getColumnIndex(ProfileTable.P_NAME));
        indoorServer = cursor.getString(cursor.getColumnIndex(ProfileTable.P_INDOOR_SERVER));
        login = cursor.getString(cursor.getColumnIndex(ProfileTable.P_LOGIN));
        password  = cursor.getString(cursor.getColumnIndex(ProfileTable.P_PASSWORD));
        latitude = cursor.getDouble(cursor.getColumnIndex(ProfileTable.P_LATITUDE));
        longitude = cursor.getDouble(cursor.getColumnIndex(ProfileTable.P_LONGITUDE));
        active  = cursor.getInt(cursor.getColumnIndex(ProfileTable.P_ACTIVE)) == 1;
    }

}