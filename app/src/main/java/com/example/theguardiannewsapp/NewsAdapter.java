package com.example.theguardiannewsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_view_items, parent, false);
        }
        News news = getItem(position);
        TextView typeTextView = listView.findViewById(R.id.type);
        TextView sectionNameTextView = listView.findViewById(R.id.section);
        TextView titleTextView = listView.findViewById(R.id.title);
        TextView authorNameTextView = listView.findViewById(R.id.author_name);
        TextView dateTextView = listView.findViewById(R.id.date);

        String formattedDate = null;
        try {
            formattedDate = formatDate(news.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dateTextView.setText(formattedDate);
        typeTextView.setText(news.getType());
        sectionNameTextView.setText(news.getSectionName());
        titleTextView.setText(news.getTitle());
        authorNameTextView.setText(news.getAuthorName());
        return listView;
    }

    private String formatDate(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(dateString);

        SimpleDateFormat simpleDateFormatOut = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormatOut.format(date);

    }
}
