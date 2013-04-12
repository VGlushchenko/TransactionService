package com.mypal.entity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    private int id;
    private User debit;
    private User credit;
    private double sum;
    private String status;
    private TransactionLog log;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "debit_id")
    public User getDebit() {
        return debit;
    }

    public void setDebit(User debit) {
        this.debit = debit;
    }

    @ManyToOne
    @JoinColumn(name = "credit_id")
    public User getCredit() {
        return credit;
    }

    public void setCredit(User credit) {
        this.credit = credit;
    }

    @Column(name = "sum")
    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //@OneToOne(fetch = FetchType.LAZY, mappedBy = "transaction", cascade = CascadeType.ALL)
    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "log_id")*/

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "transaction_log",
            joinColumns = @JoinColumn(name="transaction_id"),
            inverseJoinColumns = @JoinColumn(name="log_id")
    )
    public TransactionLog getLog() {
        return log;
    }

    public void setLog(TransactionLog log) {
        this.log = log;
    }

    public boolean completed() {
        return (this.getLog().getCompletedAt() != null)
                ? true
                : false;
    }

    public void complete() {
        this.getLog().setCompletedAt(new Date());
        this.setStatus("complete");
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject result = new JSONObject();
        result.put("debit", this.getDebit().getEmail());
        result.put("credit", this.getCredit().getEmail());
        result.put("status", this.getStatus());
        result.put("sum", this.getSum());
        return result;
    }
}
