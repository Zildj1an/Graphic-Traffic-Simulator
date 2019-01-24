package model;

public interface Observable<T> {
	public void addObserver(T o);
	
	public void removeObserver(T o);
}
