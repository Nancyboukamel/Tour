package benkoreatech.me.tour.interfaces;

public interface RegistrationSuccess {
   // this i interface

    // when you sign up
    public void onRegistrationSuccess();

    // when you login
    public void onLoginSuccess(String name);

    // when login fail
    public void onLoginFail();
}
