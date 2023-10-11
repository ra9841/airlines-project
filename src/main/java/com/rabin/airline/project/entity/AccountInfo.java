package com.rabin.airline.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACCOUNT_INFO")
public class AccountInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accId;
    private String accountNo;
    private double accAmount;

}
