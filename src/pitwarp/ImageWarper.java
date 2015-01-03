/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitwarp;

import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

/////////////////////////////////////////////////////////////////////////

class ImageWarper
{
    Point mFromPoint;
    Point mToPoint;
    int[] mFromPixels;
    int[] mToPixels;
    int mWidth;
    int mHeight; // width & height of warp image
    Component m_parent;

    ImageWarper()
    {
    }

    // warp mFromPixels into mToPixels
    public Image WarpPixels(Component obs, Image img, Point fromPoint, Point toPoint)
    {
        mFromPoint = fromPoint;
        mToPoint = toPoint;
        m_parent = obs;
        while ((mWidth = img.getWidth(obs)) < 0)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
            } // supposed to do this. oh well, it works.
        }
        while ((mHeight = img.getHeight(obs)) < 0)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
            }
        }
        // get the pixel data from the image
        mFromPixels = new int[mWidth * mHeight];
        mToPixels = new int[mWidth * mHeight];
        PixelGrabber grabber = new PixelGrabber(img, 0, 0, mWidth, mHeight, mFromPixels, 0, mWidth);
        boolean done = false;
        do
        {
            // grab pixels for 500 msec
            try
            {
                done = grabber.grabPixels(500);
            }
            catch (InterruptedException e)
            {
            }
        }
        while (!done);
        int dx = mToPoint.x - mFromPoint.x;
        int dy = mToPoint.y - mFromPoint.y;
        int dist = (int) Math.sqrt(dx * dx + dy * dy) * 2;
        Rectangle r = new Rectangle();
        Point ne = new Point(0, 0);
        Point nw = new Point(0, 0);
        Point se = new Point(0, 0);
        Point sw = new Point(0, 0);
        // copy mFromPixels to mToPixels, so the non-warped parts will be identical
        System.arraycopy(mFromPixels, 0, mToPixels, 0, mWidth * mHeight);
        if (dist == 0)
        {
            return null;
        }
        // warp northeast quadrant
        SetRect(r, mFromPoint.x - dist, mFromPoint.y - dist, mFromPoint.x, mFromPoint.y);
        ClipRect(r, mWidth, mHeight);
        SetPt(ne, r.x, r.y);
        SetPt(nw, r.x + r.width, r.y);
        SetPt(se, r.x, r.y + r.height);
        SetPt(sw, mToPoint.x, mToPoint.y);
        WarpRegion(r, nw, ne, sw, se);
        // warp nortwest quadrant
        SetRect(r, mFromPoint.x, mFromPoint.y - dist, mFromPoint.x + dist, mFromPoint.y);
        ClipRect(r, mWidth, mHeight);
        SetPt(ne, r.x, r.y);
        SetPt(nw, r.x + r.width, r.y);
        SetPt(se, mToPoint.x, mToPoint.y);
        SetPt(sw, r.x + r.width, r.y + r.height);
        WarpRegion(r, nw, ne, sw, se);
        // warp southeast quadrant
        SetRect(r, mFromPoint.x - dist, mFromPoint.y, mFromPoint.x, mFromPoint.y + dist);
        ClipRect(r, mWidth, mHeight);
        SetPt(ne, r.x, r.y);
        SetPt(nw, mToPoint.x, mToPoint.y);
        SetPt(se, r.x, r.y + r.height);
        SetPt(sw, r.x + r.width, r.y + r.height);
        WarpRegion(r, nw, ne, sw, se);
        // warp southwest quadrant
        SetRect(r, mFromPoint.x, mFromPoint.y, mFromPoint.x + dist, mFromPoint.y + dist);
        ClipRect(r, mWidth, mHeight);
        SetPt(ne, mToPoint.x, mToPoint.y);
        SetPt(nw, r.x + r.width, r.y);
        SetPt(se, r.x, r.y + r.height);
        SetPt(sw, r.x + r.width, r.y + r.height);
        WarpRegion(r, nw, ne, sw, se);
        return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(mWidth, mHeight, mToPixels, 0, mWidth));
    }

    // warp a quadrilateral into a rectangle (magic!)
    private void WarpRegion(Rectangle fromRect, Point nw, Point ne, Point sw, Point se)
    {
        int dx = fromRect.width;
        int dy = fromRect.height;
        double invDX = 1.0 / dx;
        double invDY = 1.0 / dy;
        for (int a = 0; a < dx; a++)
        {
            double aa = a * invDX;
            double x1 = ne.x + (nw.x - ne.x) * aa;
            double y1 = ne.y + (nw.y - ne.y) * aa;
            double x2 = se.x + (sw.x - se.x) * aa;
            double y2 = se.y + (sw.y - se.y) * aa;
            double xin = x1;
            double yin = y1;
            double dxin = (x2 - x1) * invDY;
            double dyin = (y2 - y1) * invDY;
            int toPixel = fromRect.x + a + fromRect.y * mWidth;
            for (int b = 0; b < dy; b++)
            {
                if (xin < 0)
                {
                    xin = 0;
                }
                if (xin >= mWidth)
                {
                    xin = mWidth - 1;
                }
                if (yin < 0)
                {
                    yin = 0;
                }
                if (yin >= mHeight)
                {
                    yin = mHeight - 1;
                }
                int pixelValue = mFromPixels[(int) xin + (int) yin * mWidth];
                mToPixels[toPixel] = pixelValue;
                xin += dxin;
                yin += dyin;
                toPixel += mWidth;
            }
        }
    }

    void ClipRect(Rectangle r, int w, int h)
    {
        if (r.x < 0)
        {
            r.width += r.x;
            r.x = 0;
        }
        if (r.y < 0)
        {
            r.height += r.y;
            r.y = 0;
        }
        if (r.x + r.width >= w)
        {
            r.width = w - r.x - 1;
        }
        if (r.y + r.height >= h)
        {
            r.height = h - r.y - 1;
        }
    }

    // SetRect and SetPt are Mac OS functions. I wrote my own versions here
    // so I didn't have to rewrite too much of the code.
    void SetRect(Rectangle r, int left, int top, int right, int bottom)
    {
        r.x = left;
        r.y = top;
        r.width = right - left;
        r.height = bottom - top;
    }

    void SetPt(Point pt, int x, int y)
    {
        pt.x = x;
        pt.y = y;
    }
    
}
