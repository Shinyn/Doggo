package com.l.doggo;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddPetFragment extends Fragment {

    public EditText name, breed, age, height, weight;
    CheckBox checkBoxNeutered;
    RadioButton genderButton;
    boolean genderCheck = true;
    boolean neuteredCheck = false;
    private ArrayList<Dog> dogArrayList = new ArrayList<>();
    int number;

    CollectionReference collectionRef;


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

        /* Måste lägga till Uri-länk separat sen phoneNr och owner UTAN att det är fält som behöver fyllas i */
        // Behöver hämta phoneNr och Owner från firebase -.-
        // Ta den inloggade användarens userID och sätt till owner, behöver inte hämta från firebase med get
        /*-------------------------------------------------*/



        //----


        // Känns som extrajobb att ta en siffra -> konvertera till String -> konvertera till siffra
        // Bara för att få in den i Dog klassens konstruktor
        final String name2 = name.getText().toString();
        final String breed2 = breed.getText().toString();
        final String age2 = age.getText().toString();
        final String height2 = height.getText().toString();
        final String weight2 = weight.getText().toString();


        // Kollar om hunden är kastrerad eller ej
        checkBoxNeutered = getView().findViewById(R.id.checkBoxNeutered);
        neuteredCheck = checkBoxNeutered.isChecked();
                /* ^ Gör samma sak som:
                if (checkBoxNeutered.isChecked()) {
                    neuteredCheck = true;
                } else {
                    neuteredCheck = false;
                }
                */

        // Kollar hane eller hona -> ändra med setGender till true eller false
        radioGroup = getView().findViewById(R.id.radioGroup);      // <-- LÖSNINGEN!!!
        final int radioId = radioGroup.getCheckedRadioButtonId();  // Detta värde VAR null här = crash
        genderButton = getView().findViewById(radioId);
        if (radioId == R.id.radioBtnMale) {
            genderCheck = true;
        } else if (radioId == R.id.radioBtnFemale) {
            genderCheck = false;
        }


        final String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Krashar här pga null
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(owner);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                number = task.getResult().getDouble( "phoneNumber").intValue();


                // Måste kolla så att alla fält är ifyllda korrekt, är dom det så skapa hund annars error
                if (!name2.equals("") || !breed2.equals("") || !age2.equals("")
                        || !height2.equals("") || !weight2.equals("")) {

                    //FirebaseFirestore db = FirebaseFirestore.getInstance();
                    //FirebaseAuth auth = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    // Skapar ny hund

                    dog = new Dog(name2, breed2, owner, null, number,
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
        });

    }

    public void addDogToArrayList(Dog dog) {
        dogArrayList.add(dog);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Lägg till hund i database under /dogs/dogOwner/userDogs
        // Använd userId i dokumentet och skapa en collection som alla får läsa men bara userId får skriva till
      //  db.collection("dogs").document(userId).collection("userDogs").add(dog);
        db.collection("dogs").add(dog);

        // Uppdatera listan på firestore
    }

    public void printDogArrayList() {

        for( Dog dog : dogArrayList) {
            Log.d("!!!", "Array contain these dogs: " + "Name: " + dog.getName() + ", "
                    + "Breed: " + dog.getBreed() + ", " + "Age: " + dog.getAge() + ", "
                    + "Height " + dog.getHeight() + ", " + "Weight " + dog.getWeight() + ", "
                    + "Neutered " + dog.isNeutered() + ", " + "Male " + dog.isMale());
            // Skriv ut listan som test för o se om dog läggs till
        }
    }
}