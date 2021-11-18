package com.smart.sso.demo.utils;

/**
 * @Author xhx
 * @Date 2021/11/10 19:10
 */
public class UploadParam {
    private int chunkNumber;
    private int chunkSize;
    private int currentChunkSize;
    private int totalSize;
    private String identifier;
    private int totalChunk;

    public int getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(int chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public int getCurrentChunkSize() {
        return currentChunkSize;
    }

    public void setCurrentChunkSize(int currentChunkSize) {
        this.currentChunkSize = currentChunkSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getTotalChunk() {
        return totalChunk;
    }

    public void setTotalChunk(int totalChunk) {
        this.totalChunk = totalChunk;
    }
}
