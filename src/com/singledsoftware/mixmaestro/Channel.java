// Copyright 2013 Judson D Neer

package com.singledsoftware.mixmaestro;

import java.io.Serializable;

/**
 * Stores data for an individual channel.
 *
 * @see Serializable
 * @author Judson D Neer
 */
public class Channel implements Serializable {

    // Unique serializable version ID
    private static final long serialVersionUID = -3705403776567370897L;

    // Unique identifier for the channel
    private final String id;

    // Single character that identifies a channel type
    // TODO make this an enumeration
    private final char type;

    // User-assigned name for the channel
    private final String name;

    /**
     * Constructor.
     *
     * @param i New id
     * @param t New type
     * @param n New name
     */
    public Channel(String i, char t, String n) {
        id = i;
        type = t;
        name = n;
    }

    /**
     * @see java.io.Serializable#toString()
     */
    @Override
    public String toString() {
        // TODO put id and type in parens?
        return name;
    }

}
