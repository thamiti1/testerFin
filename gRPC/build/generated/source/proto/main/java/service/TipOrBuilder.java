// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/tips.proto

package service;

public interface TipOrBuilder extends
    // @@protoc_insertion_point(interface_extends:services.Tip)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * name of the person giving the tip
   * </pre>
   *
   * <code>string name = 1;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <pre>
   * name of the person giving the tip
   * </pre>
   *
   * <code>string name = 1;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   * the tip -- should be an array
   * </pre>
   *
   * <code>string tip = 2;</code>
   * @return The tip.
   */
  java.lang.String getTip();
  /**
   * <pre>
   * the tip -- should be an array
   * </pre>
   *
   * <code>string tip = 2;</code>
   * @return The bytes for tip.
   */
  com.google.protobuf.ByteString
      getTipBytes();
}
