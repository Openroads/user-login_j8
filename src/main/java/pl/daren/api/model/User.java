package pl.daren.api.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class User {

    private String name;

    private String email;

    private String password;

    private String phone;
}
