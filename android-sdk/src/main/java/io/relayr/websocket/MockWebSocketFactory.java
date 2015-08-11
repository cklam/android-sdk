package io.relayr.websocket;

import javax.inject.Inject;

import io.relayr.api.mock.MockBackend;

class MockWebSocketFactory extends WebSocketFactory {

    private final MockBackend mMockBackend;

    @Inject
    MockWebSocketFactory(MockBackend mockBackend) {
        mMockBackend = mockBackend;
    }

    public WebSocket createWebSocket() {
        return new MockWebSocket(mMockBackend);
    }

}
