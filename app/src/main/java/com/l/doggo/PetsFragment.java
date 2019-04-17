package com.l.doggo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class PetsFragment extends Fragment {

    public ArrayList<Dog> dogs;
    public Dog dog;
    // lista av hundar
    // Måste skapa listan med hundar OCH lägga till hundar

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // inte ny dog utan hämta från firebase?
        dogs = new ArrayList<>();
        dogs.add(dog);

        ListView dogListView = getView().findViewById(R.id.listView);
        final DogAdapter dogAdapter = new DogAdapter(getActivity(), dogs);
        dogListView.setAdapter(dogAdapter);

        // Ska skriva ut alla hundar till listviewn

        //dog = (Dog) getActivity().getIntent().getSerializableExtra("updatedDogList");


    }
}
