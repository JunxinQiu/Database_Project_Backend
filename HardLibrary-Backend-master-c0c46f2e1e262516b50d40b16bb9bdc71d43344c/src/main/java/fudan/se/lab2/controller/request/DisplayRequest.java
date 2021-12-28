package fudan.se.lab2.controller.request;

public class DisplayRequest {
    private String name;
    private String author;
    private String cover;
    private String description;
    public DisplayRequest(){}
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCover() {
        return cover;
    }

    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
