package fudan.se.lab2.controller;

import fudan.se.lab2.domain.Employee;
import fudan.se.lab2.domain.Lesson;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.*;
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
public class ManagerController {

    private AuthService authService;
    private EmployeeService employeeService;
    private Utility utility;
    private JwtTokenUtil jwtTokenUtil;
    private AdminService adminService;
    private ManagerService managerService;
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库

    public ManagerController(AuthService authService, Utility utility,EmployeeService employeeService,AdminService adminService,JwtTokenUtil jwtTokenUtil,ManagerService managerService) {
        this.authService = authService;
        this.utility = utility;
        this.jwtTokenUtil = jwtTokenUtil;
        this.employeeService = employeeService;
        this.adminService = adminService;
        this.managerService = managerService;
    }

    //主管查看自己部门所有员工的信息
    @PostMapping("/checkemployeeinfo")
    @ResponseBody
    public ResponseEntity<?> checkEmployeeInfo(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee employee = jwtTokenUtil.getEmployeeFromToken(token);
        if(utility.isManager(employee,employee.getDepartmentId()).equals("yes")){
            return ResponseEntity.status(HttpStatus.CREATED).body(utility.getEmployeeListfromDepartmentID(employee.getDepartmentId()));
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限");
        }
    }

    //查看某一特定员工的信息
    @PostMapping("/checkspecificemployeeinfo")
    @ResponseBody
    public ResponseEntity<?> checksSpecificEmployeeInfo(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee employee = jwtTokenUtil.getEmployeeFromToken(token);
        Long employeeId = Long.valueOf(request.get("employeeId"));
        if(utility.isManager(employee,employee.getDepartmentId()).equals("yes")){
            if (utility.findEmployeeById(employeeId).getDepartmentId().equals(employee.getDepartmentId())) {
                return ResponseEntity.status(HttpStatus.CREATED).body(utility.findEmployeeById(employeeId));
                }else {
                return ResponseEntity.status(HttpStatus.CREATED).body("你与该员工不处于一个部门");
            }
            } else{
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限");
        }
    }

    //查看某个员工的testhistory
    @PostMapping("/checkspecificemployeetesthistory")
    @ResponseBody
    public ResponseEntity<?> checksSpecificEmployeeTestHistory(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee employee = jwtTokenUtil.getEmployeeFromToken(token);
        Long employeeId = Long.valueOf(request.get("employeeId"));
        if(utility.isManager(employee,employee.getDepartmentId()).equals("yes")){
            if (utility.findEmployeeById(employeeId).getDepartmentId().equals(employee.getDepartmentId())) {
                return ResponseEntity.status(HttpStatus.CREATED).body(utility.getTestHistoryListFromTestHistorybyEmployeeId(employeeId));
            }else {
                return ResponseEntity.status(HttpStatus.CREATED).body("你与该员工不处于一个部门");
            }
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限");
        }
    }



    //主管查看自己部门必修与选修课
    @PostMapping("/checkdepartmentlesson")
    @ResponseBody
    public ResponseEntity<?> checkDepartmentLesson(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee employee = jwtTokenUtil.getEmployeeFromToken(token);
        if(utility.isManager(employee,employee.getDepartmentId()).equals("yes")){
            return ResponseEntity.status(HttpStatus.CREATED).body(utility.findSelectableLessonByDepartmentId(employee.getDepartmentId()));
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限");
        }
    }

    //主管修改自己部门员工的信息(密码/部门/名字/username/入职日期/id不可修改)
    @PostMapping("/updatedepartmentemployeeinfo")
    @ResponseBody
    public ResponseEntity<?> updateDepartmentEmployeeInfo(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee manager = jwtTokenUtil.getEmployeeFromToken(token);
        Long id = Long.valueOf(request.get("id"));
        //暂时不允许修改username
        //String username = request.get("username");
        String password = request.get("password");
        //不允许修改name
        String email = request.get("email");
        //这个方法里不允许修改type，转部门属于另一个方法
        int age = Integer.parseInt(request.get("age"));
        //不允许修改id
        //不允许自己修改自己的部门
        String location = request.get("location");
        String sex = request.get("sex");
        //不允许修改入职日期
        String telephoneNumber = request.get("telephoneNumber");
        if(utility.isManager(manager,manager.getDepartmentId()).equals("yes")){
            String result = employeeService.updateInfo(id,null,email,age,location,sex,telephoneNumber);
            String updateLog = utility.updateLog(manager.getUsername(),"update employee "+utility.findEmployeeById(id).getName()+" info", utility.getCurrentDate());
            return  ResponseEntity.status(HttpStatus.CREATED).body(result);
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限");
        }
    }

