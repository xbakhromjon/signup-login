package uz.bakhromjon.collection.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:43
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "select * from users where not is_deleted and phone = :phone")
    Optional<User> findByPhoneAndNotIsDeleted(String phone);

    @Query(nativeQuery = true, value = "select :phone in (select phone from users where not is_deleted)")
    Boolean existsByPhoneAndNotIsDeleted(String phone);
}