package com.l.doggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddPetFragment extends Fragment {

    public EditText name, breed, age, height, weight, neutered, gender;
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_pet, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = getView().findViewById(R.id.dogName);
        breed = getView().findViewById(R.id.dogBreed);
        age = getView().findViewById(R.id.dogAge);
        height = getView().findViewById(R.id.dogHeight);
        weight = getView().findViewById(R.id.dogWeight);
        neutered = getView().findViewById(R.id.dogNeutered);
        gender = getView().findViewById(R.id.dogGender);

    }

    public void createDog(String name, String breed, int age, int height, int weight, boolean neutered, boolean male) {
        Dog newDog = new Dog(name, breed, age, height, weight, neutered, male);


            intent = new Intent(getActivity(), PetsFragment.class);
            intent.putExtra("updateDogList", newDog);
            startActivity(intent);


    }
}


//