    //主管修改自己部门员工的密码
    @PostMapping("/updateemployeepassword")
    @ResponseBody
    public ResponseEntity<?> updateEmployeeInfo(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee manager = jwtTokenUtil.getEmployeeFromToken(token);
        Long id = Long.valueOf(request.get("id"));
        String password = request.get("password");
        if (utility.isManager(manager, manager.getDepartmentId()).equals("yes")) {
            String result = employeeService.updatePassword(id, password);
            String updateLog = utility.updateLog(manager.getUsername(), "update employee " + utility.findEmployeeById(id).getName() + " password", utility.getCurrentDate());
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限");
        }
    }


        //主管给自己部门的成员选课
        //1 只能选自己部门可选的
        //2 已经完成过的课程不能选
        //3 正在修读未完成的课程不能选
        @PostMapping("/managerchooseemployeelesson")
        @ResponseBody
        public ResponseEntity<?> managerChooseEmployeeLesson(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
            String token = headers.get("authorization");
            Employee manager = jwtTokenUtil.getEmployeeFromToken(token);
            Long employeeId = Long.valueOf(request.get("employeeId"));//员工id
            Long lessonId = Long.valueOf(request.get("lessonId"));
            String lessonName = request.get("lessonName");
            String tutorName = request.get("tutorName");
            if(utility.isManager(manager,manager.getDepartmentId()).equals("yes")){
                String result = managerService.managerChooseLesson(employeeId,lessonId,lessonName,manager.getDepartmentId(),tutorName);
                String updateLog = utility.updateLog(manager.getUsername(),"update employee "+utility.findEmployeeById(employeeId).getName()+" lesson", utility.getCurrentDate());
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            }else{
                return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限");
            }
        }


        //返回需要修读的课程
    @PostMapping("/changeemployeedepartment")
    @ResponseBody
    public ResponseEntity<?> changeEmployeeDepartment(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee manager = jwtTokenUtil.getEmployeeFromToken(token);
        Long employeeId = Long.valueOf(request.get("employeeId"));//员工id
        Long departmentId = Long.valueOf(request.get("departmentId"));
        String departmentName = request.get("departmentName");
        if(utility.isManager(manager,manager.getDepartmentId()).equals("yes")){
            if(utility.isSameDepartment(manager,utility.findEmployeeById(employeeId))){
                if(utility.passedAllRequiredTest(employeeId) && !utility.hasfailTest(employeeId,departmentId)){
                    String result = utility.changeDepartment(employeeId,departmentName,departmentId);
                    String updateLog = utility.updateLog(manager.getUsername(),"change employee "+utility.findEmployeeById(employeeId).getName()+" department", utility.getCurrentDate());
                    List<Lesson> resultSet = utility.passedAllRequiredTest(employeeId,departmentId);
                    return ResponseEntity.status(HttpStatus.CREATED).body(resultSet);
                }else{
                    return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限（该员工不符合要求）");
                }
            }else{
                return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限（不在同一部门）");
            }
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限(不是主管)");
        }
    }

    //查看某门课挂了n次的员工
    @PostMapping("/checkfailedtimes")
    @ResponseBody
    public ResponseEntity<?> checkFailedTimes(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee manager = jwtTokenUtil.getEmployeeFromToken(token);
        Long lessonId = Long.valueOf(request.get("lessonId"));
        int failedTimes = Integer.parseInt(request.get("failedTimes"));
        if(utility.isManager(manager,manager.getDepartmentId()).equals("yes")){
            return ResponseEntity.status(HttpStatus.CREATED).body(managerService.failedTest(lessonId,failedTimes,manager.getDepartmentId()));
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限");
        }
    }

    //返回考试通过的员工成绩
    @PostMapping("/checkpassemployee")
    @ResponseBody
    public ResponseEntity<?> checkPassEmployee(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee manager = jwtTokenUtil.getEmployeeFromToken(token);
        if(utility.isManager(manager,manager.getDepartmentId()).equals("yes")){
            return ResponseEntity.status(HttpStatus.CREATED).body(managerService.checkPassEmployee(manager.getDepartmentId(),"已通过"));
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限");
        }
    }

    //返回修读某一课程的员工成绩
    @PostMapping("/checklessontest")
    @ResponseBody
    public ResponseEntity<?> checkEmployeeTestFromLesson(@RequestBody Map<String,String> request,@RequestHeader Map<String, String> headers) throws JSONException {
        String token = headers.get("authorization");
        Employee manager = jwtTokenUtil.getEmployeeFromToken(token);
        Long lessonId = Long.valueOf(request.get("lessonId"));
        if(utility.isManager(manager,manager.getDepartmentId()).equals("yes")){
            return ResponseEntity.status(HttpStatus.CREATED).body(managerService.checkBylessonId(lessonId,manager.getDepartmentId()));
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body("你没有对应权限");
        }
    }







}
