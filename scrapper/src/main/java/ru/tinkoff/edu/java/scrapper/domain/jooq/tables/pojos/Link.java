/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String url;
    private LocalDateTime lastUpdated;
    private LocalDateTime lastChecked;
    private LocalDateTime lastCheckedWhenWasUpdated;
    private LocalDateTime lastCommitDate;
    private LocalDateTime lastAnswerDate;

    public Link() {}

    public Link(Link value) {
        this.id = value.id;
        this.url = value.url;
        this.lastUpdated = value.lastUpdated;
        this.lastChecked = value.lastChecked;
        this.lastCheckedWhenWasUpdated = value.lastCheckedWhenWasUpdated;
        this.lastCommitDate = value.lastCommitDate;
        this.lastAnswerDate = value.lastAnswerDate;
    }

    @ConstructorProperties({ "id", "url", "lastUpdated", "lastChecked", "lastCheckedWhenWasUpdated", "lastCommitDate", "lastAnswerDate" })
    public Link(
        @NotNull Long id,
        @NotNull String url,
        @Nullable LocalDateTime lastUpdated,
        @Nullable LocalDateTime lastChecked,
        @Nullable LocalDateTime lastCheckedWhenWasUpdated,
        @Nullable LocalDateTime lastCommitDate,
        @Nullable LocalDateTime lastAnswerDate
    ) {
        this.id = id;
        this.url = url;
        this.lastUpdated = lastUpdated;
        this.lastChecked = lastChecked;
        this.lastCheckedWhenWasUpdated = lastCheckedWhenWasUpdated;
        this.lastCommitDate = lastCommitDate;
        this.lastAnswerDate = lastAnswerDate;
    }

    /**
     * Getter for <code>LINK.ID</code>.
     */
    @NotNull
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINK.ID</code>.
     */
    public void setId(@NotNull Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINK.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 255)
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>LINK.URL</code>.
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Getter for <code>LINK.LAST_UPDATED</code>.
     */
    @Nullable
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    /**
     * Setter for <code>LINK.LAST_UPDATED</code>.
     */
    public void setLastUpdated(@Nullable LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Getter for <code>LINK.LAST_CHECKED</code>.
     */
    @Nullable
    public LocalDateTime getLastChecked() {
        return this.lastChecked;
    }

    /**
     * Setter for <code>LINK.LAST_CHECKED</code>.
     */
    public void setLastChecked(@Nullable LocalDateTime lastChecked) {
        this.lastChecked = lastChecked;
    }

    /**
     * Getter for <code>LINK.LAST_CHECKED_WHEN_WAS_UPDATED</code>.
     */
    @Nullable
    public LocalDateTime getLastCheckedWhenWasUpdated() {
        return this.lastCheckedWhenWasUpdated;
    }

    /**
     * Setter for <code>LINK.LAST_CHECKED_WHEN_WAS_UPDATED</code>.
     */
    public void setLastCheckedWhenWasUpdated(@Nullable LocalDateTime lastCheckedWhenWasUpdated) {
        this.lastCheckedWhenWasUpdated = lastCheckedWhenWasUpdated;
    }

    /**
     * Getter for <code>LINK.LAST_COMMIT_DATE</code>.
     */
    @Nullable
    public LocalDateTime getLastCommitDate() {
        return this.lastCommitDate;
    }

    /**
     * Setter for <code>LINK.LAST_COMMIT_DATE</code>.
     */
    public void setLastCommitDate(@Nullable LocalDateTime lastCommitDate) {
        this.lastCommitDate = lastCommitDate;
    }

    /**
     * Getter for <code>LINK.LAST_ANSWER_DATE</code>.
     */
    @Nullable
    public LocalDateTime getLastAnswerDate() {
        return this.lastAnswerDate;
    }

    /**
     * Setter for <code>LINK.LAST_ANSWER_DATE</code>.
     */
    public void setLastAnswerDate(@Nullable LocalDateTime lastAnswerDate) {
        this.lastAnswerDate = lastAnswerDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Link other = (Link) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.url == null) {
            if (other.url != null)
                return false;
        }
        else if (!this.url.equals(other.url))
            return false;
        if (this.lastUpdated == null) {
            if (other.lastUpdated != null)
                return false;
        }
        else if (!this.lastUpdated.equals(other.lastUpdated))
            return false;
        if (this.lastChecked == null) {
            if (other.lastChecked != null)
                return false;
        }
        else if (!this.lastChecked.equals(other.lastChecked))
            return false;
        if (this.lastCheckedWhenWasUpdated == null) {
            if (other.lastCheckedWhenWasUpdated != null)
                return false;
        }
        else if (!this.lastCheckedWhenWasUpdated.equals(other.lastCheckedWhenWasUpdated))
            return false;
        if (this.lastCommitDate == null) {
            if (other.lastCommitDate != null)
                return false;
        }
        else if (!this.lastCommitDate.equals(other.lastCommitDate))
            return false;
        if (this.lastAnswerDate == null) {
            if (other.lastAnswerDate != null)
                return false;
        }
        else if (!this.lastAnswerDate.equals(other.lastAnswerDate))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.lastUpdated == null) ? 0 : this.lastUpdated.hashCode());
        result = prime * result + ((this.lastChecked == null) ? 0 : this.lastChecked.hashCode());
        result = prime * result + ((this.lastCheckedWhenWasUpdated == null) ? 0 : this.lastCheckedWhenWasUpdated.hashCode());
        result = prime * result + ((this.lastCommitDate == null) ? 0 : this.lastCommitDate.hashCode());
        result = prime * result + ((this.lastAnswerDate == null) ? 0 : this.lastAnswerDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Link (");

        sb.append(id);
        sb.append(", ").append(url);
        sb.append(", ").append(lastUpdated);
        sb.append(", ").append(lastChecked);
        sb.append(", ").append(lastCheckedWhenWasUpdated);
        sb.append(", ").append(lastCommitDate);
        sb.append(", ").append(lastAnswerDate);

        sb.append(")");
        return sb.toString();
    }
}
