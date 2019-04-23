package com.l.doggo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DogAdapter extends ArrayAdapter<Dog> {

    private ArrayList<Dog> dogs; // lista av hundar
    private Context myContext;




    public DogAdapter(Context context, ArrayList<Dog> dogsList) { // ta emot en ista av hundar
        super(context, 0, dogsList);
        //this.dogs = dogs;
        dogs = dogsList;
        myContext = context;
    }

    // Svarar på hur lång listan kommer vara
    @Override
    public int getCount() {
        Log.d("david", "getCount: ");
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
        Log.d("david", "getView: " + currentDog.getName());

        /*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference usersRef = db.collection("dogs").document(userId);
        */


        // Kopplar id med alla textView's
        TextView petName = listItem.findViewById(R.id.petNameDisplay);
        TextView petBreed = listItem.findViewById(R.id.breedDisplay);
        TextView petAge = listItem.findViewById(R.id.dogAgeDisplay);
        TextView petHeight = listItem.findViewById(R.id.dogHeightDisplay);
        TextView petWeight = listItem.findViewById(R.id.dogWeightDisplay);
        TextView petNeutered = listItem.findViewById(R.id.neuteredDisplay);
        TextView petGender = listItem.findViewById(R.id.dogGenderDisplay);

        TextView petOwner = listItem.findViewById(R.id.owner_text_view);
        TextView phoneNumber = listItem.findViewById(R.id.phone_text_view);

        petName.setText(currentDog.getName());
        petBreed.setText(currentDog.getBreed());
        petAge.setText(String.valueOf(currentDog.getAge()));
        petHeight.setText(String.valueOf(currentDog.getHeight()));
        petWeight.setText(String.valueOf(currentDog.getWeight()));
        petNeutered.setText(String.valueOf(currentDog.isNeutered()));
        petGender.setText(String.valueOf(currentDog.isMale()));

       // Log.d("david", "onComplete: 1 " + currentDog.getOwner());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(currentDog.getOwner()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                UserAccount owner =  task.getResult().toObject(UserAccount.class);
                Log.d("david", "onComplete: " + owner.getUserName());
            }
        });



        // HOW?
        // Get current userName ska in här
        /*petOwner.setText();*/
        // phoneNumber ska in här
        /*phoneNumber.setText();*/


        //petName.setText(currentDog.getName());

        if (currentDog.isNeutered()) {
            petNeutered.setText("yes");
        } else {
            petNeutered.setText("No");
        }

        if (currentDog.isMale()) {
            petGender.setText("Male");
        } else {
            petGender.setText("Female");
        }

        return listItem;
    }
}