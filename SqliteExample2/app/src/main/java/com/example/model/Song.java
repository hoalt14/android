package com.example.model;

public class Song {
    private int id;
    private int songCode;
    private String songName;
    private String singer;
    private int favourite;

    public Song() {
    }

    public Song(int id, int songCode, String songName, String singer, int favourite) {
        this.id = id;
        this.songCode = songCode;
        this.songName = songName;
        this.singer = singer;
        this.favourite = favourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSongCode() {
        return songCode;
    }

    public void setSongCode(int songCode) {
        this.songCode = songCode;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
