package beans;

/**
 *
 * @author Michel
 */
public class usuariosBeans {
    private String code;
    private String nomb;
    private int edad;
    private String perfil;
    private String password;

    public usuariosBeans(String code, String nomb, int edad, String perfil, String password) {
        this.code = code;
        this.nomb = nomb;
        this.edad = edad;
        this.perfil = perfil;
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNomb() {
        return nomb;
    }

    public void setNomb(String nomb) {
        this.nomb = nomb;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
