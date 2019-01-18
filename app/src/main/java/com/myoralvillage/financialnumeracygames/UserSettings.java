package com.myoralvillage.financialnumeracygames;

/**
 * Created by paulj on 2016-10-16.
 *
 * This just keeps track of some user inputted data
 */

public class UserSettings {

        /*
         * In order to add a new country a few steps have to be followed
         *   1) Extend the Country enum below
         *   2) Add the new currency information in CurrencyActiveGame
         *   3) Make sure the appropriate images with the correct names have been added to
         *      the drawables directory
         *   4) Add a new currency to the onCreateCurrencySpecific functions
         *      in the files Level3ActivityCurrencyGame, Level3ActivityDemoCurrency, Level3ActuvityDemoExactChange
         *   5) TODO : Extend the mechanism to run time select the country
         *
         *   That should be all
         */
        /*
         * We support 3 countries right now
         *   Tanzania - Shilling, TZ-
         *   Vanuatu - Vatu VT
         *   Tonga - Pa'Anga T$
         */
        public enum Country {TANZANIA, VANUATU, TONGA}
        public String userName = "admin"; // Make this the default for now. CHANGE BEFORE RELEASE
        public int userId; // An internal ID. TODO - Map this to more than one fruit
        public boolean[] demosViewed = {false,false,false,false,false,false,false,false,false};
        public boolean[] availableLevels = {true,false,false};
        public boolean[] activityProgress = {false,false,false,false,false,false,false,false,false};
        Country actual_country = Country.TANZANIA;
        public boolean admin = true;
}
