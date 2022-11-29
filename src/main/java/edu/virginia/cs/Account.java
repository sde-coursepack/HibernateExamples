package edu.virginia.cs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactionList;

    public Account() {
        transactionList = new ArrayList<>();
    }

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

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public void deposit(double amount) {
        setBalance(getBalance() + amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", accountHolder=" + accountHolder.getId() +
                '}';
    }
}
