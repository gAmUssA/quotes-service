package io.kong.developer.servicereactive.grpc;

import com.google.protobuf.Empty;

import com.github.javafaker.Faker;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import io.grpc.stub.StreamObserver;
import io.kong.developer.quoteservice.QuoteMessage;
import io.kong.developer.quoteservice.QuoteServiceGrpc;
import lombok.RequiredArgsConstructor;

@GRpcService
@RequiredArgsConstructor
public class DuneRpcService extends QuoteServiceGrpc.QuoteServiceImplBase {
  
  @Override
  public void getQuote(final Empty request,
                       final StreamObserver<QuoteMessage> responseObserver) {

    final QuoteMessage message = QuoteMessage.newBuilder().setMessage(Faker.instance().dune().quote()).build();
    responseObserver.onNext(message);
    responseObserver.onCompleted();
  }
}
