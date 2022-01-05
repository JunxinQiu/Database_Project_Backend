package fudan.se.lab2;

import fudan.se.lab2.controller.EmployeeController;
import fudan.se.lab2.domain.Employee;
import fudan.se.lab2.domain.Lesson;
import fudan.se.lab2.domain.TestHistory;
import fudan.se.lab2.security.jwt.JwtConfigProperties;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Welcome to 2021 Software Engineering Lab2.
 * This is your first lab to write your own code and build a spring boot application.
 * Enjoy it :)
 *
 * @author LBW
 */
@SpringBootApplication
public class Lab2Application {

//    private EmployeeController employeeController;
//    private EmployeeService employeeService;
//    private Utility utility;
//    private JwtTokenUtil jwtTokenUtil;
//    private AdminService adminService;
//    private ManagerService managerService;

    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);

        ApplicationContext context = SpringUtil.getApplicationContext();
        JwtTokenUtil jwtTokenUtil;
        Employee currentUser = new Employee();
        String token;
        String isManager = "";
        Utility utility = context.getBean(Utility.class);
        EmployeeService employeeService = context.getBean(EmployeeService.class);
        ManagerService managerService = context.getBean(ManagerService.class);
        TutorService tutorService = context.getBean(TutorService.class);


        System.out.println("命令行版数据库展示，请输入你的身份（employee/admin）");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        //员工和管理员部分
        if(input.equals("employee")){
            boolean login = false;
            while(!login){
                System.out.println("请输入你的用户名");
                String username = scanner.nextLine();
                System.out.println("请输入你的密码");
                String password = scanner.nextLine();
                token = employeeService.login(username,password);
                if(token.equals("false")){
                    System.out.println("登陆失败");
                }else{
                    login = true;
                    Employee employee = utility.findEmployeeByUsername(username);
                    currentUser = utility.findEmployeeByUsername(username);
                    isManager = utility.isManager(employee,employee.getDepartmentId());
                    if(isManager.equals("yes")){
                        System.out.println("登陆成功，你的身份是部门主管");
                    }else{
                        System.out.println("登陆成功，你的身份是普通员工");
                    }
                }
            }
            //登录结束
            if(isManager.equals("yes")){
                System.out.println("你的身份是部门主管，你可以做的事有");
                System.out.println("checkemployeeinfo : 查看自己部门所有员工的信息(不包含自己的信息)");
                System.out.println("checkspecificemployeeinfo : 查看某一特定员工的信息");
                System.out.println("checkspecificemployeetesthistory : 查看某个员工的testhistory");
                System.out.println("checkdepartmentlesson : 查看自己部门必修与选修课");
                System.out.println("updatedepartmentemployeeinfo : 修改自己部门员工的信息(密码/部门/名字/username/入职日期/id不可修改)");
                System.out.println("updateemployeepassword : 修改自己部门员工的密码");
                System.out.println("managerchooseemployeelesson :  //主管给自己部门的成员选课\n" +
                        "        //1 只能选自己部门可选的\n" +
                        "        //2 已经完成过的课程不能选\n" +
                        "        //3 正在修读未完成的课程不能选");
                System.out.println("changeemployeedepartment : 更换员工部门，返回值是转部门后需要修读的课程");
                System.out.println("checkfailedtimes : 查看某门课挂了n次的员工");
                System.out.println("checkpassemployee : 返回考试通过的员工成绩");
                System.out.println("checklessontest : 返回修读某一课程的员工成绩");
                while (true){
                    System.out.println("请输入指令");
                    input = scanner.nextLine();
                    switch(input){
                        case "checkemployeeinfo" :
                            System.out.println(utility.getEmployeeListfromDepartmentID(currentUser.getDepartmentId()).toString());
                            break;
                        case "checkspecificemployeeinfo" :
                            System.out.println("请输入你要查找的员工的id");
                            Long employeeId = Long.valueOf(scanner.nextLine());
                            if (utility.findEmployeeById(employeeId).getDepartmentId().equals(currentUser.getDepartmentId())) {
                                System.out.println(utility.findEmployeeById(employeeId));
                            }else {
                                System.out.println("你与该员工不处于一个部门");
                            }
                            break;
                        case "checkspecificemployeetesthistory" :
                            System.out.println("请输入你要查找的员工的id");
                            Long employeeId2 = Long.valueOf(scanner.nextLine());
                            if (utility.findEmployeeById(employeeId2).getDepartmentId().equals(currentUser.getDepartmentId())) {
                                System.out.println(utility.getTestHistoryListFromTestHistorybyEmployeeId(employeeId2));
                            }else {
                                System.out.println("你与该员工不处于一个部门");
                            }
                            break;
                        case "checkdepartmentlesson" :
                            System.out.println(utility.findSelectableLessonByDepartmentId(currentUser.getDepartmentId()));
                            break;
                        case "updatedepartmentemployeeinfo" :
                            System.out.println("请输入你要更改的员工的id");
                            Long id = Long.valueOf(scanner.nextLine());
                            //暂时不允许修改username
                            //String username = request.get("username");
                            //不允许修改name
                            System.out.println("请输入你要更改的员工的email");
                            String email = scanner.nextLine();
                            //这个方法里不允许修改type，转部门属于另一个方法
                            System.out.println("请输入你要更改的员工的年龄");
                            int age = Integer.parseInt(scanner.nextLine());
                            //不允许修改id
                            //修改自己的部门不在此方法中
                            System.out.println("请输入你要更改的员工的地址");
                            String location = scanner.nextLine();
                            System.out.println("请输入你要更改的员工的性别");
                            String sex = scanner.nextLine();
                            //不允许修改入职日期
                            System.out.println("请输入你要更改的员工的电话号码");
                            String telephoneNumber =  scanner.nextLine();;
                            String result = employeeService.updateInfo(id,null,email,age,location,sex,telephoneNumber);
                            System.out.println(result);
                            String updateLog = utility.updateLog(currentUser.getUsername(),"update employee "+utility.findEmployeeById(id).getName()+" info", utility.getCurrentDate());
                            System.out.println(updateLog);
                            break;
                        case "updateemployeepassword" :
                            System.out.println("请输入你要更改的员工的id");
                            id = Long.valueOf(scanner.nextLine());
                            System.out.println("请输入你要更改的员工的密码");
                            String password = scanner.nextLine();
                            result = employeeService.updatePassword(id, password);
                            System.out.println(result);
                            updateLog = utility.updateLog(currentUser.getUsername(), "update employee " + utility.findEmployeeById(id).getName() + " password", utility.getCurrentDate());
                            System.out.println(updateLog);
                            break;
                        case "managerchooseemployeelesson" :
                            System.out.println("请输入你要选课的员工的id");
                            id = Long.valueOf(scanner.nextLine());
                            System.out.println("请输入你要选课的课程id");
                            Long lessonId = Long.valueOf(scanner.nextLine());
                            System.out.println("请输入你要选课的课程名字");
                            String lessonName = scanner.nextLine();
                            System.out.println("请输入你要选课的课程导师名字");
                            String tutorName = scanner.nextLine();
                            result = managerService.managerChooseLesson(id,lessonId,lessonName,currentUser.getDepartmentId(),tutorName);
                            System.out.println(result);
                            updateLog = utility.updateLog(currentUser.getUsername(),"update employee "+utility.findEmployeeById(id).getName()+" lesson", utility.getCurrentDate());
                            System.out.println(updateLog);
                            break;
                        case "changeemployeedepartment" :
                            System.out.println("请输入你要转部门的员工的id");
                            id = Long.valueOf(scanner.nextLine());
                            System.out.println("请输入转入部门的id");
                            Long departmentId = Long.valueOf(scanner.nextLine());
                            System.out.println("请输入转入部门的名字");
                            String departmentName = scanner.nextLine();
                            if(utility.isSameDepartment(currentUser,utility.findEmployeeById(id))){
                                if(utility.passedAllRequiredTest(id) && !utility.hasfailTest(id,departmentId)){
                                    result = utility.changeDepartment(id,departmentName,departmentId);
                                    updateLog = utility.updateLog(currentUser.getUsername(),"change employee "+utility.findEmployeeById(id).getName()+" department", utility.getCurrentDate());
                                    System.out.println(result);
                                    System.out.println(updateLog);
                                    List<Lesson> resultSet = utility.passedAllRequiredTest(id,departmentId);
                                    System.out.println(result);
                                }else{
                                    System.out.println("你没有对应权限（该员工不符合要求）");
                                }
                            }else{
                                System.out.println("你没有对应权限（不在同一部门）");
                            }
                            break;
                        case "checkfailedtimes" :
                            System.out.println("请输入你要查询的课程id");
                            lessonId = Long.valueOf(scanner.nextLine());
                            System.out.println("请输入挂科次数");
                            int failedTimes = Integer.parseInt(scanner.nextLine());
                            System.out.println(managerService.failedTest(lessonId,failedTimes,currentUser.getDepartmentId()));
                            break;
                        case "checkpassemployee" :
                            System.out.println(managerService.checkPassEmployee(currentUser.getDepartmentId(),"已通过"));
                            break;
                        case "checklessontest" :
                            System.out.println("请输入你要查询的课程id");
                            lessonId = Long.valueOf(scanner.nextLine());
                            System.out.println(managerService.checkBylessonId(lessonId,currentUser.getDepartmentId()));
                            break;
                        default:
                            System.out.println("非法指令");
                            break;
                    }
                }

            }else{
                System.out.println("你的身份可能是导师或者普通员工，你可以做的有");
                System.out.println("checkselfinfo : 查看自己的信息");
                System.out.println("updateselfinfo : 修改个人信息");
                System.out.println("checkselftest : 查看个人课程修读状态");
                System.out.println("checkselflessontutor : 员工查看个人课程对应的讲师的信息");
                System.out.println("checkselflesson ：员工查看个人课程");
                System.out.println("createlesson ： 创建课程");
                System.out.println("ratetest ：教员给员工打分");
                System.out.println("checkstudentinfo : 查看选自己课的学生的信息");
                while (true){
                    System.out.println("请输入指令");
                    input = scanner.nextLine();
                    switch(input){
                        case "checkselfinfo" :
                            System.out.println(jwtTokenUtil.getEmployeeFromToken(token));
                            break;
                        case "checkselftest" :
                            System.out.println(utility.getTestHistoryListFromTestHistorybyEmployeeId(currentUser.getId()));
                            break;
                        case "updateselfinfo" :
                            //暂时不允许修改username
                            //String username = request.get("username");
                            //不允许修改name
                            System.out.println("请输入你要更改的email");
                            String email = scanner.nextLine();
                            System.out.println("请输入你要更改的密码");
                            String password = scanner.nextLine();
                            //这个方法里不允许修改type，转部门属于另一个方法
                            System.out.println("请输入你要更改的年龄");
                            int age = Integer.parseInt(scanner.nextLine());
                            //不允许修改id
                            //修改自己的部门不在此方法中
                            System.out.println("请输入你要更改的地址");
                            String location = scanner.nextLine();
                            System.out.println("请输入你要更改的性别");
                            String sex = scanner.nextLine();
                            //不允许修改入职日期
                            System.out.println("请输入你要更改的电话号码");
                            String telephoneNumber =  scanner.nextLine();;
                            String result = employeeService.updateInfo(currentUser.getId(),password,email,age,location,sex,telephoneNumber);
                            System.out.println(result);
                            String updateLog = utility.updateLog(currentUser.getUsername(),"update employee "+utility.findEmployeeById(currentUser.getId()).getName()+" info", utility.getCurrentDate());
                            System.out.println(updateLog);
                            break;
                        case "checkselflessontutor":
                            System.out.println("请输入你要查询的老师所在的选修记录id");
                            Long id = Long.valueOf(scanner.nextLine());
                            System.out.println(utility.getTutorFromTestHistory(id));
                            break;
                        case "checkselflesson":
                            Employee employee = jwtTokenUtil.getEmployeeFromToken(token);
                            List<TestHistory> historyList = utility.getTestHistoryListFromTestHistorybyEmployeeId(employee.getId());
                            System.out.println(utility.getLessonListFromTestHistoryList(historyList));
                            break;
                        case "createlesson" :
                            System.out.println("请输入你要创建的课程的id");
                            Long lessonId =  Long.parseLong(scanner.nextLine());
                            System.out.println("请输入你要创建的课程的名字");
                            String name = scanner.nextLine();
                            System.out.println("请输入你要创建的课程的分类");
                            String genre = scanner.nextLine();
                            System.out.println("请输入你要创建的课程的课程描述");
                            String description = scanner.nextLine();
                            System.out.println("请输入你要创建的课程的必修部门，格式如： 1,2,3 ");
                            String requiredDepartment = scanner.nextLine();
                            System.out.println("请输入你要创建的课程的选秀部门，格式如： 1,2,3 ");
                            String availableDepartment = scanner.nextLine();
                            ArrayList<Long> requiredDepartmentList;
                            ArrayList<Long> availableDepartmentList;
                            requiredDepartmentList = utility.String2List(requiredDepartment);
                            availableDepartmentList = utility.String2List(availableDepartment);
                            employee = jwtTokenUtil.getEmployeeFromToken(token);
                            if(utility.isTutor(employee).equals("yes")){
                                tutorService.createNewLesson(lessonId,name,employee.getId(),genre,description);
                                tutorService.handoutLessontoDepartment(requiredDepartmentList,lessonId,"必修",name);
                                tutorService.handoutLessontoEmployee(requiredDepartmentList,lessonId,name,employee.getId());
                                tutorService.handoutLessontoDepartment(availableDepartmentList,lessonId,"选修",name);
                                System.out.println(utility.updateLog(employee.getUsername(),"create new lesson",utility.getCurrentDate()));
                                System.out.println("在输入参数正确的情况下，已完成新课程的创建");
                            }else{
                                System.out.println("你没有对应的权限");
                            }
                        case "ratetest":
                            employee = jwtTokenUtil.getEmployeeFromToken(token);
                            System.out.println("请输入你要评价的学生的id");
                            long employeeId =  Long.parseLong(scanner.nextLine());
                            System.out.println("请输入分数");
                            int score = Integer.parseInt(scanner.nextLine());
                            String status;
                            if(score >=60){
                                status = "已通过";
                            }else{
                                status = "不合格";
                            }
                            if(employee.getId() == utility.getTutorFromTestHistory(employeeId).getId()){
                                System.out.println(utility.updateLog(employee.getUsername(),"update test grade for id"+employeeId, utility.getCurrentDate()));
                                System.out.println(tutorService.rateTest(employeeId,score,status));
                            }else{
                                System.out.println("你没有对应的权限");
                            }
                            break;
                        case "checkstudentinfo":
                            System.out.println("请输入你要查询的课程的id");
                            lessonId =  Long.parseLong(scanner.nextLine());
                            return ResponseEntity.status(HttpStatus.CREATED).body(utility.getEmployeeListFromTestHistoryList(utility.getTestHistoryListFromTestHistorybyTutoridAndLessonId(lessonId,employee.getName())));
                        default:
                            System.out.println("非法指令");
                            break;
                    }
            }

            //管理员部分
        }else if(input.equals("admin")){

        }else{
            System.out.println("不合法输入");
        }

    }

    /**
     * This is a function to create some basic entities when the application starts.
     * Now we are using a In-Memory database, so you need it.
     * You can change it as you like.
     */
//    @Bean
//    public CommandLineRunner dataLoader(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                // Create authorities if not exist.
//                Authority librarianAuthority = getOrCreateAuthority("Librarian", authorityRepository);
//                Authority teacherAuthority = getOrCreateAuthority("Teacher", authorityRepository);
//                Authority studentAuthority = getOrCreateAuthority("Student", authorityRepository);
//
//                // Create an librarian if not exists.
////                if (userRepository.findByUsername("admin") == null) {
////                    User admin = new User(
////                            "admin",
////                            passwordEncoder.encode("password"),
////                            "libowen",
////                            new HashSet<>(Collections.singletonList(librarianAuthority))
////                    );
////                    userRepository.save(admin);
////                }
//            }

//            private Authority getOrCreateAuthority(String authorityText, AuthorityRepository authorityRepository) {
//                Authority authority = authorityRepository.findByName(authorityText);
//                if (authority == null) {
//                    authority = new Authority(authorityText);
//                    authorityRepository.save(authority);
//                }
//                return authority;
//            }
        //};
   // }
}

