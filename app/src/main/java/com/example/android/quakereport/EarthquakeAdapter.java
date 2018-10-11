package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kamal Dev Sharma on 6/28/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquakes> {
    Context mcontext;
    ArrayList<Earthquakes> mearthquakes;
    ViewHolder holder;

    public EarthquakeAdapter(@NonNull EarthquakeActivity context, ArrayList<Earthquakes> resource) {
        super(context, 0, resource);
        mcontext = context;
        mearthquakes = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.magnitudeTextView = (TextView) convertView.findViewById(R.id.magnitude_textview);
            holder.primaryLocationTextView = (TextView) convertView.findViewById(R.id.primary_location_textview);
            holder.offsetLocationTextView = (TextView) convertView.findViewById(R.id.offset_location_textview);
            holder.timeTextView = (TextView) convertView.findViewById(R.id.time_textview);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.date_textview);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        //truncate magnitude into 2 decimal places
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String mag = decimalFormat.format(mearthquakes.get(position).getMagnitude());
        //chose the color according to the magnitude
        GradientDrawable gradientDrawable = (GradientDrawable) holder.magnitudeTextView.getBackground();
        int roundedMagnitude = (int) Math.floor(mearthquakes.get(position).getMagnitude());
        int magnitudeColor = getMagnitudeColor(roundedMagnitude);
        gradientDrawable.setColor(magnitudeColor);
        holder.magnitudeTextView.setText(mag);
        //primary location and offset location get seperated here
        String location = mearthquakes.get(position).getPlace();
        String offsetLocation;
        String primaryLocation;
        if(!location.contains("of")) {
            offsetLocation = "Near the";
            primaryLocation = location;
        }
        else {
            String locationAray[] = location.split("of");
            offsetLocation = locationAray[0] + "of";
            primaryLocation = locationAray[1] + "\b";
        }
        holder.primaryLocationTextView.setText(primaryLocation);
        holder.offsetLocationTextView.setText(offsetLocation);
        Date date = new Date(mearthquakes.get(position).getTime());
        String formattedDate = formatDate(date);
        String formattedTime = formatTime(date);
        holder.dateTextView.setText(formattedDate);
        holder.timeTextView.setText(formattedTime);
        return convertView;
    }

    private int getMagnitudeColor(int magnitude) {
        int magnitudeColor ;
        switch (magnitude) {
            case 0:
            case 1: magnitudeColor = R.color.magnitude1;
                    break;
            case 2: magnitudeColor = R.color.magnitude2;
                    break;
            case 3: magnitudeColor = R.color.magnitude3;
                    break;
            case 4: magnitudeColor = R.color.magnitude4;
                    break;
            case 5: magnitudeColor = R.color.magnitude5;
                break;
            case 6: magnitudeColor = R.color.magnitude6;
                break;
            case 7: magnitudeColor = R.color.magnitude7;
                break;
            case 8: magnitudeColor = R.color.magnitude8;
                break;
            case 9: magnitudeColor = R.color.magnitude9;
                break;
            case 10: magnitudeColor = R.color.magnitude10plus;
                break;
            default: magnitudeColor = 0;
        }
        return   ContextCompat.getColor(getContext(), magnitudeColor);
    }

    private String formatTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        return  simpleDateFormat.format(date);
    }

    private String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return simpleDateFormat.format(date);
    }
}
