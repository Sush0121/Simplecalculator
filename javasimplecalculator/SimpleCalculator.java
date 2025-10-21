import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SimpleCalculator extends JFrame {
    private final JTextField display;
    private final JTextArea historyArea;
    private double firstOperand = 0.0;
    private String pendingOperator = null;
    private boolean shouldResetDisplay = false;

    public SimpleCalculator() {
        super("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        // === Display + Clear ===
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton clearBtn = makeButton("C", e -> clearAll());
        JPanel header = new JPanel(new BorderLayout());
        header.add(display, BorderLayout.CENTER);
        header.add(clearBtn, BorderLayout.EAST);
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(header, BorderLayout.NORTH);

        // === Buttons Panel ===
        JPanel panel = new JPanel(new GridLayout(4, 4, 6, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] labels = {
            "7","8","9","/",
            "4","5","6","*",
            "1","2","3","-",
            "0",".","=","+"
        };

        for (String lab : labels) {
            JButton b;
            if (lab.matches("\\d")) b = makeButton(lab, e -> onDigit(lab));
            else if (lab.equals(".")) b = makeButton(lab, e -> onDecimal());
            else if (lab.equals("=")) b = makeButton(lab, e -> onEquals());
            else b = makeButton(lab, e -> onOperator(lab));
            panel.add(b);
        }

        add(panel, BorderLayout.CENTER);

        // === History Panel ===
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane historyPane = new JScrollPane(historyArea);
        historyPane.setPreferredSize(new Dimension(160, 0));
        historyPane.setBorder(BorderFactory.createTitledBorder("History"));
        add(historyPane, BorderLayout.EAST);

        setSize(500, 420);
        setLocationRelativeTo(null);
    }

    private JButton makeButton(String text, ActionListener al) {
        JButton btn = new JButton(text);
        btn.addActionListener(al);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        return btn;
    }

    private void onDigit(String d) {
        if (shouldResetDisplay || display.getText().equals("0") || display.getText().equals("Error")) {
            display.setText(d);
            shouldResetDisplay = false;
        } else {
            display.setText(display.getText() + d);
        }
    }

    private void onDecimal() {
        String current = display.getText();
        if (shouldResetDisplay || current.equals("Error")) {
            display.setText("0.");
            shouldResetDisplay = false;
            return;
        }
        if (!current.contains(".")) {
            display.setText(current + ".");
        }
    }

    private void onOperator(String op) {
        if (pendingOperator == null) {
            firstOperand = parseDisplay();
            pendingOperator = op;
            shouldResetDisplay = true;
        } else {
            double second = parseDisplay();
            String res = compute(firstOperand, second, pendingOperator);
            display.setText(res);
            addToHistory(firstOperand, second, pendingOperator, res);
            if ("Error".equals(res)) {
                pendingOperator = null;
                shouldResetDisplay = true;
                firstOperand = 0.0;
            } else {
                firstOperand = Double.parseDouble(res);
                pendingOperator = op;
                shouldResetDisplay = true;
            }
        }
    }

    private void onEquals() {
        if (pendingOperator == null) return;
        double second = parseDisplay();
        String res = compute(firstOperand, second, pendingOperator);
        display.setText(res);
        addToHistory(firstOperand, second, pendingOperator, res);
        pendingOperator = null;
        shouldResetDisplay = true;
        if ("Error".equals(res)) {
            firstOperand = 0.0;
        } else {
            firstOperand = Double.parseDouble(res);
        }
    }

    private void clearAll() {
        display.setText("0");
        firstOperand = 0.0;
        pendingOperator = null;
        shouldResetDisplay = false;
        historyArea.setText(""); // clear history too
    }

    private double parseDisplay() {
        String t = display.getText();
        if (t.equals("Error") || t.isEmpty() || t.equals(".")) return 0.0;
        try {
            return Double.parseDouble(t);
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }

    private String compute(double a, double b, String op) {
        switch (op) {
            case "+": return formatResult(a + b);
            case "-": return formatResult(a - b);
            case "*": return formatResult(a * b);
            case "/":
                if (Math.abs(b) < 1e-12) return "Error";
                return formatResult(a / b);
            default: return formatResult(b);
        }
    }

    private String formatResult(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) return "Error";
        double rounded = Math.round(value);
        if (Math.abs(value - rounded) < 1e-10) {
            return String.valueOf((long) rounded);
        }
        String s = String.format("%.10f", value);
        s = s.replaceAll("0+$", "").replaceAll("\\.$", "");
        return s;
    }

    private void addToHistory(double a, double b, String op, String result) {
        historyArea.append(a + " " + op + " " + b + " = " + result + "\n");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new SimpleCalculator().setVisible(true));
    }
}
