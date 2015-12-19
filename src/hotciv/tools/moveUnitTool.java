package hotciv.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import hotciv.view.UnitFigure;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;
import minidraw.standard.handlers.DragTracker;

import java.awt.event.MouseEvent;

/**
 * Created by Milledsk on 18-12-2015.
 */
public class MoveUnitTool extends AbstractTool implements Tool {
    private Game game;
    private DrawingEditor editor;
    private Figure draggedFigure;
    private Tool fChild;
    private Tool cachedNullTool;
    private int fromX;
    private int fromY;


    public MoveUnitTool(DrawingEditor editor, Game game) {
        super(editor);
        this.game = game;
        fChild = cachedNullTool = new NullTool();
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y){
        Drawing model = this.editor().drawing();
        model.lock();
        fromX = x;
        fromY = y;

        draggedFigure = model.findFigure(e.getX(), e.getY());
        if(draggedFigure != null && draggedFigure instanceof UnitFigure) {
            fChild = createDragTracker(draggedFigure);
            fChild.mouseDown(e, x, y);
        }
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
        editor().drawing().unlock();
        boolean movePossible;

        if(draggedFigure instanceof UnitFigure){
            Position from = GfxConstants.getPositionFromXY(fromX, fromY);
            Position to = GfxConstants.getPositionFromXY(x,y);

            movePossible = game.moveUnit(from, to);

            if(!movePossible){
                draggedFigure.moveBy(fromX - x, fromY - y);
                System.out.println("moveUnit is false");
            } else{
                System.out.println("moveUnit is true");
            }


        }

        fChild.mouseUp(e, x, y);
        fChild = cachedNullTool;
        draggedFigure = null;
    }
    private Tool createDragTracker(Figure draggedFigure) {
        return new DragTracker(this.editor(), draggedFigure);
    }
}
