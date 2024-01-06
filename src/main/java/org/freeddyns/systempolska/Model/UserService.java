package org.freeddyns.systempolska.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.freeddyns.systempolska.ColumnName;
import org.freeddyns.systempolska.Exception.WrongFileFormatException;
import org.freeddyns.systempolska.Model.Dto.ReadUserDto;
import org.freeddyns.systempolska.Model.Dto.WriteUserDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;


@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final ColumnName columnName;
    private final int PAGE_SIZE = 2;

    public UserService(UserRepository repository, UserMapper mapper, ColumnName columnName) {
        this.repository = repository;
        this.mapper = mapper;
        this.columnName = columnName;
    }

    public Mono<List<WriteUserDto>> writeUserToDatabase(MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            List<WriteUserDto> writeUserDto = readUserDtoFromXml(reader);
            List<Users> users = writeUserDto.stream().map(mapper::map).toList();
            repository.saveAll(users);


            return Mono.just(writeUserDto);

        } catch (IOException e) {
            throw new WrongFileFormatException();
        }


    }

    private List<WriteUserDto> readUserDtoFromXml(Reader reader) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        TypeReference<List<WriteUserDto>> typeReference = new TypeReference<>() {
        };

        return xmlMapper.readValue(reader, typeReference);
    }

    public List<ReadUserDto> getUsersWithPaginationAndSortingAndSearch(String searchKeyword, String searchBy,
                                                                       String sortBy, int page) {


        String searchByCheck = convertNullToId(searchBy);
        String sortByCheck = convertNullToId(sortBy);

        Pageable pageRequest;


        long count = countTotalPage(repository.countByColumnAndSearchKeyword(searchKeyword, searchByCheck));

        if (page >= count) {
         pageRequest =  createPageRequest(0,sortByCheck);
        } else {
            pageRequest = createPageRequest(page,sortByCheck);

        }
        return repository.findAllWithPaginationAndSortingAndSearch(searchKeyword, searchByCheck, pageRequest)
                .getContent().stream().map(mapper::map).toList();

    }

    List<ReadUserDto> getUsersWithPaginationAndSorting(String sortBy, int page) {

        String sortByCheck = convertNullToId(sortBy);
        long count = countTotalPage(repository.countAllUsers());
        Pageable pageRequest;
        if (page >= count) {

            pageRequest = createPageRequest(0,sortByCheck);
        } else {
            pageRequest = createPageRequest(page,sortByCheck);

        }
        return repository.findAndSortedBy( pageRequest).stream().map(mapper::map).toList();
    }

    Set<String> columnNames() {
        return columnName.getAllColumnNames(Users.class);

    }

    private String convertNullToId(String s) {
        return (s == null || "null".equals(s)) ? "id" : s;

    }

    private long countTotalPage(long numberOfEntries) {
        return (numberOfEntries + PAGE_SIZE - 1) / PAGE_SIZE;
    }

    private Pageable createPageRequest(int page, String sortBy) {
        Sort.Order order;
        if ("login".equals(sortBy)) {
            order = Sort.Order.by("login");
        } else if ("name".equals(sortBy)) {
            order = Sort.Order.by("name");
        } else if ("surname".equals(sortBy)) {
            order = Sort.Order.by("surname");
        } else {

            order = Sort.Order.by("id");

        }


        return PageRequest.of(page, PAGE_SIZE, Sort.by(order));
    }
}
