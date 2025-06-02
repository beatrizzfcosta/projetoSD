package com.example.projetosd.model;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Transient
    private String confirmarPassword;

    @Column(name = "nif", unique = true)
    private String nif;

    @Column(name = "morada")
    private String morada;

    @Column(name = "codigoPostal")
    private String codigoPostal;

    @Column(name = "pais")
    private String pais;

    @Column(name = "telefone")
    private String telefone;


    public User() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getConfirmarPassword() {return confirmarPassword;}

    public void setConfirmarPassword(String confirmarPassword) {this.confirmarPassword = confirmarPassword;}

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}