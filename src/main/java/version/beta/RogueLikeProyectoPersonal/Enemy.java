package version.beta.RogueLikeProyectoPersonal;

public class Enemy implements Character {

    private int danio;
    private int vidaMaxima;
    private int vidaActual;
    private int defensaActual;
    private int defensaBase;

    public Enemy(int danio, int vidaMaxima, int defensaBase) {
        this.danio = Math.max(0, danio);
        this.vidaMaxima = Math.max(1, vidaMaxima);
        this.vidaActual = this.vidaMaxima;
        this.defensaBase = Math.max(0, defensaBase);
        this.defensaActual = this.defensaBase;
    }

    @Override
    public boolean isEnemy() {
        return true;
    }

    public void kill() {
        System.out.println("¡El enemigo ha sido derrotado!");
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public int getDefensaActual() {
        return defensaActual;
    }

    public int getDanio() {
        return danio;
    }

    public void setDanio(int danio) {
        this.danio = Math.max(0, danio);
    }

    public int getDefensaBase() {
        return defensaBase;
    }

    public void setDefensaBase(int defensaBase) {
        this.defensaBase = Math.max(0, defensaBase);
    }

    public void setVidaMaxima(int vidaMaxima) {
        if (vidaMaxima > 0) {
            this.vidaMaxima = vidaMaxima;
            this.vidaActual = Math.min(this.vidaActual, vidaMaxima);
        }
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = Math.max(0, Math.min(vidaActual, vidaMaxima));
    }

    public void setDefensaActual(int defensaActual) {
        this.defensaActual = Math.max(0, defensaActual);
    }

    public void recibirDanio(int danioRecibido) {
        if (danioRecibido < 0 || !estaVivo()) {
            return;
        }

        int danioFinal = Math.max(0, danioRecibido - defensaActual);
        vidaActual = Math.max(0, vidaActual - danioFinal);

        System.out.println("El enemigo ha recibido " + danioFinal + " puntos de danio.");
        System.out.println("Vida del enemigo: " + vidaActual + "/" + vidaMaxima);

        if (vidaActual == 0) {
            kill();
        }
    }

    public void atacar(Hero hero) {
        if (hero == null || !estaVivo() || hero.getVidaActual() <= 0) {
            return;
        }

        System.out.println("¡El enemigo ataca al heroe!");
        hero.recibirDanio(danio);
    }

    public void defenderse() {
        if (!estaVivo()) {
            return;
        }

        defensaActual += 2;
        System.out.println("¡El enemigo se ha defendido!");
        System.out.println("Defensa actual: " + defensaActual);
    }

    public boolean estaVivo() {
        return vidaActual > 0;
    }

    public void resetearDefensa() {
        defensaActual = defensaBase;
        System.out.println("La defensa del enemigo vuelve a " + defensaBase + ".");
    }
}
