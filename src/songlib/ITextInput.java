package songlib;

public interface ITextInput {
	
	public void notifyBadInput();

	public void restoreInputColor();
	
	public void populateFields(int index);
	
	public void clearTextFields();
	
}
