package com.l.doggo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class PetsFragment extends Fragment {

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

        dogAdapter = new DogAdapter(getActivity(), dogList);
        getDoggos();
        listView.setAdapter(dogAdapter);
    }


    public void getDoggos() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference dogCollectionRef = db.collection("dogs");
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
