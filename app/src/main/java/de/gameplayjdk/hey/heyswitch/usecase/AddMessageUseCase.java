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

import de.gameplayjdk.hey.heyswitch.model.Message;
import de.gameplayjdk.hey.heyswitch.model.MessageRepository;
import de.gameplayjdk.hey.mvp.clean.UseCase;
import de.gameplayjdk.hey.repository.Repository;

/**
 * Created by GameplayJDK on 15.01.2018.
 */

public class AddMessageUseCase extends UseCase<AddMessageUseCase.RequestValue, AddMessageUseCase.ResponseValue, AddMessageUseCase.ErrorResponseValue> {

    private final MessageRepository mRepository;

    public AddMessageUseCase() {
        this(MessageRepository.getInstance());
    }

    public AddMessageUseCase(MessageRepository repository) {
        this.mRepository = repository;
    }

    @Override
    protected void executeUseCase(RequestValue requestValue) {
        this.mRepository.add(requestValue.getMessage(), new Repository.RepositoryCallback<Message>() {
            @Override
            public void onSuccess(Message response) {
                ResponseValue responseValue = new ResponseValue(response);

                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(int errorResponse) {
                ErrorResponseValue errorResponseValue = new ErrorResponseValue(errorResponse);

                getUseCaseCallback().onError(errorResponseValue);
            }
        });
    }

    public static final class RequestValue implements UseCase.RequestValue {

        private final Message mMessage;

        public RequestValue(Message message) {
            this.mMessage = message;
        }

        public Message getMessage() {
            return this.mMessage;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Message mMessage;

        public ResponseValue(Message message) {
            this.mMessage = message;
        }

        public Message getMessage() {
            return this.mMessage;
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
