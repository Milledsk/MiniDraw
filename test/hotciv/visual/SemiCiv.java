package hotciv.visual;

import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.SemiCivFactory;
import hotciv.standard.strategies.RandomDieStrategy;
import hotciv.tools.CompositionTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/**
 * Created by Milledsk on 19-12-2015.
 */
public class SemiCiv {

    public static void main(String[] args){

        Game game = new GameImpl(new SemiCivFactory(new RandomDieStrategy()));

        DrawingEditor editor =
                new MiniDrawApplication( "Shift-Click unit to invoke its action",
                        new HotCivFactory4(game) );
        editor.open();
        editor.showStatus("Shift-Click on unit to see Game's performAction method being called.");

        // Replace the setting of the tool with your ActionTool implementation.
        editor.setTool( new CompositionTool(editor, game) );
    }
}
