package com.dfl.topicality;

import com.dfl.topicality.database.DatabaseInteractor;
import com.dfl.topicality.saved.SavedArticlesContract;
import com.dfl.topicality.saved.SavedArticlesPresenter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SavedArticlesPresenterTest {

    @Mock
    private SavedArticlesContract.View view;
    @Mock
    private DatabaseInteractor databaseInteractor;

    private SavedArticlesPresenter savedArticlesPresenter;

    @Before
    public void setup() {
        savedArticlesPresenter = new SavedArticlesPresenter(view, databaseInteractor);
    }

}
