package com.dfl.topicality;

import com.dfl.topicality.injection.application.TestTopicalityApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestTopicalityApplication.class)
public class MainActivityTest {

    @Mock
    private MainViewPagerAdapter mainViewPagerAdapter;

    private ActivityController<MainActivity> mainActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mainActivity = Robolectric.buildActivity(MainActivity.class);

        doAnswer(invocation -> {
            mainActivity.get().mainViewPagerAdapter = mainViewPagerAdapter;
            return null;
        }).when(((TestTopicalityApplication) RuntimeEnvironment.application).getComponentsFactory().getMainActivityComponent(mainActivity.get())).inject(mainActivity.get());

        mainActivity.setup();
    }

    @Test
    public void test() {
        assertNotNull(mainActivity);
        assertNotNull(mainViewPagerAdapter);
    }

}
