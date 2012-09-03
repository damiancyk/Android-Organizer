package net.organizer.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.organizer.R;
import net.organizer.rest.RestService;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {
	private String showTxt;
	private String showTitle = "wydarzenia";
	private String name[];
	private Calendar[] date;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		try {
			showTxt = receiveAction(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				MyWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);

			remoteViews.setTextViewText(R.id.textViewWidgetAction, showTxt);
			remoteViews.setTextViewText(R.id.textViewWidgetTitle, showTitle);

			// Register an onClickListener
			Intent intent = new Intent(context, MyWidgetProvider.class);
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.textViewWidgetAction,
					pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}

	private String receiveAction(String[] name) throws JSONException {
		String showTxt = "";
		JSONArray fromServletNote = null;
		int lengthArray = 0;
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		fromServletNote = (JSONArray) RestService.request(
				"/mobile/action/list/0", RestService.GET, RestService.ARRAY,
				params);

		if (fromServletNote != null) {
			lengthArray = fromServletNote.length();
			showTitle += "(" + lengthArray + ") - najbli¿sze";
			name = new String[lengthArray];
			date = new Calendar[lengthArray];
			for (int i = 0; i < lengthArray; i++)
				date[i] = Calendar.getInstance();

			for (int i = 0; i < lengthArray; i++) {
				JSONObject singleContact;
				try {
					singleContact = fromServletNote.getJSONObject(i);
					name[i] = singleContact.getString("name");

					Long miliseconds = Long.parseLong(singleContact
							.getString("dateWhen"));
					date[i].setTime(new Date(miliseconds));

					if (i < 2)
						showTxt += name[i] + " (" + date[i].get(Calendar.YEAR)
								+ "-" + (date[i].get(Calendar.MONTH) + 1) + "-"
								+ date[i].get(Calendar.DAY_OF_MONTH) + ")\n";

				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}

		} else {
			showTxt = "..kliknij, aby sprawdzic..";
		}
		return showTxt;
	}
}