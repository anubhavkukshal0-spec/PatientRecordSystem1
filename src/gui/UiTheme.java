package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UiTheme {
    public static final Color BG = new Color(245, 247, 251);
    public static final Color SURFACE = Color.WHITE;
    public static final Color TEXT = new Color(17, 24, 39);
    public static final Color MUTED = new Color(107, 114, 128);
    public static final Color BORDER = new Color(229, 231, 235);
    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color DANGER = new Color(220, 38, 38);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public static JPanel pagePanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }

    public static JPanel cardPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(SURFACE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(16, 16, 16, 16)
        ));
        return panel;
    }

    public static JLabel heading(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel subheading(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SUBTITLE_FONT);
        label.setForeground(MUTED);
        return label;
    }

    public static void styleLabel(JLabel label) {
        label.setFont(LABEL_FONT);
        label.setForeground(MUTED);
    }

    public static void styleInput(JComponent input) {
        input.setFont(INPUT_FONT);
        input.setBackground(SURFACE);
        input.setForeground(TEXT);
        input.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(6, 8, 6, 8)
        ));
    }

    public static void styleTable(JTable table) {
        table.setFont(INPUT_FONT);
        table.setRowHeight(30);
        table.getTableHeader().setFont(LABEL_FONT);
        table.getTableHeader().setBackground(SURFACE);
        table.getTableHeader().setForeground(MUTED);
        table.setSelectionBackground(new Color(239, 246, 255));
        table.setSelectionForeground(TEXT);
        table.setGridColor(BORDER);
    }

    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        styleButton(btn, PRIMARY, Color.WHITE);
        return btn;
    }

    public static JButton secondaryButton(String text) {
        JButton btn = new JButton(text);
        styleButton(btn, new Color(238, 242, 255), new Color(30, 64, 175));
        return btn;
    }

    public static JButton dangerButton(String text) {
        JButton btn = new JButton(text);
        styleButton(btn, DANGER, Color.WHITE);
        return btn;
    }

    private static void styleButton(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 16, 10, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
}
