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
public class EmployeeService {
    private JwtTokenUtil jwtTokenUtil;
    private Utility utility;
    String verify;
    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库

    public EmployeeService(Utility utility){
    this.utility = utility;
    }

    public String updateInfo(long id,String password,String email,int age, String location,String sex,String telephoneNumber) {
        String sql = "UPDATE `employee` SET `password` = '"+password+"', `sex` = '"+sex+"', `telephone_number` = '"+telephoneNumber+"', `age` = '"+age+"', `location` = '"+location+"', `email` = '"+email+"' WHERE `employee`.`id` = "+id;
        jdbcTemplate.update(sql);
        return "修改成功";
    }




}
