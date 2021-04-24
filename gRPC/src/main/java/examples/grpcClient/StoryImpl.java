package examples.grpcClient;

import java.util.ArrayList;
import java.util.List;

import io.grpc.stub.StreamObserver;
import service.*;

public class StoryImpl extends StoryGrpc.StoryImplBase {

    List<String> ls = new ArrayList<String>();

    public StoryImpl(){
        super();
        ls.add("One day, during SER321 everything was going normally, when suddenly...");
    }

    private boolean addSentence(String strB){
        return ls.add(strB);
    }

    private String returnStory(){
        String resp = "";
        for(String str: ls){
            resp = resp + " " + str;
        }

        return resp;
    }

    public synchronized void read(Empty empty, StreamObserver<ReadResponse> responseObserver){
        ReadResponse.Builder resp = ReadResponse.newBuilder();
        String str = returnStory();
        if( str != null && str.length() > 0){
            resp.setIsSuccess(true);
            resp.setSentence(str);
            resp.setError("Null");
        }else{
            resp.setIsSuccess(false);
            resp.setSentence("");
            resp.setError("Nothing added");
        }

        ReadResponse respRead = resp.build();
        responseObserver.onNext(respRead);
        responseObserver.onCompleted();
    }

    public synchronized void write(WriteRequest req, StreamObserver<WriteResponse> responsObserver){
        WriteResponse.Builder resp = WriteResponse.newBuilder();
        String strR = req.getNewSentence();

        if( strR.length() > 0 && strR != null ){
            boolean b = addSentence(strR);
            if(b){
                resp.setIsSuccess(true);
                resp.setStory(returnStory());
                resp.setError("Null");
            }else{
                resp.setIsSuccess(false);
                resp.setStory(returnStory());
                resp.setError("Nothing was written to story");
            }
        }else{
            resp.setIsSuccess(false);
            resp.setStory(returnStory());
            resp.setError("Error in adding written message");
        }
        WriteResponse respWrite = resp.build();
        responsObserver.onNext(respWrite);
        responsObserver.onCompleted();
    }
}