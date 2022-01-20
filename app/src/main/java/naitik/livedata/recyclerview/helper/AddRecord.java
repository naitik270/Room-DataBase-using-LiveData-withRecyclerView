package naitik.livedata.recyclerview.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import naitik.livedata.recyclerview.R;
import naitik.livedata.recyclerview.listener.DialogAddButtonClickListener;

public class AddRecord {

    public void showCustomDialog(String message, String buttonText,
                                 Context context,
                                 DialogAddButtonClickListener dialogButtonClickListener) {

        Dialog dialog = new Dialog(context, R.style.Theme_Dialog_Custom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_dialog_layout, null, false);
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setContentView(view);

        TextView txt_dialog_message = view.findViewById(R.id.txt_dialog_message);
        txt_dialog_message.setText(message);
        Button btn_dialog = view.findViewById(R.id.btn_dialog);
        btn_dialog.setText(buttonText);
        EditText edt_ticket_count = view.findViewById(R.id.edt_ticket_count);
        EditText edt_add_content = view.findViewById(R.id.edt_add_content);
        btn_dialog.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_white,
                0, 0, 0);
        edt_ticket_count.setVisibility(View.VISIBLE);
        edt_add_content.setVisibility(View.VISIBLE);

        dialog.show();

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation(edt_ticket_count, edt_add_content)) {
                    if (dialogButtonClickListener != null) {
                        dialogButtonClickListener.onDialogAddButtonClicked(edt_ticket_count.getText().toString(),
                                edt_add_content.getText().toString(), dialog);
                    }
                } else {
                    Toast.makeText(context, "Duplicates Item...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edt_add_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edt_add_content.getText().toString().equals("")
                        || edt_add_content.getText().toString().equals("0")) {
                    btn_dialog.setEnabled(false);
                    btn_dialog.setAlpha((float) 0.50);
                } else {
                    btn_dialog.setEnabled(true);
                    btn_dialog.setAlpha((float) 0.99);
                }
            }
        });

        edt_ticket_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edt_ticket_count.getText().toString().equals("")
                        || edt_ticket_count.getText().toString().equals("0")) {
                    btn_dialog.setEnabled(false);
                    btn_dialog.setAlpha((float) 0.50);
                } else {
                    btn_dialog.setEnabled(true);
                    btn_dialog.setAlpha((float) 0.99);
                }
            }
        });
    }

    private boolean validation(EditText edt_ticket_count, EditText edt_add_content) {

        if (edt_ticket_count.getText() == null ||
                edt_ticket_count.getText().toString().isEmpty()) {
            edt_ticket_count.setError("Title is Required...!");
            edt_ticket_count.requestFocus();

            return false;
        } else {
            edt_ticket_count.setError(null);
        }

        if (edt_add_content.getText() == null ||
                edt_add_content.getText().toString().isEmpty()) {
            edt_add_content.setError("Content is Required...!");
            edt_add_content.requestFocus();
            return false;
        } else {
            edt_add_content.setError(null);
        }
        return true;
    }


}
