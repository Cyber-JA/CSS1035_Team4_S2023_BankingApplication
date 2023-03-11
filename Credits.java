import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

public class Credits {

	protected Shell Credits;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Credits window = new Credits();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		Credits.open();
		Credits.layout();
		while (!Credits.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		Credits = new Shell();
		Credits.setSize(450, 83);
		Credits.setText("Credits");
		
		Label lblNewLabel = new Label(Credits, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 414, 186);
		lblNewLabel.setText("Credits: Program by Hassan, Tatiana, Joke, Rav, Giuseppe ");

	}
}
