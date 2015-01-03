/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitwarp;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

class ImageView extends Canvas
{
    Image m_img = null;

    ImageView()
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

    public void setImage(Image img)
    {
        if (m_img != null)
        {
            m_img.flush();
        }
        m_img = img;
        repaint();
    }

    public void swapImage(ImageView iv)
    {
        if (iv != this)
        {
            Image img1 = iv.m_img;
            iv.m_img = m_img;
            m_img = img1;
            iv.repaint();
            repaint();
        }
    }
    
}
