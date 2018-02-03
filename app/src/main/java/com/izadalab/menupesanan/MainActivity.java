package com.izadalab.menupesanan;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice() {
        return quantity * 5;
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "\nNama Pemesan : " + name;
        priceMessage += "\nMau tambah whipped cream? " + addWhippedCream;
        priceMessage += "\nMau tambah chocolate? " + addChocolate;
        priceMessage += "\nJumlah item: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thankyou);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.tv_quantity);
        quantityTextView.setText("" + numberOfCoffees);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.et_nama);
        String name = nameField.getText().toString();
        Log.v("MainActivity", "Name = " + name);
        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.cb_whipped);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants whipped cream topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.cb_coklat);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice();

        // Display the order summary on the screen
        String pricemessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: ririn.nfa@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pesanan Kopi atas nama " +name);
        intent.putExtra(Intent.EXTRA_TEXT, pricemessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
