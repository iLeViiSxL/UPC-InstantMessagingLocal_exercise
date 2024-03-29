package publisher;

import util.Message;
import subscriber.Subscriber;

public interface Publisher {
    
    int    incPublishers();
    int     decPublishers();
    void    attachSubscriber(Subscriber subscriber);
    boolean detachSubscriber(Subscriber subscriber);
    void    detachAllSubscribers();
    
    void    publish(Message message);
}
