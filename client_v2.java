

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Scanner;

import com.sun.tools.javac.main.Main.Result;

import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.feature.profile.ClientCoreEventHandler;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.ChangeConfigurationRequest;
import eu.chargetime.ocpp.model.core.ChangeAvailabilityConfirmation;
import eu.chargetime.ocpp.model.core.AuthorizeConfirmation;
import eu.chargetime.ocpp.model.core.AvailabilityStatus;
import eu.chargetime.ocpp.model.core.BootNotificationConfirmation;
import eu.chargetime.ocpp.model.core.ClearCacheRequest;
import eu.chargetime.ocpp.model.core.RemoteStartTransactionRequest;
import eu.chargetime.ocpp.model.core.UnlockConnectorRequest;
import eu.chargetime.ocpp.model.core.ChangeAvailabilityRequest;
import eu.chargetime.ocpp.model.core.GetConfigurationRequest;
import eu.chargetime.ocpp.model.core.MeterValuesConfirmation;
import eu.chargetime.ocpp.model.core.ChangeConfigurationConfirmation;
import eu.chargetime.ocpp.model.core.ClearCacheConfirmation;
import eu.chargetime.ocpp.model.core.DataTransferConfirmation;
import eu.chargetime.ocpp.model.core.UnlockConnectorConfirmation;
import eu.chargetime.ocpp.model.core.GetConfigurationConfirmation;
import eu.chargetime.ocpp.model.core.RemoteStartTransactionConfirmation;
import eu.chargetime.ocpp.model.core.RemoteStopTransactionConfirmation;
import eu.chargetime.ocpp.model.core.RemoteStopTransactionRequest;
import eu.chargetime.ocpp.model.core.ResetConfirmation;
import eu.chargetime.ocpp.model.core.DataTransferRequest;
import eu.chargetime.ocpp.model.core.ResetRequest;
import eu.chargetime.ocpp.model.core.SampledValue;
import eu.chargetime.ocpp.model.core.StartTransactionConfirmation;
import eu.chargetime.ocpp.model.core.StopTransactionConfirmation;



public class client_v2 {
	 
