package org.freeddyns.systempolska.user;

import org.freeddyns.systempolska.configuration.NameEncoder;
import org.freeddyns.systempolska.user.Model.dto.ReadUserDto;
import org.freeddyns.systempolska.user.Model.dto.WriteUserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private final NameEncoder nameEncoder;
    private final StringBuilder str;

    public UserMapper(NameEncoder nameEncoder, StringBuilder str) {
        this.nameEncoder = nameEncoder;
        this.str = str;
    }

    Users map(WriteUserDto dto) {
        Users user = new Users();
        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setLogin(dto.login());

        return user;
    }

    ReadUserDto map(Users users) {
        str.setLength(0);

        String name = users.getName();
        String hash = nameEncoder.encode(name);
        String surname = users.getSurname();
        String loginHash = str.append(surname).append("_").append(hash).toString();


        return new ReadUserDto(loginHash, users.getLogin());

    }
}
