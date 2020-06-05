package com.example.ecommerceproject.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.Home.HomeActivity;
import com.example.ecommerceproject.Home.EditInfoActivity;
import com.example.ecommerceproject.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserInfoActivity extends AppCompatActivity
{

    private Toolbar toolbar7;
    private TextView profileChangeTextBtn;
    private CircleImageView profileImageView;

    private EditText etEditName, etEditPhone, etEditAddress;
    private Button editsavebtn;

    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private String user_id;

    private String ds_name;
    private String ds_email;
    private String ds_phone_number;
    private String ds_address;
    private Integer phone_no;

    private String checker = "";
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePictureRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        profileImageView = (CircleImageView) findViewById(R.id.settings_profile_image1);
        profileChangeTextBtn = (TextView) findViewById(R.id.profile_image_change_btn1);

        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Company Profile pictures");

        toolbar7 = findViewById(R.id.edit_info_toolbar);
        toolbar7.setTitle("Edit user info");
        setSupportActionBar(toolbar7);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar7.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar7.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        etEditName = findViewById(R.id.etEditName);
        etEditPhone = findViewById(R.id.etEditPhone);
        etEditAddress = findViewById(R.id.etEditAddress);
        editsavebtn = findViewById(R.id.editsavebtn);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        if (currentUser == null) {
            sendToLogin();
        } else {
            user_id = currentUser.getUid();
            getUserDetails();
            editsavebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    if (checker.equals("clicked"))
                    {
                        userInfoSaved();
                    }
                    else
                    {
                        saveUserDetails();
                    }

                }
            });

        }

        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (ActivityCompat.checkSelfPermission(EditUserInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(EditUserInfoActivity.this,"Please grant the permission.", Toast.LENGTH_SHORT).show();
                    requestPermission();
                }else
                {
                    checker = "clicked";
                    CropImage.activity(imageUri)
                            .setAspectRatio(1, 1)
                            .start(EditUserInfoActivity.this);
                }
            }
            private void requestPermission()
            {
                ActivityCompat.requestPermissions(EditUserInfoActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        });
    }



    private void saveUserDetails()
    {
        String name = etEditName.getText().toString();
        String phone_number = etEditPhone.getText().toString();
        String address = etEditAddress.getText().toString();
        final ProgressDialog progressDialog1 = new ProgressDialog(this);
        progressDialog1.setTitle("Update changes");
        progressDialog1.setMessage("Please wait, while we are updating your profile information");
        progressDialog1.setCanceledOnTouchOutside(false);
        progressDialog1.show();

        if (name.isEmpty()) {
            etEditName.setError("Enter your name");
            progressDialog1.dismiss();
        } else if (phone_number.isEmpty()) {
            etEditPhone.setError("Enter your phone number");
            progressDialog1.dismiss();
        } else if (address.isEmpty()) {
            etEditAddress.setError("Enter your address");
            progressDialog1.dismiss();
        } else if (phone_number.length() < 10) {
            etEditPhone.setError("Please enter correct phone number");
            progressDialog1.dismiss();
        } else {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("name", name);
            dataMap.put("phone", phone_number);
            dataMap.put("address", address);
            mDatabase.child("Sellers").child(user_id).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View viewLyout;
                    viewLyout = layoutInflater.inflate(R.layout.ztoast_layout, (ViewGroup)findViewById(R.id.custom_layout));
                    Toast toast = Toast.makeText(getApplicationContext(),"Toast:Gravity.TOP",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setView(viewLyout);
                    toast.show();
                    //Toast.makeText(getApplicationContext(), "Details saved Successfully", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(EditUserInfoActivity.this, Main2Activity.class);
                    startActivity(mainIntent);
                    finish();
                }
            });
        }


    }


    private void userInfoSaved()
    {
         if(checker.equals("clicked"))
        {
            uploadImage();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(EditUserInfoActivity.this, EditInfoActivity.class));
            finish();
        }
    }


    private void uploadImage()
    {

        final String name = etEditName.getText().toString();
        final String phone_number = etEditPhone.getText().toString();
        final String address = etEditAddress.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePictureRef
                    .child(user_id + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Sellers");

                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap. put("name", name);
                                userMap. put("address", address);
                                userMap. put("phoneOrder", phone_number);
                                userMap. put("image", myUrl);
                                ref.child(user_id).updateChildren(userMap);

                                progressDialog.dismiss();

                                startActivity(new Intent(EditUserInfoActivity.this, HomeActivity.class));
                                Toast.makeText(EditUserInfoActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(EditUserInfoActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }



    private void getUserDetails()
    {
        mDatabase.child("Sellers").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("name").exists()) {
                    ds_name = dataSnapshot.child("name").getValue().toString();
                }
                if (dataSnapshot.child("phone").exists()) {
                    ds_phone_number = dataSnapshot.child("phone").getValue().toString();
                }
                if (dataSnapshot.child("address").exists()) {
                    ds_address = dataSnapshot.child("address").getValue().toString();
                }
                if (dataSnapshot.child("image").exists()) {
                    String image = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(image).into(profileImageView);
                }

                putUserDetailsToEditText();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void putUserDetailsToEditText()
    {
        etEditName.setText(ds_name);
        etEditPhone.setText(ds_phone_number);
        etEditAddress.setText(ds_address);
    }

    private void sendToLogin()
    {
        Intent loginIntent = new Intent(EditUserInfoActivity.this, SellerLoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

}
