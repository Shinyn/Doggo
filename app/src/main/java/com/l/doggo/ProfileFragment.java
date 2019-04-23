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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {

    private EditText profileUserName, profilePhoneNumber, profileDescription;
    private Button saveChangesButton;
    private ImageView profilePicture;

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


        profilePicture = getView().findViewById(R.id.profilePictureEdit);

        profileUserName = getView().findViewById(R.id.userNameEdit);
        profilePhoneNumber = getView().findViewById(R.id.phoneEdit);
        profileDescription = getView().findViewById(R.id.descriptionEdit);

        saveChangesButton = getView().findViewById(R.id.saveProfileChanges);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveChanges();
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Invalid phone-number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveChanges() {

        String userName = profileUserName.getText().toString();
        int phoneNumber = Integer.parseInt(profilePhoneNumber.getText().toString());
        String description = profileDescription.getText().toString();

        // Skriver över den gamla usern med den nya istället för att uppdatera all info i firebase
        UserAccount newUser = new UserAccount(userName, description, phoneNumber);
        /*String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); *///May cause NullPointerException
        if (userId != null) {
            db.collection("users").document(userId).set(newUser);
            Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("!!!", "saveChanges: userId is null");
        }

        //db.collection("users").document(userId).set(newUser);
    }

    @Override
    public void onStart() {
        super.onStart();

        usersRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Loads details from firebase
                    UserAccount user = task.getResult().toObject(UserAccount.class);
                    String userName = user.getUserName();
                    int phoneNumber = user.getPhoneNumber();
                    String description = user.getDescription();

                    profileUserName.setText(userName);
                    profilePhoneNumber.setText(String.valueOf(phoneNumber));
                    profileDescription.setText(description);
                }
            }
        });
    }
}
