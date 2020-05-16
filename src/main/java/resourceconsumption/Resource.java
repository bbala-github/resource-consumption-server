package resourceconsumption;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/resourceconsumption")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {
    private final String defaultName;

    public Resource(String defaultName) {
        this.defaultName = defaultName;
    }

    List<Thread> threads = new ArrayList<>();
    List<ByteBuffer> buffers = null;

    @GET
    @Timed
    public Msg sayHello(@QueryParam("q") Optional<String> name) {
        return new Msg(name.orElse(defaultName));
    }

    @GET
    @Timed
    @Path("/memory")
    public Msg laodMem(@QueryParam("memInGiB") Optional<String> numberInGiB) {
        int memoryInGiB = Integer.parseInt(numberInGiB.orElseGet(() -> "0"));
        int memoryGiBInBytes = 1024 * 1024 * 1024;

        if (buffers != null) {
            buffers = null;
            System.gc();
            System.out.println("old memory clearing done");
        }

        try {
            buffers = new ArrayList<>(memoryInGiB);
            for (int i = 0; i < memoryInGiB; i++) {
                buffers.add(ByteBuffer.allocateDirect(memoryGiBInBytes));
                for (int j = 0; j < memoryGiBInBytes - 1; j++) {
                    buffers.get(i).putChar(j, 'f');
                }
            }

            System.out.println("allocated " + memoryInGiB + " GiB");
        } catch (Throwable t) {
            System.out.println("exception");
            t.printStackTrace();
            return new Msg("unable to allocate required memory");
        }
        return new Msg("Memory consumption done successfully for " + memoryInGiB + " GB");
    }

    @GET
    @Path("/cpu")
    public Msg loadCpu(@QueryParam("cores") Optional<String> optNumCores) throws InterruptedException {
        if (!threads.isEmpty()) {
            for (Thread t : threads) {
                t.stop();
                t.join();
                System.out.println("join completed");
            }
        }

        int numCores = Integer.parseInt(optNumCores.orElseGet(() -> "0"));
        if (numCores > 0) {
            threads = new ArrayList<>(numCores);
            for (int i = 0; i < numCores; i++) {
                Runnable r = () -> {
                    while (true) {
                    }
                };
                Thread t = new Thread(r);
                t.start();
                threads.add(t);
            }
        }
        return new Msg("CPU consumption started successfully for " + numCores + " cores");
    }
}
