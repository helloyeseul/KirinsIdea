package com.kirinsidea.data.source.local.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.kirinsidea.data.source.remote.kirin.response.NewBookmarkResponse;

import java.util.Objects;

/**
 * 북마크
 *
 * @member id               북마크 ID (PK)
 * @member originalWebUrl   웹 페이지 URL
 * @member simpleWebUrl     웹 페이지 URL 중 플랫폼 이름까지
 * @member mainImageUrl     메인 이미지 URL
 * @member title            포스팅 제목
 * @member author           포스팅 저자
 * @member writeTime        포스팅 시간
 * @member path             로컬 저장 경로
 * @member storageTime      서버 저장 시간
 * @member folderId         저장 폴더 ID
 */
@Entity(tableName = "bookmark")
public class BookmarkEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String originalWebUrl;
    private String simpleWebUrl;
    private String mainImageUrl;
    private String title;
    private String author;
    private String writeTime;
    private String path;
    private String storageTime;
    private int folderId;

    public BookmarkEntity() {
    }

    private BookmarkEntity(Builder builder) {
        this.id = builder.id;
        this.originalWebUrl = builder.originalWebUrl;
        this.simpleWebUrl = builder.simpleWebUrl;
        this.mainImageUrl = builder.mainImageUrl;
        this.title = builder.title;
        this.author = builder.author;
        this.writeTime = builder.writeTime;
        this.path = builder.path;
        this.storageTime = builder.storageTime;
        this.folderId = builder.folderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalWebUrl() {
        return originalWebUrl;
    }

    public void setOriginalWebUrl(String originalWebUrl) {
        this.originalWebUrl = originalWebUrl;
    }

    public String getSimpleWebUrl() {
        return simpleWebUrl;
    }

    public void setSimpleWebUrl(String simpleWebUrl) {
        this.simpleWebUrl = simpleWebUrl;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(String storageTime) {
        this.storageTime = storageTime;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookmarkEntity that = (BookmarkEntity) o;
        return id == that.id &&
                folderId == that.folderId &&
                Objects.equals(originalWebUrl, that.originalWebUrl) &&
                Objects.equals(simpleWebUrl, that.simpleWebUrl) &&
                Objects.equals(mainImageUrl, that.mainImageUrl) &&
                Objects.equals(title, that.title) &&
                Objects.equals(author, that.author) &&
                Objects.equals(writeTime, that.writeTime) &&
                Objects.equals(path, that.path) &&
                Objects.equals(storageTime, that.storageTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalWebUrl, simpleWebUrl, mainImageUrl, title, author, writeTime, path, storageTime, folderId);
    }

    public static class Builder {
        private final int id = 0;
        private final String originalWebUrl;
        private final String simpleWebUrl;
        private final String mainImageUrl;
        private final String title;
        private final String author;
        private final String writeTime;
        private final String storageTime;
        private final int folderId;
        private String path = "";

        public Builder(@NonNull final NewBookmarkResponse response) {
            this.originalWebUrl = response.getOriginalweburl();
            this.simpleWebUrl = response.getHosturl();
            this.mainImageUrl = response.getMainimage();
            this.title = response.getTitle();
            this.author = response.getAuthor();
            this.writeTime = response.getWritetime();
            this.storageTime = response.getStoretime();
            this.folderId = response.getFolderId();
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public BookmarkEntity build() {
            return new BookmarkEntity(this);
        }
    }
}