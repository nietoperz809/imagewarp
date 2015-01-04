/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitwarp;

import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Point;

public class ActionImage extends ImageView
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
    }

    @Override
    public boolean handleEvent(Event evt)
    {
        Graphics g = getGraphics();
        g.setXORMode(m_xorcolor);
        switch (evt.id)
        {
            case Event.MOUSE_DOWN:
                m_x = evt.x;
                m_y = evt.y;
                m_oldx = m_x;
                m_oldy = m_y;
                break;

            case Event.MOUSE_DRAG:
                g.drawLine(m_x, m_y, m_oldx, m_oldy);
                g.drawLine(m_x, m_y, evt.x, evt.y);
                m_oldx = evt.x;
                m_oldy = evt.y;
                break;

            case Event.MOUSE_UP:
                repaint();
                float yb = m_img.getHeight();
                float xb = m_img.getWidth();
                float ys = size().height;
                float xs = size().width;
                int xb1 = (int)(xb * m_x / xs);
                int yb1 = (int)(yb * m_y / ys);
                int xb2 = (int)(xb * evt.x / xs);
                int yb2 = (int)(yb * evt.y / ys);
                //getParent().postEvent(new Event(this, 0, Event.ACTION_EVENT, (int) xb2, (int) yb2, (int) xb1, (int) yb1, this));
                target.doWarp(new Point(xb2, yb2), new Point(xb1, yb1));
                break;
        }
        return true;
    }
}
