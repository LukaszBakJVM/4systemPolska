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

    @Query("SELECT COUNT(u) FROM Users u")
    Long countAllUsers();


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
    Page<Users> findAllWithPaginationAndSortingAndSearch(
            @Param("searchKeyword") String searchKeyword,
            @Param("searchBy") String searchBy,
            @Param("sortBy") String sortBy,
            Pageable pageable


    );


    @Query("SELECT COUNT(u) FROM Users u WHERE " +
            "   (:searchBy = 'name' AND LOWER(u.name) LIKE LOWER(:searchKeyword)) OR " +
            "   (:searchBy = 'surname' AND LOWER(u.surname) LIKE LOWER(:searchKeyword)) OR " +
            "   (:searchBy = 'login' AND LOWER(u.login) LIKE LOWER(:searchKeyword)) OR " +
            "   (:searchBy = 'id' AND CAST(u.id AS string) LIKE :searchKeyword) ")
    long countByColumnAndSearchKeyword(@Param("searchKeyword") String searchKeyword, @Param("searchBy") String searchBy);


}
