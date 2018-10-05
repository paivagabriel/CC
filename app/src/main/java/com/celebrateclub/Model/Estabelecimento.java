package com.celebrateclub.Model;

public class Estabelecimento {
    String nomeEstabelecimento;
    Double latitude, longitude;
    String enderecoEstabelecimento;
    String colorBackground;
    String colorText;

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }




    public Estabelecimento() {
    }


    public String getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public String getEnderecoEstabelecimento() {
        return enderecoEstabelecimento;
    }

    public void setEnderecoEstabelecimento(String enderecoEstabelecimento) {
        this.enderecoEstabelecimento = enderecoEstabelecimento;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getNomeEstabelecimento() {
        return nomeEstabelecimento;
    }

    public void setNomeEstabelecimento(String nomeEstabelecimento) {
        this.nomeEstabelecimento = nomeEstabelecimento;
    }
}


