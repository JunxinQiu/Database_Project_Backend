package fudan.se.lab2.controller;

import fudan.se.lab2.domain.Employee;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.AdminService;
import fudan.se.lab2.service.AuthService;
import fudan.se.lab2.service.EmployeeService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LBW
 */
@Controller
public class AdminController {

    private AuthService authService;
    private EmployeeService employeeService;
    private Utility utility;
    private JwtTokenUtil jwtTokenUtil;//可能有bug
    private AdminService adminService;
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库

    public AdminController(AuthService authService, Utility utility,EmployeeService employeeService,AdminService adminService,JwtTokenUtil jwtTokenUtil) {
        this.authService = authService;
        this.utility = utility;
        this.jwtTokenUtil = jwtTokenUtil;
        this.employeeService = employeeService;
        this.adminService = adminService;
    }



    //登录方法
    @PostMapping("/adminlogin")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String,String> request) throws JSONException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.adminLogin(request.get("username"),request.get("password")));
    }

    //管理员修改员工个人信息,未完成
    @PostMapping("/adminupdateemployeeinfo")
    @ResponseBody
    public ResponseEntity<?> updateSelfInfo(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee employee = jwtTokenUtil.getEmployeeFromToken(token);
        //暂时不允许修改username
        //String username = request.get("username");
        String password = request.get("password");
        //不允许修改name
        String email = request.get("email");
        //不允许修改type
        int age = Integer.parseInt(request.get("age"));
        //不允许修改id
        //不允许管理员修改自己的部门
        String location = request.get("location");
        String sex = request.get("sex");
        //不允许修改入职日期
        String telephoneNumber = request.get("telephoneNumber");
        String result = employeeService.updateInfo(employee.getId(),password,email,age,location,sex,telephoneNumber);
        String updateLog = utility.updateLog(employee.getUsername(),"admin update employee info", utility.getCurrentDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //管理员查看log
    @PostMapping("/adminchecklog")
    @ResponseBody
    public ResponseEntity<?> adminCheckLog(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        return ResponseEntity.status(HttpStatus.CREATED).body(utility.checkLog());
    }

}
