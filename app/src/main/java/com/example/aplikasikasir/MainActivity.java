package com.example.aplikasikasir;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final double PRICE_SAMSUNG = 500000;
    private static final double PRICE_IPHONE = 1000000;
    private static final double PRICE_XIAOMI = 300000;

    private RadioGroup radioGroupItem;
    private EditText editTextQuantity;
    private CheckBox checkBoxMembership;
    private Button buttonAdd, buttonProcess, buttonClear;
    private TextView textViewReceipt, textViewTotal;

    private double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupItem = findViewById(R.id.radioGroupItem);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        checkBoxMembership = findViewById(R.id.checkBoxMembership);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonProcess = findViewById(R.id.buttonProcess);
        buttonClear = findViewById(R.id.buttonClear);
        textViewReceipt = findViewById(R.id.textViewReceipt);
        textViewTotal = findViewById(R.id.textViewTotal);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        buttonProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTransaction();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
            }
        });
    }

    private void addItem() {
        RadioButton selectedRadioButton = findViewById(radioGroupItem.getCheckedRadioButtonId());
        if (selectedRadioButton == null) {
            return;
        }

        String item = selectedRadioButton.getText().toString();
        int quantity = Integer.parseInt(editTextQuantity.getText().toString());
        double price = 0;

        if (item.equalsIgnoreCase("Samsung")) {
            price = PRICE_SAMSUNG;
        } else if (item.equalsIgnoreCase("iPhone")) {
            price = PRICE_IPHONE;
        } else if (item.equalsIgnoreCase("Xiaomi")) {
            price = PRICE_XIAOMI;
        }

        double subtotal = price * quantity;
        total += subtotal;

        textViewReceipt.append("Item: " + item + ", Quantity: " + quantity + ", Subtotal: Rp" + subtotal + "\n");
    }

    private void processTransaction() {
        textViewReceipt.append("\nDetail Pembelian:\n");

        RadioButton selectedRadioButton = findViewById(radioGroupItem.getCheckedRadioButtonId());
        if (selectedRadioButton == null) {
            textViewReceipt.append("Pilih barang terlebih dahulu\n");
            return;
        }

        String item = selectedRadioButton.getText().toString();
        double price = 0;

        if (item.equalsIgnoreCase("Samsung")) {
            price = PRICE_SAMSUNG;
        } else if (item.equalsIgnoreCase("iPhone")) {
            price = PRICE_IPHONE;
        } else if (item.equalsIgnoreCase("Xiaomi")) {
            price = PRICE_XIAOMI;
        }

        int quantity = Integer.parseInt(editTextQuantity.getText().toString());
        double subtotal = price * quantity;
        textViewReceipt.append("Item: " + item + ", Quantity: " + quantity + ", Subtotal: Rp" + subtotal + "\n");

        if (checkBoxMembership.isChecked()) {
            textViewReceipt.append("Diskon Member: 5%\n");
        }

        double discount = checkBoxMembership.isChecked() ? total * 0.05 : 0;
        double totalAfterDiscount = total - discount;

        textViewReceipt.append("Total Payment (Sebelum Diskon): Rp" + total + "\n");
        textViewReceipt.append("Diskon Member: Rp" + discount + "\n");
        textViewReceipt.append("Total Payment (Setelah Diskon): Rp" + totalAfterDiscount + "\n");

        textViewReceipt.append("\nTerima kasih atas pembelian Anda!");

        textViewTotal.setText("Total Payment: Rp" + totalAfterDiscount);

        // Menonaktifkan radio button setelah proses transaksi
        radioGroupItem.clearCheck();
        checkBoxMembership.setChecked(false);

        // Reset total
        total = 0;
    }

    private void clearAll() {
        textViewReceipt.setText("");
        textViewTotal.setText("");
        editTextQuantity.setText("");
        radioGroupItem.clearCheck();
        checkBoxMembership.setChecked(false);
        total = 0;
    }
}
