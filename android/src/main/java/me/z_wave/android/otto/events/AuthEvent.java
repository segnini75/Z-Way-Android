/*
 * Z-Way for Android is a UI for Z-Way server
 *
 * Created by Ivan Platonov on 19.10.14 18:13.
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

package me.z_wave.android.otto.events;

import me.z_wave.android.dataModel.LocalProfile;
import me.z_wave.android.services.AuthService;

/**
 * Created by Ivan Pl on 19.10.2014.
 */
public class AuthEvent {

    public static class Success {
        public final LocalProfile profile;
        public final AuthService.LoginType loginType;

        public Success(LocalProfile profile, AuthService.LoginType loginType) {
            this.profile = profile;
            this.loginType = loginType;
        }
    }

    public static class Fail {
        public final LocalProfile profile;
        public final AuthService.LoginType loginType;
        public final boolean isNetworkError;

        public Fail(LocalProfile profile, AuthService.LoginType loginType, boolean isNetworkError) {
            this.profile = profile;
            this.loginType = loginType;
            this.isNetworkError = isNetworkError;
        }
    }
}
