/*
 * Z-Way for Android is a UI for Z-Way server
 *
 * Created by Ivan Platonov on 15.06.14 15:01.
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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.otto.Subscribe;
import me.z_wave.android.R;
import me.z_wave.android.dataModel.Device;
import me.z_wave.android.dataModel.Filter;
import me.z_wave.android.otto.events.OnDataUpdatedEvent;
import me.z_wave.android.ui.adapters.DevicesGridAdapter;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.List;

public class DevicesFragment extends BaseFragment implements DevicesGridAdapter.DeviceStateUpdatedListener {

    public static final String FILTER_KEY = "filter_key";
    public static final String FILTER_NAME_KEY = "filter_name_key";

    @InjectView(R.id.devices_widgets)
    GridView widgetsGridView;

    @InjectView(R.id.devices_msg_empty)
    View emptyListMsg;

    private List<Device> mDevices;
    private DevicesGridAdapter mAdapter;

    public static DevicesFragment newInstance(Filter filter, String filterValue){
        final DevicesFragment devicesFragment = new DevicesFragment();
        final Bundle args = new Bundle();
        args.putInt(FILTER_KEY, filter.ordinal());
        args.putString(FILTER_NAME_KEY, filterValue);
        devicesFragment.setArguments(args);
        return devicesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_devices, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareDevicesView();
        changeEmptyMsgVisibility();
    }

    @Override
    public void onExactChanged(Device updatedDevice) {

    }

    @Subscribe
    public void onDataUpdated(OnDataUpdatedEvent event){
        Timber.v("Device list updated!");
        mDevices.clear();
        mDevices.addAll(getFilteredDeviceList());

        mAdapter.notifyDataSetChanged();
        changeEmptyMsgVisibility();
    }

    private void changeEmptyMsgVisibility(){
        final int msgVisibility = mAdapter == null || mAdapter.getCount() == 0 ? View.VISIBLE : View.GONE;
        if(emptyListMsg.getVisibility() != msgVisibility){
            emptyListMsg.setVisibility(msgVisibility);
        }
    }

    private void prepareDevicesView(){
        mDevices =  getFilteredDeviceList();
        mAdapter = new DevicesGridAdapter(getActivity(), mDevices, this);
        widgetsGridView.setAdapter(mAdapter);
    }

    private List<Device> getFilteredDeviceList(){
        final Filter filter = Filter.values()[getArguments().getInt(FILTER_KEY, 0)];
        final String filterValue = getArguments().getString(FILTER_NAME_KEY, Filter.DEFAULT_FILTER);
        switch (filter){
            case LOCATION:
                return dataContext.getDevicesForLocation(filterValue);
            case TYPE:
                return dataContext.getDevicesWithType(filterValue);
            case TAG:
                return dataContext.getDevicesWithTag(filterValue);
        }
        return new ArrayList<Device>();
    }
}