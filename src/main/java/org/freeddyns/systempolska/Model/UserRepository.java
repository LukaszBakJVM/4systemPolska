package org.freeddyns.systempolska.Model;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<Users, Long> {


    @Query("SELECT u FROM Users u " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'name' THEN u.name " +
            "     WHEN :sortBy = 'surname' THEN u.surname " +
            "     WHEN :sortBy = 'login' THEN u.login " +
            "     ELSE '' END")
    Page<Users> findAndSortedBy(@Param("sortBy") String sortBy, Pageable pageable);
   List<Users>saveAll(Iterable<Users> entities);
    @Query("SELECT u FROM Users u " +
            "WHERE (:searchKeyword IS NULL OR u.name = :searchKeyword OR u.surname = :searchKeyword OR u.login = :searchKeyword) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'name' THEN u.name " +
            "     WHEN :sortBy = 'surname' THEN u.surname " +
            "     WHEN :sortBy = 'login' THEN u.login " +
            "     ELSE '' END")

    Page<Users> findAllWithPaginationAndSortingAndSearch(
            @Param("searchKeyword") String searchKeyword,
            @Param("sortBy") String sortBy,
            Pageable pageable


    );



}
