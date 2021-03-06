package hotciv.tools;

import hotciv.framework.Game;
import hotciv.view.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;
import java.awt.event.MouseEvent;

/**
 * Created by Milledsk on 18-12-2015.
 */

public class SetFocusTool extends AbstractTool implements Tool{
    private final Game game;
    private Tool fChild;

    public SetFocusTool(DrawingEditor editor, Game game) {
        super(editor);
        this.game = game;
        this.editor = editor;
        this.fChild = new NullTool();
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Drawing model = this.editor().drawing();
        model.lock();

        game.setTileFocus(GfxConstants.getPositionFromXY(x,y));

        this.fChild.mouseDown(e, x, y);
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y){
        fChild.mouseDrag(e, x, y);
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y) {
        fChild.mouseMove(e, x, y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y){
        game.setTileFocus(GfxConstants.getPositionFromXY(x,y));
        fChild.mouseUp(e, x, y);
    }
}
