package publisher;

import util.Subscription_close;
import util.Message;
import util.Topic;
import java.util.ArrayList;
import java.util.List;
import subscriber.Subscriber;

public class PublisherImpl implements Publisher {

    private List<Subscriber> subscriberSet;
    private int numPublishers;
    private Topic topic;

    public PublisherImpl(Topic topic) {
        subscriberSet = new ArrayList<Subscriber>();
        numPublishers = 1;
        this.topic = topic;
    }

    @Override
    public int incPublishers() {
        numPublishers++;
        return  numPublishers;
    }

    @Override
    public int decPublishers() {
        numPublishers--;
        if(numPublishers == 0) detachAllSubscribers();
        return numPublishers;
    }

    @Override
    public void attachSubscriber(Subscriber subscriber) {
        System.out.println("Subscriber attach to: "+ topic.name);
        subscriberSet.add(subscriber);
    }

    @Override
    public boolean detachSubscriber(Subscriber subscriber) {
        System.out.println("Subscriber detach to: "+ topic.name);
        return subscriberSet.remove(subscriber);
    }

    @Override
    public void detachAllSubscribers() {
        System.out.println("Detach all subscribers to: "+ topic.name);
        subscriberSet = (ArrayList<Subscriber>) subscriberSet;
        for (Subscriber s : subscriberSet) {
            s.onClose(new Subscription_close(topic, Subscription_close.Cause.PUBLISHER));
        }
    }

    @Override
    public void publish(Message message) {
        System.out.println("Publish message: "+message.content+" to: "+ topic.name);
        for (Subscriber s : subscriberSet) {
            s.onMessage(message);
        }
    }

}
