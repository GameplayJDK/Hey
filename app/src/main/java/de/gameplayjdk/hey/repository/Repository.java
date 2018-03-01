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

package de.gameplayjdk.hey.repository;

import java.util.List;
import java.util.Random;

/**
 * Created by GameplayJDK on 05.01.2018.
 */

public interface Repository<T> {

    interface RepositoryCallback<S> {

        void onSuccess(S response);

        void onError(int errorResponse);
    }

    /**
     * Proxy callback class.
     *
     * Redirect all calls to {@link RepositoryListCallback#onSuccess(List)} for each list item to
     * the inner callback. Calls to {@link RepositoryListCallback#onError(int)} are also redirected
     * to the inner callback.
     *
     * @param <S>
     */
    class RepositoryListCallback<S> implements RepositoryCallback<List<S>> {

        private final RepositoryCallback<S> callback;

        public RepositoryListCallback(RepositoryCallback<S> callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(List<S> response) {
            for (S item : response) {
                this.callback.onSuccess(item);
            }
        }

        @Override
        public void onError(int errorResponse) {
            this.callback.onError(errorResponse);
        }
    }

    /**
     * Proxy callback class.
     *
     * Redirect all calls to {@link RepositoryListCallback#onSuccess(List)} for one random list item
     * to the inner callback. Calls to {@link RepositoryListCallback#onError(int)} are also
     * redirected to the inner callback.
     *
     * @param <S>
     */
    class RepositoryRandomCallback<S> implements RepositoryCallback<List<S>> {

        private final RepositoryCallback<S> callback;

        public RepositoryRandomCallback(RepositoryCallback<S> callback) {
            this.callback = callback;
        }

        private int getRandom(int limit) {
            Random random = new Random();

            return random.nextInt(limit);
        }

        @Override
        public void onSuccess(List<S> response) {
            int size = response.size();

            int index = (size == 1) ? 0 : this.getRandom(size);

            this.callback.onSuccess(response.get(index));
        }

        @Override
        public void onError(int errorResponse) {
            this.callback.onError(errorResponse);
        }
    }

    void add(List<T> list, RepositoryCallback<List<T>> callback);

    void update(Specification<T> specification, RepositoryCallback<List<T>> callback);

    void remove(Specification<T> specification, RepositoryCallback<List<T>> callback);

    void get(Specification<T> specification, RepositoryCallback<List<T>> callback);
}
