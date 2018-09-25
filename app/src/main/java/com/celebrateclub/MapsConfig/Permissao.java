package com.celebrateclub.MapsConfig;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes) {

        if (Build.VERSION.SDK_INT >= 23) {

            List<String> listaPermissoes = new ArrayList<String>();

            /* Aqui vai percorrer as permissões passadas e ver se
                todas elas já estão liberadas     */
            for (String permissao : permissoes) {

                //verifica se para essa activity têm-se a permissão necessária
                boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao)
                        == PackageManager.PERMISSION_GRANTED;
                if (!validaPermissao) listaPermissoes.add(permissao);
            }

            //Caso a lista esteja vazia, não é necessário solicitar permissões
            if (listaPermissoes.isEmpty()) return true;

            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            ///Solicita permissão que não tenha
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
            //o requestCode é somente um controle para saber de onde veio a solicitação
        }

        return true;
    }
}
