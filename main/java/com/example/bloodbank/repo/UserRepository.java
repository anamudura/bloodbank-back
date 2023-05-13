package com.example.bloodbank.repo;

import com.example.bloodbank.appuser.Locations;
import com.example.bloodbank.appuser.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {
    //returneaza userul la care ii este asociata programarea cu id-ul dat.
    @Query("select u from Users u inner join u.app app where app.id = ?1")
    Users findByApp_Id(Long id);



    Users findByEmail(String email);


    @Query("SELECT user from Users user left join user.roles role where role.id = :id")
    List<Users> findUserByRole(@Param("id") Long id);

    @Query("SELECT user from Users user left join user.roles role where role.name = :name")
    List<Users> findUsersByRole2(@Param("name") String nume);


    @Modifying
    @Query("delete from Users u where u.id = :id")
    void deleteUser(@Param("id") Long ig);

    @Modifying
    @Query("update Users u set u.email = :email, u.location = :location, u.nume = :nume where u.id = :id")
    void updateDoc(@Param("id") Long id, @Param("email") String email, @Param("location") String location,
                   @Param("nume") String nume);

    @Query("SELECT l FROM Locations l JOIN l.user u WHERE u.id = :userId")
    Locations findLocationByUserId(@Param("userId") Long userId);


    @Query("SELECT u from Users u where u.id = ?1")
    Users findById1(Long aLong);

    @Query("SELECT u from Users u left join u.app app where app.id = ?1")
    Users findByAppId(Long id);
}
