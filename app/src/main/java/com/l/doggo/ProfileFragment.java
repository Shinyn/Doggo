package com.l.doggo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {

    EditText profileUserName, profilePhoneNumber, profileDescription;
    Button saveChangesButton;
    ImageView profilePicture;
    UserAccount newUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newUser = new UserAccount();
        profilePicture = getView().findViewById(R.id.profilePictureEdit);

        saveChangesButton = getView().findViewById(R.id.saveProfileChanges);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    public void saveChanges() {

        // Koppla id till EditTexts och uppdatera dessa vid inloggning
        profileUserName = getView().findViewById(R.id.userNameEdit);
        profilePhoneNumber = getView().findViewById(R.id.phoneEdit);
        profileDescription = getView().findViewById(R.id.descriptionEdit);

        String uName = profileUserName.getText().toString();
        int pNumber = Integer.parseInt(profilePhoneNumber.getText().toString());
        String desc = profileDescription.getText().toString();

        //newUser = new UserAccount(uName, desc, pNumber);
        newUser.setUserName(uName);
        newUser.setPhoneNumber(pNumber);
        newUser.setDescription(desc);

        //String userNameDisplay = getView().findViewById(R.id.nav_header_username);

        /*------------------------------------------------------------------------------------*/
        // Skriv över den gamla usern med den nya istället för att uppdatera all info i firebase
    }
}
