/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package android.example.com.squawker.following;

import android.content.SharedPreferences;
import android.example.com.squawker.R;
import android.example.com.squawker.provider.SquawkContract;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.google.firebase.messaging.FirebaseMessaging;


/**
 * Shows the list of instructors you can follow
 */
// TODO (1) Implement onSharedPreferenceChangeListener
public class FollowingPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final static String LOG_TAG = FollowingPreferenceFragment.class.getSimpleName();

    SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    SharedPreferences mPreferences;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Add visualizer preferences, defined in the XML file in res->xml->preferences_squawker
        addPreferencesFromResource(R.xml.following_squawker);
    }
    // TODO (2) When a SharedPreference changes, check which preference it is and subscribe or
    // un-subscribe to the correct topics.

    // Ex. FirebaseMessaging.getInstance().subscribeToTopic("key_lyla");
    // subscribes to Lyla's squawks.

    // HINT: Checkout res->xml->following_squawker.xml. Note how the keys for each of the
    // preferences matches the topic to subscribe to for each instructor.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (!sharedPreferences.getBoolean(key, false)) {
            switch (sharedPreferences.getInt(key, 0)) {
                case 0:
                    FirebaseMessaging.getInstance().subscribeToTopic(SquawkContract.ASSER_KEY);
                    break;
                case 1:
                    FirebaseMessaging.getInstance().subscribeToTopic(SquawkContract.CEZANNE_KEY);
                    break;
                case 2:
                    FirebaseMessaging.getInstance().subscribeToTopic(SquawkContract.JLIN_KEY);
                    break;
                case 3:
                    FirebaseMessaging.getInstance().subscribeToTopic(SquawkContract.LYLA_KEY);
                    break;
                case 4:
                    FirebaseMessaging.getInstance().subscribeToTopic(SquawkContract.NIKITA_KEY);
                    break;
            }
        } else if (sharedPreferences.getBoolean(key, true)) {
            switch (sharedPreferences.getInt(key, 0)) {
                case 0:
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(SquawkContract.ASSER_KEY);
                    break;
                case 1:
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(SquawkContract.CEZANNE_KEY);
                    break;
                case 2:
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(SquawkContract.JLIN_KEY);
                    break;
                case 3:
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(SquawkContract.LYLA_KEY);
                    break;
                case 4:
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(SquawkContract.NIKITA_KEY);
                    break;
            }

        }
    }



    // TODO (3) Make sure to register and unregister this as a Shared Preference Change listener, in
    // onCreate and onDestroy.




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

}
