package org.example;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

class InsufficientFundsException extends Exception
{
    public InsufficientFundsException(String message)
    {
        super(message);
    }
}

class NegativeAmountException extends Exception
{
    public NegativeAmountException(String message)
    {
        super(message);
    }
}

class AccountNotFoundException extends Exception
{
    public AccountNotFoundException(String message)
    {
        super(message);
    }
}

class BankAccount
{
    private int accountNumber;
    private String accountName;
    private double balance;

    public BankAccount(int accountNumber, String accountName, double initialDeposit)
    {
        this.accountNumber = accountNumber;

        this.accountName = accountName;

        this.balance = initialDeposit;
    }

    public int getAccountNumber()
    {
        return accountNumber;
    }

    public void deposit(double amount) throws NegativeAmountException
    {
        if (amount < 0)
        {
            throw new NegativeAmountException("Amount should be positive");
        }

        this.balance += amount;
    }

    public void withdraw(double amount) throws NegativeAmountException, InsufficientFundsException
    {
        if (amount < 0)
        {
            throw new NegativeAmountException("Amount should be positive");
        }
        if (amount > this.balance)
        {
            throw new InsufficientFundsException("Insufficient funds");
        }

        this.balance -= amount;
    }

    public double getBalance()
    {
        return balance;
    }

    public String getAccountSummary()
    {
        return "Account Number: " + accountNumber + "\nAccount Name: " + accountName + "\nBalance: " + balance;
    }
}

class Bank
{
    private List<BankAccount> accounts = new ArrayList<>();

    public BankAccount createAccount(String accountName, double initialDeposit)
    {
        int accountNumber = accounts.size() + 1;

        BankAccount newAccount = new BankAccount(accountNumber, accountName, initialDeposit);

        accounts.add(newAccount);

        return newAccount;
    }

    public BankAccount findAccount(int accountNumber) throws AccountNotFoundException
    {
        for (BankAccount account : accounts)
        {
            if (account.getAccountNumber() == accountNumber)
            {
                return account;
            }
        }

        throw new AccountNotFoundException("Account not found");
    }

    public void transferMoney(int fromAccountNumber, int toAccountNumber, double amount)
    throws NegativeAmountException, InsufficientFundsException, AccountNotFoundException
    {
        BankAccount fromAccount = findAccount(fromAccountNumber);
        BankAccount toAccount = findAccount(toAccountNumber);

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }
}

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Bank bank = new Bank();
            BankAccount account1 = bank.createAccount("John Doe", 1000);
            BankAccount account2 = bank.createAccount("Jane Doe", 500);

            System.out.println("Initial Account 1:\n" + account1.getAccountSummary());
            System.out.println("Initial Account 2:\n" + account2.getAccountSummary());

            bank.transferMoney(1, 2, 200);

            System.out.println("Account 1 after transfer:\n" + account1.getAccountSummary());
            System.out.println("Account 2 after transfer:\n" + account2.getAccountSummary());
        }
        catch (NegativeAmountException | InsufficientFundsException | AccountNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}