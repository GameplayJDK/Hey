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
 * Created by GameplayJDK on 14.01.2018.
 */

public class AcceptFirstSpecification implements Specification<Message> {

    private final int amount;
    private final int offset;

    private int count;

    private AcceptFirstSpecification(final int amount, final int offset) {
        this.amount = amount;
        this.offset = offset;

        this.count = 0;
    }

    public static AcceptFirstSpecification newInstance(final int amount, final int offset) {
        return new AcceptFirstSpecification(amount, offset);
    }

    @Override
    public boolean accept(Message item) {
        return (item != null) && (this.count > this.getOffset()) && (this.count < this.getOffset() + this.getAmount());
    }

    public int getAmount() {
        return this.amount;
    }

    public int getOffset() {
        return this.offset;
    }
}
