package com.Ako.wiki.req;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserResetPassword {
    private Long id;

    @NotEmpty(message = "[Password] can't be empty.")
//    @Length(min = 6, max = 20, message = "[Password] needs to be 6 to 20 digits.")
    @Pattern(regexp = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$", message = "[Password] must to have digits " +
            "and letters, and its digit length must be from 6 to 32.")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", password=").append(password);
        sb.append("]");
        return sb.toString();
    }
}