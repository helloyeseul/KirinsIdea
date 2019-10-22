package com.kirinsidea.data.repository;

import androidx.annotation.NonNull;

import com.kirinsidea.data.source.local.room.dao.HighlightDao;
import com.kirinsidea.data.source.local.room.entity.HighlightEntity;
import com.kirinsidea.ui.highlight.Highlight;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class HighlightRepositoryImpl implements HighlightRepository {
    private static class LazyHolder {
        private static final HighlightRepository INSTANCE = new HighlightRepositoryImpl();
    }

    @NonNull
    public static HighlightRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private HighlightRepositoryImpl() {
    }

    private HighlightDao highlightLocalDataSource;

    @NonNull
    @Override
    public BaseRepository init(@NonNull Object... dataSources) {
        this.highlightLocalDataSource = (HighlightDao) dataSources[0];
        return this;
    }

    @NonNull
    @Override
    public Completable observeAddNewHighlight(@NonNull Highlight highlight) {
        return highlightLocalDataSource
                .insert(new HighlightEntity.Builder(highlight).build())
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Completable observeUpdateHighlight(@NonNull Highlight highlight) {
        return highlightLocalDataSource
                .update(new HighlightEntity.Builder(highlight).build())
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Single<List<Highlight>> observeHighlightListByBookmarkId(int bookmarkId) {
        return highlightLocalDataSource
                .selectAllByBookmarkId(bookmarkId)
                .map(list -> {
                    List<Highlight> resultList = new ArrayList<>();
                    for (HighlightEntity entity : list) {
                        resultList.add(new Highlight.Builder(entity).build());
                    }
                    return resultList;
                })
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Completable observeDeleteHighlight(@NonNull Highlight highlight) {
        return highlightLocalDataSource
                .delete(new HighlightEntity.Builder(highlight).build())
                .subscribeOn(Schedulers.io());
    }

}