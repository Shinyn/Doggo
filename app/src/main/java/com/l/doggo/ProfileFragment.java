package com.l.doggo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class ProfileFragment extends Fragment {

    private EditText profileUserName, profilePhoneNumber, profileDescription;
    private Button saveChangesButton;
    private ImageView profilePicture;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DocumentReference usersRef = db.collection("users").document(userId);

    //------------------------------------------------//
    Uri imageUri;
    ImageButton addProfileImageBtn;
    private static final int PIC_IMAGE_REQUEST = 1;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    UserAccount newUser;
    //------------------------------------------------//

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    /*--------------------------------------------------------------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/
    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PIC_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    String uploadId = databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(newUser.getImageUrl());  // Kanske blir fel här
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            profilePicture.setImageURI(imageUri);
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //--------------------------------------------------
        addProfileImageBtn = getView().findViewById(R.id.add_profile_image_button);
        profilePicture = getView().findViewById(R.id.profilePictureEdit);

        addProfileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        //--------------------------------------------------

        // Display username, number, description from firebase


        //profilePicture = getView().findViewById(R.id.profilePictureEdit);

        profileUserName = getView().findViewById(R.id.userNameEdit);
        profilePhoneNumber = getView().findViewById(R.id.phoneEdit);
        profileDescription = getView().findViewById(R.id.descriptionEdit);

        saveChangesButton = getView().findViewById(R.id.saveProfileChanges);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveChanges();
                    uploadFile();
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

        storageReference = FirebaseStorage.getInstance().getReference("profile_pics");
        databaseReference = FirebaseDatabase.getInstance().getReference("profile_pics");

        // Skriver över den gamla usern med den nya istället för att uppdatera all info i firebase
        newUser = new UserAccount(userName, description, phoneNumber, imageUri.toString());
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
