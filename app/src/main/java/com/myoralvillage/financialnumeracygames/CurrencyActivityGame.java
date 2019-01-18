package com.myoralvillage.financialnumeracygames;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by paulj on 2016-10-26.
 *
 * This class is the superclass that contains the currency specific functionality
 *
 * The intent is that almost all of the currency specific data will be defined here
 *
 * The changes between the exact change and PV games (the only two subclasses) should eventually
 * be VERY minor (with luck, eliminated totally although that remains to be seen)
 *
 * Originally I used subclasses of exactChange and PV to hold this information. While arguably
 * more object oriented that lead to two problems
 *       1) Code Duplication
 *       2) Having to update multiple places when a new currency was added
 *
 * So, instead I have brought the information up here in the form of arrays
 * TODO : Should I use maps or the like instead?
 *
 * There are two distinct types of information here
 *     1) An array of information required for each cash unit. Each unit
 *        can be a coin or a bill. At this moment, there are 5 units per currency
 *        This may change to 6. More than that might be too many as the images become
 *        too small. TODO : Investigate
 *     2) The per currency information. The image and value of each unit of cash
 */

public abstract class CurrencyActivityGame extends GenericActivityGame {
    /*
     *
     * So, I need a class that has the appropriate information for each unit of cash
     *
     * Note that all the information here is independent of the currency. It basically
     * holds layout elements created from the activity_level3_currency_games.xml file
     *
     * I'd like to lift this into a separate class and not be part of an Activity
     * but that really doesn't seem to be the model that Android uses.
     *
     * TODO : Can I create an Intent that doesn't actually do anything just to map the resource
     * files?
     */

    public class PerCash {
        TextView numView;  // The layout holding the number of paid items
        TextView paidView; // The layout holding the total amount paid
        ImageView paid;    // The actual drawable. Maybe?
        ImageView bill; // Image ?? Note, includes coins despite the name
        ImageView snap;   // The image of the bill as it is being moved from the bottom of the
        // screen to the paid area
        int num;    // Number transferred
        String resource_name; // This is the string in the resource file used by the code to
        // determine which image was selected and moved
        float value;
        int drawable_id;

        PerCash(int r_num, int r_paid_view, int r_paid, int r_bill, int r_snap,
                String r_nam) {
            numView = (TextView) findViewById(r_num);
            paidView = (TextView) findViewById(r_paid_view);
            paid = (ImageView) findViewById(r_paid);
            bill =  (ImageView) findViewById(r_bill);
            snap = (ImageView) findViewById(r_snap);
            num = 0;
            resource_name = r_nam;
        }

        /*
         * And this sets the currency specific values. It makes the calling
         * code MUCH clearer to separate these out
         */

        void country_specific(int drawable, float val) {
            value = val;
            drawable_id = drawable;
            bill.setImageDrawable(getDrawable(drawable_id));
            bill.setOnTouchListener(new ChoiceTouchListener());
        }
    }

    PerCash[] cash_units;

    String format_string;  // Used to print the locale and currency specific numbers.

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_currency_games);
        cash_units = new PerCash[] {
                new PerCash(R.id.currency_1_num, R.id.currency_1_paidview, R.id.currency_1_paid,
                        R.id.currency_1_bill, R.id.currency_1_billSnap,
                        "com.myoralvillage.financialnumeracygames:id/currency_1_bill"),
                new PerCash(R.id.currency_2_num, R.id.currency_2_paidview, R.id.currency_2_paid,
                        R.id.currency_2_bill, R.id.currency_2_billSnap,
                        "com.myoralvillage.financialnumeracygames:id/currency_2_bill"),
                new PerCash(R.id.currency_3_num, R.id.currency_3_paidview, R.id.currency_3_paid,
                        R.id.currency_3_bill, R.id.currency_3_billSnap,
                        "com.myoralvillage.financialnumeracygames:id/currency_3_bill"),
                new PerCash(R.id.currency_4_num, R.id.currency_4_paidview, R.id.currency_4_paid,
                        R.id.currency_4_bill, R.id.currency_4_billSnap,
                        "com.myoralvillage.financialnumeracygames:id/currency_4_bill"),
                new PerCash(R.id.currency_5_num, R.id.currency_5_paidview, R.id.currency_5_paid,
                        R.id.currency_5_bill, R.id.currency_5_billSnap,
                        "com.myoralvillage.financialnumeracygames:id/currency_5_bill"),
        };

        /*
         * Now we have to set the value for each national currency
         *
         * Note that ALL currencies are calculated as floating point numbers and
         * just printed as decimal or whole integer numbers as appropriate
         */
        switch(thisUser.actual_country) {
            case TONGA:
                format_string = "$T %.2f";
                cash_units[0].country_specific(R.drawable.pa_anga_50_senti, 0.5f);
                cash_units[1].country_specific(R.drawable.pa_anga_1, 1f);
                cash_units[2].country_specific(R.drawable.pa_anga_5, 5f);
                cash_units[3].country_specific(R.drawable.pa_anga_10, 10f);
                cash_units[4].country_specific(R.drawable.pa_anga_20, 20f);
                break;

            case VANUATU:
                format_string = "%.0f VT";
                cash_units[0].country_specific(R.drawable.vatu_100, 100.0f);
                cash_units[1].country_specific(R.drawable.vatu_200, 200.0f);
                cash_units[2].country_specific(R.drawable.vatu_500, 500.0f);
                cash_units[3].country_specific(R.drawable.vatu_1000, 1000.0f);
                cash_units[4].country_specific(R.drawable.vatu_5000, 5000.0f);
                break;
            case TANZANIA:
                format_string = "%.0f /-Tsh";
                cash_units[0].country_specific(R.drawable.shilling_500, 500.0f);
                cash_units[1].country_specific(R.drawable.shilling_1000, 1000.0f);
                cash_units[2].country_specific(R.drawable.shilling_2000, 2000.0f);
                cash_units[3].country_specific(R.drawable.shilling_5000, 5000.0f);
                cash_units[4].country_specific(R.drawable.shilling_10000, 10000.0f);
                break;
            default:
                throw new AssertionError("Unrecognized Country");

        }
    }

    abstract public void advanceGame(View v);

    }
