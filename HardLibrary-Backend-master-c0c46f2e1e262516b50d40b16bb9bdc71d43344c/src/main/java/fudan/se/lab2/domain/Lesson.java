package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity
public class Lesson {
    @Column(unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(name="teacher_id")
    private long teacherId;
    private String description;
    private String genre;
    @Column(name="available_department")
    private String availableDepartment;
    @Column(name="required_department")
    private String requiredDepartment;

    public Lesson(){}
    public Lesson(int id,String name,long teacherId,String description,String genre,String availableDepartment,String requiredDepartment){
        this.availableDepartment=availableDepartment;
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.description = description;
        this.genre = genre;
        this.requiredDepartment = requiredDepartment;
    }


    public String getDescription() {
        return description;
    }


    public String getName() {
        return name;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long id){
        this.teacherId = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAvailableDepartment() {
        return availableDepartment;
    }

    public void setAvailableDepartment(String availableDepartment) {
        this.availableDepartment = availableDepartment;
    }

    public String getRequiredDepartment() {
        return requiredDepartment;
    }

    public void setRequiredDepartment(String requiredDepartment) {
        this.requiredDepartment = requiredDepartment;
    }
}
