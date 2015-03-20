package me.z_wave.android.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.List;

import me.z_wave.android.R;
import me.z_wave.android.data.DataContext;
import me.z_wave.android.dataModel.Device;
import me.z_wave.android.network.devices.DevicesStateRequest;
import me.z_wave.android.network.devices.DevicesStateResponse;
import retrofit.Callback;
import retrofit.http.Path;
import retrofit.http.Query;


/**
 * Implementation of App Widget functionality.
 */
public class ThermostatWidget extends AppWidgetProvider {

    private static int selectedThermostatIndex = 0;
    private static List<Device> thermostats;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        getThermostats();
        String temp = getTemperature();
        CharSequence widgetText = temp;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.thermostat_widget);
        views.setTextViewText(R.id.thermostat_temp, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void getThermostats(){
        //TODO
    }

    private static String getTemperature(){
        //TODO
        return "00.00º";
    }
}


