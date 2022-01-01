package fudan.se.lab2.service;

import fudan.se.lab2.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

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
                employee.setDepartmentId(resultSet.getLong("department_id"));
                employee.setDepartmentName(resultSet.getString("department_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setEmployDate(resultSet.getString("employ_date"));
                employee.setLocation(resultSet.getString("location"));
                employee.setSex(resultSet.getString("sex"));
                employee.setTelephoneNumber(resultSet.getString("telephone_number"));
                return employee;
            }
        });
        if(employeeList.size()>0){
            return employeeList.get(0);
        }else{
            return null;
        }
    }

    public Employee findEmployeeByName(String name){
        String sql = "SELECT * FROM `employee` WHERE `name` = '"+name+"'";
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
                employee.setDepartmentId(resultSet.getLong("department_id"));
                employee.setDepartmentName(resultSet.getString("department_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setEmployDate(resultSet.getString("employ_date"));
                employee.setLocation(resultSet.getString("location"));
                employee.setSex(resultSet.getString("sex"));
                employee.setTelephoneNumber(resultSet.getString("telephone_number"));
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
                employee.setDepartmentId(resultSet.getLong("department_id"));
                employee.setDepartmentName(resultSet.getString("department_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setEmployDate(resultSet.getString("employ_date"));
                employee.setLocation(resultSet.getString("location"));
                employee.setSex(resultSet.getString("sex"));
                employee.setTelephoneNumber(resultSet.getString("telephone_number"));
                return employee;
            }
        });
        if(employeeList.size()>0){
            return employeeList.get(0);
        }else{
            return null;
        }
    }

    public Admin findAdminByUsername(String username){
        String sql = "SELECT * FROM `admin` WHERE `username` = '"+username+"'";
        List<Admin> adminList =jdbcTemplate.query(sql, new RowMapper<Admin>() {
            Admin admin;
            @Override
            public Admin mapRow(ResultSet resultSet, int i) throws SQLException {
                admin =new Admin();
                admin.setPassword(resultSet.getString("password"));
                admin.setUsername(resultSet.getString("username"));
                admin.setId(resultSet.getLong("id"));
                return admin;
            }
        });
        if(adminList.size()>0){
            return adminList.get(0);
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public String updateEmployeeInfo(long id,String password,String email,int age, String location,String sex,String telephoneNumber) {
        String sql = "UPDATE `employee` SET `password` = '"+password+"', `sex` = '"+sex+"', `telephone_number` = '"+telephoneNumber+"', `age` = '"+age+"', `location` = '"+location+"', `email` = '"+email+"' WHERE `employee`.`id` = "+id;
        jdbcTemplate.update(sql);
        return "修改成功";
    }

    public String isManager(Employee employee, Long departmentId){
        long employee_id = employee.getId();
        String sql= "SELECT * FROM `department` WHERE `id` = "+departmentId+" AND `manager_id` = "+employee_id;
        List<Department> departmentList = jdbcTemplate.query(sql, new RowMapper<Department>() {
            Department department;
            @Override
            public Department mapRow(ResultSet resultSet, int i) throws SQLException {
                department = new Department();
                department.setId(resultSet.getLong("id"));
                department.setName(resultSet.getString("name"));
                department.setManagerId(resultSet.getLong("manager_id"));
                return department;
            }
        });
        if(departmentList.size()==0){
            return "no";
        }else{
            return "yes";
        }
    }

    public String isTutor(Employee employee){
        long employee_id = employee.getId();
        String sql= "SELECT * FROM `tutor` WHERE `id` = "+employee_id;
        List<Tutor> tutorList = jdbcTemplate.query(sql, new RowMapper<Tutor>() {
            Tutor tutor;
            @Override
            public Tutor mapRow(ResultSet resultSet, int i) throws SQLException {
                tutor = new Tutor();
                tutor.setDate(resultSet.getString("tutor_date"));
                tutor.setId(resultSet.getLong("id"));
                tutor.setName(resultSet.getString("name"));
                return tutor;
            }
        });
        if(tutorList.size() ==1){
            return "yes";
        }else{
            return  "no";
        }
    }

    //在设置一门课程的必修课程和选修课程时，需要这个方法 输入的格式为 1,2,3
    public ArrayList<Long> String2List(String s){
        String strArray[]=s.split(",");
        ArrayList<Long> result = new ArrayList();
        for(int i = 0; i<strArray.length;i++){
            result.add(Long.valueOf(strArray[i]));
        }
        return result;
    }

    public List<Employee> getEmployeeListfromDepartmentIDandTutorId(Long departmentId,Long tutorId){
        String sql = "SELECT * FROM `employee` WHERE `department_id` = " + departmentId;
        List<Employee> employeeList = jdbcTemplate.query(sql, new RowMapper<Employee>() {
            Employee employee;
            @Override
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                employee=new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setPassword(resultSet.getString("password"));
                employee.setUsername(resultSet.getString("username"));
                employee.setName(resultSet.getString("name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setDepartmentId(resultSet.getLong("department_id"));
                employee.setDepartmentName(resultSet.getString("department_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setEmployDate(resultSet.getString("employ_date"));
                employee.setLocation(resultSet.getString("location"));
                employee.setSex(resultSet.getString("sex"));
                employee.setTelephoneNumber(resultSet.getString("telephone_number"));
                //不给必修部门的经理和教员本身分配该项课程
                if( isManager(employee,departmentId).equals("yes") || (isTutor(employee).equals("yes") && employee.getId() == tutorId) ){
                    return null;
                }else{
                    return employee;
                }
            }
        });
        //往list里面加null，size居然会增加，我以为无事发生呢
        employeeList.removeAll(Collections.singleton(null));
        employeeList.removeIf(Objects::isNull);
        if(employeeList.size()>0){
            return employeeList;
        }else{
            return null;
        }
    }

    public Employee getTutorFromTestHistory(Long id){
        String sql = "SELECT * FROM `test_history` WHERE `id` = "+id;
        List<TestHistory> testHistoryList = jdbcTemplate.query(sql, new RowMapper<TestHistory>() {
            TestHistory testHistory;
            @Override
            public TestHistory mapRow(ResultSet resultSet, int i) throws SQLException {
                testHistory = new TestHistory();
                testHistory.setBelongTo(resultSet.getLong("belong_to"));
                testHistory.setDate(resultSet.getString("date"));
                testHistory.setGrade(resultSet.getInt("grade"));
                testHistory.setId(resultSet.getLong("id"));
                testHistory.setName(resultSet.getString("tutor_name"));
                return testHistory;
            }
        });
        Employee employee = findEmployeeByName(testHistoryList.get(0).getTutorName());
        return employee;

    }






}
