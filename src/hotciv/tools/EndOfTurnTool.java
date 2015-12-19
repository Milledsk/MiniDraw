package hotciv.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/**
 * Created by Milledsk on 19-12-2015.
 */
public class EndOfTurnTool extends AbstractTool implements Tool {

    private final Game game;
    private Tool fChild;
    private Tool cachedNullTool;

    public EndOfTurnTool(DrawingEditor editor, Game game) {
        super(editor);
        this.game = game;
        this.editor = editor;
        this.fChild = cachedNullTool = new NullTool();
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Drawing model = this.editor().drawing();
        model.lock();

        if(x >= GfxConstants.TURN_SHIELD_X && x <= GfxConstants.TURN_SHIELD_X + GfxConstants.TILESIZE &&
                y >= GfxConstants.TURN_SHIELD_Y && y <= GfxConstants.TURN_SHIELD_Y + GfxConstants.TILESIZE){
            System.out.println("The Shield is pressed");
            game.endOfTurn();
        }

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
        fChild.mouseUp(e, x, y);
    }
}
