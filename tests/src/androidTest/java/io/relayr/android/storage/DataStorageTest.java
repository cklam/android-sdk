package io.relayr.android.storage;

import org.junit.Before;
import org.junit.Test;

import io.relayr.android.TestEnvironment;

import static org.fest.assertions.api.Assertions.assertThat;

public class DataStorageTest extends TestEnvironment {

    @Before
    public void init() {
        super.init();
        io.relayr.android.storage.DataStorage.logOut();
    }

    @Test
    public void storageSaveTest() {
        io.relayr.android.storage.DataStorage.saveUserId("user");

        assertThat(io.relayr.android.storage.DataStorage.getUserId()).isNotNull();
        assertThat(io.relayr.android.storage.DataStorage.getUserId()).isEqualTo("user");
    }

    @Test
    public void storageLogInTest() {
        io.relayr.android.storage.DataStorage.saveUserId("user");
        assertThat(io.relayr.android.storage.DataStorage.isUserLoggedIn()).isFalse();

        io.relayr.android.storage.DataStorage.saveUserToken("token");
        assertThat(io.relayr.android.storage.DataStorage.isUserLoggedIn()).isTrue();
    }
}
