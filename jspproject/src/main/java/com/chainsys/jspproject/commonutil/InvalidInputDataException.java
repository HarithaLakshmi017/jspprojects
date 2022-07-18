package com.chainsys.jspproject.commonutil;

	public class InvalidInputDataException extends Exception {
        public InvalidInputDataException() {
                super("The Input data is not valid");
        }
        public InvalidInputDataException(String message) {
                super(message);
        }
		public void printStackTrace() {
			// TODO Auto-generated method stub
			
		}
}


