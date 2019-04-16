package com.l.doggo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DogAdapter extends ArrayAdapter<Dog> {

    private Dog dog;
    private LayoutInflater inflater;



    public DogAdapter(Context context, Dog dog) {
        super(context, -1);
        this.dog = dog;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View dogListItem = inflater.inflate(R.layout.fragment_pets, parent, false);
        ArrayList<Dog> dogArrayList = new ArrayList<>();
        // Koppla id med alla textView's som finns - namn, ras, ålder osv
        // Fyll alla textView's med data från hundarna (olika hundar, olika stats)

        return dogListItem;
    }

}
