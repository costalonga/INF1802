import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TweetLifecycleManager implements LifecycleManager {

    static String _consumerKey = System.getenv().get("TWITTER_CONSUMER_KEY");
    static String _consumerSecret = System.getenv().get("TWITTER_CONSUMER_SECRET");
    static String _accessToken = System.getenv().get("TWITTER_ACCESS_TOKEN");
    static String _accessTokenSecret = System.getenv().get("TWITTER_ACCESS_TOKEN_SECRET");

    public static TwitterStream getTwitterStreamInstance(){
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
//        configurationBuilder.setDebugEnabled(true);
        configurationBuilder.setOAuthConsumerKey(_consumerKey);
        configurationBuilder.setOAuthConsumerSecret(_consumerSecret);
        configurationBuilder.setOAuthAccessToken(_accessToken);
        configurationBuilder.setOAuthAccessTokenSecret(_accessTokenSecret);
        TwitterStreamFactory tf = new TwitterStreamFactory(configurationBuilder.build());
        return tf.getInstance();
    }

    StatusListener listener = new StatusListener() {
        @Override
        public void onStatus(Status status) {
            Tweet tweet = new Tweet(status.getUser().getName(), status.getText(), status.getCreatedAt());
            System.out.println("User name: " + tweet.getUserName());
            System.out.println("Tweet date: " + tweet.getDate().toString());
            System.out.println("Text: " + tweet.getText() + "\n");
        }
        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        @Override
        public void onTrackLimitationNotice(int i) {}
        @Override
        public void onScrubGeo(long l, long l1) {}
        @Override
        public void onStallWarning(StallWarning stallWarning) {}
        @Override
        public void onException(Exception e) {
            e.printStackTrace();
        }
    };

    public void start(){
        TwitterStream twitterStream = getTwitterStreamInstance();
        twitterStream.addListener(listener);
    }

    public void stop(){

    }
}
