package org.freeddyns.systempolska.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.freeddyns.systempolska.ColumnName;
import org.freeddyns.systempolska.Exception.WrongFileFormatException;
import org.freeddyns.systempolska.Model.Dto.ReadUserDto;
import org.freeddyns.systempolska.Model.Dto.WriteUserDto;
import org.springframework.data.domain.PageRequest;
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
    private final int PAGE_SIZE = 20;

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

    public List<ReadUserDto> getUsersWithPaginationAndSortingAndSearch(String searchKeyword, String searchBy, String sortBy, int page) {


        String searchByCheck = convertNullToId(searchBy);
        String sortByCheck = convertNullToId(sortBy);


        long count = countTotalPage(repository.countByColumnAndSearchKeyword(searchKeyword, searchByCheck));
        PageRequest pageRequest;
        if (page >= count) {
            pageRequest = PageRequest.of(0, PAGE_SIZE);
        } else {
            pageRequest = PageRequest.of(page, PAGE_SIZE);

        }
        return repository.findAllWithPaginationAndSortingAndSearch(searchKeyword, searchByCheck, sortByCheck, pageRequest).getContent().stream().map(mapper::map).toList();

    }

    List<ReadUserDto> getUsersWithPaginationAndSorting(String sortBy, int page) {

        String sortByCheck = convertNullToId(sortBy);
        long count = countTotalPage(repository.countAllUsers());
        PageRequest pageRequest;
        if (page >= count) {
            pageRequest = PageRequest.of(0, PAGE_SIZE);
        } else {
            pageRequest = PageRequest.of(page, PAGE_SIZE);

        }
        return repository.findAndSortedBy(sortByCheck, pageRequest).stream().map(mapper::map).toList();
    }

    Set<String> columnNames() {
        return columnName.getAllColumnNames(Users.class);

    }

    private String convertNullToId(String s) {
        return (s == null || "null".equals(s)) ? "id" : s;

    }

    private long countTotalPage(long numberOfEntries){
        return (numberOfEntries + PAGE_SIZE - 1) / PAGE_SIZE;
    }
}
