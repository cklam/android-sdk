package io.relayr;

import android.app.Activity;
import android.content.Context;

import javax.inject.Inject;

import io.relayr.activity.LoginActivity;
import io.relayr.api.AccountsApi;
import io.relayr.api.DeviceModelsApi;
import io.relayr.api.GroupsApi;
import io.relayr.api.RelayrApi;
import io.relayr.api.UserApi;
import io.relayr.ble.BleUtils;
import io.relayr.ble.RelayrBleSdk;
import io.relayr.log.Logger;
import io.relayr.model.Device;
import io.relayr.model.Transmitter;
import io.relayr.model.User;
import io.relayr.model.account.Account;
import io.relayr.model.groups.Group;
import io.relayr.storage.DataStorage;
import io.relayr.storage.DeviceModelCache;
import io.relayr.util.ReachabilityUtils;
import io.relayr.websocket.WebSocketClient;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * The RelayrSdk Class serves as the access point to all endpoints in the Android SDK.
 * It includes basic calls such as user login validation and can also call the handlers of the
 * other classes- {@link io.relayr.api.RelayrApi}, {@link io.relayr.ble.RelayrBleSdk} and
 * {@link io.relayr.websocket.WebSocketClient}.
 * -----------------------------------------------------------------------------------------------
 * For easy start, after logging in, obtain {@link User} by calling {@link #getUser()}.
 * This is main object in Relayr SDK that can be used to fetch users {@link Device},
 * {@link Transmitter}, {@link Group} and {@link Account} objects.
 * <p/>
 * Every mentioned object has it's own interaction methods so direct usage of APIs is not necessary.
 * However it's still possible to obtain any of the desired API handlers through appropriate method:
 * <ul>
 * <li>{@link #getUserApi()}</li>
 * <li>{@link #getRelayrApi()}</li>
 * <li>{@link #getGroupsApi()}</li>
 * <li>{@link #getAccountsApi()}</li>
 * <li>{@link #getDeviceModelsApi()} or using cache {@link #getDeviceModelsCache()}</li>
 * </ul>
 * For other details check methods JavaDoc
 */
public class RelayrSdk {

    public static final String PERMISSION_INTERNET = "android.permission.INTERNET";
    public static final String PERMISSION_NETWORK = "android.permission.ACCESS_NETWORK_STATE";

    @Inject static UserApi mUserApi;
    @Inject static RelayrApi mRelayrApi;

    @Inject static GroupsApi mGroupsApi;
    @Inject static AccountsApi mAccountsApi;
    @Inject static DeviceModelsApi sDeviceModelsApi;
    @Inject static DeviceModelCache sDeviceModelsCache;

    @Inject static WebSocketClient mWebSocketClient;

    @Inject static Logger mLoggerUtils;
    @Inject static ReachabilityUtils sReachabilityUtils;

    @Inject static BleUtils mBleUtils;
    @Inject static RelayrBleSdk mRelayrBleSdk;

    private static Subscriber<? super User> mLoginSubscriber;

    /**
     * Initializes the SDK. Should be built when the {@link android.app.Application} is
     * created. After SDK is initiated
     */
    public static class Builder {

        private final Context mContext;
        private boolean mInMockMode;

        public Builder(Context context) {
            mContext = context;
        }

        /**
         * Initializes the SDK in Mock Mode.
         * In this mode, mock reading values are generated.
         * Used for testing purposes, without the need of a WunderBar or an internet connection.
         */
        public Builder inMockMode(boolean inMockMode) {
            mInMockMode = inMockMode;
            return this;
        }

        public void build() {
            RelayrApp.init(mContext, mInMockMode);
        }
    }

    /**
     * Returns user object. This is main object in Relayr SDK that can be used to fetch user {@link Device},
     * {@link Transmitter}, {@link Group} and {@link Account}.
     * Any of the mentioned objects has it's own special methods so direct usage of APIs is not necessary but it's still possible
     */
    public Observable<User> getUser() {
        return mUserApi.getUserInfo();
    }

    /**
     * Returns the version of the SDK
     * @return the version String
     */
    public static String getVersion() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * Returns the handler of the Relayr API. Use after obtaining User data.
     * Used as an access point to class {@link io.relayr.api.RelayrApi}
     */
    public static RelayrApi getRelayrApi() {
        return mRelayrApi;
    }

    /**
     * Returns the handler of the Accounts API. Use to add third party accounts to the relayr user.
     * Used as an access point to class {@link io.relayr.api.AccountsApi}
     */
    public static AccountsApi getAccountsApi() {
        return mAccountsApi;
    }

    /**
     * Returns the handler of the Groups API.
     * Used as an access point to class {@link io.relayr.api.GroupsApi}
     */
    public static GroupsApi getGroupsApi() {
        return mGroupsApi;
    }

    /**
     * Return the handler of the relayr DeviceModels API. Returns new device models. To use properly
     * Used as an access point to class {@link io.relayr.api.DeviceModelsApi}
     */
    public static DeviceModelsApi getDeviceModelsApi() {
        return sDeviceModelsApi;
    }

    /**
     * Returns cached {@link io.relayr.model.models.DeviceModel} objects.
     * Cache will be populated with models from {@link Device#model} when device is fetched.
     * Use instead of {@link #getDeviceModelsApi()}
     */
    public static DeviceModelCache getDeviceModelsCache() {
        return sDeviceModelsCache;
    }

    /**
     * @return the handler of the relayr User API. Use to get user data and user
     * transmitters, devices and accounts
     * Used as an access point to class {@link io.relayr.api.UserApi}
     */
    public static UserApi getUserApi() {
        return mUserApi;
    }

    /**
     * Launches the login activity. Enables the user to log in to the relayr platform.
     */
    public static Observable<User> logIn(Activity currentActivity) {
        Observable.OnSubscribe<User> onSubscribe = new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                mLoginSubscriber = subscriber;
            }
        };
        LoginActivity.startActivity(currentActivity);
        return Observable
                .create(onSubscribe)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Checks whether or not a user is logged in to the relayr platform.
     * @return true if the user is logged in, false otherwise.
     */
    public static boolean isUserLoggedIn() {
        return DataStorage.isUserLoggedIn();
    }

    /**
     * Logs the user out of the relayr platform.
     */
    public static void logOut() {
        DataStorage.logOut();
    }

    /**
     * Logs an event in the relayr platform. In debug mode, the event will be logged in the console
     * instead. In production mode messages will be saved locally and logged to platform
     * in bulks after every 5 logged messages. Connection availability and platform reachability
     * are checked automatically when using this method.
     * @return whether the logging event was performed
     */
    public static boolean logMessage(String message) {
        return mLoggerUtils.logMessage(message);
    }

    /**
     * Sends bulk of all previously logged messages to relayr platform. Connection availability
     * and platform reachability are checked automatically when using this method.
     * @return whether the messages were flushed
     */
    public static boolean flushLoggedMessages() {
        return mLoggerUtils.flushLoggedMessages();
    }

    /**
     * Checks if android.permission.INTERNET permission is granted by application.
     * @return true if granted, false otherwise
     */
    public static boolean isPermissionGrantedToAccessInternet() {
        return sReachabilityUtils.isPermissionGranted(PERMISSION_INTERNET);
    }

    /**
     * Checks if android.permission.ACCESS_NETWORK_STATE permission is granted by application.
     * @return true if granted, false otherwise
     */
    public static boolean isPermissionGrantedToAccessTheNetwork() {
        return sReachabilityUtils.isPermissionGranted(PERMISSION_NETWORK);
    }

    /**
     * Checks connection to the Internet.
     * @return true if connected, false otherwise
     */
    public static boolean isConnectedToInternet() {
        return sReachabilityUtils.isConnectedToInternet();
    }

    /**
     * Checks relayr platform reachability.
     * @return true if platform is reachable, false otherwise
     */
    public static Observable<Boolean> isPlatformReachable() {
        return sReachabilityUtils.isPlatformReachable();
    }

    /**
     * Used as an access point to the class {@link io.relayr.websocket.WebSocketClient}
     * @return the handler of the WebSocket client
     */
    public static WebSocketClient getWebSocketClient() {
        return mWebSocketClient;
    }

    /**
     * Provides the relayr sdk with a BLE implementation or an empty implementation, in case
     * bluetooth is not available on the device.
     * An empty implementation is one in which the methods do not function
     * This call should be preceded by {@link io.relayr.RelayrSdk#isBleSupported}
     * to check whether BLE is supported
     * and by {@link io.relayr.RelayrSdk#isBleAvailable} to check whether BLE is activated
     * @return the handler of the Relayr BLE SDK
     */
    public static RelayrBleSdk getRelayrBleSdk() {
        return mRelayrBleSdk;
    }

    /**
     * Checks whether or not Bluetooth is supported.
     * Should be called before the RelayrBleSdk handler {@link #getRelayrBleSdk}
     * @return true if Bluetooth is supported, false otherwise.
     */
    public static boolean isBleSupported() {
        return mBleUtils.isBleSupported();
    }

    /**
     * Checks whether Bluetooth is turned on or not.
     * @return true if Bluetooth is turned on, false otherwise.
     * Should be called before calling the RelayrBleSdk handler {@link #getRelayrBleSdk}.
     * The user can be prompted to activate their Bluetooth using
     * {@link #promptUserToActivateBluetooth}
     */
    public static boolean isBleAvailable() {
        return mBleUtils.isBleAvailable();
    }

    /**
     * Prompts the user to activate Bluetooth.
     * The method will not perform any action in case Bluetooth is not supported,
     * i.e. if {@link #isBleSupported()} returns true.
     * @param activity an instance of {@link android.app.Activity}
     */
    public static void promptUserToActivateBluetooth(Activity activity) {
        if (isBleSupported()) mBleUtils.promptUserToActivateBluetooth(activity);
    }

    /**
     * Listener indicating a 'login' event
     * @return the listener or null if doesn't exist
     */
    public static Subscriber<? super User> getLoginSubscriber() {
        return mLoginSubscriber;
    }
}
