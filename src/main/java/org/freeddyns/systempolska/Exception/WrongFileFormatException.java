package org.freeddyns.systempolska.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = ("Wrong File Format"))
public class WrongFileFormatException extends RuntimeException{
}
