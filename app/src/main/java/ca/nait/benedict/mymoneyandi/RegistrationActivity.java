package ca.nait.benedict.mymoneyandi;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegistrationActivity extends AppCompatActivity
{
    static final String TAG = "MainActivity";

    static SQLiteDatabase database;
    static DBManager manager;

    EditText Email;
    EditText Password;
    Button BtnRegistration;
    TextView SignIn;
    ProgressDialog Dialog;

    //FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //mAuth=FirebaseAuth.getInstance();
        registration();
        manager = new DBManager(this);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Dialog = new ProgressDialog(this);
    }
    private void registration()
    {
        Email = findViewById(R.id.email_reg);
        Password = findViewById(R.id.password_reg);
        BtnRegistration = findViewById(R.id.btn_reg);
        SignIn = findViewById(R.id.signin_here);

        BtnRegistration.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = Email.getText().toString().trim();
                String pass = Password.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    Email.setError("Password is Required");
                    return;
                }
//                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener(){
//
//                    @Override
//                    public void onComplete(@NonNull Task task)
//                    {
//                        if(task.isSuccessful())
//                        {
//                            Dialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Registration complete",Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//                        }
//                        else
//                        {
//                            Dialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
                Email.setText("");
                Password.setText("");
                Dialog.setMessage("Processing..");

                ContentValues values = new ContentValues();
                values.put(DBManager.C_EMAIL, email);
                values.put(DBManager.C_PASSWORD, pass);
                try
                {
                    database = manager.getWritableDatabase();
                    database.insertOrThrow(DBManager.TABLE_USERS, null, values);
                    Toast.makeText(getApplicationContext(), "You added:" + email, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    //Dialog.dismiss();
                    Dialog.show();
                    database.close();

                } catch (Exception e)
                {
                    Log.d(TAG, "Error" + e);
                   Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
                }


            }
        });
        SignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}