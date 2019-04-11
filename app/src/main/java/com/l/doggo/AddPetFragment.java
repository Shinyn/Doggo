package com.l.doggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class AddPetFragment extends Fragment {

    public EditText name, breed, age, height, weight;
    CheckBox checkBoxNeutered;
    RadioButton genderButton;
    boolean genderCheck = true;
    boolean neuteredCheck = false;

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



        // Kollar om hunden är kastrerad eller ej
        checkBoxNeutered = getView().findViewById(R.id.checkBoxNeutered);
        checkBoxNeutered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxNeutered.isChecked()) {
                    neuteredCheck = true;
                } else {
                    neuteredCheck = false;
                }
            }
        });

        //-----------------
        /*KRASHAR JUST NU*/
        //-----------------

        // Kollar vilken checkBox som är ikryssad och sätter gender till den som är vald
        // Detta måste göras i onViewCreated som en onClickListener

        // Kollar hane eller hona -> ändra med setGender till true eller false
        final int radioId = radioGroup.getCheckedRadioButtonId();  // Detta värde är null här = crash
        genderButton = getView().findViewById(radioId);
        genderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioId == R.id.radioBtnMale) {
                    genderCheck = true;
                } else if (radioId == R.id.radioBtnFemale) {
                    genderCheck = false;
                }
            }
        });













        Dog testDog = new Dog("test", "dobberman", 3, 2, 3, true, true);
        testDog.addDogToArrayList(testDog);



        radioGroup = getView().findViewById(R.id.radioGroup);
        //radioButtonMale = getView().findViewById(R.id.radioBtnMale);

        createDogBtn = getView().findViewById(R.id.createDogBtn);
        createDogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // Kolla om checkboxen är ikryssad, är den det -> ändra till kastrerad ANNARS inte kastrerad
                /*checkBox = getView().findViewById(R.id.checkBoxNeutered);*/




                // Toasten kommer att spammas, behöver flyttas!!!
                // Måste kolla så att alla fält är ifyllda korrekt, är dom det så skapa hund och lägg till i arraylista
                if (name != null && breed != null && age != null && height != null && weight != null
                        && checkBoxNeutered != null && radioGroup != null) {
                    createDog();
                    // clear all editTexts / set all paramaters to default
                    name.setText("");
                    breed.setText("");
                    age.setText("");
                    height.setText("");
                    weight.setText("");
                    checkBoxNeutered.toggle();
                    radioGroup.clearCheck();

                } else {
                    Toast.makeText(getActivity(), "You have to fill all fields correctly", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    /*
    public void createDog(String name, String breed, int age, int height, int weight, boolean neutered, boolean male) {
        Dog newDog = new Dog(name, breed, age, height, weight, neutered, male);

            intent = new Intent(getActivity(), PetsFragment.class);
            intent.putExtra("updateDogList", newDog);
            startActivity(intent);

    } */



    public void createDog() {

        // Kopplar inputen i xml:en till namnen som ska in i Dog konstruktorn
        name = getView().findViewById(R.id.dogName);
        breed = getView().findViewById(R.id.dogBreed);
        age = getView().findViewById(R.id.dogAge);
        height = getView().findViewById(R.id.dogHeight);
        weight = getView().findViewById(R.id.dogWeight);
        checkBoxNeutered = getView().findViewById(R.id.checkBoxNeutered);
        // Ska denna verkligen va set till radioGroup?
        genderButton = getView().findViewById(R.id.radioGroup);

        // Känns som extrajobb att ta en siffra -> konvertera till String -> konvertera till siffra
        // Bara för att få in den i Dog klassens konstruktor
        String age2 = age.getText().toString();
        String height2 = height.getText().toString();
        String weight2 = weight.getText().toString();






        // Skapar ny hund
        // Måste kolla hur man ändrar checkBox och Radioknapp till booleans

        dog = new Dog(name.getText().toString(), breed.getText().toString(),
                Integer.parseInt(age2), Integer.parseInt(height2), Integer.parseInt(weight2),
                neuteredCheck, genderCheck); /* Den sätts till true som default men får error? */

        // Lägger till hunden till ArrayListan
        dog.addDogToArrayList(dog);

        Toast.makeText(getActivity(), "Dog created", Toast.LENGTH_SHORT).show();

    }
}