package br.com.avancard.model;

public class TelefoneDTO {

    //ATRIBUTOS
    private String numero;
    private String tipo;
    private Long usuario;


    //MÉTODOS ESPECIAIS
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Long getUsuario() {
        return usuario;
    }
    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }
}
