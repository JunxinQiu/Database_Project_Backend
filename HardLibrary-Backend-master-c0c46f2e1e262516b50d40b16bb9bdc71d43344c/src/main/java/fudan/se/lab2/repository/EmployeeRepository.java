package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {
    Employee findByName(String name);
}
