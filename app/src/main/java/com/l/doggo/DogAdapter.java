package com.l.doggo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DogAdapter extends ArrayAdapter<Dog> {

    private ArrayList<Dog> dogs; // lista av hundar
    /*private LayoutInflater inflater;*/
    private Context myContext;




    public DogAdapter(Context context, ArrayList<Dog> dogsList) { // ta emot en ista av hundar
        super(context, 0, dogsList);
        //this.dogs = dogs;
        dogs = dogsList;
        myContext = context;
        /*inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
    }

    // Svarar på hur lång listan kommer vara
    @Override
    public int getCount() {
        if (dogs == null)
            return 0;
        return dogs.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(myContext).inflate(R.layout.item_list, parent, false);
        }

        Dog currentDog = dogs.get(position);

        /*View dogListItem = inflater.inflate(R.layout.fragment_pets, parent, false);*/
        //ArrayList<Dog> dogArrayList = new ArrayList<>();

        // Behöver vara new dog som hämtas från databasen??
        /*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference usersRef = db.collection("dogs").document(userId);
        */

        //Dog dog = dogs.get(position);

        // Kopplar id med alla textView's
        TextView petName = listItem.findViewById(R.id.petNameDisplay);
        TextView petBreed = listItem.findViewById(R.id.breedDisplay);
        TextView petAge = listItem.findViewById(R.id.dogAgeDisplay);
        TextView petHeight = listItem.findViewById(R.id.dogHeightDisplay);
        TextView petWeight = listItem.findViewById(R.id.dogWeightDisplay);
        TextView petNeutered = listItem.findViewById(R.id.neuteredDisplay);
        TextView petGender = listItem.findViewById(R.id.dogGenderDisplay);

        petName.setText(currentDog.getName());
        petBreed.setText(currentDog.getBreed());
        petAge.setText(currentDog.getAge());
        petHeight.setText(currentDog.getHeight());
        petWeight.setText(currentDog.getWeight());
        //petNeutered.setText(currentDog.isNeutered());
        //petGender.setText(currentDog.isMale());



        return listItem;

        /*
        try {
            String name = dog.getName(); // Crash pga NullPointerException
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

        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Dog is null", Toast.LENGTH_SHORT).show();
        }
        */


    }

}