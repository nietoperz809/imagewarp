/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitwarp;

import java.awt.Color;
import java.awt.Event;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class WTest extends JFrame implements Warpable
{
    public static void main(String str[])
    {
        new WTest();
    }

    ActionImage actionImage = new ActionImage(this);
    ImageView imageView = new ImageView();
    Toolkit toolkit = Toolkit.getDefaultToolkit();

    public WTest()
    {
        setLayout(new GridLayout(1, 1));
        setMenuBar(createMenuBar());
        add(actionImage);
        add(imageView);
        resize(600, 300);
        show();
    }

    private MenuBar createMenuBar()
    {
        MenuBar bar = new MenuBar();
        Menu m = new Menu("Image");
        m.add("Load");
        m.add("Swap");
        bar.add(m);
        return bar;
    }

    @Override
    public boolean handleEvent(Event evt)
    {
        switch (evt.id)
        {
            case Event.ACTION_EVENT:
            {
                if (evt.arg.equals("Swap"))
                {
                    actionImage.swapImage(imageView);
                }
                else if (evt.arg.equals("Load"))
                {
                    FileDialog fd = new FileDialog(this, "Load", FileDialog.LOAD);
                    fd.show();
                    if (fd.getFile() != null)
                    {
                        File f = new File(fd.getDirectory() + fd.getFile());
                        BufferedImage img;
                        try
                        {
                            img = ImageIO.read(f);
                            actionImage.setImage(img);
                        }
                        catch (IOException ex)
                        {
                        }
                    }
                }
            }
            break;

            case Event.WINDOW_DESTROY:
                System.exit(0);
                break;
        }
        return true;
    }

    @Override
    public void doWarp(Point pt1, Point pt2)
    {
        Window wnd = new Window(this);
        wnd.setBackground(new Color(255, 100, 100));
        wnd.resize(50, 20);
        wnd.add("Center", new Label("Wait..."));
        wnd.show();

        ImageWarper warper = new ImageWarper();
        BufferedImage img2 = warper.WarpPixels (actionImage.m_img, pt1, pt2);

        wnd.dispose();
        imageView.setImage(img2);
    }
}
