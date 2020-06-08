package com.sel.smartfood.login.data.remote.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.core.Completable;

public class FirebaseAuthenticationImpl implements FirebaseAuthentication {
    private static FirebaseAuthenticationImpl instance;
    private final FirebaseAuth firebaseAuth;
    public FirebaseAuthenticationImpl(){
        firebaseAuth = FirebaseAuth.getInstance();
    }
    @NonNull
    public Completable loginWithEmail(String email, String password){
        return Completable.create(emitter->{
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (!emitter.isDisposed() && task.isSuccessful()){
                            emitter.onComplete();
                        }
                        else{
                            emitter.onError(task.getException());
                        }
                    });
        });
    }
    @Nullable
    public final FirebaseUser getCurrentUser(){
        return firebaseAuth.getCurrentUser();
    }
}
