package io.relayr.api.mock;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import io.relayr.api.CloudApi;
import io.relayr.model.LogEvent;
import io.relayr.model.Status;
import rx.Observable;
import rx.Subscriber;

import static io.relayr.api.mock.MockBackend.SERVER_STATUS;

public class MockCloudApi implements CloudApi {

    private static final String TAG = "MockCloudApi";
    private final MockBackend mMockBackend;

    @Inject
    public MockCloudApi(MockBackend mockBackend) {
        mMockBackend = mockBackend;
    }

    @Override
    public Observable<Status> getServerStatus() {
        return mMockBackend.createObservable(new TypeToken<Status>() { }, SERVER_STATUS);
    }

    @Override
    public Observable<Void> logMessage(final List<LogEvent> events) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                for (LogEvent event : events) {
                    Log.d(TAG, new Gson().toJson(event));
                }
                subscriber.onNext(null);
            }
        });
    }
}
