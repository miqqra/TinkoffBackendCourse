/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq;


import javax.annotation.processing.Generated;

import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;


/**
 * Convenience access to all sequences in the default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>CHAT_SEQUENCE</code>
     */
    public static final Sequence<Long> CHAT_SEQUENCE = Internal.createSequence("CHAT_SEQUENCE", DefaultSchema.DEFAULT_SCHEMA, SQLDataType.BIGINT, null, null, null, null, false, null);

    /**
     * The sequence <code>LINKS_SEQUENCE</code>
     */
    public static final Sequence<Long> LINKS_SEQUENCE = Internal.createSequence("LINKS_SEQUENCE", DefaultSchema.DEFAULT_SCHEMA, SQLDataType.BIGINT, null, null, null, null, false, null);
}
