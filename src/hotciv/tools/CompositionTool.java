package hotciv.tools;

import hotciv.framework.Game;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milledsk on 19-12-2015.
 */
public class CompositionTool extends AbstractTool implements Tool {

    private Game game;
    private List<Tool> tools = new ArrayList<Tool>();
    private Tool fChild;
    private Tool cachedNullTool;
    protected Figure draggedFigure;

    public CompositionTool(DrawingEditor editor, Game game) {
        super(editor);
        this.game = game;
        fChild = cachedNullTool = new NullTool();

        tools.add(new EndOfTurnTool(editor, game));
        tools.add(new MoveUnitTool(editor, game));
        tools.add(new SetFocusTool(editor, game));
        tools.add(new UnitActionTool(editor, game));
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y)
    {
        Drawing model = this.editor().drawing();
        model.lock();

        for(Tool tool : tools){
            tool.mouseDown(e, x, y);
        }

        fChild.mouseDown(e, x, y);
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        for(Tool tool : tools){
            tool.mouseDrag(e, x, y);
        }
        fChild.mouseDrag(e, x, y);
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y) {
        for(Tool tool : tools){
            tool.mouseMove(e, x, y);
        }
        fChild.mouseMove(e, x, y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        for(Tool tool : tools){
            tool.mouseUp(e, x, y);
        }

        fChild.mouseUp(e, x, y);

    }

}
