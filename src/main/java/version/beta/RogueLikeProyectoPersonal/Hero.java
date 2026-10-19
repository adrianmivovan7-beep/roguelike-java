package version.beta.RogueLikeProyectoPersonal;

public class Hero implements Character {

    private int vidaActual;
    private int vidaMaxima;
    private int danioBase;
    private int danioActual;
    private int defensaBase;
    private int defensaActual;
    private String nombre;
    private int enemigosMatados;
    private int amigosDefendidos;
    private int victorias;
    private int derrotas;
    private int escudo;

    public Hero(String nombre) {
        this.nombre = nombre;
        enemigosMatados = 0;
        amigosDefendidos = 0;
        vidaMaxima = 20;
        vidaActual = vidaMaxima;
        danioBase = 3;
        danioActual = danioBase;
        defensaBase = 3;
        defensaActual = defensaBase;
        escudo = 0;
        victorias = 0;
        derrotas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    public void attack(Enemy enemy) {
        if (enemy == null || !enemy.estaVivo()) {
            System.out.println("No hay un enemigo valido al que atacar.");
            return;
        }

        System.out.println("¡" + nombre + " ataca al enemigo!");
        enemy.recibirDanio(danioActual);

        if (!enemy.estaVivo()) {
            enemigosMatados++;
        }
    }

    public void defend(Friend friend) {
        System.out.println("¡He defendido a un amigo!");
        friend.heal();
        amigosDefendidos++;
    }

    public void defenderse() {
        escudo += defensaActual;
        System.out.println("¡" + nombre + " se ha defendido!");
        System.out.println("Escudo actual: " + escudo);
    }

    public void recibirDanio(int danioRecibido) {
        if (danioRecibido < 0 || vidaActual <= 0) {
            return;
        }

        int danioRestante = danioRecibido;
        int danioAbsorbido = Math.min(escudo, danioRestante);

        escudo -= danioAbsorbido;
        danioRestante -= danioAbsorbido;

        vidaActual -= danioRestante;

        if (vidaActual < 0) {
            vidaActual = 0;
        }

        System.out.println("Danio recibido: " + danioRecibido);
        System.out.println("Danio absorbido por el escudo: " + danioAbsorbido);
        System.out.println("Escudo restante: " + escudo);
        System.out.println("Vida actual: " + vidaActual + "/" + vidaMaxima);

        if (vidaActual == 0) {
            System.out.println("¡El heroe ha sido derrotado!");
        }
    }

    public void curarse() {
        vidaActual = Math.min(vidaActual + 5, vidaMaxima);
    }

    public void mostrarEstadisticas() {
        System.out.println();
        System.out.println("===== ESTADISTICAS =====");
        System.out.println("Heroe: " + nombre);
        System.out.println("Vida: " + vidaActual + "/" + vidaMaxima);
        System.out.println("Danio actual: " + danioActual);
        System.out.println("Defensa actual: " + defensaActual);
        System.out.println("Escudo: " + escudo);
        System.out.println("Enemigos derrotados: " + enemigosMatados);
        System.out.println("Amigos defendidos: " + amigosDefendidos);
        System.out.println("Victorias: " + victorias);
        System.out.println("Derrotas: " + derrotas);
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = Math.max(0, Math.min(vidaActual, vidaMaxima));
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        if (vidaMaxima > 0) {
            this.vidaMaxima = vidaMaxima;
            this.vidaActual = Math.min(this.vidaActual, vidaMaxima);
        }
    }

    public int getDanioBase() {
        return danioBase;
    }

    public void setDanioBase(int danioBase) {
        this.danioBase = Math.max(0, danioBase);
    }

    public int getDanioActual() {
        return danioActual;
    }

    public void setDanioActual(int danioActual) {
        this.danioActual = Math.max(0, danioActual);
    }

    public int getDefensaBase() {
        return defensaBase;
    }

    public void setDefensaBase(int defensaBase) {
        this.defensaBase = Math.max(0, defensaBase);
    }

    public int getDefensaActual() {
        return defensaActual;
    }

    public void setDefensaActual(int defensaActual) {
        this.defensaActual = Math.max(0, defensaActual);
    }

    public int getEnemigosMatados() {
        return enemigosMatados;
    }

    public void setEnemigosMatados(int enemigosMatados) {
        this.enemigosMatados = Math.max(0, enemigosMatados);
    }

    public int getAmigosDefendidos() {
        return amigosDefendidos;
    }

    public void setAmigosDefendidos(int amigosDefendidos) {
        this.amigosDefendidos = Math.max(0, amigosDefendidos);
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = Math.max(0, victorias);
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = Math.max(0, derrotas);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEscudo() {
        return escudo;
    }

    public void setEscudo(int escudo) {
        this.escudo = Math.max(0, escudo);
    }
}
