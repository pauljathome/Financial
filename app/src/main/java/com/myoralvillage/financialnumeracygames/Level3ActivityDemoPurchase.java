package com.myoralvillage.financialnumeracygames;

import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by paulj on 2016-10-24.
 *
 * This implements the tiny bit of data initialization that is game specific.
 */

public class Level3ActivityDemoPurchase extends Level3ActivityDemoCurrency {
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

         * Note that no math is done in the demo so make sure the answer is right :-)
         * TODO : Change so that an exception is thrown if the math is wrong
         *
         * Also note that the demo code currently demands that the item
         * can be bought with exactly two bills.
		 */

        switch(thisUser.actual_country) {
            case TONGA:
                item_bought = R.drawable.basket_fish_pa_anga_5_50;
                first_bill = 0;  // 50 senti
                second_bill = 2;  // 1 panga
                //    bill_paid = 2;  // 5 panga
                break;

            case VANUATU:
                item_bought = R.drawable.calculator_vatu_600;
                // bill_paid = R.drawable.vatu_1000;
                first_bill = 0;  // 100
                second_bill = 2;  // 500
                break;
            case TANZANIA:
                item_bought = R.drawable.corn_shilling_2500;
                //bill_paid = R.drawable.shilling_5000;
                first_bill = 0;  // 500
                second_bill = 2;  // 2000
                break;
            default:
                throw new AssertionError("Unrecognized Country");

        }

    }
}
