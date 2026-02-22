package GameSystem;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FontManagement {
    private static final Map<String, Font> fontCache = new HashMap<>();

    public static Font getFont(String filePath, float size){
        if (!fontCache.containsKey(filePath)) {
            try {
                File fontFile = new File(filePath);
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);

                fontCache.put(filePath, customFont);
                System.out.println("font loading success: " + filePath);
            } catch (IOException | FontFormatException e) {
                System.out.println("Can't find font path");
                e.printStackTrace();

                return new Font("SansSerif", Font.PLAIN, (int) size);
            }
        }
        return fontCache.get(filePath).deriveFont(size);
    }


}
