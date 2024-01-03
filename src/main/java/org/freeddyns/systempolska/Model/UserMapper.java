package org.freeddyns.systempolska.Model;

import org.freeddyns.systempolska.Configuration.NameEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private final NameEncoder nameEncoder;
    private final StringBuilder str;

    public UserMapper(NameEncoder nameEncoder, StringBuilder str) {
        this.nameEncoder = nameEncoder;
        this.str = str;
    }

    User map (WriteUserDto dto){
        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setLogin(dto.getLogin());

        return user;
    }
    ReadUserDto map (User user){
        ReadUserDto dto = new ReadUserDto();
        String name = user.getName();
        String hash = nameEncoder.encode(name);
        String surname = user.getSurname();
        str.append(surname);
        str.append("_");
        str.append(hash);
        dto.setSurname(str.toString());
        str.setLength(0);
        dto.setLogin(user.getLogin());

        return dto;

    }
}
