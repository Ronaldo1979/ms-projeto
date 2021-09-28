package br.com.cognitivebrasil.enumerator;

public enum Pasta {

	 SAUDE("Saúde"),
	 EDUCACAO("Educação"),
	 SPORT("Sport");

    private String descricao;

    Pasta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
