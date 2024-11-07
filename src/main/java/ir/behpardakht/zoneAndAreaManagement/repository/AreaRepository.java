package ir.behpardakht.zoneAndAreaManagement.repository;

import ir.behpardakht.zoneAndAreaManagement.model.AreaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<AreaModel, Long> {

    boolean existsByCodeOrName(String code, String name);  // Check if an Area exists by code or name
    AreaModel findAreaModelByCode(String code);
    AreaModel findByCode(String code);
}
