package benkoreatech.me.tour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import benkoreatech.me.tour.interfaces.RegistrationSuccess;
import benkoreatech.me.tour.objects.Constants;
import benkoreatech.me.tour.utils.Registration;

public class Register extends AppCompatActivity implements RegistrationSuccess{
    EditText username,password,confirm_password,email;
    RelativeLayout signup;
    TextView already_have_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        confirm_password=(EditText) findViewById(R.id.confirm_password);
        email=(EditText) findViewById(R.id.email);
        signup=(RelativeLayout) findViewById(R.id.signup);
        already_have_account=(TextView) findViewById(R.id.already_have_account);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void signUp(){
        String Username=username.getText().toString().trim();
        String Password=password.getText().toString().trim();
        String Confirm_Password=confirm_password.getText().toString().trim();
        String Email=email.getText().toString().trim();
        if(VerifySignup(Username,Password,Confirm_Password,Email)){
            // http call to post data then go to sign in page
            Registration registration=new Registration(this);
            registration.register(Username,Email,Password, Constants.register);
        }
    }

    private boolean VerifySignup(String Username, String Password, String Confirm_password, String Email) {
        if(Username==null|| Username.equalsIgnoreCase("")){
            username.setError(getResources().getString(R.string.enter_username));
            return false;
        }
        else {
            username.setError(null);
        }
        if(Password==null || Password.equalsIgnoreCase("")){
            password.setError(getResources().getString(R.string.enter_password));
            return false;
        }
        else{
            password.setError(null);
        }
        if(Confirm_password==null || Confirm_password.equalsIgnoreCase("")){
            confirm_password.setError(getResources().getString(R.string.enter_confirm_password));
            return false;
        }
        else if(!Password.equalsIgnoreCase(Confirm_password)){
            confirm_password.setError(getResources().getString(R.string.password_match));
            return false;
        }
        else {
            confirm_password.setError(null);
        }

        if (Email == null || Email.equalsIgnoreCase("")) {
            email.setError(getResources().getString(R.string.enter_email));
            return false;
        } else if (!isEmailValid(Email)) {
            email.setError(getResources().getString(R.string.email_not_valid));
            return false;
        } else {
            email.setError(null);
        }

       return true;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onRegistrationSuccess() {
        Intent intent=new Intent(Register.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFail() {

    }


}
