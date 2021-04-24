package examples.grpcClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import service.*;

import test.TestProtobuf;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Client that requests `parrot` method from the `EchoServer`.
 */
public class EchoClient {
  private final EchoGrpc.EchoBlockingStub blockingStub;
  private final JokeGrpc.JokeBlockingStub blockingStub2;
  private final RegistryGrpc.RegistryBlockingStub blockingStub3;
  private final CalcGrpc.CalcBlockingStub blockingStub4;
  private final StoryGrpc.StoryBlockingStub blockingStub5;

  /** Construct client for accessing server using the existing channel. */
  public EchoClient(Channel channel, Channel regChannel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    blockingStub3 = RegistryGrpc.newBlockingStub(regChannel);
    blockingStub4 = CalcGrpc.newBlockingStub(channel);
    blockingStub5 = StoryGrpc.newBlockingStub(channel);
  }

  public void askServerToParrot(String message) {
    ClientRequest request = ClientRequest.newBuilder().setMessage(message).build();
    ServerResponse response;
    try {
      response = blockingStub.parrot(request);
    } catch (Exception e) {
      System.err.println("\nRPC failed: " + e.getMessage());
      return;
    }
    System.out.println("\nReceived from server: " + response.getMessage());
  }

  public void askForJokes(int num) {
    JokeReq request = JokeReq.newBuilder().setNumber(num).build();
    JokeRes response;

    try {
      response = blockingStub2.getJoke(request);
    } catch (Exception e) {
      System.err.println("\nRPC failed: " + e);
      return;
    }
    System.out.println("\nYour jokes: ");
    for (String joke : response.getJokeList()) {
      System.out.println("--- " + joke);
    }
  }

  public void setJoke(String joke) {
    JokeSetReq request = JokeSetReq.newBuilder().setJoke(joke).build();
    JokeSetRes response;

    try {
      response = blockingStub2.setJoke(request);
      if(response.getOk()){
        System.out.println("\nJoke has been added");
      }else{
        System.out.println("\nJoke was not added");
      }
    } catch (Exception e) {
      System.err.println("\nRPC failed: " + e);
      return;
    }
  }

  public void askForStory() {
    Empty em = Empty.newBuilder().build();
    ReadResponse response;

    try {
      response = blockingStub5.read(em);
    } catch (Exception e) {
      System.err.println("\nRPC failed: " + e);
      return;
    }
    if(response.getIsSuccess()) {
      System.out.println("\nThe Story\n");
      System.out.println(response.getSentence());
    }else{
      System.out.println(response.getError());
    }
  }

  public void writeStory(String lString){
    WriteRequest writeRequest = WriteRequest.newBuilder().setNewSentence(lString).build();
    WriteResponse response;

    try{
      response = blockingStub5.write(writeRequest);
    }catch(Exception e){
      System.err.println("\nRPC Failed: " + e);
      return;
    }
    if(response.getIsSuccess()){
      System.out.println("\nLine added: " + lString);
      System.out.println("\nThe Story\n");
      System.out.println(response.getError());
    }
  }

  public void add(String list){
    CalcRequest request = establishCalcRequest(list);
    CalcResponse response;

    try{
      response = blockingStub4.add(request);
    }catch(Exception e){
      System.err.println("\nRPC failed: " + e);
      return;
    }

    if(response.getIsSuccess()){
      System.out.println("\nAnswer to addition: " + response.getSolution());
    }
    else{
      System.out.println(response.getError());
    }
  }

  public void subtract(String list){
    CalcRequest request = establishCalcRequest(list);
    CalcResponse response;

    try{
      response = blockingStub4.subtract(request);
    }catch(Exception e){
      System.err.println("\nRPC failed: " + e);
      return;
    }

    if(response.getIsSuccess()){
      System.out.println("\nAnswer to subtraction: " + response.getSolution());
    }
    else{
      System.out.println(response.getError());
    }
  }

