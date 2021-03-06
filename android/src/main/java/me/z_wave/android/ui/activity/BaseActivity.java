/*
 * Z-Way for Android is a UI for Z-Way server
 *
 * Created by Ivan Platonov on 15.06.14 16:25.
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

package me.z_wave.android.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;

import com.crittercism.app.Crittercism;

import me.z_wave.android.R;
import me.z_wave.android.app.ZWayApplication;
import me.z_wave.android.data.NewProfileContext;
import me.z_wave.android.dataModel.LocalProfile;
import me.z_wave.android.dataModel.Theme;
import me.z_wave.android.database.DatabaseDataProvider;
import me.z_wave.android.otto.MainThreadBus;
import me.z_wave.android.otto.events.CancelConnectionEvent;
import me.z_wave.android.otto.events.ProgressEvent;
import me.z_wave.android.otto.events.ShowAttentionDialogEvent;
import me.z_wave.android.otto.events.ShowReconnectionProgressEvent;
import me.z_wave.android.ui.dialogs.AttentionDialogFragment;
import me.z_wave.android.ui.dialogs.ConnectionLoseDialog;
import me.z_wave.android.ui.dialogs.ProgressDialog;
import me.z_wave.android.ui.dialogs.ReconnectionProgressDialog;
import me.z_wave.android.utils.FragmentUtils;

import javax.inject.Inject;

public class BaseActivity extends Activity implements AttentionDialogFragment.AttentionDialogListener,
        ConnectionLoseDialog.ConnectionLoseDialogListener {

    @Inject
    MainThreadBus bus;

    @Inject
    NewProfileContext profileContext;

    private ProgressDialog mProgressDialog;
    private ReconnectionProgressDialog mReconnectionProgressDialog;
    private boolean mIsDialogVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crittercism.initialize(getApplicationContext(), "53ef3abdd478bc401300000a");
        ((ZWayApplication) getApplication()).inject(this);

        final LocalProfile profile = DatabaseDataProvider.getInstance(this).getActiveLocalProfile();
        if(profile != null) {
            final Theme theme = profile.theme != null ? profile.theme : Theme.DEFAULT;
            setTheme(theme.getThemeId());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public void onDismiss() {
        mIsDialogVisible = false;
    }

    @Override
    public void onCloseAppClicked() {
        finish();
    }

    @Override
    public void onShowNetworkSettings() {
        final Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }

    public int getScreenOrientationOption(){
        final boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        return  isTablet ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    public void commitFragment(Fragment fragment, boolean addToBackStack){
        FragmentUtils.commitFragment(getFragmentManager(),
                R.id.fragment_container, fragment, addToBackStack);
    }

    public void showAttentionDialog(ShowAttentionDialogEvent event){
        if(!mIsDialogVisible){
            onShowHideProgress(new ProgressEvent(false, false));
            final AttentionDialogFragment dialog = AttentionDialogFragment.newInstance(event.alertMessage);
            dialog.setCancelable(false);
            dialog.show(getFragmentManager(), AttentionDialogFragment.class.getSimpleName());
            mIsDialogVisible = true;
        }
    }

    public void onShowHideProgress(ProgressEvent event){
        if(event.show && mProgressDialog == null){
            mProgressDialog = new ProgressDialog();
            mProgressDialog.show(getFragmentManager(), ProgressDialog.class.getSimpleName());
        } else if(!event.show && mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void onShowHideReconnectionProgress(ShowReconnectionProgressEvent event){
        if(event.show && mReconnectionProgressDialog == null){
            mReconnectionProgressDialog = new ReconnectionProgressDialog() {
                @Override
                public void onCancelConnection() {
                    bus.post(new CancelConnectionEvent());
                    bus.post(new ShowReconnectionProgressEvent(false, false, ""));
                }
            };
            mReconnectionProgressDialog.setProfileName(event.profileName);
            mReconnectionProgressDialog.show(getFragmentManager(), ProgressDialog.class.getSimpleName());
        } else if(!event.show && mReconnectionProgressDialog != null) {
            mReconnectionProgressDialog.dismiss();
            mReconnectionProgressDialog = null;
        }
    }

}
