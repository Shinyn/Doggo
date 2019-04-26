package com.l.doggo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddPetFragment extends Fragment {

    public EditText name, breed, age, height, weight;
    CheckBox checkBoxNeutered;
    RadioButton genderButton;
    boolean genderCheck = true;
    boolean neuteredCheck = false;
    private ArrayList<Dog> dogArrayList = new ArrayList<>();
    int number;

    Button createDogBtn;
    RadioGroup radioGroup;
    Dog dog;

    ImageButton addDogImageBtn;
    ImageView dogImage;
    Uri imageUri;
    private static final int PIC_IMAGE_REQUEST = 1;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_pet, container, false);
    }

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
                    Uri downloadUri = task.getResult();
                    createDog(downloadUri.toString());
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
            dogImage.setImageURI(imageUri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storageReference = FirebaseStorage.getInstance().getReference("dog_pics");
        databaseReference = FirebaseDatabase.getInstance().getReference("dog_pics");
        addDogImageBtn = getView().findViewById(R.id.add_pet_image_button);
        dogImage = getView().findViewById(R.id.addDogPicture);

        addDogImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        createDogBtn = getView().findViewById(R.id.createDogBtn);
        createDogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    uploadFile();
                    printDogArrayList();

                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "You have to fill all fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createDog(final String downloadUrl ) {

        // Kopplar inputen i xml:en till namnen som ska in i Dog konstruktorn
        name = getView().findViewById(R.id.dogName);
        breed = getView().findViewById(R.id.dogBreed);
        age = getView().findViewById(R.id.dogAge);
        height = getView().findViewById(R.id.dogHeight);
        weight = getView().findViewById(R.id.dogWeight);
        checkBoxNeutered = getView().findViewById(R.id.checkBoxNeutered);
        radioGroup = getView().findViewById(R.id.radioGroup);

        final String name2 = name.getText().toString().trim();
        final String breed2 = breed.getText().toString().trim();
        final String age2 = age.getText().toString();
        final String height2 = height.getText().toString();
        final String weight2 = weight.getText().toString();

        // Kollar om hunden är kastrerad eller ej
        checkBoxNeutered = getView().findViewById(R.id.checkBoxNeutered);
        neuteredCheck = checkBoxNeutered.isChecked();
                /* ^ Gör samma sak som:
                if (checkBoxNeutered.isChecked()) {
                    neuteredCheck = true;
                } else {
                    neuteredCheck = false;
                }
                */

        // Kollar hane eller hona -> ändra med setGender till true eller false
        final int radioId = radioGroup.getCheckedRadioButtonId();
        genderButton = getView().findViewById(radioId);
        if (radioId == R.id.radioBtnMale) {
            genderCheck = true;
        } else if (radioId == R.id.radioBtnFemale) {
            genderCheck = false;
        }

        // Sätter hundens ägare och dess telefonnummer
        final String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(owner);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                number = task.getResult().getDouble( "phoneNumber").intValue();


                // Måste kolla så att alla fält är ifyllda korrekt, är dom det så skapa hund annars error
                if (!name2.equals("") || !breed2.equals("") || !age2.equals("")
                        || !height2.equals("") || !weight2.equals("")) {


                    // Skapar ny hund
                    dog = new Dog(name2, breed2, owner, downloadUrl, number,
                            Integer.parseInt(age2), Integer.parseInt(height2), Integer.parseInt(weight2),
                            neuteredCheck, genderCheck); /* Integer ger NumberFormatException om man inte gör try / catch */

                    // Lägger till hunden till ArrayListan med toastmeddelande
                    addDogToArrayList(dog);
                    Toast.makeText(getActivity(), "Dog created", Toast.LENGTH_SHORT).show();

                    // clear all editTexts / set all paramaters to default
                    name.setText("");
                    breed.setText("");
                    age.setText("");
                    height.setText("");
                    weight.setText("");
                    if (checkBoxNeutered.isChecked()) {
                        checkBoxNeutered.toggle();
                    }

                } else {
                    Toast.makeText(getActivity(), "You have to fill all fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addDogToArrayList(Dog dog) {
        // Uppdaterar listan på firestore
        dogArrayList.add(dog);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("dogs").add(dog);
    }

    public void printDogArrayList() {

        for( Dog dog : dogArrayList) {
            Log.d("!!!", "Array contain these dogs: " + "Name: " + dog.getName() + ", "
                    + "Breed: " + dog.getBreed() + ", " + "Age: " + dog.getAge() + ", "
                    + "Height " + dog.getHeight() + ", " + "Weight " + dog.getWeight() + ", "
                    + "Neutered " + dog.isNeutered() + ", " + "Male " + dog.isMale());
            // Skriv ut listan som test för o se om dog läggs till
        }
    }
}