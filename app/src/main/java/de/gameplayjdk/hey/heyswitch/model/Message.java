/*
 * Hey Android App
 * Copyright (C) 2018  GameplayJDK
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.gameplayjdk.hey.heyswitch.model;

/**
 * Created by GameplayJDK on 05.01.2018.
 */

public class Message {

    /**
     * Type of message. This is hardcoded per array resource.
     */
    public enum Mood {
        NONE, GOOD, BAD, SURPRISED;
    }

    private final int id;

    private String text;
    private Mood mood;

    public Message(int id, String text, Mood mood) {
        this.id = id;
        this.text = text;
        this.mood = mood;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    /**
     * Generated {@code equals()} method.
     *
     * @see Object#equals(Object)
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Message message = (Message) obj;

        return message.getId() == this.getId();
    }

    /**
     * Basically the message id is its hash code.
     *
     * @see Object#hashCode()
     *
     * @return The message hash code.
     */
    @Override
    public int hashCode() {
        return this.getId();
    }
}
