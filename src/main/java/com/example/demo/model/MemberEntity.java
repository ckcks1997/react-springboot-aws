package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer memberId;
    private String userName;
    private Date birthDate;

}
