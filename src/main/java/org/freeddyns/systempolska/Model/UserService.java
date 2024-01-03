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

import reactor.core.publisher.Flux;

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

    Flux<WriteUserDto> saveUsersFromFile(MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            List<WriteUserDto> writeUserDto = readUserDtoFromXml(reader);
            List<Users> users = writeUserDto.stream().map(mapper::map)
                    .toList();
            repository.saveAll(users);
            return Flux.fromIterable(writeUserDto);

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

    public List<ReadUserDto> getUsersWithPaginationAndSortingAndSearch(
            String searchKeyword, String sortBy, int page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);

        return repository.findAllWithPaginationAndSortingAndSearch(searchKeyword, sortBy, pageRequest).getContent()
                .stream()
                .map(mapper::map)
                .toList();
    }

    ///sortowanie all
    List<ReadUserDto> getUsersWithPaginationAndSorting(String sortBy, int page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        return repository.findAndSortedBy(sortBy, pageRequest).stream().map(mapper::map).
                toList();
    }

    Set<String> columnNames() {
        return columnName.getAllColumnNames(Users.class);

    }
}
