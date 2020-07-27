package com.sel.smartfood.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sel.smartfood.data.local.PreferenceManager;
import com.sel.smartfood.data.model.TransHistory;
import com.sel.smartfood.data.model.User;
import com.sel.smartfood.data.remote.firebase.FirebaseInfo;
import com.sel.smartfood.data.remote.firebase.FirebasePaymentAccountImpl;
import com.sel.smartfood.data.remote.firebase.FirebaseService;
import com.sel.smartfood.data.remote.firebase.FirebaseServiceBuilder;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminViewModel extends AndroidViewModel {
    private PreferenceManager preferenceManager;
    private FirebaseService firebaseService;
    private MutableLiveData<User> userInfo = new MutableLiveData<>();
    private MutableLiveData<List<TransHistory>> tranHistories = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable;
    public AdminViewModel(@NonNull Application application) {
        super(application);
        preferenceManager = new PreferenceManager(application);
        firebaseService = new FirebaseServiceBuilder().addInfo(new FirebaseInfo()).addPaymentAccount(new FirebasePaymentAccountImpl()).build();
        compositeDisposable = new CompositeDisposable();
    }

    public void getUser(){
        String key = preferenceManager.getEmail().split("@")[0];
        Disposable d = firebaseService.getUser(key)
                        .subscribeOn(Schedulers.io())
                        .subscribe(u -> userInfo.postValue(u), e -> userInfo.postValue(null));
        compositeDisposable.add(d);
    }
    public void getAllTransHistories(){
        Disposable d = firebaseService.getAllTransHistories()
                    .subscribeOn(Schedulers.io())
                    .subscribe(user -> tranHistories.postValue(user), e -> tranHistories.postValue(null));
        compositeDisposable.add(d);
    }
    public void logout(){
        preferenceManager.deleteLogInState();
        preferenceManager.clearEmail();
    }

    public LiveData<List<TransHistory>> getTranHistories() {
        return tranHistories;
    }

    public LiveData<User> getUserInfo() {
        return userInfo;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }
}
