package examples.interactivequeries.Demo;

import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.StreamsMetadata;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.mhealth.open.data.avro.MEvent;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;


@Path("measures")
public class Demo1RestService {

    private final KafkaStreams streams;
    private final String profileStoreName;
    private final String searchStoreName;
    private Server jettyServer;
    private final HostInfo hostInfo;
    private final Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

    Demo1RestService(final KafkaStreams streams, final HostInfo hostInfo, final String profileStoreName, final String searchStoreName){
        this.streams = streams;
        this.profileStoreName = profileStoreName;
        this.searchStoreName = searchStoreName;
        this.hostInfo = hostInfo;
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public MEventBean searchProfile(@QueryParam("user_id") String user_id) {
        final HostStoreInfo host = hostInfoForStoreAndKey(searchStoreName, user_id, new StringSerializer());
        if (!host.equivalent(hostInfo)) {
            return fetchProfile(host, "search?user_id=" + user_id);
        }
        ReadOnlyKeyValueStore<String, MEvent> stateStore = waitUntilStoreIsQueryable(searchStoreName);
        System.out.println("starting");
        MEvent event = findProfileByKey(user_id, stateStore);
        return new MEventBean(event.getUserId(),event.getTimestamp(),event.getMeasures().get("heart_rate").getValue());
    }

/*    @GET
    @Path("/profile/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MEventBean getProfileByID(@PathParam("user_id") String user_id){
        final HostStoreInfo host = hostInfoForStoreAndKey(profileStoreName, user_id, new StringSerializer());
        if (!host.equivalent(hostInfo)) {
            return fetchProfile(host, "profile/" + user_id);
        }
        ReadOnlyKeyValueStore<String, MEventBean> stateStore = waitUntilStoreIsQueryable(profileStoreName);
        return findProfileByKey(user_id, stateStore);
    }*/
 /*   @GET
    @Path("/abnormal_bp")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HostStoreInfo> streamsMetadata() {
        return metadataService.streamsMetadata();
    }

    public List<MEvent> abnormalBP() {

        // The abnormal_bp might be hosted elsewhere.
        // so we need to first find where it is and then we can do a local or remote lookup.
        final HostStoreInfo host =
                metadataService.streamsMetadataForStoreAndKey(Demo1.DEMO1_STORE, Demo1.DEMO1_KEY, new StringSerializer());

        // abnormal_bp is hosted on another instance
        if (!thisHost(host)) {
            return fetchDemo1(host, "measures/abnormal_bp/");
        }

        // abnormal_bp is on this instance
        return bp(Demo1.DEMO1_KEY, Demo1.DEMO1_STORE);
    }*/

/*    private boolean thisHost(final HostStoreInfo host) {
        return host.getHost().equals(hostInfo.host()) &&
                host.getPort() == hostInfo.port();
    }
    private List<MEvent> fetchDemo1(final HostStoreInfo host, final String path) {
        return client.target(String.format("http://%s:%d/%s", host.getHost(), host.getPort(), path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<MEvent>>() {
                });
    }*/

/*    private List<Demo1Bean> bp(final String key, final String storeName) {

        final ReadOnlyKeyValueStore<String, KafkaMusicExample.TopFiveSongs> topFiveStore =
                streams.store(storeName, QueryableStoreTypes.<String, KafkaMusicExample.TopFiveSongs>keyValueStore());
        // Get the value from the store
        final KafkaMusicExample.TopFiveSongs value = topFiveStore.get(key);
        if (value == null) {
            throw new NotFoundException(String.format("Unable to find value in %s for key %s", storeName, key));
        }
    }*/


    private MEventBean fetchProfile(final HostStoreInfo host, final String path) {
        return client.target(String.format("http://%s:%d/%s", host.getHost(), host.getPort(), path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<MEventBean>(){});
    }

    private MEvent findProfileByKey(String key, ReadOnlyKeyValueStore<String, MEvent> stateStore) {
        final MEvent value = stateStore.get(key);
        if (value == null) {
            throw new NotFoundException();
        }
        return value;
    }

    private ReadOnlyKeyValueStore<String, MEvent> waitUntilStoreIsQueryable(final String storeName) throws InvalidStateStoreException {
        final long maxWaitMillis = 3000;
        long currentWaitMillis = 0;

        while (true) {
            try {
                return streams.store(storeName, QueryableStoreTypes.<String, MEvent>keyValueStore());
            } catch (InvalidStateStoreException ex) {
                // store not yet ready for querying
                if (currentWaitMillis >= maxWaitMillis) {
                    throw ex;
                }
                currentWaitMillis += 100;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignore) {
                    //ignore this exception
                }
            }
        }
    }

    /**
     * Start an embedded Jetty Server
     * @throws Exception
     */
    void start(final int port) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        jettyServer = new Server(hostInfo.port());
        jettyServer.setHandler(context);

        ResourceConfig rc = new ResourceConfig();
        rc.register(this);
        rc.register(JacksonFeature.class);

        ServletContainer sc = new ServletContainer(rc);
        ServletHolder holder = new ServletHolder(sc);
        context.addServlet(holder, "/*");

        System.out.println("working");
        jettyServer.start();
    }

    /**
     * Stop the Jetty Server
     * @throws Exception
     */
    void stop() throws Exception {
        if (jettyServer != null) {
            jettyServer.stop();
        }
    }

    private HostStoreInfo hostInfoForStoreAndKey(final String store, final String key,
                                                 final Serializer<String> serializer) {
        final StreamsMetadata metadata = streams.metadataForKey(store, key, serializer);
        if (metadata == null) {
            throw new NotFoundException();
        }

        return new HostStoreInfo(metadata.host(),
                metadata.port(),
                metadata.stateStoreNames());
    }
}