  public void multiply(String list){
    CalcRequest request = establishCalcRequest(list);
    CalcResponse response;

    try{
      response = blockingStub4.multiply(request);
    }catch(Exception e){
      System.err.println("\nRPC failed: " + e);
      return;
    }

    if(response.getIsSuccess()){
      System.out.println("\nAnswer to multiplication: " + response.getSolution());
    }
    else{
      System.out.println(response.getError());
    }
  }

  public void divide(String list){
    CalcRequest request = establishCalcRequest(list);
    CalcResponse response;

    try{
      response = blockingStub4.divide(request);
    }catch(Exception e){
      System.err.println("\nRPC failed: " + e);
      return;
    }

    if(response.getIsSuccess()){
      System.out.println("\nAnswer to division: " + response.getSolution());
    }
    else{
      System.out.println(response.getError());
    }
  }
  
  public void modulus(String list){
    CalcRequest request = establishCalcRequest(list);
    CalcResponse response;

    try{
      response = blockingStub4.modulus(request);
    }catch(Exception e){
      System.err.println("\nRPC failed: " + e);
      return;
    }

    if(response.getIsSuccess()){
      System.out.println("\nAnswer to modulus: " + response.getSolution());
    }
    else{
      System.out.println(response.getError());
    }
  }

  public void getServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub3.getServices(request);
      for(int i=0;i<response.getServicesCount();i++){
        System.out.println(response.getServices(i));
      }
    } catch (Exception e) {
      System.err.println("\nRPC failed: " + e);
      return;
    }
  }
  
  private CalcRequest establishCalcRequest(String eString){
    CalcRequest.Builder requestB = CalcRequest.newBuilder();

    for(String str: eString.split(" ")){
      try{
        requestB.addNum(Double.parseDouble(str));
      }catch(NumberFormatException nfe){
        System.out.println("\nInvalid input!");
      }
    }
    return requestB.build();
  }

  public void findServer(String name) {
    FindServerReq request = FindServerReq.newBuilder().setServiceName(name).build();
    SingleServerRes response;
    try {
      response = blockingStub3.findServer(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("\nRPC failed: " + e);
      return;
    }
  }

  public void findServers(String name) {
    FindServersReq request = FindServersReq.newBuilder().setServiceName(name).build();
    ServerListRes response;
    try {
      response = blockingStub3.findServers(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("\nRPC failed: " + e);
      return;
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 6) {
      System.out
          .println("Expected arguments: <host(String)> <port(int)> <regHost(string)> <regPort(int)> <message(String)>");
      System.exit(1);
    }
    int port = 9099;
    int regPort = 9003;
    String host = args[0];
    String regHost = args[2];
    String message = args[4];
    int x = 0;
    try{
      x = Integer.valueOf(args[5]);
    }catch(NumberFormatException nfe){
      System.out.println("Input Must be an Integer!");
      System.exit(2);
    }
    try {
      port = Integer.parseInt(args[1]);
      regPort = Integer.parseInt(args[3]);
    } catch (NumberFormatException nfe) {
      System.out.println("[Port] must be an integer");
      System.exit(2);
    }

    // Create a communication channel to the server, known as a Channel. Channels
    // are thread-safe
    // and reusable. It is common to create channels at the beginning of your
    // application and reuse
    // them until the application shuts down.
    String target = host + ":" + port;
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS
        // to avoid
        // needing certificates.
        .usePlaintext().build();

    String regTarget = regHost + ":" + regPort;
    ManagedChannel regChannel = ManagedChannelBuilder.forTarget(regTarget).usePlaintext().build();
    try {

      // ##############################################################################
      // ## Assume we know the port here from the service node it is basically set through Gradle
      // here.
      // In your version you should first contact the registry to check which services
      // are available and what the port
      // etc is.

      /**
       * Your client should start off with 
       * 1. contacting the Registry to check for the available services
       * 2. List the services in the terminal and the client can
       *    choose one (preferably through numbering) 
       * 3. Based on what the client chooses
       *    the terminal should ask for input, eg. a new sentence, a sorting array or
       *    whatever the request needs 
       * 4. The request should be sent to one of the
       *    available services (client should call the registry again and ask for a
       *    Server providing the chosen service) should send the request to this service and
       *    return the response in a good way to the client
       * 
       * You should make sure your client does not crash in case the service node
       * crashes or went offline.
       */

      // Just doing some hard coded calls to the service node without using the
      // registry
      // create client
      EchoClient client = new EchoClient(channel, regChannel);

      // call the parrot service on the server
      client.askServerToParrot(message);

      // ask the user for input how many jokes the user wants
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      System.out.println(" ");
      System.out.println("Available services below:");
      System.out.println("\n[1] add \n[2] subtract \n[3] multiply \n[4] divide \n[5] read \n[6] write \n[7] jokes \n[8] addjoke \n[9] broken-record\n[10] modulus");
      System.out.println(" ");
      while (true){
		System.out.println(" ");
        System.out.println("Select an Option: ");
		System.out.print("Type exit to exit ");
        String cString = reader.readLine();
        if(cString.equalsIgnoreCase("exit")){
          System.out.println("Exiting...");
          break;
        }
        switch(cString.toLowerCase()){
          case "1":
            System.out.print("Input a String of Numbers (seperated by a space): \n\n");
            String addNumbers = reader.readLine();
            client.add(addNumbers);
            break;
          case "2":
            System.out.print("Input a String of Numbers (seperated by a space): \n\n");
            String subNumbers = reader.readLine();
            client.subtract(subNumbers);
            break;
          case "3":
            System.out.print("Input a String of Numbers (seperated by a space): \n\n");
            String multNumbers = reader.readLine();
            client.multiply(multNumbers);
            break;
          case "4":
            System.out.print("Input a String of Numbers (seperated by a space): \n\n");
            String divNumbers = reader.readLine();
            client.divide(divNumbers);
            break;
          case "5":
            client.askForStory();
            break;
          case "6":
            System.out.println("\n\nInput a sentence to add to the story");
            System.out.print("Sentence: \n\n");
            String newLine = reader.readLine();
            client.writeStory(newLine);
            break;
          case "7":
            System.out.println("\n\nHow many jokes would you like to hear?");
            System.out.print("Number: \n\n");
            try {
              int num = Integer.valueOf(reader.readLine());
              client.askForJokes(num);
            }catch(NumberFormatException e){
              System.out.println("\n\nIncorrect Number given!");
            }
            break;
          case "8":
            System.out.println("\n\nType a joke to add!");
            System.out.print("Joke: \n\n");
            String newJoke = reader.readLine();
            client.setJoke(newJoke);
            break;
          case "9":
            System.out.println("\n\nType a Sentence to have returned here");
            System.out.print("Sentence: \n\n");
            String parrot = reader.readLine();
            client.askServerToParrot(parrot);
            break;
		case "10":
            System.out.println("\n\nTwo numbers (seperated by a space) to perform modulus operator");
            System.out.print("Sentence: \n\n");
            String modu = reader.readLine();
            client.modulus(modu);
            break;
          default:
            System.out.println("\n\nInvalid service value!");
        }
      }
      /* 
       System.out.println("How many jokes would you like?"); // NO ERROR handling of wrong input here.
      String num = reader.readLine();

      // calling the joked service from the server with num from user input
      client.askForJokes(Integer.valueOf(num));

      // adding a joke to the server
      client.setJoke("I made a pencil with two erasers. It was pointless.");

      // showing 6 joked
      client.askForJokes(Integer.valueOf(6));

      // ############### Contacting the registry just so you see how it can be done

      // Comment these last Service calls while in Activity 1 Task 1, they are not needed and wil throw issues without the Registry running
      // get thread's services
      client.getServices();

      // get parrot
      client.findServer("services.Echo/parrot");
      
      // get all setJoke
      client.findServers("services.Joke/setJoke");

      // get getJoke
      client.findServer("services.Joke/getJoke");

      // does not exist
      client.findServer("random");
	  */


    } finally {
      // ManagedChannels use resources like threads and TCP connections. To prevent
      // leaking these
      // resources the channel should be shut down when it will no longer be used. If
      // it may be used
      // again leave it running.
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
      regChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}