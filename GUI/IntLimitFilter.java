package GUI;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IntLimitFilter extends DocumentFilter {
    private int maxInt;
    private int minInt;

    public IntLimitFilter(int minLimit, int maxLimit) {
        this.maxInt = maxLimit;
        this.minInt = minLimit;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        replace(fb, offset, 0, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text==null) return;

        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String proposedText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

        if (proposedText.isEmpty()) {
            super.replace(fb, offset, length, proposedText, attrs);
            return;
        }

        try {
            int value = Integer.parseInt(proposedText);

            if (value <= maxInt && value >= minInt) {
                super.replace(fb, offset, length, text, attrs);
            }
            else if (value > maxInt) {
                super.replace(fb, 0, fb.getDocument().getLength(), String.valueOf(maxInt), attrs);
                Toolkit.getDefaultToolkit().beep();
            }
            else {
                super.replace(fb, 0, fb.getDocument().getLength(), String.valueOf(minInt), attrs);
                Toolkit.getDefaultToolkit().beep();
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
