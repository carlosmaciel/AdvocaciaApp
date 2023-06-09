package br.com.advocacia.exception;

public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = -3280102298348869777L;
	
	public BusinessException(String msg){
		super(msg);
	}
	
	public BusinessException(Exception e){
		super(e);
	}
	
	public BusinessException(String msg, Exception e){
		super(msg, e);
	}
}
