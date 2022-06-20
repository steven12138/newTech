package newTech.demo.Module.Data.repository;


import newTech.demo.Module.Data.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByUsername(String username);

    Account findAccountBySid(String sid);

    void deleteAccountBySid(String sid);

}
