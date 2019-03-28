package com.pracownia.vanet.net;

import java.util.*;

public interface VanetNode {

    String getId();
    Set<VanetNode> getConnectedNodes();

    default void connect(VanetNode other) {
        getConnectedNodes().add(other);
        other.getConnectedNodes().add(this);
    }

    default void disconnect(VanetNode other) {
        this.getConnectedNodes().remove(other);
        other.getConnectedNodes().remove(this);
    }
}
