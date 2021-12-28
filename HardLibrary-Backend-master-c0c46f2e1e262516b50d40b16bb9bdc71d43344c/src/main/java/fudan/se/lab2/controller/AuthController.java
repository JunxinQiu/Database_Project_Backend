package fudan.se.lab2.controller;

import fudan.se.lab2.domain.Employee;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
@RestController
public class AuthController {

    private AuthService authService;
    private JwtTokenUtil jwtTokenUtil;//可能有bug

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil) {
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库


//    @PostMapping("/register")
//    @ResponseBody
//    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
//        logger.debug("RegistrationForm: " + request.toString());
//        List<User> userList = jdbcTemplate.query("SELECT * FROM user",new RowMapper<User>() {
//                    User user = null;
//                    @Override
//                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//                        user = new User();
//                        user.setId(rs.getLong("id"));
//                        user.setUsername(rs.getString("username"));
//                        user.setEmail(rs.getString("email"));
//                        user.setAuthorities(rs.getString("type"));
//                        return user;
//                    }
//                });
//        boolean register=true;
//        for (User user : userList) {
//            if (user.getUsername().equals(request.get("username"))) {
//                register=false;
//                break;
//            }
//        }
//        if (register){
//            String sql="INSERT INTO user VALUES (?,?,?,?,?,?,?)";
//            jdbcTemplate.update(sql,null, request.get("type"),100,request.get("email"),"",request.get("password"),request.get("username"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("true");
//        }else {
//            return ResponseEntity.status(HttpStatus.CREATED).body("false");
//        }
//    }

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

//    @GetMapping("/book")
//    @ResponseBody
//    public ResponseEntity<?> display(DisplayRequest request) {
//        logger.debug("DisplayForm: "+request.toString());
//        List<Book> bookList = jdbcTemplate.query("SELECT * FROM book_definition",new RowMapper<Book>() {
//            Book book=null;
//            @Override
//            public Book mapRow(ResultSet rs,int rowNum) throws SQLException{
//                book=new Book();
//                book.setCover(rs.getString("cover"));
//                book.setDescription(rs.getString("description"));
//                book.setAuthor(rs.getString("author"));
//                book.setName(rs.getString("name"));
//                book.setCategory(rs.getString("category"));
//                book.setISBN(rs.getString("isbn"));
//                return book;
//            }
//        });
//        return ResponseEntity.ok(bookList);
//    }
//    @PostMapping("/upload")
//    @ResponseBody
//    public ResponseEntity<?> upload(@RequestBody Map<String, String> request){
//        logger.debug("UploadForm: " + request.toString());
//        List<Book> bookList = jdbcTemplate.query("SELECT * FROM book_definition",new RowMapper<Book>() {
//            Book book=null;
//            @Override
//            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
//                book = new Book();
//                book.setISBN(rs.getString("isbn"));
//                return book;
//            }
//        });
//        boolean upload=true;
//        for (Book book : bookList) {
//            if (book.getISBN().equals(request.get("isbn"))) {
//                upload=false;
//                break;
//            }
//        }
//        if (upload){
//            String sql="INSERT INTO book_definition VALUES (?,?,?,?,?,?,?)";
//            jdbcTemplate.update(sql,request.get("isbn"),request.get("category"),request.get("name"),request.get("author"),request.get("cover"),request.get("publish_date"),request.get("description"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("true");
//        }else {
//            return ResponseEntity.status(HttpStatus.CREATED).body("false");
//        }
//    }
    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "Welcome to 2021 Software Engineering Lab2. ";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

}
