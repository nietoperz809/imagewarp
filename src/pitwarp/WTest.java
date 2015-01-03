/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitwarp;

import java.awt.*;
import java.awt.image.*;

public class WTest extends Frame
{
    public static void main(String str[])
    {
        new WTest();
    }

    ActionImage actionImage = new ActionImage();
    ImageView imageView = new ImageView();
    Toolkit toolkit = Toolkit.getDefaultToolkit();

    WTest()
    {
        setLayout(new GridLayout(1, 1));
        setMenuBar(new WTMenu());
        add(actionImage);
        add(imageView);
        actionImage.setImage(toolkit.getImage("bump.gif"));
        resize(600, 300);
        show();
    }

    @Override
    public boolean handleEvent(Event evt)
    {
        switch (evt.id)
        {
            case Event.ACTION_EVENT:
            {
                if (evt.target == actionImage && evt.arg == actionImage)
                {
                    Window wnd = new Window(this);
                    wnd.setBackground(new Color(255, 100, 100));
                    wnd.resize(50, 20);
                    wnd.add("Center", new Label("Wait..."));
                    wnd.show();

                    ImageWarper warper = new ImageWarper();
                    Image img = warper.WarpPixels(this,
                            actionImage.m_img,
                            new Point(evt.x, evt.y),
                            new Point(evt.key, evt.modifiers));

                    wnd.dispose();

                    imageView.setImage(img);
                }
                else if (evt.arg.equals("Swap"))
                {
                    actionImage.swapImage(imageView);
                }
                else if (evt.arg.equals("Load"))
                {
                    FileDialog fd = new FileDialog(this, "Load", FileDialog.LOAD);
                    fd.show();
                    if (fd.getFile() != null)
                    {
                        Image img = toolkit.getImage(fd.getDirectory() + fd.getFile());
                        actionImage.setImage(img);
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
}

////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////
