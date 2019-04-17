package com.l.doggo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        //ArrayList<Dog> dogArrayList = new ArrayList<>();

        // Kopplar id med alla textView's
        TextView petName = dogListItem.findViewById(R.id.petNameDisplay);
        TextView petBreed = dogListItem.findViewById(R.id.breedDisplay);
        TextView petAge = dogListItem.findViewById(R.id.dogAgeDisplay);
        TextView petHeight = dogListItem.findViewById(R.id.dogHeightDisplay);
        TextView petWeight = dogListItem.findViewById(R.id.dogWeightDisplay);
        TextView petNeutered = dogListItem.findViewById(R.id.neuteredDisplay);
        TextView petGender = dogListItem.findViewById(R.id.dogGenderDisplay);

        String name = dog.getName();
        String breed = dog.getBreed();
        int age = dog.getAge();
        int height = dog.getHeight();
        int weight = dog.getWeight();
        boolean neutered = dog.isNeutered();
        boolean gender = dog.isMale();

        petName.setText(name);
        petBreed.setText(breed);
        petAge.setText(age);
        petHeight.setText(height);
        petWeight.setText(weight);
        petNeutered.setText(String.valueOf(neutered));
        petGender.setText(String.valueOf(gender));

        return dogListItem;
    }

}
