package ca.nait.benedict.mymoneyandi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static ca.nait.benedict.mymoneyandi.DBManager.C_EMAIL;
import static ca.nait.benedict.mymoneyandi.DBManager.C_ID;
import static ca.nait.benedict.mymoneyandi.DBManager.C_PASSWORD;
import static ca.nait.benedict.mymoneyandi.DBManager.TABLE_USERS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    static final String TAG = "MainActivity";

    static SQLiteDatabase database;
    static DBManager manager;


    EditText Email;
    EditText Password;
    Button BtnLogin;
    TextView ForgetPassword;
    TextView SignUpHere;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new DBManager(this);

        LoginInfo();

    }

    private void LoginInfo()
    {
        Email = findViewById(R.id.email_login);
        Password =findViewById(R.id.password_login);
        BtnLogin = findViewById(R.id.btn_login);
        ForgetPassword = findViewById(R.id.forget_password);
        SignUpHere = findViewById(R.id.sign_up);
        //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        BtnLogin.setOnClickListener(new View.OnClickListener()
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
               check_User(email, pass);
           }
        });
        // Registration Activity..
        SignUpHere.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
        //Reset Password Activity..
        ForgetPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
    }
    public Boolean check_User(String email, String pass)
    {
        SQLiteDatabase database = manager.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from  Users  where email =" + C_EMAIL + "", null);
        Boolean records_Exist = false;

        if(cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            do
            {
                if(cursor.getString(cursor.getColumnIndex(C_EMAIL)).equals(email))
                {
                    if(pass.equals(cursor.getColumnIndex(C_PASSWORD)))
                    {
                        records_Exist = true;
                        break;
                    }
                }

            }while(cursor.moveToNext());
        };
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        cursor.close();
        database.close();

        return records_Exist;
    }
    @Override
    public void onClick(View button)
    {

    }
}