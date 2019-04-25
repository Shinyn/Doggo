package com.l.doggo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class PetsFragment extends Fragment {

    ListView listView;
    DogAdapter dogAdapter;
    ArrayList<Dog> dogList;
    DatabaseReference databaseReference;
    //TextView ownerDisplay;
    //TextView ownerNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ownerDisplay = getView().findViewById(R.id.owner_text_view);
        //ownerNumber = getView().findViewById(R.id.phone_text_view);
        /*---------------------------------*/

        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Dog dog = postSnapshot.getValue(Dog.class);
                    dogList.add(dog); // Får aldrig access hit
                    Log.d("###", "onDataChange: Started");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        listView = getView().findViewById(R.id.listView);
        Log.d("david", "onViewCreated: " + listView);
        dogList = new ArrayList<>();

        dogAdapter = new DogAdapter(getActivity(), dogList);
        getDoggos();
        listView.setAdapter(dogAdapter);
    }


    public void getDoggos() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final CollectionReference dogCollectionRef = db.collection("dogs");
        dogCollectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Dog doog = doc.toObject(Dog.class);

                    // -Crash-
                    //ownerDisplay.setText(doog.getOwner());
                    //ownerNumber.setText(doog.getPhoneNumber());
                    dogList.add(doog);
                    Log.d("david", "onEvent: " + doog.getName());
                }
                dogAdapter.notifyDataSetChanged();
            }
        });

        //final DocumentReference dogUriRef = db.collection("dogs").document("imageUrl");
        /* dogUriRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                ImageView petView = getView().findViewById(R.id.pet_image_view);
                Glide.with(getActivity()).load().into(petView);
            }
        });*/
    }
}
