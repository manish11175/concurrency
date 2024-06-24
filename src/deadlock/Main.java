package deadlock;

import java.util.Random;

class MessageRepository{
    private String message;
    private boolean hasMessage = false;

    public synchronized String read(){
        while(!hasMessage){
            System.out.println("Locked in read");
        }
        hasMessage=false;
        return message;
    }

    public synchronized void write(String message){
        while(hasMessage){
            System.out.println("Locked in write");
        }
        hasMessage=true;
        this.message=message;
    }
}
class MessageWrite implements Runnable{

    private MessageRepository ongoingMessage;


    private final String text = """
                     Humpty Dumpty sat on the wall,
                      Humpty Dumpty had a great fall,
                      All the king's and all the king's men,
                       Couldn't  put Humpty together again.
                """;

    public MessageWrite(MessageRepository messageRepository) {
        this.ongoingMessage = messageRepository;
    }


    @Override
    public void run(){
        Random random= new Random();
        String[]lines= text.split("\n");
        for(String line:lines){
            ongoingMessage.write(line);
            try{
                Thread.sleep(random.nextInt(500,2000));
            }
            catch (InterruptedException e){
                throw  new RuntimeException();
            }
        }

    }
}

class MessageReader implements Runnable{

    private MessageRepository incomingMessage;

    public MessageReader(MessageRepository incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    @Override
    public void run(){
         Random random = new Random();
          do {
              try {
                  Thread.sleep(random.nextInt(500,2000));
              }
              catch (InterruptedException e){
                  throw new RuntimeException();
              }
              String latestMessage = incomingMessage.read();
              System.out.println(latestMessage);
          }
          while (!incomingMessage.equals("Finished"));
    }
}

public class Main {
    public static void main(String[] args) {
        MessageRepository messageRepository = new MessageRepository();
        Thread reader = new Thread(new MessageReader(messageRepository));
        Thread writer = new Thread(new MessageWrite(messageRepository));
        reader.start();
        writer.start();
    }
}
