// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/sort.proto

package service;

public final class SortProto {
  private SortProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_SortRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_SortRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_SortResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_SortResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023services/sort.proto\022\010services\"=\n\013SortR" +
      "equest\022\034\n\004algo\030\001 \001(\0162\016.services.Algo\022\020\n\004" +
      "data\030\002 \003(\005B\002\020\001\"B\n\014SortResponse\022\021\n\tisSucc" +
      "ess\030\001 \001(\010\022\020\n\004data\030\002 \003(\005B\002\020\001\022\r\n\005error\030\003 \001" +
      "(\t*(\n\004Algo\022\t\n\005MERGE\020\000\022\t\n\005QUICK\020\001\022\n\n\006INTE" +
      "RN\020\0022?\n\004Sort\0227\n\004sort\022\025.services.SortRequ" +
      "est\032\026.services.SortResponse\"\000B\026\n\007service" +
      "B\tSortProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_services_SortRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_services_SortRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_SortRequest_descriptor,
        new java.lang.String[] { "Algo", "Data", });
    internal_static_services_SortResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_services_SortResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_SortResponse_descriptor,
        new java.lang.String[] { "IsSuccess", "Data", "Error", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
