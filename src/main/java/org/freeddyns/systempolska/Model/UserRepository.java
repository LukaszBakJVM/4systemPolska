package org.freeddyns.systempolska.Model;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;



public interface UserRepository extends JpaRepository<Users, Long> {


    @Query("SELECT u FROM Users u ORDER BY " +
            "CASE WHEN :sortBy = 'login' THEN u.login END, " +
            "CASE WHEN :sortBy = 'name' THEN u.name END, " +
            "CASE WHEN :sortBy = 'surname' THEN u.surname END, " +
            "CASE WHEN :sortBy = 'id' THEN u.id END")
    Page<Users> findAndSortedBy(@Param("sortBy") String sortBy, Pageable pageable);











    @Query("SELECT u FROM Users u " +
            "WHERE " +
            "   (:searchBy = 'name' AND LOWER(u.name) LIKE LOWER(:searchKeyword)) OR " +
            "   (:searchBy = 'surname' AND LOWER(u.surname) LIKE LOWER(:searchKeyword)) OR " +
            "   (:searchBy = 'login' AND LOWER(u.login) LIKE LOWER(:searchKeyword)) OR " +
            "   (:searchBy = 'id' AND CAST(u.id AS string) LIKE :searchKeyword) " +
          "ORDER BY " +
          "CASE WHEN :sortBy = 'login' THEN u.login END, " +
          "CASE WHEN :sortBy = 'name' THEN u.name END, " +
          "CASE WHEN :sortBy = 'surname' THEN u.surname END, " +
          "CASE WHEN :sortBy= 'id' THEN u.id END")


 /*   @Query("SELECT u FROM Users u " +
            "WHERE " +
            "   CASE " +
            "       WHEN :searchBy = 'name' THEN LOWER(u.name) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) OR " +
            "       WHEN :searchBy = 'surname' THEN LOWER(u.surname) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) " +
            "       WHEN :searchBy = 'login' THEN LOWER(u.login) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) " +
            "       WHEN :searchBy = 'id' THEN CAST(u.id AS string) LIKE :searchKeyword " +
            "   END = TRUE " + // Użyj = TRUE, aby uzyskać wynik logiczny
            "ORDER BY " +
            "   CASE :sortBy " +
            "       WHEN 'login' THEN u.login " +
            "       WHEN 'name' THEN u.name " +
            "       WHEN 'surname' THEN u.surname " +
            "       WHEN 'id' THEN CAST(u.id AS string) " +
            "   END")*/
    Page<Users> findAllWithPaginationAndSortingAndSearch(
            @Param("searchKeyword") String searchKeyword,
            @Param("searchBy") String searchBy,
            @Param("sortBy") String sortBy,
            Pageable pageable


    );


}
