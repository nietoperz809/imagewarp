/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitwarp;

import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

class ActionImage extends ImageView
{
    int m_x;
    int m_y;
    int m_oldx;
    int m_oldy;
    Color m_xorcolor = new Color(255, 255, 255);

    ActionImage()
    {
        super();
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
                float yb = (float) m_img.getHeight(this);
                float xb = (float) m_img.getWidth(this);
                float ys = (float) size().height;
                float xs = (float) size().width;
                float xb1 = xb * (float) m_x / xs;
                float yb1 = yb * (float) m_y / ys;
                float xb2 = xb * (float) evt.x / xs;
                float yb2 = yb * (float) evt.y / ys;
                getParent().postEvent(new Event(this, 0, Event.ACTION_EVENT, (int) xb2, (int) yb2, (int) xb1, (int) yb1, this));
                break;
        }
        return true;
    }
}
