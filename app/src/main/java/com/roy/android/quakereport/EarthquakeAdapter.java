package com.roy.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public static final String LOCATION_SEPERATOR = " of ";

    public EarthquakeAdapter(@NonNull Context context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false
            );
        }

        Earthquake earthquake = getItem(position);

        String formattedMagnitude = formatMagnitude(earthquake.getMagnitude());
        TextView magnitudeTV = listView.findViewById(R.id.magnitude);
        magnitudeTV.setText(formattedMagnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTV.getBackground();
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        String originalLocation = earthquake.getLocation();
        String primaryLocation, locationOffset;

        if (originalLocation.contains(LOCATION_SEPERATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPERATOR);
            locationOffset = parts[0] + LOCATION_SEPERATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView locationOffsetTV = listView.findViewById(R.id.location_offset);
        locationOffsetTV.setText(locationOffset);

        TextView primaryLocationTV = listView.findViewById(R.id.primary_location);
        primaryLocationTV.setText(primaryLocation);

        Date dateObject = new Date(earthquake.getTimeInMilliseconds());
        TextView dateTV = listView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        dateTV.setText(formattedDate);

        TextView timeTV = listView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeTV.setText(formattedTime);

        return listView;
    }

    @NonNull
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    @NonNull
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.US);
        return dateFormat.format(dateObject);
    }

    @NonNull
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
