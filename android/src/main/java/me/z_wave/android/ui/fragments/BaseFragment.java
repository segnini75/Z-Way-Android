/*
 * Z-Way for Android is a UI for Z-Way server
 *
 * Created by Ivan Platonov on 31.05.14 13:32.
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

package me.z_wave.android.ui.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import me.z_wave.android.app.TrackerName;
import me.z_wave.android.app.ZWayApplication;
import me.z_wave.android.data.DataContext;
import me.z_wave.android.otto.MainThreadBus;

import javax.inject.Inject;

public class BaseFragment extends Fragment{

    @Inject
    public DataContext dataContext;

    @Inject
    public MainThreadBus bus;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ZWayApplication) getActivity().getApplication()).inject(this);
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
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            goBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goBack() {
        getActivity().onBackPressed();
    }

    public ActionBar getActionBar(){
        return getActivity().getActionBar();
    }

    public Tracker getTracker() {
        return ((ZWayApplication) getActivity().getApplication()).getTracker(TrackerName.BASIC);
    }

    public void trackEvent(int categoryId, int actionId, int labelId) {
        getTracker().send(new HitBuilders.EventBuilder()
                .setCategory(getString(categoryId))
                .setAction(getString(actionId))
                .setLabel(getString(labelId))
                .build());
    }

    public void trackEvent(int categoryId, int actionId) {
        getTracker().send(new HitBuilders.EventBuilder()
                .setCategory(getString(categoryId))
                .setAction(getString(actionId))
                .build());
    }

    protected void showToast(int stringId) {
        Toast.makeText(getActivity(), stringId, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }



}
