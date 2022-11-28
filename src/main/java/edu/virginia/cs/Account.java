package edu.virginia.cs;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "ACCOUNT_NUMBER")
    private int id;

    @Column(name="ACCOUNT_TYPE", nullable = false)
    private AccountType accountType;

    @Column(name="BALANCE", nullable = false)
    private double balance;

    @ManyToOne
    @JoinColumn(name="CLIENT_ID", referencedColumnName = "ID")
    private Client accountHolder;

    public Account() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(Client accountHolder) {
        this.accountHolder = accountHolder;
    }

    public void deposit(double amount) {
        setBalance(getBalance() + amount);
    }
}
