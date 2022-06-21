package newTech.demo.Module.Data.repository;


import newTech.demo.Module.Data.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByUsername(String username);

    Account findAccountBySid(String sid);

    @Query("select count(a) from Account a where a.is_tech = true or a.is_phy = true")
    int countAccountsByIs_techIsTrueOrIs_phyIsTrue();

    @Query("select count(a) from Account a where a.is_admin = false")
    int countAccountByIs_adminIsFalse();

    void deleteAccountBySid(String sid);

}
