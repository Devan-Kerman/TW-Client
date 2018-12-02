package Exceptions;

public class StopYourSelfException extends Exception {
	private static final long serialVersionUID = -8261103872055540620L;

	public StopYourSelfException() {}

	public StopYourSelfException(String arg0) {super(arg0);}

	public StopYourSelfException(Throwable arg0) {super(arg0);}

	public StopYourSelfException(String arg0, Throwable arg1) {super(arg0, arg1);}

	public StopYourSelfException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {super(arg0, arg1, arg2, arg3);}

}
