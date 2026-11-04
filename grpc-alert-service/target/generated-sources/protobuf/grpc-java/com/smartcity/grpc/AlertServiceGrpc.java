package com.smartcity.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: alert.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AlertServiceGrpc {

  private AlertServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.smartcity.grpc.AlertService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.smartcity.grpc.AlertRequest,
      com.smartcity.grpc.AlertResponse> getSendAlertMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendAlert",
      requestType = com.smartcity.grpc.AlertRequest.class,
      responseType = com.smartcity.grpc.AlertResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.smartcity.grpc.AlertRequest,
      com.smartcity.grpc.AlertResponse> getSendAlertMethod() {
    io.grpc.MethodDescriptor<com.smartcity.grpc.AlertRequest, com.smartcity.grpc.AlertResponse> getSendAlertMethod;
    if ((getSendAlertMethod = AlertServiceGrpc.getSendAlertMethod) == null) {
      synchronized (AlertServiceGrpc.class) {
        if ((getSendAlertMethod = AlertServiceGrpc.getSendAlertMethod) == null) {
          AlertServiceGrpc.getSendAlertMethod = getSendAlertMethod =
              io.grpc.MethodDescriptor.<com.smartcity.grpc.AlertRequest, com.smartcity.grpc.AlertResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendAlert"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smartcity.grpc.AlertRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smartcity.grpc.AlertResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AlertServiceMethodDescriptorSupplier("sendAlert"))
              .build();
        }
      }
    }
    return getSendAlertMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AlertServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AlertServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AlertServiceStub>() {
        @java.lang.Override
        public AlertServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AlertServiceStub(channel, callOptions);
        }
      };
    return AlertServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AlertServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AlertServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AlertServiceBlockingStub>() {
        @java.lang.Override
        public AlertServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AlertServiceBlockingStub(channel, callOptions);
        }
      };
    return AlertServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AlertServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AlertServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AlertServiceFutureStub>() {
        @java.lang.Override
        public AlertServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AlertServiceFutureStub(channel, callOptions);
        }
      };
    return AlertServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void sendAlert(com.smartcity.grpc.AlertRequest request,
        io.grpc.stub.StreamObserver<com.smartcity.grpc.AlertResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendAlertMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AlertService.
   */
  public static abstract class AlertServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AlertServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AlertService.
   */
  public static final class AlertServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AlertServiceStub> {
    private AlertServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AlertServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AlertServiceStub(channel, callOptions);
    }

    /**
     */
    public void sendAlert(com.smartcity.grpc.AlertRequest request,
        io.grpc.stub.StreamObserver<com.smartcity.grpc.AlertResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendAlertMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AlertService.
   */
  public static final class AlertServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AlertServiceBlockingStub> {
    private AlertServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AlertServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AlertServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.smartcity.grpc.AlertResponse sendAlert(com.smartcity.grpc.AlertRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendAlertMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AlertService.
   */
  public static final class AlertServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AlertServiceFutureStub> {
    private AlertServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AlertServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AlertServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.smartcity.grpc.AlertResponse> sendAlert(
        com.smartcity.grpc.AlertRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendAlertMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_ALERT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_ALERT:
          serviceImpl.sendAlert((com.smartcity.grpc.AlertRequest) request,
              (io.grpc.stub.StreamObserver<com.smartcity.grpc.AlertResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSendAlertMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.smartcity.grpc.AlertRequest,
              com.smartcity.grpc.AlertResponse>(
                service, METHODID_SEND_ALERT)))
        .build();
  }

  private static abstract class AlertServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AlertServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.smartcity.grpc.AlertServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AlertService");
    }
  }

  private static final class AlertServiceFileDescriptorSupplier
      extends AlertServiceBaseDescriptorSupplier {
    AlertServiceFileDescriptorSupplier() {}
  }

  private static final class AlertServiceMethodDescriptorSupplier
      extends AlertServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AlertServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AlertServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AlertServiceFileDescriptorSupplier())
              .addMethod(getSendAlertMethod())
              .build();
        }
      }
    }
    return result;
  }
}
