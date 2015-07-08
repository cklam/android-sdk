package io.relayr.api;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import io.relayr.TestEnvironment;
import io.relayr.model.account.AccountUrl;
import io.relayr.model.groups.Group;
import rx.Observer;

import static org.fest.assertions.api.Assertions.assertThat;

public class MockGroupsApiTest extends TestEnvironment {

    @Inject GroupsApi api;

    private int numOfObjects = 0;
    private Group group;

    @Before
    public void init() {
        super.init();
        inject();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getGroupsTest() throws Exception {
        api.getGroups().subscribe(new Observer<List<Group>>() {
            @Override public void onCompleted() {
                countDown();
            }

            @Override public void onError(Throwable e) {
                countDown();
            }

            @Override public void onNext(List<Group> groups) {
                numOfObjects = groups.size();
                countDown();
            }
        });

        await();

        assertThat(numOfObjects).isEqualTo(2);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getGroupByIdTest() throws Exception {
        api.getGroup("ID").subscribe(new Observer<Group>() {
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
