package org.freeddyns.systempolska.Model;

import com.fasterxml.jackson.core.type.TypeReference;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class UserService {
    private final WebClient webClient;
    private final UserRepository repository;
    private final UserMapper mapper;
    private final int PAGE_SIZE = 20;

    public UserService(WebClient.Builder webClient, UserRepository repository, UserMapper mapper) {
        this.webClient = webClient.build();
        this.repository = repository;
        this.mapper = mapper;
    }
    Flux<WriteUserDto>saveUsersFromFile(MultipartFile file){
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            List<WriteUserDto> writeUserDtos = readUserDtoFromJson(reader);
            List<User> users = writeUserDtos.stream().map(mapper::map)
                    .toList();
            repository.saveAll(users);
            return Flux.fromIterable(writeUserDtos);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    private List<WriteUserDto> readUserDtoFromJson(Reader reader) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        TypeReference<List<WriteUserDto>> typeReference = new TypeReference<>() {};

        return xmlMapper.readValue(reader, typeReference);
    }
    public List<ReadUserDto> getUsersWithPaginationAndSortingAndSearch(
            String searchKeyword, String sortBy,int page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        return repository.findAllWithPaginationAndSortingAndSearch(searchKeyword, sortBy, pageRequest).getContent()
                .stream()
                 .map(mapper::map)
                 .toList();
    }

    Flux<List<ReadUserDto>>findAll(int page){
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        List<User> all = repository.findAllBy(pageRequest).getContent();

        return Flux.fromIterable(all)
                .map(mapper::map)
                .collectList()
                .flux();
    }
}
