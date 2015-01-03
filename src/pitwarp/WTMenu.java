/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitwarp;

import java.awt.Menu;
import java.awt.MenuBar;

////////////////////////////////////////////////////////////////////////

class WTMenu extends MenuBar
{
    Menu m_menu = new Menu("Image");

    WTMenu()
    {
        m_menu.add("Load");
        m_menu.add("Swap");
        add(m_menu);
    }
    
}
