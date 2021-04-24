package examples.grpcClient;

import io.grpc.stub.StreamObserver;
import service.CalcGrpc;
import service.CalcRequest;
import service.CalcResponse;
import java.util.ArrayList;
import java.util.List;

//ArrayList<Integer> numbers = new ArrayList<>();

public class CalcImpl extends CalcGrpc.CalcImplBase {

    public CalcImpl(){
        super();
    }

    @Override
    public void add(CalcRequest req, StreamObserver<CalcResponse> responseObserver){

        CalcResponse.Builder response = CalcResponse.newBuilder();
        double val = 0;
        for(int i = 0; i<req.getNumCount(); i++){
            val = val + req.getNum(i);
        }

        response.setIsSuccess(true);
        response.setError("Error");
        response.setSolution(val);

        CalcResponse respon = response.build();
        responseObserver.onNext(respon);
        responseObserver.onCompleted();
    }

    @Override
    public void subtract(CalcRequest req, StreamObserver<CalcResponse> responseObserver){
        
        CalcResponse.Builder response = CalcResponse.newBuilder();

        if(req.getNumCount() > 1){
            double val = req.getNum(0);
            for(int i = 1; i<req.getNumCount(); i++){
                val = val - req.getNum(i);
            }

            response.setIsSuccess(true);
            response.setError("Error");
            response.setSolution(val);
        }else{
            response.setIsSuccess(false);
            response.setError("Invalid Input value!");
            response.setSolution(0);
        }
        CalcResponse respon = response.build();
        responseObserver.onNext(respon);
        responseObserver.onCompleted();
    }

    @Override
    public void multiply(CalcRequest req, StreamObserver<CalcResponse> responseObserver){

        CalcResponse.Builder response = CalcResponse.newBuilder();
        double val = 1;
        for(int i = 0; i<req.getNumCount(); i++){
            val = val * req.getNum(i);
        }

        response.setIsSuccess(true);
        response.setError("Error");
        response.setSolution(val);

        CalcResponse respon = response.build();
        responseObserver.onNext(respon);
        responseObserver.onCompleted();
    }
    
    @Override
    public void divide(CalcRequest req, StreamObserver<CalcResponse> responseObserver){
        CalcResponse.Builder response = CalcResponse.newBuilder();
        
        if(req.getNumCount() > 1){
            double val = req.getNum(0);
            double denominator = 0;

            for(int i = 1; i<req.getNumCount(); i++){
                denominator = denominator + req.getNum(i);
            }

            if(denominator != 0){
                double divi = val/denominator;
                response.setIsSuccess(true);
                response.setSolution(divi);
                response.setError("Error");
            }else{
                response.setIsSuccess(false);
                response.setSolution(0);
                response.setError("Unsupported value is in the denominator.");
            }
        }else{
            response.setIsSuccess(false);
            response.setSolution(0);
            response.setError("Two values must be entered");
        }

        CalcResponse respon = response.build();
        responseObserver.onNext(respon);
        responseObserver.onCompleted();
    }
	
	//Added this method below
	
	@Override
    public void modulus(CalcRequest req, StreamObserver<CalcResponse> responseObserver){

       CalcResponse.Builder response = CalcResponse.newBuilder();
        
        if(req.getNumCount() > 1){
            double val = req.getNum(0);
            double modu = 0;

            for(int i = 1; i<req.getNumCount(); i++){
                modu = modu + req.getNum(i);
            }

            if(modu != 0){
                double divi = val%modu;
                response.setIsSuccess(true);
                response.setSolution(divi);
                response.setError("Error");
            }else{
                response.setIsSuccess(false);
                response.setSolution(0);
                response.setError("Invalid Input value!");
            }
        }else{
            response.setIsSuccess(false);
            response.setSolution(0);
            response.setError("Two values must be entered");
        }

        CalcResponse respon = response.build();
        responseObserver.onNext(respon);
        responseObserver.onCompleted();
    }
}