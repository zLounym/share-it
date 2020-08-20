package cz.zlounym.shareit.data.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document("user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String username;

    private String password;

    private String firstName;

    private String lastName;

}
