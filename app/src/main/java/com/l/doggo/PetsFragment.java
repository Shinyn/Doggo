package com.l.doggo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class PetsFragment extends Fragment {

   // RecyclerView recyclerView;

    /*
    public ArrayList<Dog> dogs = new ArrayList<>();
    public Dog dog;
    */

    // lista av hundar
    // Måste skapa listan med hundar OCH lägga till hundar

    ListView listView;
    DogAdapter dogAdapter;
    ArrayList<Dog> dogList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = getView().findViewById(R.id.listView);
        Log.d("david", "onViewCreated: " + listView);
        dogList = new ArrayList<>();
        //-----------------------------------------------------

        //-----------------------------------------------------
        //Måste hämta från databasen på nån vänster
        //dogList.add(new Dog(R.id.));

        // Kan testa med getContext också

        dogAdapter = new DogAdapter(getActivity(), dogList);
        getDoggos();
        listView.setAdapter(dogAdapter);



        // inte ny dog utan hämta från firebase?

        /*
        dogs = new ArrayList<>();
        dogs.add(dog);

        ListView dogListView = getView().findViewById(R.id.listView);
        final DogAdapter dogAdapter = new DogAdapter(getActivity(), dogs);
        dogListView.setAdapter(dogAdapter);
        */

        // Ska skriva ut alla hundar till listviewn

        //dog = (Dog) getActivity().getIntent().getSerializableExtra("updatedDogList");


    }


    public void getDoggos() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference dogCollectionRef = db.collection("dogs");
       // Query dogQuery = dogCollectionRef.whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        dogCollectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Dog doog = doc.toObject(Dog.class);
                    dogList.add(doog);
                    Log.d("david", "onEvent: " + doog.getName());
                }
                dogAdapter.notifyDataSetChanged();
            }
        });
    }
}
