package com.sel.smartfood.login.viewmodel;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.sel.smartfood.R;
import com.sel.smartfood.login.data.remote.firebase.FirebaseRegistrationImpl;
import com.sel.smartfood.login.data.remote.firebase.FirebaseService;
import com.sel.smartfood.login.data.remote.firebase.FirebaseServiceBuilder;
import com.sel.smartfood.login.data.model.RegisterFormState;
import com.sel.smartfood.utils.Result;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterViewModel extends ViewModel {
    public final static int TIME_OUT_SEC = 10;
    public final static String INVALID_EMAIL = "Email đã tồn tại! Thử email khác.";
    public final static String ERROR_MESSAGE = "Đăng ký thất bại! Vui lòng thử lại sau.";

    private MutableLiveData<Result<Boolean>> registerResult = new MutableLiveData<>();
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private FirebaseService firebaseService = new FirebaseServiceBuilder()
                                                .addRegistration(new FirebaseRegistrationImpl())
                                                .build();
    private CompositeDisposable compositeDisposable;

    public void register(String email, String password){
        Disposable d = firebaseService.register(email, password)
                                      .timeout(TIME_OUT_SEC, TimeUnit.SECONDS)
                                      .subscribeOn(Schedulers.io())
                                      .subscribe(() -> registerResult.postValue(new Result.Success<>(true)), this::handleRegisterError);
        if (compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(d);
    }

    private void handleRegisterError(Throwable e) {
        if (e instanceof FirebaseAuthUserCollisionException){
            registerResult.postValue(new Result.Error(new Exception(INVALID_EMAIL)));
        }
        else{
            registerResult.postValue(new Result.Error(new Exception(ERROR_MESSAGE)));
        }
    }

    public void registerDataChanged(String fullname, String email, String phoneNumber, String password, String repassword){
        Integer name, mail, phone, pass, repass;
        name = mail = phone = pass = repass = null;
        boolean inValid = false;

        if (!isFullnameValid(fullname)){
            inValid = true;
            name = R.string.invalid_new_fullname;
        }
        if (!isEmailValid(email)){
            inValid = true;
            mail = R.string.invalid_new_email;
        }
        if (!isPhoneNumberValid(phoneNumber)){
            inValid = true;
            phone = R.string.invalid_new_phone_number;
        }
        if (!isPasswordValid(password)){
            inValid = true;
            pass = R.string.invalid_new_password;
        }
        if (repassword == null){
            inValid = true;
            repass = R.string.invalid_renew_password;
        }
        else if (!repassword.equals(password)){
            inValid = true;
            repass = R.string.invalid_renew_password;
        }
        if (inValid){
            registerFormState.setValue(new RegisterFormState(name, mail, phone, pass, repass));
        }
        else{
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null && compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    public LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    public LiveData<Result<Boolean>> getRegisterResult() {
        return registerResult;
    }

    private boolean isFullnameValid(String fullname){
        return fullname != null && fullname.length() > 2;
    }
    private boolean isEmailValid(String email){
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isPhoneNumberValid(String phoneNumber){
        return phoneNumber != null && Patterns.PHONE.matcher(phoneNumber).matches();
    }
    private boolean isPasswordValid(String password){
        return password != null && password.length() > 5 && password.length() < 30;
    }
}
