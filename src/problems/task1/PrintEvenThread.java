package problems.task1;

public class PrintEvenThread extends Thread{

    private int num;
    public PrintEvenThread(int num,String name){
        this.num=num;
        this.setName(name);
    }


    @Override
    public void run(){
        try{
            for (int i = 0; i <num ; i++) {
                System.out.print(i*2+" ");
                Thread.sleep(1000);
            }
        }
        catch (InterruptedException e){
            System.out.println(this.getName()+" is interrupted !!");
            this.interrupt();
        }

    }
}
