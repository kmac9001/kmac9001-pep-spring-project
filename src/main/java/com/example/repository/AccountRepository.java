package com.example.repository;
import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    //See if the repo has an account with a particular name
    @Query("FROM Account WHERE username = :nameVar")
    Account accountByName(@Param("nameVar") String name);

    @Query("FROM Account WHERE username = :n AND password = :p")
    Account validAccount(@Param("n") String n, @Param("p") String p);

    //Default method wants long but id is int, so we must override
    @Query("FROM Account WHERE accountId = :x")
    Account findById(@Param("x") Integer x);

}
