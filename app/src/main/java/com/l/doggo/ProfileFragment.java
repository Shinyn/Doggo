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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
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


//1 ladda ner profiluppgifter
//2 fyll i profil uppgifter

//camera tryck:
// sätt bild till imageview


// spara-knapp:
// 1.ladda upp bilden till firestore
// 2. få tag på länken till bilden på firestore
// 3 skapa en ny användare-objekt
// 4. ersätt användare i firestore med det nya objektet


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
    // Set onClick på saveChanges, ladda ner currentUser och ta bild från den i onStart

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
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    imageUri = task.getResult();
                    saveChanges(imageUri.toString());
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
            if (profilePicture != null) {
                profilePicture.setImageURI(imageUri);
                Log.d("!!!!", "onActivityResult: Started and found picture in database");
            } else {
                Log.d("!!!!", "onActivityResult: Started without picture found");
                Toast.makeText(getActivity(), "No picture available", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /*--------------------------------------------------------------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storageReference = FirebaseStorage.getInstance().getReference("user_pics");
        databaseReference = FirebaseDatabase.getInstance().getReference("user_pics");

        //--------------------------------------------------
        addProfileImageBtn = getView().findViewById(R.id.add_profile_image_button);
        //profilePicture = getView().findViewById(R.id.profilePictureEdit);
        addProfileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        //--------------------------------------------------

        profileUserName = getView().findViewById(R.id.userNameEdit);
        profilePhoneNumber = getView().findViewById(R.id.phoneEdit);
        profileDescription = getView().findViewById(R.id.descriptionEdit);

        saveChangesButton = getView().findViewById(R.id.saveProfileChanges);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Sätt imageUri till nåt
                    saveChanges(null); // Hur gör man med uri?
                    uploadFile();
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Invalid phone-number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveChanges(final String downloadUrl) {

        String userName = profileUserName.getText().toString();
        String phoneNumber = profilePhoneNumber.getText().toString();
        String description = profileDescription.getText().toString();

        storageReference = FirebaseStorage.getInstance().getReference("profile_pics");
        databaseReference = FirebaseDatabase.getInstance().getReference("profile_pics");

        // Skriver över den gamla usern med den nya istället för att uppdatera all info i firebase
        newUser = new UserAccount(userName, description, phoneNumber, downloadUrl);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("!!!", "saveChanges: write");

        if (currentUserId != null) {
            db.collection("users").document(currentUserId).set(newUser);
            Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();
            Log.d("!!!", "saveChanges: write success");

        } else {
            Log.d("!!!", "saveChanges: userId is null");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("!!!!", "onStart: Started");
        usersRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Loads details from firebase

                    UserAccount user = task.getResult().toObject(UserAccount.class);
                    if (user == null) {
                        Toast.makeText(getActivity(), "no user found", Toast.LENGTH_SHORT).show();
                    } else {
                        String userName = user.getUserName();
                        String phoneNumber = user.getPhoneNumber();
                        String description = user.getDescription();

                        //Crashar appen pga null
                        profilePicture = getView().findViewById(R.id.profilePictureEdit);
                        Log.d("!!!!", "onComplete: Started");

                        if (profilePicture.getDrawable() == null) {
                            Glide.with(getContext()).load(user.getImageUrl()).into(profilePicture);
                        }
                        //ImageView navImage = getView().findViewById(R.id.nav_header_image);
                        //Glide.with(getContext()).load()
                        profileUserName.setText(userName);
                        profilePhoneNumber.setText(String.valueOf(phoneNumber));
                        profileDescription.setText(description);
                    }
                }
            }
        });
    }
}
