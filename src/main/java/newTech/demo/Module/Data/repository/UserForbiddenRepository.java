package newTech.demo.Module.Data.repository;

import newTech.demo.Module.Data.UserForbidden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserForbiddenRepository extends JpaRepository<UserForbidden, Integer> {
}
