package org.freeddyns.systempolska.Model;

import org.freeddyns.systempolska.Configuration.NameEncoder;
import org.freeddyns.systempolska.Model.Dto.ReadUserDto;
import org.freeddyns.systempolska.Model.Dto.WriteUserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private final NameEncoder nameEncoder;
    private final StringBuilder str;

    public UserMapper(NameEncoder nameEncoder, StringBuilder str) {
        this.nameEncoder = nameEncoder;
        this.str = str;
    }

    Users map (WriteUserDto dto){
        Users user = new Users();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setLogin(dto.getLogin());

        return user;
    }
    ReadUserDto map (Users users){
        ReadUserDto dto = new ReadUserDto();
        String name = users.getName();
        String hash = nameEncoder.encode(name);
        String surname = users.getSurname();
        str.append(surname);
        str.append("_");
        str.append(hash);
        dto.setSurname(str.toString());
        str.setLength(0);
        dto.setLogin(users.getLogin());

        return dto;

    }
}
