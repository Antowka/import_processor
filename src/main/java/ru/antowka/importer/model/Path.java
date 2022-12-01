package ru.antowka.importer.model;

public class Path {

    private String path;
    private String type;
    private String lastModificationDate;
    private String bjDate;
    private String diffDateMs;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(String lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getBjDate() {
        return bjDate;
    }

    public void setBjDate(String bjDate) {
        this.bjDate = bjDate;
    }

    public String getDiffDateMs() {
        return diffDateMs;
    }

    public void setDiffDateMs(String diffDateMs) {
        this.diffDateMs = diffDateMs;
    }
}