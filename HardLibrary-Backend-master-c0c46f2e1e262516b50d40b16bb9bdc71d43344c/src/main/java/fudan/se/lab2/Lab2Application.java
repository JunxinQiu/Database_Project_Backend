package fudan.se.lab2;

//import fudan.se.lab2.repository.AuthorityRepository;
//import fudan.se.lab2.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Welcome to 2021 Software Engineering Lab2.
 * This is your first lab to write your own code and build a spring boot application.
 * Enjoy it :)
 *
 * @author LBW
 */
@SpringBootApplication
public class Lab2Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
        System.out.println("命令行版数据库展示，请输入你的身份（employee/admin）");
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

