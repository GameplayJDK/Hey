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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.gameplayjdk.hey.repository.Repository;
import de.gameplayjdk.hey.repository.Specification;

/**
 * Created by GameplayJDK on 05.01.2018.
 */

public class MessageRepository implements Repository<Message> {

    // TODO: Add different repository types (remote, local, memory, ...).

    public static final int ERROR_UNKNOWN = 0;
    public static final int ERROR_DUPLICATE_MESSAGE = 1;
    public static final int ERROR_NO_SUCH_MESSAGE = 2;

    private static MessageRepository instance;

    private final Set<Message> messageSet;
    private final boolean strict;

    /**
     * A repository holding all the messages.
     * The repository is a application-wide singleton and is not responsible to populate it's data.
     */
    private MessageRepository() {
        this.messageSet = new HashSet<Message>();
        this.strict = true;
    }

    public static MessageRepository getInstance() {
        if (instance == null) {
            instance = new MessageRepository();
        }

        return instance;
    }

    /**
     * Filter {@link #messageSet} based on the given {@link Specification<Message>}. This method is
     * used internally with every method accepting a specification.
     *
     * @param specification
     * @return
     */
    private List<Message> filter(Specification<Message> specification) {
        final List<Message> result = new ArrayList<Message>();

        for (Message message : this.messageSet) {
            if (specification.accept(message)) {
                result.add(message);
            }
        }

        return result;
    }

    /**
     * Add {@code list} to the repository.
     *
     * @param list
     * @param callback
     */
    @Override
    public void add(List<Message> list, RepositoryCallback<List<Message>> callback) {
        boolean success = this.messageSet.addAll(list);

        if (!success) {
            callback.onError(ERROR_UNKNOWN);
        } else {
            callback.onSuccess(list);
        }
    }

    /**
     * Add {@code item} to the repository.
     *
     * @param item
     * @param callback
     */
    public void add(Message item, RepositoryCallback<Message> callback) {
        boolean success = this.messageSet.add(item);

        if (!success) {
            callback.onError(ERROR_DUPLICATE_MESSAGE);
        } else {
            callback.onSuccess(item);
        }
    }

    /**
     * Update a specific message (or set of messages) using the given {@link Specification<Message>}
     * to filter and then call the given {@code callback}.
     *
     * TODO: Add shortcut methods.
     * - {@link #update(int, RepositoryCallback)} with {@link AcceptIdSpecification}
     * - {@link #update(RepositoryCallback)} with {@link AcceptAllSpecification}
     *
     * @deprecated As the messages in the message repository are never updated.
     *
     * @param specification
     * @param callback
     */
    @Deprecated
    @Override
    public void update(Specification<Message> specification, RepositoryCallback<List<Message>> callback) {
        List<Message> result = this.filter(specification);

        if (result.isEmpty()) {
            callback.onError(ERROR_NO_SUCH_MESSAGE);

            return;
        }

        callback.onSuccess(result);

        // TODO: Check if objects have changed.
    }

    /**
     * Remove a specific message (or set of messages) using the given {@link Specification<Message>}
     * to filter and then call the given {@code callback}.
     *
     * @deprecated As the messages in the message repository are never removed.
     *
     * @param specification
     * @param callback
     */
    @Deprecated
    @Override
    public void remove(Specification<Message> specification, RepositoryCallback<List<Message>> callback) {
        List<Message> result = this.filter(specification);

        if (result.isEmpty()) {
            callback.onError(ERROR_NO_SUCH_MESSAGE);

            return;
        }

        boolean success = this.messageSet.removeAll(result);

        if (!success) {
            callback.onError(ERROR_UNKNOWN);
        } else {
            callback.onSuccess(result);
        }
    }

    /**
     * Remove a single message with matching id.
     * Internally {@link #remove(Specification, RepositoryCallback)} is called with a
     * {@link AcceptIdSpecification} as first parameter constructed on the fly based on the
     * {@code id} and the {@code callback} is passed through as the second one.
     *
     * @param id
     * @param callback
     */
    @Deprecated
    public void remove(int id, RepositoryCallback<List<Message>> callback) {
        this.remove(AcceptIdSpecification.newInstance(id), callback);
    }

    /**
     * Remove all {@link Message} objects in this repository.
     * Internally {@link #remove(Specification, RepositoryCallback)} is called with an
     * {@link AcceptAllSpecification} as first parameter and the {@code callback} is passed through
     * as the second one.
     *
     * @param callback
     */
    @Deprecated
    public void remove(RepositoryCallback<List<Message>> callback) {
        this.remove(AcceptAllSpecification.newInstance(), callback);
    }

    /**
     * Get a specific message (or set of messages) using the given {@link Specification<Message>}
     * to filter and then call the given {@code callback}.
     *
     * @param specification
     * @param callback
     */
    @Override
    public void get(Specification<Message> specification, RepositoryCallback<List<Message>> callback) {
        List<Message> result = this.filter(specification);

        if (result.isEmpty()) {
            callback.onError(ERROR_NO_SUCH_MESSAGE);
        } else {
            callback.onSuccess(result);
        }
    }

    /**
     * Get the single message with the matching id.
     * Internally {@link #get(Specification, RepositoryCallback)} is called with an
     * {@link AcceptIdSpecification} as first parameter constructed on the fly based on the
     * {@code id} and the {@code callback} is passed through as the second one.
     *
     * @param id int
     * @param callback {@link de.gameplayjdk.hey.repository.Repository.RepositoryCallback<Message>}
     */
    public void get(int id, RepositoryCallback<List<Message>> callback) {
        this.get(AcceptIdSpecification.newInstance(id), callback);
    }

    /**
     * Get all {@link Message} objects in this repository.
     * Internally {@link #get(Specification, RepositoryCallback)} is called with an
     * {@link AcceptAllSpecification} as first parameter and the {@code callback} is passed through
     * as the second one.
     *
     * @param callback {@link de.gameplayjdk.hey.repository.Repository.RepositoryCallback<Message>}
     */
    public void get(RepositoryCallback<List<Message>> callback) {
        this.get(AcceptAllSpecification.newInstance(), callback);
    }
}
