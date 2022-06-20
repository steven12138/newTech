package newTech.demo.Module.Data.repository;

import newTech.demo.Module.Data.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Integer> {
    Setting findFirstById(int id);
}
