import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import ui.windows.StockApplicationWindow;

public class StockApplication extends Application {

	public static void main(String[] args) {
		new StockApplication().start();
	}

	@Override
	protected Window<?> createMainWindow() {
		return new StockApplicationWindow(this);
	}
}
