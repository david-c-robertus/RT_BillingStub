
public class BillingServletRunner {

    private BillingServiceTestHarness testHarness;

    private static final String SESS_ID = "THISISACRAZYTESTSESSIONVALUE";

    public static void main(String[] args) {


        BillingServletRunner runner = new BillingServletRunner();
        try {
            runner.start();

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }


    public void start() throws Exception {

        testHarness = new BillingServiceTestHarness();
        testHarness.start();

        testHarness.setResponse("/billing/xmlrpc<?xml version=\"1.0\" encoding=\"UTF-8\"?><methodCall><methodName>login.login</methodName><params><param><value>usage</value></param><param><value>secret</value></param></params></methodCall>",
                "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><methodResponse><params><param><value>" + SESS_ID +"</value></param></params></methodResponse>");
        testHarness.setResponse("/billing/xmlrpc;jsessionid=" + SESS_ID +"<?xml version=\"1.0\" encoding=\"UTF-8\"?><methodCall><methodName>login.logout</methodName><params/></methodCall>",
                "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><methodResponse><params><param><value><boolean>1</boolean></value></param></params></methodResponse>");


    }

}
