package io.relayr.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import javax.inject.Inject;

import io.relayr.TestEnvironment;
import io.relayr.model.Status;
import rx.Observer;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class MockCloudApiTest extends TestEnvironment {

    @Inject CloudApi api;

    @Captor private ArgumentCaptor<Status> statusCaptor;

    @Mock Observer<Status> subscriber;

    @Before
    public void init() {
        super.init();
        inject();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getServerStatusTest() throws Exception {
        api.getServerStatus().subscribe(subscriber);

        verify(subscriber).onNext(statusCaptor.capture());

        assertThat(statusCaptor.getValue()).isNotNull();
    }
}
