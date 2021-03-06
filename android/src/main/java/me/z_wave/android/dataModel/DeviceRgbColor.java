/*
 * Z-Way for Android is a UI for Z-Way server
 *
 * Created by Ivan Platonov on 03.07.14 15:25.
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

import android.graphics.Color;

import java.io.Serializable;

public class DeviceRgbColor implements Serializable {
    public int r;
    public int g;
    public int b;

    public int getColorAsInt() {
        return Color.argb(255, r, g, b);
    }
}
