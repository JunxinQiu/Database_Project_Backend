package fudan.se.lab2.service;

import fudan.se.lab2.domain.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
