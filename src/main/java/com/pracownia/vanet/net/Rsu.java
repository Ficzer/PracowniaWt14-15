package com.pracownia.vanet.net;

import com.pracownia.vanet.Point;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.*;

public class Rsu implements VanetNode {

    @Getter private String id = UUID.randomUUID().toString();
    @Getter private Set<VanetNode> connectedNodes = new HashSet<>();
    @Getter private Point coordinates;

    public Rsu(Point coordinates) {
        this.coordinates = coordinates;
    }

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
        } else if (!(obj instanceof Rsu)) {
            return false;
        } else {
            return this.id.equals(((Rsu) obj).getId());
        }
    }
}
