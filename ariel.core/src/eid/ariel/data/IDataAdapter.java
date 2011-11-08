package eid.ariel.data;

public interface IDataAdapter<E> {
	IDataCollection<E> from(String collectionName);
}
