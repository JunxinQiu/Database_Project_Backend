package fudan.se.lab2.controller.request;

public class UploadRequest {
    private String ISBN;
    private String name;
    private String author;
    private String cover;
    private String publish_date;
    private String description;
    private String category;
    public UploadRequest(){}
    public UploadRequest(String ISBN,String name,String author,String cover,String publish_date,String description,String category){
        this.ISBN=ISBN;
        this.name=name;
        this.author=author;
        this.cover=cover;
        this.publish_date=publish_date;
        this.description=description;
        this.category=category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public String getCover() {
        return cover;
    }

    public String getDescription() {
        return description;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getName() {
        return name;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }
}
