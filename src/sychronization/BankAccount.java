package sychronization;

public class BankAccount {

    private double balance;

    private String name;

    private final Object lockName = new Object();
    private final Object lockBalance = new Object();

    public BankAccount(String name,double balance) {
        this.balance = balance;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        synchronized (lockName){
            this.name = name;
            System.out.println("Bank is name is updated to "+this.name);
        }
    }

    public double getBalance() {
        return balance;
    }

    public  void deposit(double amount){
        try{
            System.out.println("Bank is confirming.............");
            Thread.sleep(7000);
        }
        catch (InterruptedException e){
            throw new RuntimeException();
        }

        synchronized (lockBalance){
            double origBalance = this.balance;
            this.balance+=amount;
            System.out.printf("Starting balance: %.0f, Deposit (%.0f)"+" : New Balance = %.0f%n",origBalance,amount,balance);
            addPromoBalance(amount);
        }
     }

     private void addPromoBalance(double amount){
        synchronized (lockBalance){
            if(amount>=5000){
                this.balance+=25;
                System.out.printf("%nCongratulations!! you win a promo of %d Rs",25);
            }
        }
     }

    public synchronized void withdraw(double amount){
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e){
            throw new RuntimeException();
        }

        double origBalance = balance;
        if(amount<=balance){
            this.balance-=amount;
            System.out.printf("Starting balance: %.0f, Withdrawal (%.0f)"+" : New Balance = %.0f%n",origBalance,amount,balance);
        }else{
            System.out.printf("Starting balance: %.0f, Withdrawal (%.0f)"+" : Insufficient Funds!",origBalance,amount);

        }
    }
}
