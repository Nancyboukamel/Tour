package benkoreatech.me.tour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import benkoreatech.me.tour.interfaces.RegistrationSuccess;
import benkoreatech.me.tour.objects.Constants;
import benkoreatech.me.tour.utils.Registration;
import benkoreatech.me.tour.utils.SigninPreference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RegistrationSuccess,View.OnFocusChangeListener {

    RelativeLayout tick;
    EditText email,password;
    String Email,Password;
    TextView signup,loginfail;
    SigninPreference signinPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // any view we need to get it by (findViewById)
        tick=(RelativeLayout) findViewById(R.id.tick);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        signup=(TextView) findViewById(R.id.signup);
        loginfail=(TextView) findViewById(R.id.loginfail);
        // Sign in preference storage to save username and email of user and if its login or not ( Shared Preference )
        signinPreference=new SigninPreference(this);
        // if we click on tick or sign up then go to OnClick
        tick.setOnClickListener(this);
        signup.setOnClickListener(this);
        // if the user is already login just open Maps activity
        if(signinPreference.getLogin()){
            Intent intent=new Intent(this,MapsActivity.class);
            startActivity(intent);
        }
        // If we got login error in order to remove the login error text we listen for focus of edit text so it gonna be clear
        email.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // when we click on Tick we check if user exist by api call if yes go to maps activity if no just return error
            case R.id.tick:
                if(CheckSignin()){
                    // declaration of class
                    Registration registration=new Registration(this);
                    // passing email password and URl from Constant class
                    registration.register(null,Email,Password, Constants.login);
                }
                break;
            // when we click on sign up open Register activity
            case R.id.signup:
                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                break;
        }
    }

    public boolean CheckSignin(){
        // check if edit text is null or empty. If yes return error else do nothing
        Email =email.getText().toString().trim();
        Password=password.getText().toString().trim();
        if (Email == null || Email.equalsIgnoreCase("")) {
            email.setError(getResources().getString(R.string.enter_email));
            return false;
        } else if (!isEmailValid(Email)) {
            email.setError(getResources().getString(R.string.email_not_valid));
            return false;
        } else {
            email.setError(null);
        }
        if(Password==null || Password.equalsIgnoreCase("")){
            password.setError(getResources().getString(R.string.enter_password));
            return false;
        }
        else {
            password.setError(null);
        }
            return true;
    }

    public static boolean isEmailValid(String email) {
        // checking if email is valid using regex expression
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onRegistrationSuccess() {

    }

    @Override
    public void onLoginSuccess(String name) {
        // if user login success then savw email name and is Login boolean true in shared preference
        loginfail.setVisibility(View.GONE);
        signinPreference.isSignin(true,Email,name);
        // open Maps activity
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);

    }

    @Override
    public void onLoginFail() {
        loginfail.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            // when we focus we hide (login error)
            case R.id.email :
                loginfail.setVisibility(View.GONE);
                break;
            case R.id.password:
                loginfail.setVisibility(View.GONE);
                break;

        }
    }
}
