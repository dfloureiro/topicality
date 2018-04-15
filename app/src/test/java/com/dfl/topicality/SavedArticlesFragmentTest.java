package com.dfl.topicality;

import android.view.View;

import com.dfl.topicality.database.DatabaseArticle;
import com.dfl.topicality.injection.application.TestTopicalityApplication;
import com.dfl.topicality.saved.SavedArticlesAdapter;
import com.dfl.topicality.saved.SavedArticlesFragment;
import com.dfl.topicality.saved.SavedArticlesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestTopicalityApplication.class)
public class SavedArticlesFragmentTest {

    @Mock
    private SavedArticlesPresenter savedArticlesPresenter;
    @Mock
    private SavedArticlesAdapter savedArticlesAdapter;


    private SavedArticlesFragment savedArticlesFragment;
    private View view;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        savedArticlesFragment = SavedArticlesFragment.newInstance();

        doAnswer(invocation -> {
            savedArticlesFragment.presenter = savedArticlesPresenter;
            savedArticlesFragment.savedArticlesAdapter = savedArticlesAdapter;
            return null;
        }).when(((TestTopicalityApplication) RuntimeEnvironment.application).getComponentsFactory().getSavedArticlesComponent(savedArticlesFragment)).inject(savedArticlesFragment);
        startFragment(savedArticlesFragment);
        view = savedArticlesFragment.getView();
    }

    @Test
    public void givenDatabaseArticleWhenFragmentAddsArticleThenAdapterAddsArticle() {
        //given
        DatabaseArticle databaseArticle = mock(DatabaseArticle.class);

        //when
        savedArticlesFragment.addArticle(databaseArticle);

        //then
        verify(savedArticlesAdapter).addDatabaseArticle(databaseArticle);
    }
}
