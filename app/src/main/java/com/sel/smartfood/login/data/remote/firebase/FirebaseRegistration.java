package com.sel.smartfood.login.data.remote.firebase;

import io.reactivex.rxjava3.core.Completable;

public interface FirebaseRegistration {
    Completable register(String email, String password);
}
