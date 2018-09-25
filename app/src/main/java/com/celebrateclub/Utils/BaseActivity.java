package com.celebrateclub.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.celebrateclub.R;

import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.Locale;



public class BaseActivity extends AppCompatActivity {

    public void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }



    public void voltar(){
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }

    public double convertStringToDouble(String s){
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        double d = 0;
        try {
            Number number = format.parse(s);
            d = number.doubleValue();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Log.e("convertToNumber", "Erro ao converter String para número");
        }
        return d;
    }

    public String convertDoubleToString(double d){
        return String.format(Locale.FRANCE, "%.2f", d);
    }

    public void snack(String msg){
        Snackbar.make (findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }
                ).show();
    }

    public String convertSpecialCharacters(String s){
        return s.replaceAll("[ãâáàä]", "a")
                .replaceAll("[êéèë]", "e")
                .replaceAll("[îíìï]", "i")
                .replaceAll("[õôòóö]", "o")
                .replaceAll("[ûúùü]", "u")
                .replaceAll("[ÂÂÁÀÄ]", "A")
                .replaceAll("[ÊÉÈË]", "E")
                .replaceAll("[ÎÍÌÏ]", "I")
                .replaceAll("[ÔÕÓÒÖ]", "O")
                .replaceAll("[ÛÚÙÜ]", "U")
                .replaceAll("Ç", "C")
                .replaceAll("ç", "c")
                .replaceAll("ñ", "n")
                .replaceAll("Ñ", "N");
    }

    public String removeAcentos(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFC).replaceAll("[^\\p{ASCII}]", "");
    }

    private void copiar(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Texto copiado: ", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(context, "Texto copiado", Toast.LENGTH_LONG).show();
    }

    public static Bitmap convertViewToBitmap(View view)
    {
        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();

        return view.getDrawingCache();
    }



}
