package fudan.se.lab2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestHistory {
    @Column(unique = true)
    @Id
    private int id;
    private String name;
    @Column(name="belong_to")
    private int belongTo;
    private int grade;
    private String lesson;
    @Column(name="is_passed")
    private String isPassed;
    @Column(name="is_finished")
    private String isFinished;

    public TestHistory(){}
    public TestHistory(int id,String name,int belongTo,int grade,String lesson,String isPassed,String isFinished){
        this.id = id;
        this.name = name;
        this.belongTo = belongTo;
        this.grade = grade;
        this.lesson = lesson;
        this.isFinished = isFinished;
        this.isPassed = isPassed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public String getIsFinished() {
        return isFinished;
    }

    public String getIsPassed() {
        return isPassed;
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

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    public void setIsPassed(String isPassed) {
        this.isPassed = isPassed;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }
}
