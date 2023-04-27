/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos;


import java.beans.ConstructorProperties;
import java.io.Serializable;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long tgchatid;
    private Long trackedlink;

    public Chat() {}

    public Chat(Chat value) {
        this.id = value.id;
        this.tgchatid = value.tgchatid;
        this.trackedlink = value.trackedlink;
    }

    @ConstructorProperties({ "id", "tgchatid", "trackedlink" })
    public Chat(
        @NotNull Long id,
        @NotNull Long tgchatid,
        @Nullable Long trackedlink
    ) {
        this.id = id;
        this.tgchatid = tgchatid;
        this.trackedlink = trackedlink;
    }

    /**
     * Getter for <code>CHAT.ID</code>.
     */
    @NotNull
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>CHAT.ID</code>.
     */
    public void setId(@NotNull Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>CHAT.TGCHATID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getTgchatid() {
        return this.tgchatid;
    }

    /**
     * Setter for <code>CHAT.TGCHATID</code>.
     */
    public void setTgchatid(@NotNull Long tgchatid) {
        this.tgchatid = tgchatid;
    }

    /**
     * Getter for <code>CHAT.TRACKEDLINK</code>.
     */
    @Nullable
    public Long getTrackedlink() {
        return this.trackedlink;
    }

    /**
     * Setter for <code>CHAT.TRACKEDLINK</code>.
     */
    public void setTrackedlink(@Nullable Long trackedlink) {
        this.trackedlink = trackedlink;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Chat other = (Chat) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.tgchatid == null) {
            if (other.tgchatid != null)
                return false;
        }
        else if (!this.tgchatid.equals(other.tgchatid))
            return false;
        if (this.trackedlink == null) {
            if (other.trackedlink != null)
                return false;
        }
        else if (!this.trackedlink.equals(other.trackedlink))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.tgchatid == null) ? 0 : this.tgchatid.hashCode());
        result = prime * result + ((this.trackedlink == null) ? 0 : this.trackedlink.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Chat (");

        sb.append(id);
        sb.append(", ").append(tgchatid);
        sb.append(", ").append(trackedlink);

        sb.append(")");
        return sb.toString();
    }
}
