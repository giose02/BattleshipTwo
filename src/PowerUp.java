public class PowerUp {
    private String nombre;
    private String efecto;
    private String rareza;

    public PowerUp(String nombre, String efecto, String rareza){
        this.nombre = nombre;
        this.efecto = efecto;
        this.rareza = rareza;
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