	 public static void main(String[] args) {

	  ClientCoreProfile core = new ClientCoreProfile(new ClientCoreEventHandler() {
	         @Override
	         public ChangeAvailabilityConfirmation handleChangeAvailabilityRequest(ChangeAvailabilityRequest request) {

	             System.out.println(request);
	             // ... handle event

	             return new ChangeAvailabilityConfirmation(AvailabilityStatus.Accepted);
	         }

	         @Override
	         public GetConfigurationConfirmation handleGetConfigurationRequest(GetConfigurationRequest request) {

	             System.out.println(request);
	             // ... handle event

	             return null; // returning null means unsupported feature
	         }

	         @Override
	         public ChangeConfigurationConfirmation handleChangeConfigurationRequest(ChangeConfigurationRequest request) {

	             System.out.println(request);
	             // ... handle event

	             return null; // returning null means unsupported feature
	         }

	         @Override
	         public ClearCacheConfirmation handleClearCacheRequest(ClearCacheRequest request) {

	             System.out.println(request);
	             // ... handle event

	             return null; // returning null means unsupported feature
	         }

	         @Override
	         public DataTransferConfirmation handleDataTransferRequest(DataTransferRequest request) {

	             System.out.println(request);
	             // ... handle event

	             return null; // returning null means unsupported feature
	         }

	         @Override
	         public RemoteStartTransactionConfirmation handleRemoteStartTransactionRequest(RemoteStartTransactionRequest request) {

	             System.out.println(request);
	             // ... handle event

	             return null; // returning null means unsupported feature
	         }

	         @Override
	         public RemoteStopTransactionConfirmation handleRemoteStopTransactionRequest(RemoteStopTransactionRequest request) {

	             System.out.println(request);
	             // ... handle event

	             return null; // returning null means unsupported feature
	         }

	         @Override
	         public ResetConfirmation handleResetRequest(ResetRequest request) {

	             System.out.println(request);
	             // ... handle event

	             return null; // returning null means unsupported feature
	         }

	         @Override
	         public UnlockConnectorConfirmation handleUnlockConnectorRequest(UnlockConnectorRequest request) {

	             System.out.println(request);
	             // ... handle event

	             return null; // returning null means unsupported feature
	         }
	     });

	  	 // create a client
   	     JSONClient client = new JSONClient(core, "chargeboxIdentity");
   	     
	     // connect with server
	  	 try {	
		    client.connect("ws://localhost:8774", null);
		    System.out.println("Connection succeeded!");
	  	 } catch (Exception e ) {
	  		System.out.println("Connection failed!");
	  	 }
	     
	  	 // create a scanner 
	  	 Scanner sc = new Scanner(System.in); 
	     while(true) {
	    	 System.out.println("# 1 - Send a Boot Notification request #");
	    	 System.out.println("# 2 - Send a Authorization request #");
	    	 System.out.println("# 3 - Send a Start transaction request #");
	    	 System.out.println("# 4 - Send a Meter value Request #");
	    	 System.out.println("# 5 - Send a Stop transaction request #");
	    	 String input = sc.next();
	    	 
	    	 // Boot Notification (client sends a request to server, server sends a confirmation to client)
	    	 if(input.equals("1")) {  		 
	    		// reference: https://github.com/ChargeTimeEU/Java-OCA-OCPP/wiki/Setting-up-v1.6-OCPP-J-client
	    		// Use the feature profile to help create event
	    		    Request request = core.createBootNotificationRequest("some vendor", "some model");
 
    		    // Client returns a promise which will be filled once it receives a confirmation.
    		    try {    	
					client.send(request).whenComplete((res, exception) -> {
						BootNotificationConfirmation confirm = (BootNotificationConfirmation) res; // change type to BootNotificationConfirmation
						System.out.println("confirm: "+confirm); 
						if (confirm.getStatus().toString().equals("Accepted") ) {
							System.out.println("Boot Notification Confirmation succeeded!");
						}
						else {
							System.out.println("Boot Notification Confirmation failed!");
						}
					});
				} catch (OccurenceConstraintException | UnsupportedFeatureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	 }
	    	 
	    	 // Authorization
	    	 else if(input.equals("2"))  {
	    		 String idTag = "111"; // initialize idTag (just create a fake id tag)
	    		 Request request = core.createAuthorizeRequest(idTag); // create a Authorize Request with id tag
	    		 
	    		 // client sends authorize request to server and will get a confirmation from server.
	    		 try {    	
						client.send(request).whenComplete((res, exception) -> {
							AuthorizeConfirmation confirm = (AuthorizeConfirmation) res; // change type to BootNotificationConfirmation
							System.out.println("confirm: "+confirm); 						
							if (confirm.getIdTagInfo().getStatus().toString().equals("Accepted") ) {
								System.out.println("Authorization succeeded!");
							}
							else {
								System.out.println("Authorization failed!");
							}
						});
					} catch (OccurenceConstraintException | UnsupportedFeatureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		 
	    	 }
	    	 
	    	 // start transaction
	    	 else if(input.equals("3")) {
	    		 
	    		 // just create a fake data
	    		 Integer connectorId = 50;
	    		 String idTag = "111"; // initialize idTag (just create a fake id tag)
	    		 Integer meterStart = 20;
	    		 ZonedDateTime timestamp = ZonedDateTime.now(); 
	    		 
	    		 Request request = core.createStartTransactionRequest(connectorId, idTag, meterStart, timestamp);
	    		 
	    		// client sends a Start Transaction Request to server and will get a confirmation from server.
	    		 try {    	
						client.send(request).whenComplete((res, exception) -> {
							StartTransactionConfirmation confirm = (StartTransactionConfirmation) res; // change type to BootNotificationConfirmation
							System.out.println("confirm: "+confirm); 						
							if(confirm.validate())
								System.out.println("Start Transaction succeeded!");
							else
								System.out.println("Start Transaction failed!");
							
						});
					} catch (OccurenceConstraintException | UnsupportedFeatureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		 
	    	 }
	    	 
	    	 
	    	 // send request
	    	 else if(input.equals("4")) {
	    		 Integer connectorId = 50;
	    		 ZonedDateTime timestamp = ZonedDateTime.now();
	    		 String value = "33";
	    		// Request request = core.createMeterValuesRequest(connectorId, timestamp, value);
//	    		 Client returns a promise which will be filled once it receives a confirmation.
	    		 // Enter data using BufferReader
	    		 try {
	    			 System.out.println("please enter a value: ");
	    			 BufferedReader reader = new BufferedReader(
	    	            new InputStreamReader(System.in));
	    	 
	    			 // Reading data using readLine
	    			 value = reader.readLine();
	    	 
	    			 // Printing the read line
	    			 System.out.println(value);
	    			
	    		 } catch(IOException e) {e.printStackTrace();}
	    		 Request request = core.createMeterValuesRequest(connectorId, timestamp, value);
	    	     try {
	    	    	// send a request to server and server will return a confirmation. 	    	    	
	    			client.send(request).whenComplete((res, exception) -> {
	    				MeterValuesConfirmation confirm = (MeterValuesConfirmation) res;
	    				System.out.println("confirm: "+confirm); 
	    				if(confirm.validate()) // isValid = true
	    					System.out.println("Send Meter Value succeeded!");
	    				else 
	    					System.out.println("Send Meter Value failed!");		    			
	    			});
	    		} catch (OccurenceConstraintException | UnsupportedFeatureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	 }
	    	 
	    	 // stop transaction
	    	 else if(input.equals("5")) {
	    		 Integer meterStop = 50; // This contains the meter value in Wh for the connector at end of the transaction. (p81 from ocpp-1.6.pdf)
	    		 ZonedDateTime timestamp = ZonedDateTime.now();
	    		 
	    		 // just set the value to 1 for now (change later), because: see the definition of "transactionId" from p81 from ocpp-1.6.pdf.
	    		 Integer transactionId = 1; // This contains the transaction-id as received by the "StartTransaction.conf". (p81 from ocpp-1.6.pdf)
	    		 
	    		 Request request = core.createStopTransactionRequest(meterStop, timestamp, transactionId);
	    		 
	    		// client sends a Stop Transaction Request to server and will get a confirmation from server.
	    		 try {    	
						client.send(request).whenComplete((res, exception) -> {
							StopTransactionConfirmation confirm = (StopTransactionConfirmation) res; // change type to BootNotificationConfirmation
							System.out.println("confirm: "+confirm); 						
							if(confirm.validate())
								System.out.println("Stop Transaction succeeded!");
							else
								System.out.println("Stop Transaction failed!");
						});
					} catch (OccurenceConstraintException | UnsupportedFeatureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		 
	    	 }

	     }
	     	     
	 }

}