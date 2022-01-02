package fudan.se.lab2.service;

import com.alibaba.fastjson.JSONObject;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TutorService {
    private JwtTokenUtil jwtTokenUtil;
    private Utility utility;
    String verify;
    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库

    public TutorService(Utility utility,JwtTokenUtil jwtTokenUtil){
        this.utility = utility;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String createNewLesson(long id,String name,long teacher_id,String genre,String description){
        String sql2 = "INSERT INTO lesson VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql2,id, name, teacher_id, genre,description);
        //原本的sql语句String sql3 = "INSERT INTO `lesson` (`id`, `name`, `teacher_id`, `genre`, `description`) VALUES ('35142', 'test', '10231106124', 'test', 'test')";
        return "已成功上传";
    }

    public String handoutLessontoDepartment(ArrayList<Long> department_list, Long lessonId, String type,String lessonName){
        for(Long department_id:department_list){
            String sql = "INSERT INTO `selectable_lesson` (`lesson_id`, `department_id`, `type`,`lesson_name`) VALUES ('"+lessonId+"', '"+department_id+"', '"+type+"','"+lessonName+"')";
            jdbcTemplate.update(sql);
        }
        return "已自动为部门分配"+type+"课程";
    }

    public String handoutLessontoEmployee(ArrayList<Long> department_list, Long lessonId,String lessonName, Long tutor_id){
        Employee tutor = utility.findEmployeeById(tutor_id);
        for(Long department_id:department_list){
            //需要选课的人
            List<Employee> employeeList = utility.getEmployeeListfromDepartmentIDandTutorId(department_id,tutor_id);
            if(employeeList != null){
                for (Employee employee:employeeList){
                    if(employeeList.size() > 0){
                        String sql = "INSERT INTO `test_history` (`tutor_name`, `belong_to`, `lesson`, `lesson_id`, `grade`, `date`, `status`) VALUES ('"+tutor.getName()+"', '"+employee.getId()+"', '"+lessonName+"', '"+lessonId+"', NULL, '"+utility.getCurrentDate()+"', '修读中')";
                        jdbcTemplate.update(sql);
                    }
                }
            }else{

            }

        }
        return "已自动为部门员工分配必修课程";
    }

    public String rateTest(Long id,int score,String status){
        String sql = "UPDATE `test_history` SET `grade` = '"+score+"', `status` = '"+status+"' WHERE `test_history`.`id` = "+id;
        jdbcTemplate.update(sql);
        return "已完成成绩的登入";
    }





}
