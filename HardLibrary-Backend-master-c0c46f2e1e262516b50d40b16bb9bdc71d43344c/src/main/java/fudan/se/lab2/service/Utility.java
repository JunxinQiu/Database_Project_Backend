package fudan.se.lab2.service;

import fudan.se.lab2.domain.Employee;
import fudan.se.lab2.domain.LogHistory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class Utility {
    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库

    public Employee findEmployeeByUsername(String username){
        String sql = "SELECT * FROM `employee` WHERE `username` = '"+username+"'";
        List<Employee> employeeList =jdbcTemplate.query(sql, new RowMapper<Employee>() {
            Employee employee;
            @Override
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                employee=new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setPassword(resultSet.getString("password"));
                employee.setUsername(resultSet.getString("username"));
                employee.setName(resultSet.getString("name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setDepartment(resultSet.getString("department"));
                employee.setEmail(resultSet.getString("email"));
                employee.setEmployDate(resultSet.getString("employ_date"));
                employee.setLocation(resultSet.getString("location"));
                employee.setSex(resultSet.getString("sex"));
                employee.setTelephoneNumber(resultSet.getString("telephone_number"));
                employee.setType(resultSet.getString("type"));
                return employee;
            }
        });
        if(employeeList.size()>0){
            return employeeList.get(0);
        }else{
            return null;
        }
    }

    public Employee findEmployeeById(long id){
        String sql = "SELECT * FROM `employee` WHERE `id` = '"+id+"'";
        List<Employee> employeeList =jdbcTemplate.query(sql, new RowMapper<Employee>() {
            Employee employee;
            @Override
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                employee=new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setPassword(resultSet.getString("password"));
                employee.setUsername(resultSet.getString("username"));
                employee.setName(resultSet.getString("name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setDepartment(resultSet.getString("department"));
                employee.setEmail(resultSet.getString("email"));
                employee.setEmployDate(resultSet.getString("employ_date"));
                employee.setLocation(resultSet.getString("location"));
                employee.setSex(resultSet.getString("sex"));
                employee.setTelephoneNumber(resultSet.getString("telephone_number"));
                employee.setType(resultSet.getString("type"));
                return employee;
            }
        });
        if(employeeList.size()>0){
            return employeeList.get(0);
        }else{
            return null;
        }
    }

    //查看log所需的方法
    public List<LogHistory> checkLog(){
        String sql = "SELECT * FROM `log_history`";
        List<LogHistory> logHistoryList = jdbcTemplate.query(sql, new RowMapper<LogHistory>() {
            LogHistory logHistory;
            @Override
            public LogHistory mapRow(ResultSet resultSet, int i) throws SQLException {
                logHistory = new LogHistory();
                logHistory.setLog(resultSet.getString("log"));
                logHistory.setUsername(resultSet.getString("username"));
                logHistory.setId(resultSet.getLong("id"));
                logHistory.setDate(resultSet.getString("date"));
                return logHistory;
            }
        });
        return logHistoryList;
    }

    //更新log_history表
    public String updateLog(String username,String log,String date){
        String sql = "INSERT INTO `log_history` (`username`, `log`, `date`) VALUES ('"+username+"', '"+log+"', '"+date+"')";
        jdbcTemplate.update(sql);
        return "log已经运行，在正确的输入下理论上会获得正确的结果";
    }

    public String getCurrentDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }


}
