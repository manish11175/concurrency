package locks;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MessageRepository {
    private String message;
    private boolean hasMessage = false;
    private Lock lock = new ReentrantLock();

    public String read() {
//        lock.lock();
        try {
            if(lock.tryLock(3, TimeUnit.SECONDS)){
                try{
                    while (!hasMessage) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Locked in read");
                    }
                    hasMessage = false;
                }
                finally {
                    lock.unlock();
                }
            }
            else{
                System.out.println("** read is blocked "+lock);
                hasMessage=false;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    public  void write(String message) {
        if(lock.tryLock()){
            try{
                while (hasMessage) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Locked in write");
                }
                hasMessage = true;
            }
            finally {
                lock.unlock();
            }
        }
        else{
            System.out.println("** write is blocked "+lock);
            hasMessage = true;
        }
        this.message = message;
    }
}

class MessageWrite implements Runnable {

    private final String text = """
            Humpty Dumpty sat on the wall,
            Humpty Dumpty had a great fall,
            All the king's and all the king's men,
            Couldn't  put Humpty together again.
            """;
    private MessageRepository ongoingMessage;

    public MessageWrite(MessageRepository messageRepository) {
        this.ongoingMessage = messageRepository;
    }


    @Override
    public void run() {
        Random random = new Random();
        String[] lines = text.split("\n");
        for (String line : lines) {
            ongoingMessage.write(line);
            try {
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
        ongoingMessage.write("Finished");
    }
}

class MessageReader implements Runnable {

    private MessageRepository incomingMessage;

    public MessageReader(MessageRepository incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    @Override
    public void run() {
        Random random = new Random();
        do {
            try {
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
            String latestMessage = incomingMessage.read();
            ;
            System.out.println(latestMessage);
        }
        while (!incomingMessage.equals("Finished"));
    }
}

public class Main {
    public static void main(String[] args) {
        MessageRepository messageRepository = new MessageRepository();
        Thread reader = new Thread(new MessageReader(messageRepository),"Reader");
        Thread writer = new Thread(new MessageWrite(messageRepository),"Writer");

        reader.start();
        writer.start();
    }
}
