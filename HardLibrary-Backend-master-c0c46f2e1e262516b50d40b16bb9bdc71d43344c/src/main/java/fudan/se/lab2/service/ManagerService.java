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
public class ManagerService {
    private JwtTokenUtil jwtTokenUtil;
    private Utility utility;
    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库

    public ManagerService(Utility utility,JwtTokenUtil jwtTokenUtil){
        this.utility = utility;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String managerChooseLesson(Long employeeId,Long lessonId,String lessonName,Long departmentId,String tutorName){
        //获取本部门能够读取的课程
        List<SelectableLesson> lessonList = utility.findSelectableLessonByDepartmentId(departmentId);
        if(lessonList.size() <= 0){
            return "该部门没有可选的课程";
        }
        boolean selectable = false;
        for(int i=0;i<lessonList.size();i++){
            if(lessonList.get(i).getLessonId() == lessonId){
                selectable = true;
            }
        }
        //如果这门课可选
        if(selectable){
            //获取员工正在修读的课程
            List<TestHistory> testHistoryList = utility.getTestHistoryListFromTestHistorybyEmployeeId(employeeId);
            if(testHistoryList.size()==0){
                //员工没有修读任何课程的话，直接添加这门课
                String sql = "INSERT INTO `test_history` (`tutor_name`, `belong_to`, `lesson`, `lesson_id`, `grade`, `date`, `status`) VALUES ('"+tutorName+"', '"+employeeId+"', '"+lessonName+"', '"+lessonId+"', NULL, '"+utility.getCurrentDate()+"', '修读中')";
                jdbcTemplate.update(sql);
                return "在输入参数正确的前提下，已经完成了给员工选课的操作";
            }else{
                boolean addable = true;
                //若有修读课程，则1.检查要添加的这门课是否已经修读完成 2.是否正在修读中
                for(int i=0;i<testHistoryList.size();i++){
                    if((testHistoryList.get(i).getLessonId().equals(lessonId)) && (testHistoryList.get(i).getStatus().equals("修读中") || testHistoryList.get(i).getStatus().equals("已通过")) ){
                        addable = false;
                    }
                    if(addable){
                        String sql = "INSERT INTO `test_history` (`tutor_name`, `belong_to`, `lesson`, `lesson_id`, `grade`, `date`, `status`) VALUES ('"+tutorName+"', '"+employeeId+"', '"+lessonName+"', '"+lessonId+"', NULL, '"+utility.getCurrentDate()+"', '修读中')";
                        jdbcTemplate.update(sql);
                        return "在输入参数正确的前提下，已经完成了给员工选课的操作";
                    }
                }
            }
        }else{
            //如果这门课不可选
            return "这门课对该部门不开放";
        }
        return "应该不会走到这里";
    }

//    public String updateEmployeeInfo(){
//
//    }

}
