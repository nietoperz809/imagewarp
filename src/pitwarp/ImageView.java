/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitwarp;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class ImageView extends JPanel
{
    BufferedImage m_img = null;

    public ImageView()
    {
        super();
    }

    @Override
    public void paint(Graphics g)
    {
        int xe = size().width;
        int ye = size().height;
        if (m_img == null)
        {
            g.drawString("No Pic", xe / 2, ye / 2);
        }
        else
        {
            g.drawImage(m_img, 0, 0, xe, ye, this);
        }
        g.drawRect(0, 0, xe - 1, ye - 1);
    }

    /**
     *
     * @param img
     */
    public void setImage(BufferedImage img)
    {
        if (m_img != null)
        {
            m_img.flush();
        }
        m_img = img;
        repaint();
    }

    /**
     *
     * @param iv
     */
    public void swapImage(ImageView iv)
    {
        if (iv != this)
        {
            BufferedImage img1 = iv.m_img;
            iv.m_img = m_img;
            m_img = img1;
            iv.repaint();
            repaint();
        }
    }
    
}
