package com.myoralvillage.financialnumeracygames;


import android.content.Intent;
import android.os.Bundle;



/**
 * Created by paulj on 2016-10-22.
 *
 * This is the superclass implementing most of the functionlity for the Purchase (a Placement Value) game
 *
 */

public class Level3ActivityGamePurchase extends Level3ActivityCurrencyGame {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        is_purchase = true;
        name_score_file = "level3purchase.txt";
        super.onCreate(savedInstanceState);
    }

    void runDemo() {
        Intent intent = new Intent(this, Level3ActivityDemoPurchase.class);
        startActivity(intent);
        thisUser.demosViewed[ActivityGame.LEVEL3PURCHASE.ordinal()] = true;
    }

    /*
	 * Set the paid information if it is relevant (exact change)
	 */
    void setPaid(int cash_unit) {
    }
}
