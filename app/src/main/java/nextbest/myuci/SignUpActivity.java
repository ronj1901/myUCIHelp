package nextbest.myuci;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton buttonSignUp;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLogin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonSignUp = (AppCompatButton) findViewById(R.id.signupID);
        editTextEmail = (EditText) findViewById(R.id.emailID);
        editTextPassword = (EditText) findViewById(R.id.passwordID);
        textViewLogin = (TextView) findViewById(R.id.loginID);

        buttonSignUp.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!= null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }


    }

    private void signUpUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener
                (this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user registrations successful
                    finish();
                    startActivity(new Intent(getApplicationContext(), EditInfo.class));
                    Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v == buttonSignUp){
            signUpUser();
        }

        if(v == textViewLogin){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
}
