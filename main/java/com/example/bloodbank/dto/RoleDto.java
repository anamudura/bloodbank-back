package com.example.bloodbank.dto;

import com.example.bloodbank.appuser.Users;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RoleDto {
    private Long id;
    private String name;
    private List<Users> user;

}
