package fudan.se.lab2.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Employee implements Serializable {

    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    private long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String email;
    private String type;
    private String department;
    private String sex;
    private String location;
    @Column(name="employ_date")
    private String employDate;
    private int age;
    @Column(name="telephone_number")
    private String telephoneNumber;


    public Employee() {}


    public Employee(int id,int age,String username, String password, String name, String type,String email,String department,String sex,String employDate,String location,String telephoneNumber) {
        this.username = username;
        this.password= password;
        this.name = name;
        this.email=email;
        this.type=type;
        this.age=age;
        this.id =id;
        this.department = department;
        this.location = location;
        this.sex = sex;
        this.employDate = employDate;
        this.telephoneNumber = telephoneNumber;
    }


    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmployDate() {
        return employDate;
    }

    public void setEmployDate(String employDate) {
        this.employDate = employDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    //    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String fullname) {
        this.name = fullname;
    }

    public void setEmail(String email){this.email=email;}

    public String getEmail(String email){return email;}

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
