/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitwarp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ActionImage extends ImageView implements MouseListener, MouseMotionListener
{
    Warpable target;
    int m_x;
    int m_y;
    int m_oldx;
    int m_oldy;
    Color m_xorcolor = new Color(255, 255, 255);

    public ActionImage(Warpable w)
    {
        super();
        target = w;
        addMouseListener(this);
        addMouseMotionListener (this);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        m_x = e.getX();
        m_y = e.getY();
        m_oldx = m_x;
        m_oldy = m_y;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        repaint();
        float yb = m_img.getHeight();
        float xb = m_img.getWidth();
        float ys = size().height;
        float xs = size().width;
        int xb1 = (int) (xb * m_x / xs);
        int yb1 = (int) (yb * m_y / ys);
        int xb2 = (int) (xb * e.getX() / xs);
        int yb2 = (int) (yb * e.getY() / ys);
        target.doWarp(new Point(xb2, yb2), new Point(xb1, yb1));
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        Graphics g = getGraphics();
        g.setXORMode(m_xorcolor);
        g.drawLine(m_x, m_y, m_oldx, m_oldy);
        g.drawLine(m_x, m_y, e.getX(), e.getY());
        m_oldx = e.getX();
        m_oldy = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
    }
}
