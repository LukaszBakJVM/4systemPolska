package org.freeddyns.systempolska.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.freeddyns.systempolska.ColumnName;
import org.freeddyns.systempolska.Exception.WrongFileFormatException;
import org.freeddyns.systempolska.User.Model.Dto.ReadUserDto;
import org.freeddyns.systempolska.User.Model.Dto.WriteUserDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final int PAGE_SIZE = 20;

    public UserService(UserRepository repository, UserMapper mapper, ColumnName columnName) {
        this.repository = repository;
        this.mapper = mapper;
        this.columnName = columnName;
    }
@Transactional

     Mono<List<WriteUserDto>> writeUserToDatabase(MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            List<WriteUserDto> writeUserDto = readUserDtoFromXml(reader);
            List<Users> users = writeUserDto.stream().map(mapper::map).toList();
            repository.saveAll(users);


            return Mono.just(writeUserDto);

        } catch (IOException e) {
            throw new WrongFileFormatException("Wrong File Format");
        }


    }

    private List<WriteUserDto> readUserDtoFromXml(Reader reader) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        TypeReference<List<WriteUserDto>> typeReference = new TypeReference<>() {
        };

        return xmlMapper.readValue(reader, typeReference);
    }

    List<ReadUserDto>connectionToController(String searchKeyword, String searchBy,
                                        String sortBy, int page){
        if (searchKeyword == null || searchKeyword.equals("null")) {
            return getUsersWithPaginationAndSorting(sortBy,page);
        }
        return getUsersWithPaginationAndSortingAndSearch(searchKeyword, searchBy, sortBy, page);

    }

  private   List<ReadUserDto> getUsersWithPaginationAndSortingAndSearch(String searchKeyword, String searchBy,
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

  private   List<ReadUserDto> getUsersWithPaginationAndSorting(String sortBy, int page) {

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

    private Pageable createPageRequest(int page,  String sortBy) {
        Sort.Order order = Sort.Order.by(sortBy);

        return PageRequest.of(page, PAGE_SIZE, Sort.by(order));
    }
}
