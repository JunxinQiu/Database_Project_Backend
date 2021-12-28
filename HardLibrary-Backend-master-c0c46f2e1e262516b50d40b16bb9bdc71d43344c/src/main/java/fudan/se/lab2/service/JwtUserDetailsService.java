package fudan.se.lab2.service;

import fudan.se.lab2.domain.Employee;
import fudan.se.lab2.repository.EmployeeRepository;
//import fudan.se.lab2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LBW
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private EmployeeRepository employeeRepository;
    @Autowired
    public JwtUserDetailsService(EmployeeRepository er) {
        this.employeeRepository = er;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("JwtUserDetailsService:" + username);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(username, "", authorityList);
    }
}
