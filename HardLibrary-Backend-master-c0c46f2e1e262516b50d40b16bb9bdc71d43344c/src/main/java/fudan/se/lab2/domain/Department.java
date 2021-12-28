package fudan.se.lab2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author LBW
 */
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String manager;
    @Column(name="assigned_course")
    private String assignedCourse;

    public Department(){}
    public Department(String name,String manager,String assignedCourse){
        this.name = name;
        this.manager = manager;
        this.assignedCourse = assignedCourse;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getAssignedCourse() {
        return assignedCourse;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignedCourse(String assignedCourse) {
        this.assignedCourse = assignedCourse;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}

