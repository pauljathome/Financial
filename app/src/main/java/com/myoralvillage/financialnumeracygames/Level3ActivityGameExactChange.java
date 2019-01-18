package com.myoralvillage.financialnumeracygames;


import android.content.Intent;
import android.os.Bundle;

/**
 * Created by paulj on 2016-10-28.
 */

public class Level3ActivityGameExactChange extends Level3ActivityCurrencyGame {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        is_purchase = false;
        name_score_file = "level3exactchange.txt";
        super.onCreate(savedInstanceState);
    }

    void runDemo() {
        Intent intent = new Intent(this, Level3ActivityDemoExactChange.class);
        startActivity(intent);
        thisUser.demosViewed[ActivityGame.LEVEL3EXACTCHANGE.ordinal()] = true;
    }

    /*
	 * Set the paid information if it is relevant (exact change)
	 */
    void setPaid(int cash_unit) {
        cash_units[cash_unit].paid.setImageResource(R.drawable.black_background);
        cash_units[cash_unit].paidView.setText(String.valueOf(tests[qNum].numPaid[cash_unit]));
        if (tests[qNum].numPaid[cash_unit] > 0) {
            cash_units[cash_unit].paid.setImageResource(cash_units[cash_unit].drawable_id);
        }
    }
}
