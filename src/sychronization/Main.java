package sychronization;

public class Main {
    public static void main(String[] args) {
        BankAccount companyAccount = new BankAccount("Manish",10000);


        Thread thread1 = new Thread(()->companyAccount.withdraw(2000));
        Thread thread2 = new Thread(()->companyAccount.deposit(10000));
//        Thread thread3 = new Thread(()->companyAccount.withdraw(6000));
        Thread thread3 = new Thread(()->companyAccount.setName("Chauhan"));
        Thread thread4 = new Thread(()->companyAccount.withdraw(6000));
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try{
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("\nfinal balance "+companyAccount.getBalance());
    }
}
