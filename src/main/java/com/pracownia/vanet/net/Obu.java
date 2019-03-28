package com.pracownia.vanet.net;

import java.text.MessageFormat;
import java.util.*;

import lombok.Data;

@Data
public class Obu implements VanetNode {

    private String id = UUID.randomUUID().toString();
    private Set<VanetNode> connectedNodes = new HashSet<>();


    @Override
    public String toString(){
        return MessageFormat.format("Id: {0}", id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        } else if (!(obj instanceof Obu)) {
            return false;
        } else {
            return this.id.equals(((Obu) obj).getId());
        }
    }
}
