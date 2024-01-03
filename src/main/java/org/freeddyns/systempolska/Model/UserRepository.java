package org.freeddyns.systempolska.Model;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.login) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<User> findByNameOrSurnameOrLogin(@Param("searchTerm") String searchTerm);

    Page<User>findAllBy(Pageable pageable);
   List<User>saveAll(Iterable<User> entities);

    @Query("SELECT u FROM User u " +
            "WHERE lower(u.name) LIKE lower(concat('%', :searchKeyword, '%')) " +
            "OR lower(u.surname) LIKE lower(concat('%', :searchKeyword, '%')) " +
            "OR lower(u.login) LIKE lower(concat('%', :searchKeyword, '%')) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'name' THEN u.name END ASC, " +
            "CASE WHEN :sortBy = 'name_desc' THEN u.name END DESC, " +
            "CASE WHEN :sortBy = 'surname' THEN u.surname END ASC, " +
            "CASE WHEN :sortBy = 'surname_desc' THEN u.surname END DESC, " +
            "CASE WHEN :sortBy = 'login' THEN u.login END ASC, " +
            "CASE WHEN :sortBy = 'login_desc' THEN u.login END DESC")
    Page<User> findAllWithPaginationAndSortingAndSearch(
            @Param("searchKeyword") String searchKeyword,
            @Param("sortBy") String sortBy,
            Pageable pageable
    );



}
