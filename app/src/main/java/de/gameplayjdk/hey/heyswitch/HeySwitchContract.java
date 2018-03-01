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

import de.gameplayjdk.hey.heyswitch.model.Message;
import de.gameplayjdk.hey.mvp.BasePresenter;
import de.gameplayjdk.hey.mvp.BaseView;

/**
 * Created by GameplayJDK on 05.01.2018.
 */

public interface HeySwitchContract {

    interface View extends BaseView<Presenter> {

        void showMessage(String text);

        void showErrorMessage(int errorCode);

        void flickSwitch();
    }

    interface Presenter extends BasePresenter {

        void addMessageArray(String[] textArray);

        void addMessageArray(String[] textArray, Message.Mood mood);

        void getRandomMessage();
    }
}
