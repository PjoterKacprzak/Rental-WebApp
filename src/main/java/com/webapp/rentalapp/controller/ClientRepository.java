package com.webapp.rentalapp.controller;

import com.webapp.rentalapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);

//    @Override
//    Optional<Client> findById(Long aLong);
//    @Query(value = "select id From clients t WHERE t.id IN :ids")
////         Client findByIdsIn(@Param("ids")Long ids);

    @Query("SELECT username FROM Client u WHERE u.id = ?1")
    String findByIds(Long id);


    @Transactional
    @Modifying
    @Query("Update Client set username = ?1 where id=?2")
    void saveUsername(String userName, Long id);

    @Transactional
    @Modifying
    @Query("Update Client set adress = ?1 where id=?2")
    void saveAdress(String adress, Long id);

    @Transactional
    @Modifying
    @Query("Update Client set telephone = ?1 where id=?2")
    void saveTelephone(String telephone, Long id);

    @Transactional
    @Modifying
    @Query("Update Client set email = ?1 where id=?2")
    void saveEmail(String email, Long id);






}
//
//    @Query("INSERT ?1 INTO Client u WHERE u.id = ?1")
//    String saveAdress(String adress);
//
//    @Query("INSERT ?1 INTO Client u WHERE u.id = ?1")
//    String saveTelephone(String telephone);





