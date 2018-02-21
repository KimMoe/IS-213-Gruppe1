package NeuralNetwork;

import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;

public class LiveView {
    private final NeuralNetwork nn;
    
    private int width, height;
    
    private final int NEURONS_DIAMETER = 60;
    private final int HORIZONTAL_SPACE_BETWEEN_NEURONS = 130;
    private final int VERTICAL_SPACE_BETWEEN_NEURONS = 30;
    private final int PANEL_PADDING = 10;
    
    protected JFrame frame;
    protected JPanel panel = new JPanel() {
        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            
            super.paint(g);
            removeAll();
            
            paintNeuralNetwork(g2d);
            
            validate();
        }
    };
    
    public LiveView(NeuralNetwork nn) {
        this.nn = nn;
        
        frame = new JFrame("Generation: 1 - Genome: 1/" + nn.genomes_per_generation);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        setWidth();
        setHeight();
        panel.setPreferredSize(new Dimension(width, height));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    private void setWidth() {
        width = PANEL_PADDING * 2 + (nn.layers_amount - 1) * HORIZONTAL_SPACE_BETWEEN_NEURONS + nn.layers_amount * NEURONS_DIAMETER;
    }
    
    private void setHeight() {
        int max = nn.neurons_amount[0];
        
        for(int i = 1; i < nn.layers_amount; i++) {
            if(nn.neurons_amount[i] > max) {
                max = nn.neurons_amount[i];
            }
        }
        
        height = PANEL_PADDING * 2 + (max - 1) * VERTICAL_SPACE_BETWEEN_NEURONS + max * NEURONS_DIAMETER;
    }
    
    protected void paintNeuralNetwork(Graphics2D g) {
        for(int i = 0; i < nn.layers_amount; i++) {
            int j = (nn.neurons_amount[i] - 1) * VERTICAL_SPACE_BETWEEN_NEURONS + nn.neurons_amount[i] * NEURONS_DIAMETER + PANEL_PADDING * 2; // Height of the current neurons (including paddings)
            int k = (height - j) / 2; // Calculate top margin

            // Draw neurons[i]
            for(int l = 0; l < nn.neurons_amount[i]; l++) {                
                // Draw synapses[current_genome][i][l]
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.PLAIN, 12));                
                if(i != nn.layers_amount - 1) {
                    int o = (nn.neurons_amount[i + 1] - 1) * VERTICAL_SPACE_BETWEEN_NEURONS + nn.neurons_amount[i + 1] * NEURONS_DIAMETER + PANEL_PADDING * 2; // Height of the next neurons (including paddings)
                    int p = (height - o) / 2; // Calculate top margin
                    int m;
                    if(i + 1 != nn.layers_amount - 1) {
                        m = nn.neurons_amount[i + 1] - 1;
                    }
                    else {
                        m = nn.neurons_amount[i + 1];
                    }
                    for(int n = 0; n < m; n++) {
                        // Draw line
                        g.drawLine(NEURONS_DIAMETER / 2 + PANEL_PADDING + NEURONS_DIAMETER * i + HORIZONTAL_SPACE_BETWEEN_NEURONS * i, NEURONS_DIAMETER / 2 + PANEL_PADDING + k + NEURONS_DIAMETER * l + VERTICAL_SPACE_BETWEEN_NEURONS * l, NEURONS_DIAMETER / 2 + PANEL_PADDING + NEURONS_DIAMETER * (i + 1) + HORIZONTAL_SPACE_BETWEEN_NEURONS * (i + 1), p - k + NEURONS_DIAMETER / 2 + PANEL_PADDING + k + NEURONS_DIAMETER * n + VERTICAL_SPACE_BETWEEN_NEURONS * n);
                     }
                }
                
                // Draw circle
                if(i == 0 || i == nn.layers_amount - 1) {
                    g.setColor(new Color(231, 76, 60));
                }
                else {
                    g.setColor(new Color(243, 156, 18));
                }
                g.fillOval(PANEL_PADDING + NEURONS_DIAMETER * i + HORIZONTAL_SPACE_BETWEEN_NEURONS * i, PANEL_PADDING + k + NEURONS_DIAMETER * l + VERTICAL_SPACE_BETWEEN_NEURONS * l, NEURONS_DIAMETER, NEURONS_DIAMETER);
                
                // Draw value
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 15));
                String value = new DecimalFormat("#0.00").format(nn.neurons[i][l]);
                g.drawString(value, NEURONS_DIAMETER / 2 - g.getFontMetrics().stringWidth(value) / 2 + PANEL_PADDING + NEURONS_DIAMETER * i + HORIZONTAL_SPACE_BETWEEN_NEURONS * i, NEURONS_DIAMETER / 2 + 7 + PANEL_PADDING + k + NEURONS_DIAMETER * l + VERTICAL_SPACE_BETWEEN_NEURONS * l);
            }
        }
    }
    
    protected void updateTitle() {
        frame.setTitle("Generation: " + (nn.current_generation + 1) + " - Genome: " + (nn.current_genome + 1) + "/" + nn.genomes_per_generation);
    }
}