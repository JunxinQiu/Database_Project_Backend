package fudan.se.lab2.controller;

import fudan.se.lab2.domain.Employee;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.AuthService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LBW
 */
@Controller
public class EmployeeController {

    private AuthService authService;
    private Utility utility;
    private JwtTokenUtil jwtTokenUtil;//可能有bug
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库

    public EmployeeController(AuthService authService, Utility utility, JwtTokenUtil jwtTokenUtil) {
        this.authService = authService;
        this.utility = utility;
        this.jwtTokenUtil = jwtTokenUtil;
    }



    //登录方法
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String,String> request) throws JSONException {
        logger.debug("LoginForm: " + request.toString());
        List<Employee> userList = jdbcTemplate.query("SELECT * FROM employee ",new RowMapper<Employee>() {
            Employee employee = null;
            @Override
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setPassword(rs.getString("password"));
                employee.setUsername(rs.getString("username"));
                return employee;
            }
        });
        boolean login=false;
        String token = "";
        for (Employee employee : userList) {
            if (employee.getUsername().equals(request.get("username"))&&employee.getPassword().equals(request.get("password"))) {
                login=true;
                token = jwtTokenUtil.generateToken(employee);
                break;
            }
        }
        if (login){
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body("false");
        }
    }

    //员工查看自己的信息
    @PostMapping("/checkselfinfo")
    @ResponseBody
    public ResponseEntity<?> checkSelfInfo(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        System.out.println("打印"+token);
        Employee employee = jwtTokenUtil.getEmployeeFromToken(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    //员工修改个人信息
    @PostMapping("/updateemployeeinfo")
    @ResponseBody
    public ResponseEntity<?> updateEmployeeInfo(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee employee = jwtTokenUtil.getEmployeeFromToken(token);
        return null;
    }
    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(){
        Map<String, String> response = new HashMap<>();
        String message = "Welcome to 2021 Software Engineering Lab2. ";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

}
