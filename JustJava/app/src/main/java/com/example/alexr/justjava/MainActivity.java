package com.example.alexr.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked
     * This invokes the display method and inputs a value as an argument for the display method
     *
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int totalPrice = calculatePrice(hasWhippedCream, hasChocolate);

        EditText nameTextField = findViewById(R.id.name_submission_view);
        String name = nameTextField.getText().toString();

        String priceMessage = createOrderSummary(name, totalPrice, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for: "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * Calculates and returns the price of the order.
     * quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price = 5;

        if (addWhippedCream) {
            price = price + 1;
        }

        if (addChocolate) {
            price = price + 2;
        }
        return quantity * price;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank You!";
        return priceMessage;
    }

    public void increment(View view) {
        if(quantity == 100) {
            Toast.makeText(this,"The maximum order is 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity + 1;
            display(quantity);
        }
    }

    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this,"The minimum order is 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity - 1;
            display(quantity);
        }
    }
    /**
     * This method displays the given quantity value on the screen
     * This method sets up how the quantity will be displayed
     * It finds the location of where the number is (the TextView quantity_text_view)
     * Then, it will use the setText method to display the number
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
            quantityTextView.setText("" + number);

    }

}
