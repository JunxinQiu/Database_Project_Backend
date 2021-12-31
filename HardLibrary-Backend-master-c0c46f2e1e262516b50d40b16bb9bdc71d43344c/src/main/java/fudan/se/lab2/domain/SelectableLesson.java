package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity
public class SelectableLesson {
    @Column(unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="department_name")
    private String departmentName;
    @Column(name="lesson_id")
    private long lessonId;
    private String type;

    public SelectableLesson(){}
    public SelectableLesson(int id, String departmentName, long lessonId, String type){
        this.id = id;
        this.departmentName = departmentName;
        this.lessonId = lessonId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLessonId() {
        return lessonId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getType() {
        return type;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public void setType(String type) {
        this.type = type;
    }

}
