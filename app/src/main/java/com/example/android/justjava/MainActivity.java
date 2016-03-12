package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream){
            basePrice = basePrice + 1;
        }

        if (addChocolate){
            basePrice = basePrice + 2;
        }
        int price = quantity * basePrice;
        return price;
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox =(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox =(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        String priceMessege = createOrderSummary(name, calculatePrice(hasWhippedCream, hasChocolate), hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for: " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessege);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

       // displayMessage(priceMessege);


    }


    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {

        quantity +=1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void decrement(View view) {
    if(quantity <= 0) {
        Toast.makeText(MainActivity.this, "Zerou!!!", Toast.LENGTH_SHORT).show();
        displayQuantity(quantity);
    }else{
        quantity -=1;
        displayQuantity(quantity);
    }


    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        return "Name: " +name+
                "\n Add whipped cream? "+addWhippedCream+
                "\n Add chocolate? "+addChocolate+
                "\n Quantity: "+quantity+
                "\n Total: $"+price+
                "\n Thank You!";

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numero) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numero);
    }


    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}
