package org.freeddyns.systempolska.Model;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.login) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Users> findByNameOrSurnameOrLogin(@Param("searchTerm") String searchTerm);
    @Query("SELECT u FROM Users u " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'name' THEN u.name END ASC, " +
            "CASE WHEN :sortBy = 'surname' THEN u.surname END ASC, " +
            "CASE WHEN :sortBy = 'login' THEN u.login END ASC")
    Page<Users> findAndSortedBy(@Param("sortBy") String sortBy, Pageable pageable);
   List<Users>saveAll(Iterable<Users> entities);

    @Query("SELECT u FROM Users u " +
            "WHERE lower(u.name) LIKE lower(concat('%', :searchKeyword, '%')) " +
            "OR lower(u.surname) LIKE lower(concat('%', :searchKeyword, '%')) " +
            "OR lower(u.login) LIKE lower(concat('%', :searchKeyword, '%')) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'name' THEN u.name END ASC, " +
            "CASE WHEN :sortBy = 'surname' THEN u.surname END ASC, " +
            "CASE WHEN :sortBy = 'login' THEN u.login END ASC NULLS LAST")
    Page<Users> findAllWithPaginationAndSortingAndSearch(
            @Param("searchKeyword") String searchKeyword,
            @Param("sortBy") String sortBy,
            Pageable pageable
    );



}
