import com.elasticconsulting.integration.esawssigning.v1.AWSV4Auth;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import org.mule.api.MuleMessage;

	
// replace following 4 String with real values to make it  work
// following 3 are fake values, so it wont run as it is...
String host = flowVars.eshost //"search-es-cloudtrail-logs-a2ouhrbg3v4s4sfi4qzg3bz4ee.us-west-2.es.amazonaws.com";
String accessKey = flowVars.accessKey
String secretKey = flowVars.secretKey
String region = flowVars.region //"us-west-2";
String serviceName = flowVars.serviceName //"es"
String httpMethodName = flowVars.httpMethodName //GET, PUT, POST, DELETE, etc...
String query = flowVars.query //"/_search";
TreeMap<String, String> queryParametes = flowVars.queryParametes 
String payload = payload //"{\"query\":{\"match_all\":{}}}";
String url = "http://" + host + query;

TreeMap<String, String> awsHeaders = new TreeMap<String, String>();
awsHeaders.put("host", host);	

AWSV4Auth aWSV4Auth = new AWSV4Auth.Builder(accessKey, secretKey)
									.regionName(region)
									.serviceName(serviceName) 
									.httpMethodName(httpMethodName) 
									.canonicalURI(query) //end point
									.queryParametes(queryParametes) //query parameters if any
									.awsHeaders(awsHeaders) //aws header parameters
									.payload(payload) // payload if any
									.debug() // turn on the debug mode
									.build();

//System.out.println(payload)

/* Get header calculated for request */
Map<String, String> header = aWSV4Auth.getHeaders();
for (Map.Entry<String, String> entrySet : header.entrySet()) {
	String key = entrySet.getKey();
	String value = entrySet.getValue();

	message.setOutboundProperty(key, value);
}

return payload


