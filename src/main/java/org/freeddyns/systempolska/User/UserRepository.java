package org.freeddyns.systempolska.User;


import org.springframework.data.domain.Page;


import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends PagingAndSortingRepository<Users, Long> {



    Page<Users> findAndSortedBy( Pageable pageable);

    @Query("SELECT COUNT(u) FROM Users u")
    Long countAllUsers();

    @Query("SELECT u FROM Users u " +
            "WHERE " +
            "(:searchBy = 'name' AND LOWER(u.name) LIKE LOWER(:searchKeyword)) OR " +
            "(:searchBy = 'surname' AND LOWER(u.surname) LIKE LOWER(:searchKeyword)) OR " +
            "(:searchBy = 'login' AND LOWER(u.login) LIKE LOWER(:searchKeyword)) OR " +
            "(:searchBy = 'id' AND CAST(u.id AS string) LIKE :searchKeyword) " )

    Page<Users> findAllWithPaginationAndSortingAndSearch(
            @Param("searchKeyword") String searchKeyword,
            @Param("searchBy") String searchBy,
            Pageable pageable);

    @Query("SELECT COUNT(u) FROM Users u WHERE " +
            "   (:searchBy = 'name' AND LOWER(u.name) LIKE LOWER(:searchKeyword)) OR " +
            "   (:searchBy = 'surname' AND LOWER(u.surname) LIKE LOWER(:searchKeyword)) OR " +
            "   (:searchBy = 'login' AND LOWER(u.login) LIKE LOWER(:searchKeyword)) OR " +
            "   (:searchBy = 'id' AND CAST(u.id AS string) LIKE :searchKeyword) ")
    long countByColumnAndSearchKeyword(@Param("searchKeyword") String searchKeyword, @Param("searchBy") String searchBy);

void saveAll(Iterable<Users>saveAll);
}
