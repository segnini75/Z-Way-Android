/*
 * Z-Way for Android is a UI for Z-Way server
 *
 * Created by Ivan Platonov on 15.06.14 23:08.
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

package me.z_wave.android.network.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.z_wave.android.dataModel.Notification;
import me.z_wave.android.dataModel.Pager;

public class NotificationDataWrapper{
    public long updateTime;
    public List<Notification> notifications;
    @SerializedName("total_count")
    public int notificationsCount;
    public Pager pager;

    @Override
    public String toString() {
        return "NotificationDataWrapper{" +
                "\nupdateTime=" + updateTime +
                ",\n notifications=" + notifications +
                "\n}";
    }
}