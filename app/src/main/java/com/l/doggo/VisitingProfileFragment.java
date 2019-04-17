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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class VisitingProfileFragment extends Fragment {

    // VisitinProfileFragment visas vid klick på användarikonerna

    private TextView profileUserName, profilePhoneNumber, profileDescription;
    private ImageView profilePicture;
   // private UserAccount newUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DocumentReference usersRef = db.collection("users").document(userId);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Display username, number, description from firebase
        showProfile(); // Basically det som finns i onStart metoden nu.

        profilePicture = getView().findViewById(R.id.profilePictureEdit);

        profileUserName = getView().findViewById(R.id.userNameEdit);
        profilePhoneNumber = getView().findViewById(R.id.phoneEdit);
        profileDescription = getView().findViewById(R.id.descriptionEdit);
    }

    public void showProfile() {

    }

    @Override
    public void onStart() {
        super.onStart();

        usersRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    UserAccount user = task.getResult().toObject(UserAccount.class);
                    // set user info i rätt views
                   // user.getUserName()

                }
            }
        });
    }
}
