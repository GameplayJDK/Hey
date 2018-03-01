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

package de.gameplayjdk.hey.heyswitch.usecase;

import java.util.List;

import de.gameplayjdk.hey.heyswitch.model.Message;
import de.gameplayjdk.hey.heyswitch.model.MessageRepository;
import de.gameplayjdk.hey.mvp.clean.UseCase;
import de.gameplayjdk.hey.repository.Repository;

/**
 * Created by GameplayJDK on 25.01.2018.
 */

public class AddMessageListUseCase extends UseCase<AddMessageListUseCase.RequestValue, AddMessageListUseCase.ResponseValue, AddMessageListUseCase.ErrorResponseValue> {

    private final MessageRepository mRepository;

    public AddMessageListUseCase() {
        this(MessageRepository.getInstance());
    }

    public AddMessageListUseCase(MessageRepository repository) {
        this.mRepository = repository;
    }

    @Override
    protected void executeUseCase(AddMessageListUseCase.RequestValue requestValue) {
        this.mRepository.add(requestValue.getMessageList(), new Repository.RepositoryCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> response) {
                AddMessageListUseCase.ResponseValue responseValue = new AddMessageListUseCase.ResponseValue(response);

                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(int errorResponse) {
                AddMessageListUseCase.ErrorResponseValue errorResponseValue = new AddMessageListUseCase.ErrorResponseValue(errorResponse);

                getUseCaseCallback().onError(errorResponseValue);
            }
        });
    }

    public static final class RequestValue implements UseCase.RequestValue {

        private final List<Message> mMessageList;

        public RequestValue(List<Message> messageList) {
            this.mMessageList = messageList;
        }

        public List<Message> getMessageList() {
            return this.mMessageList;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Message> mMessageList;

        public ResponseValue(List<Message> messageList) {
            this.mMessageList = messageList;
        }

        public List<Message> getMessage() {
            return this.mMessageList;
        }
    }

    public static final class ErrorResponseValue implements UseCase.ErrorResponseValue {

        private final int mErrorCode;

        public ErrorResponseValue(int errorCode) {
            this.mErrorCode = errorCode;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }
}
