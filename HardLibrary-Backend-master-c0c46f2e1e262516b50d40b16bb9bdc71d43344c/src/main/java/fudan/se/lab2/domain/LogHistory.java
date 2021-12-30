package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity
public class LogHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String log;
    private String date;
    public LogHistory(){}

    public LogHistory(String log,String username,String date){
        this.log=log;
        this.username=username;
        this.date=date;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }
}
