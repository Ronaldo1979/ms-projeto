package br.com.cognitivebrasil.enumerator;

public enum Origem {

	 FEDERAL("Federal"),
	 ESTADUAL("Estadual"),
	 MUNICIPAL("Municipal");

    private String descricao;

    Origem(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
