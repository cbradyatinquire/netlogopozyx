import org.nlogo.api.*;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;




public class PozyxExtension extends DefaultClassManager {
	
	protected String logtoget = "[BEGIN] ";
	
	public String my_url = "tcp://localhost:1883";
  
	protected MyMqttClient client;
	
	public Map<String,Object> values =
	    Collections.<String,Object>synchronizedMap(new HashMap<String,Object>());
  
  public void load(PrimitiveManager primitiveManager) {

	
	primitiveManager.addPrimitive("get", new Get(values));
	primitiveManager.addPrimitive("get-ids", new GetIds());
	
	primitiveManager.addPrimitive("set-url", new SetUrl());
	primitiveManager.addPrimitive("start", new Start());
	
	//restartListener();
  }
  
  public void restartListener() {
  	logtoget += "== START SUBSCRIBER ==";
	values.put("logs", logtoget );
  	//client=new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
  	//client.setCallback( new TheMqttCallBack() );
  	//client.connect();
  	//client.subscribe("tags");
  	try {
  		client = new MyMqttClient();
  	} catch (Exception e) { 
  		logtoget += "exception on create client: " + e.toString();
		values.put("logs", logtoget );
  		System.err.println(e.toString());
  	}

  }
  
  
  private class MyMqttClient extends MqttClient {
	  
	  public MyMqttClient() throws MqttException {
		  super(my_url, MqttClient.generateClientId());
		  //super("tcp://localhost:1883", MqttClient.generateClientId());
		  //super("tcp://10.2.191.28:1883", MqttClient.generateClientId());
		  setCallback( new TheMqttCallBack() );
		  connect();
		  subscribe("tags");
	  }
  }
  
  private class TheMqttCallBack implements MqttCallback {

    public void connectionLost(Throwable throwable) {
      logtoget += "Connection to MQTT broker lost!";
	  values.put("logs", logtoget );
	  restartListener();
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
     // System.out.println("Message received:\t"+ new String(mqttMessage.getPayload()) );
  	//System.out.println("Positioning update: " + s + " with payload: " + new String(mqttMessage.getPayload()) );
	//fix when i have the structure.
	//values.put(s, mqttMessage.getPayload());
	
		String proc = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
		proc = proc.substring(1, proc.length() - 1);
		Gson gson = new Gson();		
		Datum dat = gson.fromJson(proc, Datum.class);
		Coordinates cs = dat.data.coordinates;
		values.put( dat.tagId, new String("[" + cs.x + " " + cs.y + "]") );
		//System.out.println(dat.tagId + ": (" + cs.x + ", " + cs.y + ")" );
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }
  }
  
  
  public static class Get implements Reporter {
    private Map<String, Object> values;

    public Get(Map<String, Object> values) {
      this.values = values;
    }

    @Override
    public Syntax getSyntax() {
        return SyntaxJ.reporterSyntax(new int[] {Syntax.StringType()},
            Syntax.StringType() | Syntax.BooleanType() | Syntax.NumberType());
      }

    @Override
    public Object report(Argument[] args, Context ctxt)
        throws ExtensionException, LogoException {
      return get(args[0].getString());
    }

    public Object get(String key) {
      String lcKey = key.toLowerCase();
      if (values.containsKey(lcKey))  {
        return values.get(lcKey);
      } else {
        return Boolean.FALSE;
      }
    }
  }
  
  
  public class GetIds implements Reporter {
	
    public Syntax getSyntax() {
      return SyntaxJ.reporterSyntax(
  	new int[] {}, Syntax.ListType());
	
    }

    public Object report(Argument args[], Context context)
        throws ExtensionException {
			Set<String> keys = values.keySet();
			int numkeys = values.keySet().size();
      		LogoListBuilder list = new LogoListBuilder();
  			//list.add(numkeys);
			list.addAll( keys );
  			return list.toLogoList();
    }

  }
  
  public class SetUrl implements Command {
	
    public Syntax getSyntax() {
      return SyntaxJ.commandSyntax( new int[] {Syntax.StringType()} );
    }

    public void perform(Argument args[], Context context)
        throws ExtensionException {
			my_url = args[0].getString();
    }

  }
  
  
  public class Start implements Command {
	
    public Syntax getSyntax() {
      return SyntaxJ.commandSyntax( new int[] {});
    }

    public void perform(Argument args[], Context context)
        throws ExtensionException {
			restartListener();
    }

  }
  
  
}