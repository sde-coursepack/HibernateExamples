package edu.virginia.cs;

import javax.persistence.*;
import java.sql.*;


@Entity
@Table (name = "TRANSACTIONS")

public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int transactionId;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID", referencedColumnName = "ACCOUNT_NUMBER")
    private Account account;

    @Column(nullable = false, name = "AMOUNT")
    private double amount;

    @Column(nullable = false, name = "TIME_STAMP")
    private Timestamp timestamp;

    public Transaction() { }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
