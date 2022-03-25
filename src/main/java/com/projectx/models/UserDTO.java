package com.projectx.models;

import lombok.Data;

@Data
public class UserDTO {

    private String email;
    private boolean emailVerified;
    private String familyName;
    private String givenName;
    private String name; //Some cultures have more than two names so this is in consideration of those cases
    private String nickname;
    private String pictureURL;
    private String phoneNumber;
    private boolean phoneNumberVerified;
    private String birthday;
}
