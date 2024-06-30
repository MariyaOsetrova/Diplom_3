package ru.osetrova.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class User {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    private String password;
    private String name;
}
