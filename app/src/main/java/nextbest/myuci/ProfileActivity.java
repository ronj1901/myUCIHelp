package nextbest.myuci;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity   implements View.OnClickListener {

    private Button logoutButton ;
    private TextView profileName;
    private FirebaseAuth mFirebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    private TextView nameText, majorText, phoneText, levelText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirebaseAuth  = FirebaseAuth.getInstance();
        profileName = (TextView) findViewById(R.id.user_profile_name);
        majorText = (TextView) findViewById(R.id.studentMajorAndLevel);
        phoneText = (TextView) findViewById(R.id.phoneNumber);



        logoutButton = (Button) findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener((View.OnClickListener) this);



        FirebaseDatabase database= FirebaseDatabase.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference().child("users").child(user.getUid());

    ValueEventListener postListener = new ValueEventListener()

      {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                profileName.setText(user.getName());
                majorText.setText("Major: " + user.getMajor() + "/" + user.getLevel());
                phoneText.setText("Phone Number: " + user.getDescription() );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };

        myRef.addValueEventListener(postListener);



    }

    @Override
    public void onClick(View v) {
        if(v == logoutButton){
            mFirebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
