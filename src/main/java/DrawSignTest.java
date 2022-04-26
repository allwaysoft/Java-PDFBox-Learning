
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * https://blog.csdn.net/jiachunchun/article/details/89670721
 * https://blog.csdn.net/laizezhong/article/details/80652464?utm_source=distribute.pc_relevant.none-task
 *
 * @author shimanqiang
 * @since 2020/2/21 11:16
 */
public class DrawSignTest {

    public static void main(String[] args) throws Exception {
        new DrawSignTest().drawSign();
    }

    public void drawSign() throws Exception {
        int width = 320;
        int height = 320;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int height1 = image.getHeight();
        System.out.println(image.getWidth());
        System.out.println(image.getHeight());
        // 得到画笔对象
        Graphics2D g = image.createGraphics();
        // 1.0f为透明度 ，值从0-1.0，依次变得不透明
        //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.0f));
        //颜色
        g.setBackground(new Color(255, 0, 0, 0));
        g.setColor(Color.RED);
        //去文字锯齿
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //设置锯齿圆滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Graphics2D g2 = (Graphics2D) g.create();

        /**
         * 画圆
         */
        double padding = 2d;
        double centerX = width / 2d;
        double centerY = height / 2d;
        double radius1 = (width - padding - 12f) / 2d;
        drawCircle(g, centerX, centerY, radius1, 12f);
        double radius2 = (width - padding - 2f - 40f) / 2d;
        drawCircle(g, centerX, centerY, radius2, 2f);
        double radius3 = (width - padding - 8f - 140f) / 2d;
        drawCircle(g, centerX, centerY, radius3, 8f);

//        String inputMsg = "";
        String inputMsg = "北京测试假时的信息有限公司";
        StringBuilder textInfo = new StringBuilder();
        textInfo.append(inputMsg);

        String text = textInfo.toString();
        int len = text.length();

        //设置字体属性
        int fontsize = 25;
        int fontWidth = 0;
        Font f = new Font("Serif", Font.BOLD, fontsize);
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(f);
        for (int i = 0; i < len; i++) {
            int charWidth = metrics.charWidth(text.charAt(i));
            fontWidth += charWidth;
        }
        g.setFont(f);

        g.rotate(Math.toRadians(180), centerX, centerY);
        for (int i = 0; i < len; i++) {
            int charWidth = metrics.charWidth(text.charAt(i));
            int span = 3;
            int d = 360 * charWidth / (fontWidth + span * 2 * charWidth);
            if (i == 0) {
                for (int j = 0; j < span; j++) {
                    g.rotate(Math.toRadians(d), centerX, centerY);
                }
                g.rotate(Math.toRadians(d), centerX, centerY);
            }
            g.setColor(Color.RED);
            g.drawString(String.valueOf(text.charAt(i)), (int) (width - charWidth) / 2f, 55);
            g.rotate(Math.toRadians(d), centerX, centerY);
        }
        //销毁
        g.dispose();

        /**
         * 画*号
         */
        Font f2 = new Font("Serif", Font.BOLD, 40);
        g2.setFont(f2);
        Rectangle2D stringBounds = g2.getFontMetrics(f2).getStringBounds("*", g2);
        //字符的宽度
        FontDesignMetrics metrics2 = FontDesignMetrics.getMetrics(f2);
        int charWidth = metrics2.charWidth('*');
        g2.drawString(String.valueOf("*"), (int) (width - charWidth) / 2f, height - 30);
        g2.dispose();

        ImageIO.write(image, "png", new File("test.png"));
    }

    private void drawCircle(Graphics2D g, double centerX, double centerY, double radius, float strokeSize) {
        Ellipse2D circle = new Ellipse2D.Double();
        //设置圆的宽度
        g.setStroke(new BasicStroke(strokeSize));
        g.setColor(Color.BLUE);

        double radiusX = centerX + radius;
        double radiusY = centerY + radius;
        circle.setFrameFromCenter(centerX, centerY, radiusX, radiusY);
        g.draw(circle);
    }
}
