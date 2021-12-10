package com.schedule.proj.repository;

import com.schedule.proj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT u FROM USER u where u.user_role = ?1")
//    Collection<User> findByUserRole(UserRole teacher);
//}

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //@Query("Select t from User t where t.email = ?1")

    @Query("Select user from User user")
    List<User> findAll();

    Optional<User> findByEmail(String email);

    User findOneById(int id);

    User findOneByEmail(String email);
    User findUserByEmail(String email);

    Optional<User> findById(Long id);


}
