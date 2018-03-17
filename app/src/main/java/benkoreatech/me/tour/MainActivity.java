package benkoreatech.me.tour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

// we implement the interface here and it contain 2 methods
public class MainActivity extends AppCompatActivity implements View.OnClickListener,RegistrationSuccess {

    RelativeLayout tick;
    EditText email,password;
    String Email,Password;
    TextView signup;
    SigninPreference signinPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tick=(RelativeLayout) findViewById(R.id.tick);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        signup=(TextView) findViewById(R.id.signup);
        signinPreference=new SigninPreference(this);
        tick.setOnClickListener(this);
        signup.setOnClickListener(this);
        if(signinPreference.getLogin()){
            Intent intent=new Intent(this,MapsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tick:
                if(CheckSignin()){
                    Registration registration=new Registration(this);
                    registration.register(null,Email,Password, Constants.login);
                }
                break;
            case R.id.signup:
                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                break;
        }
    }

    public boolean CheckSignin(){
        Email =email.getText().toString();
        Password=password.getText().toString();
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
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onRegistrationSuccess() {

    }

    @Override
    public void onLoginSuccess() {
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
        signinPreference.isSignin(true);
    }
}
