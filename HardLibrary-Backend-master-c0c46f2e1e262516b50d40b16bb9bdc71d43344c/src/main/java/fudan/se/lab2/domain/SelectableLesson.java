package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity
public class SelectableLesson {
    @Column(unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="department_id")
    private Long departmentId;
    @Column(name="lesson_id")
    private Long lessonId;
    private String type;
    @Column(name="lesson_name")
    private String lessonName;

    public SelectableLesson(){}
    public SelectableLesson(long id, Long departmentId, long lessonId, String type,String lessonName){
        this.id = id;
        this.departmentId = departmentId;
        this.lessonId = lessonId;
        this.type = type;
        this.lessonName = lessonName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getLessonId() {
        return lessonId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getType() {
        return type;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
}
