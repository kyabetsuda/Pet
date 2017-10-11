package com.Tsuda.springboot.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user")
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;
	public enum Authority {ROLE_USER, ROLE_ADMIN};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="USER_ID", nullable=false)
	private int userid;

	@Column(name="USER_NM", nullable=false)
	@Setter
	@NotEmpty
	private String username;

	@Column(name="USER_PW", nullable=false)
	@Setter
	@NotEmpty
	private String password;

	@Column(name="INS_YMD")
	@Getter
	@Setter
	private Date insymd;

	@Column(name="UPD_YMD")
	@Getter
	@Setter
	private Date updymd;

	@Column(name="authority")
	@Enumerated(EnumType.STRING)
	@Setter
	private Authority authority;


	@Override
	public String getPassword() {
		return password;
	}


	@Override
	public String getUsername() {
		// TODO 自動生成されたメソッド・スタブ
		return username;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(authority.toString()));
      return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserDetails) {
            return username.equals(((UserDetails) o).getUsername());
        }
        return false;
    }

    //usernameのハッシュコードを返す。インスタンスの同一性の判断のときに用いられる。
    @Override
    public int hashCode() {
        return username.hashCode();
    }







}
