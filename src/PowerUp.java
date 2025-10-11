public class PowerUp {
    private String nombre;
    private String efecto;

    public PowerUp(String nombre, String efecto){
        this.nombre = nombre;
        this.efecto = efecto;
    }

    public PowerUp(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    //Metodos activar
}
