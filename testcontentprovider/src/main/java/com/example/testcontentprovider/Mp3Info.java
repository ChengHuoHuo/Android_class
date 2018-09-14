package com.example.testcontentprovider;

/**
 * Created by 44223 on 2018/5/10.
 */

class Mp3Info {
    public long id;//音乐id
    public String title;//音乐标题
    public String artist;//艺术家
    public long duration; //时长
    public long size ;//文件大小
    public String url;//文件路径
    public int  isMusic;//是否为音乐
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }


    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getIsMusic() {
        return isMusic;
    }
    public void setIsMusic(int isMusic) {
        this.isMusic = isMusic;
    }

}
