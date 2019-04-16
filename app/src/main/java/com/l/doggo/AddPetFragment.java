package com.l.doggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPetFragment extends Fragment {

    public EditText name, breed, age, height, weight;
    CheckBox checkBoxNeutered;
    RadioButton genderButton;
    boolean genderCheck = true;
    boolean neuteredCheck = false;
    private ArrayList<Dog> dogArrayList = new ArrayList<>();

    Intent intent;
    Button createDogBtn;
    RadioGroup radioGroup;
    Dog dog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_pet, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*--------------------------------------------------------------*/

        createDogBtn = getView().findViewById(R.id.createDogBtn);
        createDogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Problemet här är att nu kan man skapa en hund med enbart ålder, höjd och vikt..
                try {
                    createDog();
                    printDogArrayList();

                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "You have to fill all fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createDog() {

        // Kopplar inputen i xml:en till namnen som ska in i Dog konstruktorn
        name = getView().findViewById(R.id.dogName);
        breed = getView().findViewById(R.id.dogBreed);
        age = getView().findViewById(R.id.dogAge);
        height = getView().findViewById(R.id.dogHeight);
        weight = getView().findViewById(R.id.dogWeight);
        checkBoxNeutered = getView().findViewById(R.id.checkBoxNeutered);

        // Känns som extrajobb att ta en siffra -> konvertera till String -> konvertera till siffra
        // Bara för att få in den i Dog klassens konstruktor
        String name2 = name.getText().toString();
        String breed2 = breed.getText().toString();
        String age2 = age.getText().toString();
        String height2 = height.getText().toString();
        String weight2 = weight.getText().toString();


        // Kollar om hunden är kastrerad eller ej
        checkBoxNeutered = getView().findViewById(R.id.checkBoxNeutered);
        neuteredCheck = checkBoxNeutered.isChecked();
        /*checkBoxNeutered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxNeutered.isChecked()) {
                    neuteredCheck = true;
                } else {
                    neuteredCheck = false;
                }
            }
        }); */

        // Kollar hane eller hona -> ändra med setGender till true eller false
        radioGroup = getView().findViewById(R.id.radioGroup);      // <-- LÖSNINGEN!!!
        final int radioId = radioGroup.getCheckedRadioButtonId();  // Detta värde VAR null här = crash
        genderButton = getView().findViewById(radioId);
        if (radioId == R.id.radioBtnMale) {
            genderCheck = true;
        } else if (radioId == R.id.radioBtnFemale) {
            genderCheck = false;
        }

        /*
        genderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
        // Måste kolla så att alla fält är ifyllda korrekt, är dom det så skapa hund annars error
        if (!name2.equals("")) { // || !breed.getText().equals("") || !age.getText().equals("")
               // || !height.getText().equals("") || !weight.getText().equals("")) {

            // Skapar ny hund
            dog = new Dog(name2, breed2,
                    Integer.parseInt(age2), Integer.parseInt(height2), Integer.parseInt(weight2),
                    neuteredCheck, genderCheck); /* Integer ger NumberFormatException om man inte gör try / catch */

            // Lägger till hunden till ArrayListan
            addDogToArrayList(dog);


            Toast.makeText(getActivity(), "Dog created", Toast.LENGTH_SHORT).show();

            // clear all editTexts / set all paramaters to default
            name.setText("");
            breed.setText("");
            age.setText("");
            height.setText("");
            weight.setText("");
            if (checkBoxNeutered.isChecked()) {
                checkBoxNeutered.toggle();
            }


        } else {
            Toast.makeText(getActivity(), "You have to fill all fields correctly", Toast.LENGTH_SHORT).show();
        }
    }

    public void addDogToArrayList(Dog dog) {
        dogArrayList.add(dog);
    }

    public void printDogArrayList() {

        for( Dog dog : dogArrayList) {
            Log.d("!!!", "Array contain these dogs: " + dog.getName());
            // Skriv ut listan som test för o se om dog läggs till
        }
       // return -1;  // Vill skriva ut ArrayListan men får bara -1..
    }
}