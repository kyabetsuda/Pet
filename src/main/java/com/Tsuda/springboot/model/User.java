package com.Tsuda.springboot.model;

import java.sql.Date;
import java.sql.Timestamp;
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
	@NotEmpty(message="ユーザ名を入力してください")
	private String username;

	@Column(name="USER_PW", nullable=false)
	@NotEmpty(message="パスワードを入力してください")
	private String password;

	@Column(name="INS_YMD", insertable = false, updatable = false)
	private Timestamp insymd;

	@Column(name="UPD_YMD", insertable = false, updatable = true)
	private Timestamp updymd;

	@Column(name="authority", insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private Authority authority;


	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public int getUserid() {
		return userid;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String getUsername() {
		// TODO 自動生成されたメソッド・スタブ
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	public void setUpdymd(Timestamp updymd) {
		this.updymd = updymd;
	}
	
	public void setAuthority(Authority authority) {
		this.authority = authority;
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
