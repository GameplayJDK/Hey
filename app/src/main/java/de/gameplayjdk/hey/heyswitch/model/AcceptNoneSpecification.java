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

import de.gameplayjdk.hey.repository.Specification;

/**
 * Created by GameplayJDK on 08.01.2018.
 */

public class AcceptNoneSpecification implements Specification<Message> {

    private AcceptNoneSpecification() {
    }

    public static AcceptNoneSpecification newInstance() {
        return new AcceptNoneSpecification();
    }

    @Override
    public boolean accept(Message item) {
        return false;
    }
}
