package org.freeddyns.systempolska.Model;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<Users, Long> {


    @Query("SELECT u FROM Users u " +
            "ORDER BY " +
            "CASE " +
            "   WHEN :sortBy = 'name' THEN u.name " +
            "   WHEN :sortBy = 'surname' THEN u.surname " +
            "   WHEN :sortBy = 'login' THEN u.login " +
            "   ELSE ''END")
    Page<Users> findAndSortedBy(@Param("sortBy") String sortBy, Pageable pageable);

    void saveAll(Iterable<Users> entities);




    @Query("SELECT u FROM Users u " +
            "WHERE (:searchKeyword IS NULL OR " +
            "       (:searchBy = 'name' AND LOWER(u.name) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) OR " +
            "        :searchBy = 'surname' AND LOWER(u.surname) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) OR " +
            "        :searchBy = 'login' AND LOWER(u.login) LIKE LOWER(CONCAT('%', :searchKeyword, '%')))) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'name' THEN u.name " +
            "     WHEN :sortBy = 'surname' THEN u.surname " +
            "     WHEN :sortBy = 'login' THEN u.login " +
            "     ELSE '' END")


    Page<Users> findAllWithPaginationAndSortingAndSearch(
            @Param("searchKeyword") String searchKeyword,
            @Param(("searchBy")) String searchBy,
            @Param("sortBy") String sortBy,
            Pageable pageable


    );


}
