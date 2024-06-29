package volatile_;

public class Example {
    private  int count=0;

    private int getCount(){
        return count;
    }
    private void setCount(){
        count+=1;
    }

    public static void main(String[] args) {
        Example example = new Example();
        Thread counter1 = new Thread(()->{
            while(example.getCount()<20){
                example.setCount();
                System.out.println("Count "+example.getCount());
            }
        });

        Thread counter2 = new Thread(()->{
            while(example.getCount()<20){
                example.setCount();
                System.out.println("Count "+example.getCount());
            }
        });

        Thread counter3 = new Thread(()->{

            while(example.getCount()<20){
                example.setCount();
                System.out.println("Count "+example.getCount());
            }
        });


        counter1.start();
        counter2.start();
        counter3.start();

    }
}
