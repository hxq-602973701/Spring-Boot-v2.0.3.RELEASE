package com.example.demo.dal.entity.main.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@ToString
public class User {
    @Id
    private Integer id;
    private String name;
    private Integer age;
}