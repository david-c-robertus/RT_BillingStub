import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.List;

public class BillingServiceTestHarness {

    private Server jetty = null;
    private BillingSimulatorServlet servlet = null;


    public void start() throws Exception  {

        jetty = new Server(BillingSimulatorServlet.BILLING_SERVICE_TEST_PORT);

        servlet = new BillingSimulatorServlet();
        ServletHolder sh = new ServletHolder(servlet);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(sh, BillingSimulatorServlet.BILLING_SERVICE_PATH);

        jetty.setHandler(context);

        jetty.start();
    }


    public void tearDown() throws Exception {

        servlet.cannedResponses.clear();
        if (jetty != null) {
            jetty.stop();
        }

    }

    public void setResponse(String request, String response) {
        servlet.cannedResponses.put(request, response);
    }

    public List<String> getAndClearReceivedBillingCalls() {
        return servlet.getAndClearReceivedCalls();
    }

    public List<String> getCallsReceivedWithNoResponse(){
        return servlet.getCallsWithNoResponses();
    }
}
