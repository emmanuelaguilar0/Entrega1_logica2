import javax.swing.*;
import java.awt.*;

class Nodo {
    int dato;
    Nodo siguiente;

    public Nodo(int dato) {
        this.dato = dato;
    }
}

class ListaCircular {
    private Nodo ultimo;

    public void agregar(int dato) { 
        Nodo nuevo = new Nodo(dato);
        if (ultimo == null) {
            nuevo.siguiente = nuevo;
        } else {
            nuevo.siguiente = ultimo.siguiente;
            ultimo.siguiente = nuevo;
        }
        ultimo = nuevo;
    }

    public Nodo getHead() {
        return (ultimo != null) ? ultimo.siguiente : null;
    }
}

class Animacion extends JPanel implements Runnable {
    private Nodo actual;
    private double angle = 0, speed = -Math.toRadians(2);

    public Animacion(ListaCircular lista) {
        actual = lista.getHead();
        new Thread(this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = getWidth() / 2 + (int) (100 * Math.cos(angle));
        int y = getHeight() / 2 + (int) (100 * Math.sin(angle));
        g.setColor(new Color(128, 0, 128));
        g.fillOval(x - 10, y - 10, 20, 20);
    }

    @Override
    public void run() {
        while (true) {
            angle += speed;
            if (angle <= -Math.toRadians(360) && actual != null) {
                angle = 0;
                actual = actual.siguiente;
                System.out.println("Nodo actual: " + actual.dato);
            }
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
        }
    }
}

public class circular {
    public static void main(String[] args) {
        ListaCircular lista = new ListaCircular();
        for (int n : new int[]{10, 20, 30, 40}) lista.agregar(n);

        JFrame frame = new JFrame();
        frame.add(new Animacion(lista));
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
