package io.relayr.android.log;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.relayr.android.TestEnvironment;
import io.relayr.java.model.LogEvent;

import static org.fest.assertions.api.Assertions.assertThat;

public class LoggerStorageTest extends TestEnvironment {

    @Before
    public void before() {
        super.init();
        io.relayr.android.log.LoggerStorage.init(3);
    }

    @After
    public void after() {
        io.relayr.android.log.LoggerStorage.STORAGE.edit().clear().apply();
        io.relayr.android.log.LoggerStorage.LoggerPropertiesStorage.clear();
    }

    @Test
    public void saveMultipleMsgsTest() {
        LogEvent l = new LogEvent("");

        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l)).isFalse();
        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l)).isFalse();
        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l)).isTrue();

        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.contains("1")).isTrue();
        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.contains("2")).isTrue();
        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.contains("3")).isTrue();

        assertThat(io.relayr.android.log.LoggerStorage.sHead).isEqualTo(3);
        assertThat(io.relayr.android.log.LoggerStorage.sTotal).isEqualTo(3);
    }

    @Test
    public void loadMessagesTest() {
        LogEvent l1 = new LogEvent("1");
        LogEvent l2 = new LogEvent("2");
        LogEvent l3 = new LogEvent("3");
        LogEvent l4 = new LogEvent("4");

        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l1)).isFalse();
        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l2)).isFalse();
        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l3)).isTrue();
        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l4)).isTrue();

        assertThat(io.relayr.android.log.LoggerStorage.sHead).isEqualTo(4);
        assertThat(io.relayr.android.log.LoggerStorage.sTotal).isEqualTo(4);

        List<LogEvent> events = io.relayr.android.log.LoggerStorage.loadMessages();
        assertThat(events.isEmpty()).isFalse();
        assertThat(events.size()).isEqualTo(3);

        assertThat(events.containsAll(Arrays.asList(l1, l2, l3)));

        assertThat(io.relayr.android.log.LoggerStorage.sHead).isEqualTo(4);
        assertThat(io.relayr.android.log.LoggerStorage.sTotal).isEqualTo(1);

        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.contains("1")).isFalse();
        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.contains("4")).isTrue();
    }

    @Test
    public void loadAllMessagesTest() {
        LogEvent l1 = new LogEvent("1");

        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l1)).isFalse();
        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l1)).isFalse();
        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l1)).isTrue();
        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l1)).isTrue();
        assertThat(io.relayr.android.log.LoggerStorage.saveMessage(l1)).isTrue();

        List<LogEvent> events = io.relayr.android.log.LoggerStorage.loadAllMessages();
        assertThat(events.isEmpty()).isFalse();
        assertThat(events.size()).isEqualTo(5);

        assertThat(events.containsAll(Arrays.asList(l1, l1, l1, l1, l1)));

        assertThat(io.relayr.android.log.LoggerStorage.sHead).isEqualTo(0);
        assertThat(io.relayr.android.log.LoggerStorage.sTotal).isEqualTo(0);

        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.contains(new Gson().toJson(l1))).isFalse();
    }

    @Test
    public void saveMultipleMsgsSynchronizationTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 10; i++) {
                                io.relayr.android.log.LoggerStorage.saveMessage(new LogEvent("" + i));
                            }
                        }
                    }).start();
                }
            }
        }).start();

        await();

        assertThat(io.relayr.android.log.LoggerStorage.sHead).isEqualTo(100);
        assertThat(io.relayr.android.log.LoggerStorage.sTotal).isEqualTo(100);

        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.getString("100", null)).isNotNull();
        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.getString("101", null)).isNull();
    }

    @Test
    public void saveAndLoadMultipleMsgsSynchronizationTest() {
        for (int i = 0; i < 5; i++)
            io.relayr.android.log.LoggerStorage.saveMessage(new LogEvent(""));

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if (i % 5 == 0) io.relayr.android.log.LoggerStorage.saveMessage(new LogEvent("" + i));
                    if (i % 50 == 0)
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                io.relayr.android.log.LoggerStorage.loadMessages();
                            }
                        }).start();
                }
            }
        }).start();

        await();

        assertThat(io.relayr.android.log.LoggerStorage.sHead).isEqualTo(25);
        assertThat(io.relayr.android.log.LoggerStorage.sTotal).isEqualTo(19);

        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.getString("7", null)).isNotNull();
        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.getString("6", null)).isNull();
    }

    @Test
    public void loadAllMsgsSynchronizationTest() {
        for (int i = 0; i < 100; i++)
            io.relayr.android.log.LoggerStorage.saveMessage(new LogEvent(""));

        List<LogEvent> logEvents = io.relayr.android.log.LoggerStorage.loadAllMessages();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++)
                    io.relayr.android.log.LoggerStorage.saveMessage(new LogEvent(""));
            }
        }).start();

        await();

        assertThat(logEvents.size()).isEqualTo(100);

        assertThat(io.relayr.android.log.LoggerStorage.sHead).isEqualTo(10);
        assertThat(io.relayr.android.log.LoggerStorage.sTotal).isEqualTo(10);

        assertThat(io.relayr.android.log.LoggerStorage.STORAGE.getString("50", null)).isNull();
    }

    @Test
    public void loadOldMessages_shouldFlush() {
        io.relayr.android.log.LoggerStorage.saveMessage(new LogEvent("1"));

        assertThat(io.relayr.android.log.LoggerStorage.isEmpty()).isFalse();

        io.relayr.android.log.LoggerStorage.init(3);
        io.relayr.android.log.LoggerStorage.init(3);

        assertThat(io.relayr.android.log.LoggerStorage.isEmpty()).isFalse();

        io.relayr.android.log.LoggerStorage.init(3);
        assertThat(io.relayr.android.log.LoggerStorage.oldMessagesExist()).isTrue();
    }

    @Test
    public void loadOldMessages_shouldNOTFlush() {
        io.relayr.android.log.LoggerStorage.saveMessage(new LogEvent("1"));

        assertThat(io.relayr.android.log.LoggerStorage.isEmpty()).isFalse();

        io.relayr.android.log.LoggerStorage.init(3);
        io.relayr.android.log.LoggerStorage.init(3);

        io.relayr.android.log.LoggerStorage.saveMessage(new LogEvent("2"));

        assertThat(io.relayr.android.log.LoggerStorage.isEmpty()).isFalse();

        io.relayr.android.log.LoggerStorage.init(3);

        assertThat(io.relayr.android.log.LoggerStorage.oldMessagesExist()).isFalse();
    }
}
