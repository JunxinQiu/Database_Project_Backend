package fudan.se.lab2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestHistory {
    @Column(unique = true)
    @Id
    private int id;
    @Column(name="tutor_name")
    private String tutorName;
    @Column(name="belong_to")
    private int belongTo;
    private int grade;
    private String lesson;
    @Column(name="lesson_id")
    private Long lessonId;
    private String date;
    private String status;

    public TestHistory(){}
    public TestHistory(int id,String name,Long lessonId,int belongTo,int grade,String lesson,String isPassed,String isFinished,String date,String status){
        this.id = id;
        this.tutorName = name;
        this.belongTo = belongTo;
        this.grade = grade;
        this.lesson = lesson;
        this.status = status;
        this.date = date;
        this.lessonId = lessonId;
    }

    public void setName(String name) {
        this.tutorName = name;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getBelongTo() {
        return belongTo;
    }

    public int getGrade() {
        return grade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLesson() {
        return lesson;
    }

    public void setBelongTo(int belongTo) {
        this.belongTo = belongTo;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getLessonId() {
        return lessonId;
    }
}
