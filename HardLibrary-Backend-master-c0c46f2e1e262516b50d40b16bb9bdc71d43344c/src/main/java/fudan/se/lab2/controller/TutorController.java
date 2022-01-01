package fudan.se.lab2.controller;

import fudan.se.lab2.domain.Employee;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.AuthService;
import fudan.se.lab2.service.EmployeeService;
import fudan.se.lab2.service.TutorService;
import fudan.se.lab2.service.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LBW
 */
@Controller
public class TutorController {

    private AuthService authService;
    private EmployeeService employeeService;
    private Utility utility;
    private JwtTokenUtil jwtTokenUtil;//可能有bug
    private TutorService tutorService;
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库

    public TutorController(AuthService authService, Utility utility, EmployeeService employeeService, JwtTokenUtil jwtTokenUtil,TutorService tutorService) {
        this.authService = authService;
        this.utility = utility;
        this.jwtTokenUtil = jwtTokenUtil;
        this.employeeService = employeeService;
        this.tutorService = tutorService;
    }

    //教员创建新课程,并完成自动选课
    @PostMapping("/createlesson")
    @ResponseBody
    public ResponseEntity<?> CreateLesson(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        long id =  Long.parseLong(request.get("id"));
        String name = request.get("name");
        String genre = request.get("genre");
        String description = request.get("description");
        String requiredDepartment = request.get("requiredDepartment");
        String availableDepartment = request.get("availableDepartment");
        ArrayList<Long> requiredDepartmentList;
        ArrayList<Long> availableDepartmentList;
        requiredDepartmentList = utility.String2List(requiredDepartment);
        availableDepartmentList = utility.String2List(availableDepartment);
        Employee employee = jwtTokenUtil.getEmployeeFromToken(token);
        if(utility.isTutor(employee).equals("yes")){
            tutorService.createNewLesson(id,name,employee.getId(),genre,description);
            tutorService.handoutLessontoDepartment(requiredDepartmentList,id,"必修");
            tutorService.handoutLessontoEmployee(requiredDepartmentList,id,name,employee.getId());
            tutorService.handoutLessontoDepartment(availableDepartmentList,id,"选修");
            utility.updateLog(employee.getUsername(),"create new lesson",utility.getCurrentDate());
            return ResponseEntity.status(HttpStatus.CREATED).body("在输入参数正确的情况下，已完成新课程的创建");
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应的权限");
        }
    }


}
