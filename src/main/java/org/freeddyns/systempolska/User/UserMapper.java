package org.freeddyns.systempolska.User;

import org.freeddyns.systempolska.Configuration.NameEncoder;
import org.freeddyns.systempolska.User.Model.Dto.ReadUserDto;
import org.freeddyns.systempolska.User.Model.Dto.WriteUserDto;
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
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setLogin(dto.getLogin());

        return user;
    }

    ReadUserDto map(Users users) {
        ReadUserDto dto = new ReadUserDto();
        String name = users.getName();
        String hash = nameEncoder.encode(name);
        String surname = users.getSurname();
        String loginHash = str.append(surname).append("_").append(hash).toString();
        dto.setSurname(loginHash);
        str.setLength(0);
        dto.setLogin(users.getLogin());

        return dto;

    }
}
