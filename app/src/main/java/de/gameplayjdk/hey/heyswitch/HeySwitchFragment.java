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

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import de.gameplayjdk.hey.R;
import de.gameplayjdk.hey.heyswitch.model.MessageRepository;

/**
 * Created by GameplayJDK on 05.01.2018.
 */

public class HeySwitchFragment extends Fragment implements HeySwitchContract.View {

    private HeySwitchContract.Presenter mPresenter;

    private RelativeLayout mMainRelativeLayout;

    private TextView mMessageTextView;
    private Switch mSelfSwitch;

    public HeySwitchFragment() {
    }

    public static HeySwitchFragment newInstance() {
        return new HeySwitchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Enable menu for next release.
        //super.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hey_switch, container, false);

        // Save inflated view root
        this.mMainRelativeLayout = (RelativeLayout) root;

        // Set up view
        this.mMessageTextView = root.findViewById(R.id.message_text_view);
        this.mMessageTextView.setText(R.string.initial_message);

        this.mSelfSwitch = root.findViewById(R.id.self_switch);

        String[] textArrayMessages = super.getResources().getStringArray(R.array.messages);
        // This will eventually produce an error, as the repository may already exists. The solution
        // is to check for repository existence before calling addMessageArray(...) or ignore it.
        this.mPresenter.addMessageArray(textArrayMessages);

        final long switchDelay = Long.parseLong(super.getResources().getString(R.string.switch_delay));

        this.mSelfSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.getRandomMessage();
                        }
                    }, switchDelay);
                }
            }
        });

        String supportedLocale = new Locale("en").getLanguage();

        // TODO: Add support for translations.
        if (!Locale.getDefault().getLanguage().equals(supportedLocale)) {
            Toast toast = Toast.makeText(super.getActivity(), R.string.no_translation, Toast.LENGTH_LONG);

            toast.show();
        }

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Inflate menu below the menu items of the activity.
        inflater.inflate(R.menu.menu_hey_switch, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.suggest_message_item:
                return this.openBrowser(super.getResources().getString(R.string.web_url));
            case R.id.about_item:
                // TODO: Show about dialog. Currently this item is hidden.
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void setPresenter(HeySwitchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    /**
     * Display a {@code text} message.
     *
     * @param text
     */
    @Override
    public void showMessage(String text) {
        float textSize = this.getGoodTextSize(text);

        this.mMessageTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        this.mMessageTextView.setText(text);
        //this.mMessageTextView.invalidate();

        this.triggerLayout();
    }

    /**
     * Show an error message for the given {@code errorCode}.
     *
     * @param errorCode
     */
    @Override
    public void showErrorMessage(int errorCode) {
        Toast toast = Toast.makeText(super.getActivity(), this.getResourceIdForErrorCode(errorCode), Toast.LENGTH_SHORT);

        toast.show();
    }

    @Override
    public void flickSwitch() {
        this.mSelfSwitch.setChecked(false);
    }

    /**
     * Map an given {@code errorCode} to the corresponding resource id of the error message.
     *
     * @param errorCode
     * @return
     */
    private int getResourceIdForErrorCode(int errorCode) {
        switch (errorCode) {
            default:
            case MessageRepository.ERROR_UNKNOWN:
                return R.string.error_unknown;
            case MessageRepository.ERROR_DUPLICATE_MESSAGE:
                return R.string.error_duplicate_message;
            case MessageRepository.ERROR_NO_SUCH_MESSAGE:
                return R.string.error_no_such_message;
        }
    }

    /**
     * Create an {@link Intent} to open an url in the standard browser.
     *
     * @param urlAddress
     * @return
     */
    private boolean openBrowser(String urlAddress) {
        Uri url = Uri.parse(urlAddress);

        Activity activity = super.getActivity();
        Intent intent = new Intent(Intent.ACTION_VIEW, url);

        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);

            return true;
        }

        return false;
    }

    /**
     * Calculate text size in "sp". Internally calls
     * {@link #getGoodTextSize(float, float, String, float)} with the retrieved baseTextSize and
     * calculationStepSize values set in "prefs.xml" and the last measured layout width from the
     * root layout. Automatically converts "sp" to "px" and back.
     *
     * @param text
     * @return
     */
    private float getGoodTextSize(String text) {
        float baseTextSize = Float.parseFloat(super.getResources().getString(R.string.base_text_size));
        // Conversion: baseTextSize is given as unit "sp", but "px" is needed here.
        baseTextSize = this.getSpAsPx(baseTextSize);

        float calculationStepSize = Float.parseFloat(super.getResources().getString(R.string.calculation_step_size));

        float textSize = this.getGoodTextSize(baseTextSize, calculationStepSize, text, this.mMainRelativeLayout.getMeasuredWidth());
        // Conversion: getGoodTextSize(...) returns unit "px", but "sp" is needed here.
        textSize = this.getPxAsSp(textSize);

        return textSize;
    }

    /**
     * Calculate text size based on given {@code baseTextSize} in "px". The given
     * {@code calculationStepSize} in "px" is the amount of units to subtract on every calculation
     * cycle. The given {@code viewWidth} is the maximum width allowed in "px" for the supplied
     * {@code text} string.
     *
     * @param baseTextSize
     * @param calculationStepSize
     * @param text
     * @param viewWidth
     * @return
     */
    private float getGoodTextSize(float baseTextSize, float calculationStepSize, String text, float viewWidth) {
        float textSize = baseTextSize;

        Paint paint = new Paint();
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(baseTextSize);

        while (paint.measureText(text) > viewWidth) {
            textSize = textSize - calculationStepSize;

            paint.setTextSize(textSize);
        }

        return textSize;
    }

    /**
     * Convert {@code px} to "sp".
     *
     * @param px
     * @return
     */
    private float getPxAsSp(float px) {
        return px / super.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * Convert {@code sp} to "px".
     *
     * @param sp
     * @return
     */
    private float getSpAsPx(float sp) {
        return sp * super.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * Re-layout the root view.
     */
    private void triggerLayout() {
        // requestLayout() is async
        this.mMainRelativeLayout.requestLayout();
    }
}
