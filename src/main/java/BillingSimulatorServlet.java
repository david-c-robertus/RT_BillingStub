
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BillingSimulatorServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(BillingSimulatorServlet.class.getName());

    /**
     * port for running the service on
     */
    public static final int BILLING_SERVICE_TEST_PORT = 15188;

    public static final String BILLING_SERVICE_PATH = "/billing/xmlrpc";

    public Map<String,String> cannedResponses = new HashMap<String, String>();

    private List<String> receivedCalls = new ArrayList<String>();
    private List<String> unresponsesdCalls = new ArrayList<String>();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        //logger.info("receivedCall => " + );

        InputStream is = request.getInputStream();
        int length = request.getContentLength();

        byte[] call = new byte[length];
        is.read(call);
        String msg = new String(call);
        receivedCalls.add(uri+msg);

        logger.info("receivedCall Msg => " + uri + msg);
        String theResponse = cannedResponses.get(uri+msg);

        if (theResponse != null) {
            logger.info("responding with  => " + theResponse);
            ServletOutputStream os = response.getOutputStream();
            os.print(theResponse);
            os.flush();
            os.close();
        }
        else
        {
            this.unresponsesdCalls.add(uri+msg);
        }


    }

    public List<String> getAndClearReceivedCalls(){
        List<String> returnedVals = new ArrayList<String>();

        for (String rc: receivedCalls) {
            returnedVals.add(rc);
        }
        receivedCalls.clear();
        return returnedVals;

    }

    public List<String> getCallsWithNoResponses() {
        return unresponsesdCalls;
    }

}
