package com.myoralvillage.financialnumeracygames;


import android.os.Bundle;

/**
 * Created by paulj on 2016-10-28.
 *
 * Virtually all the logic is in the superclass. This just initializes the
 * game specific test values
 */

public class Level3ActivityDemoExactChange extends Level3ActivityDemoCurrency {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
     * This contains the game and currency specific information. It is
     * essentially the only code in the game specific subclasses
	 */

    void onCreateGameSpecific() {

		/*
		 * And the Demo information
		 *
         * Note that no math is done in the demo so make sure the answer is right :-)
         * TODO : Change so that an exception is thrown if the math is wrong
         *
         * Also note that the demo code currently demands a single note in payment
         * and exactly 2 notes in change.
         *
         * When adding new currencies, make sure to add an item where the
         * change can be a combination of exactly 2 distinct cash units
		 */

        switch(thisUser.actual_country) {
            case TONGA:
                item_bought = R.drawable.basketoranges_pa_anga_3_50;
                first_bill = 0;  // 50 senti
                second_bill = 1;  // 1 panga
            //    bill_paid = 2;  // 5 panga
                break;

            case VANUATU:
                item_bought = R.drawable.notebook_vatu_400;
                // bill_paid = R.drawable.vatu_1000;
                first_bill = 0;  // 100
                second_bill = 2;  // 500
                break;
            case TANZANIA:
                item_bought = R.drawable.basketoranges_shillng_3500;
                first_bill = 0; // 500
                second_bill = 1; // 10000
                // bill_paid = R.drawable.shilling_2000;
                break;
            default:
                throw new AssertionError("Unrecognized Country");

        }

    }

}
