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

package de.gameplayjdk.hey;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import de.gameplayjdk.hey.heyswitch.HeySwitchFragment;
import de.gameplayjdk.hey.heyswitch.HeySwitchPresenter;

/**
 * Created by GameplayJDK on 05.01.2018.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.activity_main);

        HeySwitchFragment fragment = (HeySwitchFragment) super.getFragmentManager()
                .findFragmentById(R.id.content_fragment);
        if (fragment == null) {
            fragment = HeySwitchFragment.newInstance();

            this.setContentFragment(super.getFragmentManager(), fragment, R.id.content_fragment);
        }

        HeySwitchPresenter presenter = new HeySwitchPresenter(fragment);

        if (savedInstanceState != null) {
            // Read state.
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Write state.

        super.onSaveInstanceState(outState);
    }

    /**
     * Assign a fragment to a frame.
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public void setContentFragment(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
