package io.relayr.api;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import io.relayr.TestEnvironment;
import io.relayr.model.account.AccountUrl;
import io.relayr.model.groups.Group;
import io.relayr.model.groups.GroupCreate;
import rx.Observer;
import rx.Subscriber;

import static org.fest.assertions.api.Assertions.assertThat;

public class MockGroupsApiTest extends TestEnvironment {

    @Inject GroupsApi api;

    private Group group;

    @Before
    public void init() {
        super.init();
        inject();
    }

    @Test
    public void createGroupTest() {
        api.createGroup(null)
                .subscribe(new Observer<Group>() {
                    @Override public void onCompleted() {
                        countDown();
                    }

                    @Override public void onError(Throwable e) {
                        countDown();
                    }

                    @Override public void onNext(Group g) {
                        group = g;
                        countDown();
                    }
                });

        await();

        assertThat(group).isNotNull();
        assertThat(group.getName()).isEqualTo("Test group");
    }

    @Test
    public void updateGroupTest() {
        api.createGroup(null)
                .subscribe(new Observer<Group>() {
                    @Override public void onCompleted() {countDown();}

                    @Override public void onError(Throwable e) {countDown();}

                    @Override public void onNext(Group g) {
                        group = g;
                        countDown();
                    }
                });

        await();

        group.update("name").subscribe();
        assertThat(group).isNotNull();
        assertThat(group.getName()).isEqualTo("name");
    }


    @Test
    public void getGroupByIdTest() throws Exception {
        api.getGroup("ID")
                .subscribe(new Observer<Group>() {
                    @Override public void onCompleted() {
                        countDown();
                    }

                    @Override public void onError(Throwable e) {
                        countDown();
                    }

                    @Override public void onNext(Group g) {
                        group = g;
                        countDown();
                    }
                });

        await();

        assertThat(group).isNotNull();
        assertThat(group.getName()).isEqualTo("Test group");
    }
}
