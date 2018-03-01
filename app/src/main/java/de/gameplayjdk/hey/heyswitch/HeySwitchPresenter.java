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

package de.gameplayjdk.hey.heyswitch;

import java.util.ArrayList;
import java.util.List;

import de.gameplayjdk.hey.heyswitch.model.Message;
import de.gameplayjdk.hey.heyswitch.usecase.AddMessageListUseCase;
import de.gameplayjdk.hey.heyswitch.usecase.GetMessageUseCase;
import de.gameplayjdk.hey.mvp.clean.UseCase;
import de.gameplayjdk.hey.mvp.clean.UseCaseHandler;

/**
 * Created by GameplayJDK on 05.01.2018.
 */

public class HeySwitchPresenter implements HeySwitchContract.Presenter {

    private HeySwitchContract.View mView;

    private final AddMessageListUseCase mAddMessageListUseCase;
    private final GetMessageUseCase mGetMessageUseCase;

    private final UseCaseHandler mUseCaseHandler;

    private int mMessageCount;

    public HeySwitchPresenter(HeySwitchContract.View view) {
        this(view, new AddMessageListUseCase(), new GetMessageUseCase(), UseCaseHandler.getInstance());
    }

    public HeySwitchPresenter(HeySwitchContract.View view, AddMessageListUseCase addMessageListUseCase, GetMessageUseCase getMessageUseCase, UseCaseHandler useCaseHandler) {
        this.mView = view;

        this.mAddMessageListUseCase = addMessageListUseCase;
        this.mGetMessageUseCase = getMessageUseCase;

        this.mUseCaseHandler = useCaseHandler;

        this.mMessageCount = 0;

        // Set presenter after setting all other fields to avoid NPE.
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void addMessageArray(String[] textArray) {
        this.addMessageArray(textArray, Message.Mood.NONE);
    }

    @Override
    public void addMessageArray(String[] textArray, Message.Mood mood) {
        List<Message> messageList = new ArrayList<Message>();

        for (String text : textArray) {
            this.mMessageCount++;

            messageList.add(new Message(this.mMessageCount, text, mood));
        }

        AddMessageListUseCase.RequestValue requestValue = new AddMessageListUseCase.RequestValue(messageList);

        this.mUseCaseHandler.execute(this.mAddMessageListUseCase, requestValue, new UseCase.UseCaseCallback<AddMessageListUseCase.ResponseValue, AddMessageListUseCase.ErrorResponseValue>() {
            @Override
            public void onSuccess(AddMessageListUseCase.ResponseValue response) {
                // Successfully added messages
            }

            @Override
            public void onError(AddMessageListUseCase.ErrorResponseValue errorResponse) {
                mView.showErrorMessage(errorResponse.getErrorCode());
            }
        });
    }

    @Override
    public void getRandomMessage() {
        GetMessageUseCase.RequestValue requestValue = new GetMessageUseCase.RequestValue();

        this.mUseCaseHandler.execute(this.mGetMessageUseCase, requestValue, new UseCase.UseCaseCallback<GetMessageUseCase.ResponseValue, GetMessageUseCase.ErrorResponseValue>() {
            @Override
            public void onSuccess(GetMessageUseCase.ResponseValue response) {
                Message message = response.getMessage();

                mView.flickSwitch();
                mView.showMessage(message.getText());
            }

            @Override
            public void onError(GetMessageUseCase.ErrorResponseValue errorResponse) {
                mView.flickSwitch();
                mView.showErrorMessage(errorResponse.getErrorCode());
            }
        });
    }
}
