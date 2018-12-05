package graphics.GUI;

import graphics.IO.Merchant;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Paint{
    private JPanel MainPanel;
    private JToolBar PaintToolBar;
    private JScrollPane SketchPanel;
    private BufferedImage image;
    private JLabel CoordinateLabel;
    private int scale = 0; //Images are scaled by 2^scale
    private int x, y, width, height;
    private final static String[] allowedExts = new String[]{ "jpg", "bmp", "png"};
    private Color lcolor = new Color(0,0,0);
    private Color rcolor = new Color(255,255,255);
    private JButton lcButton, rcButton;

    public static void main(String[] args)
    {

        JFrame frame = new JFrame("Paint");
        Paint paint = new Paint();
        JPanel main = paint.MainPanel;
        JScrollPane sketchPane = paint.SketchPanel;
        sketchPane.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                paint.x = paint.y = 0;
                int dy = 0;
                if(paint.height*Math.pow(2,paint.scale) < paint.SketchPanel.getHeight())
                {
                    dy = (int) ((paint.SketchPanel.getHeight() - paint.height*Math.pow(2,paint.scale))/2);
                }
                paint.x = (int) (e.getX()/(Math.pow(2,paint.scale)));
                paint.y = (int) ((e.getY() - dy)/Math.pow(2,paint.scale));
                String xys = "x: " + paint.x + " y: " + paint.y;
                paint.CoordinateLabel.setText(xys);
            }
        });
        sketchPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(paint.x < paint.image.getWidth() && paint.y < paint.image.getHeight())
                {
                    if(SwingUtilities.isLeftMouseButton(e))
                    {
                        paint.image.setRGB(paint.x, paint.y, paint.lcolor.getRGB());
                    }
                    if(SwingUtilities.isRightMouseButton(e))
                    {
                        paint.image.setRGB(paint.x, paint.y, paint.rcolor.getRGB());
                    }
                    paint.updateSketch();
                }
            }

        });
        JToolBar paintToolBar = paint.PaintToolBar;
        paint.generateToolBarButtons(paintToolBar);
        frame.setContentPane(main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        BufferedImage image = null;
        image = Merchant.load("D:\\Desktop\\studia\\Informatyka\\Java\\List7\\src\\res\\Usedom_Postkarte_004.bmp");
        paint.image = image;
        paint.width = image.getWidth();
        paint.height = image.getHeight();
        JLabel picture = new JLabel();
        picture.setIcon(new ImageIcon(image));
        sketchPane.setViewportView(picture);
    }

    private void generateToolBarButtons(JToolBar bar)
    {
        ArrayList<JButton> buttons = new ArrayList<>();
        Paint paint = this;
        BufferedImage image = null;
        JButton button;
        image = Merchant.load("D:\\Desktop\\studia\\Informatyka\\Java\\List7\\src\\res\\Folder.png");
        button = new JButton(new ImageIcon(image));
        button.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
            {
                String path = fc.getSelectedFile().getPath();
                BufferedImage img = Merchant.load(path);
                if(img != null) paint.image = img;
                updateSketch();
            }
            paint.width = paint.image.getWidth();
            paint.height = paint.image.getHeight();
        });
        buttons.add(button);
        image = Merchant.load("D:\\Desktop\\studia\\Informatyka\\Java\\List7\\src\\res\\Floppy.png");
        button = new JButton(new ImageIcon(image));
        button.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
            FileFilter jpeg = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if(f.isDirectory()) return true;
                    String ext = getExtension(f.getPath());
                    return(ext.equals("jpg"));
                }

                @Override
                public String getDescription() {
                    return ".jpg";
                }
            };
            FileFilter bmp = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if(f.isDirectory()) return true;
                    String ext = getExtension(f.getPath());
                    return(ext.equals("bmp"));
                }

                @Override
                public String getDescription() {
                    return ".bmp";
                }
            };
            FileFilter png = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if(f.isDirectory()) return true;
                    String ext = getExtension(f.getPath());
                    return(ext.equals("png"));
                }

                @Override
                public String getDescription() {
                    return ".png";
                }
            };
            fc.addChoosableFileFilter(jpeg);
            fc.addChoosableFileFilter(bmp);
            fc.addChoosableFileFilter(png);
            fc.setFileFilter(jpeg);
            if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
            {
                FileFilter ff = fc.getFileFilter();
                String path = fc.getSelectedFile().getPath();
                String ext = ff.getDescription();
                if(('.' + getExtension(path)).equals(ext))
                    ext = "";
                Merchant.save(paint.image, path, ext);
            }
        });
        buttons.add(button);
        image = Merchant.load("D:\\Desktop\\studia\\Informatyka\\Java\\List7\\src\\res\\Paper.png");
        button = new JButton(new ImageIcon(image));
        button.addActionListener(e -> {
            paint.image = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
            paint.width = 100;
            paint.height = 100;
            paint.updateSketch();
        });
        buttons.add(button);
        image = Merchant.load("D:\\Desktop\\studia\\Informatyka\\Java\\List7\\src\\res\\Microfying_glass.png");
        button = new JButton(new ImageIcon(image));
        button.addActionListener(e -> {
            if(scale > -3) {
                scale--;
                updateSketch();
            }
        });
        buttons.add(button);
        image = Merchant.load("D:\\Desktop\\studia\\Informatyka\\Java\\List7\\src\\res\\Magnifying_glass.png");
        button = new JButton(new ImageIcon(image));
        button.addActionListener(e -> {
            if(scale < 3) {
                scale++;
                updateSketch();
            }
        });
        buttons.add(button);
        image = Merchant.load("D:\\Desktop\\studia\\Informatyka\\Java\\List7\\src\\res\\Up.png");
        button = new JButton(new ImageIcon(image));
        button.addActionListener(e -> {
            Point p = paint.SketchPanel.getViewport().getViewPosition();
            p.y = 0;
            paint.SketchPanel.getViewport().setViewPosition(p);
        });
        buttons.add(button);
        image = Merchant.load("D:\\Desktop\\studia\\Informatyka\\Java\\List7\\src\\res\\Bottom.png");
        button = new JButton(new ImageIcon(image));
        button.addActionListener(e -> {
            int dy = (int) (paint.height*Math.pow(2,paint.scale) - paint.SketchPanel.getHeight());
            if(dy > 0)
            {
                Point p = paint.SketchPanel.getViewport().getViewPosition();
                p.y = dy;
                paint.SketchPanel.getViewport().setViewPosition(p);
            }

        });
        buttons.add(button);
        image = Merchant.load("D:\\Desktop\\studia\\Informatyka\\Java\\List7\\src\\res\\Left.png");
        button = new JButton(new ImageIcon(image));
        button.addActionListener(e -> {
            Point p = paint.SketchPanel.getViewport().getViewPosition();
            p.x = 0;
            paint.SketchPanel.getViewport().setViewPosition(p);
        });
        buttons.add(button);
        image = Merchant.load("D:\\Desktop\\studia\\Informatyka\\Java\\List7\\src\\res\\Right.png");
        button = new JButton(new ImageIcon(image));
        button.addActionListener(e -> {
            int dx = (int) (paint.width*Math.pow(2,paint.scale) - paint.SketchPanel.getWidth());
            if(dx > 0)
            {
                Point p = paint.SketchPanel.getViewport().getViewPosition();
                p.x = dx;
                paint.SketchPanel.getViewport().setViewPosition(p);
            }

        });
        buttons.add(button);
        Graphics graphics;
        image = new BufferedImage(40,40,BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        graphics.setColor(lcolor);
        graphics.fillRect(0,0,40,40);
        lcButton = new JButton(new ImageIcon(image));
        lcButton.addActionListener(e -> {
            paint.setLcolor(JColorChooser.showDialog(null,"choose left mouse button color", paint.lcolor));
        });
        buttons.add(lcButton);
        image = new BufferedImage(40,40,BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        graphics.setColor(rcolor);
        graphics.fillRect(0,0,40,40);
        rcButton = new JButton(new ImageIcon(image));
        rcButton.addActionListener(e -> {
            paint.setLcolor(JColorChooser.showDialog(null,"choose right mouse button color", paint.rcolor));
        });
        buttons.add(rcButton);
        for(JButton b : buttons)
        {
            b.setSize(40,40);
            bar.add(b);
        }
        buttons.clear();
        for(int r = 0; r < 3; r++) {
            for(int g = 0; g < 3; g++) {
                for(int b = 0; b < 3; b++)
                {
                    image = new BufferedImage(20,20,BufferedImage.TYPE_INT_RGB);
                    graphics = image.getGraphics();
                    Color color = new Color(r*127, g*127, b*127);
                    graphics.setColor(color);
                    graphics.fillRect(0,0,20,20);
                    button = new JButton(new ImageIcon(image));
                    button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(SwingUtilities.isLeftMouseButton(e))
                            {
                                paint.setLcolor(color);
                            }
                            if(SwingUtilities.isRightMouseButton(e))
                            {
                                paint.setRColor(color);
                            }
                        }
                    });
                    buttons.add(button);
                }
            }
        }
        for(JButton b : buttons)
        {
            b.setSize(20,20);
            bar.add(b);
        }
    }

    private void updateSketch()
    {
        Image img = new ImageIcon(image).getImage();
        int w = (int) (width*Math.pow(2.0,scale));
        int h = (int) (height*Math.pow(2.0,scale));
        Image scaled = img.getScaledInstance(w,h,Image.SCALE_SMOOTH);
        JLabel picture = new JLabel();
        picture.setIcon(new ImageIcon(scaled));
        SketchPanel.setViewportView(picture);
    }

    private String getExtension(String path)
    {
        int i = path.lastIndexOf('.');
        if( i > 0 && i < path.length() - 1)
        {
            return path.substring(i + 1).toLowerCase();
        }
        return null;
    }

    public void setRColor(Color color)
    {
        if(color != null) rcolor = color;
        updateToolBar();
    }

    public void setLcolor(Color color){
        if(color != null) lcolor = color;
        updateToolBar();
    }

    private void updateToolBar() {
        Graphics graphics;
        BufferedImage image = new BufferedImage(40,40,BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        graphics.setColor(lcolor);
        graphics.fillRect(0,0,40,40);
        lcButton.setIcon(new ImageIcon(image));
        image = new BufferedImage(40,40,BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        graphics.setColor(rcolor);
        graphics.fillRect(0,0,40,40);
        rcButton.setIcon(new ImageIcon(image));
    }
}
