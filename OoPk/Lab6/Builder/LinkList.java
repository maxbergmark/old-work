import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class LinkList<E> {
	public LinkNode<E> first = null;
	public LinkNode<E> last = null;
	public int listSize = 0;
	public int div = 1;
	public ArrayList<E> fastArray;
	public Object[] a;
	
	public LinkList() {
	}

	public LinkList(int n) {
		fastArray = new ArrayList<E>(n);
	}

	public void castToArray() {
		LinkNode<E> temp = first;
		fastArray = new ArrayList<E>(listSize);
		a = new Object[listSize];
		for (int i = 0; i < listSize-1; i++) {
			//fastArray.add(temp.index, temp.value);
			a[temp.index] = temp.value;
			temp = temp.next;
		}
		fastArray.add(temp.value);
	}

	public E fastGet(int ind) {
		return (E) a[ind];
		//return fastArray.get(ind);
	}

	public E get(int ind) {
		if (ind < listSize) {
			LinkNode<E> temp = first;
			for (int i = 0; i < ind; i++) {	
				temp = temp.next;
			}
			return temp.value;
		}
		System.out.println("error");
		return null;
	}

	public int size() {
		return listSize;
	}

	public void add(int ind, E element) {
		if (first == null) {
			first = new LinkNode<E>(element, ind);
			last = first;
		} else {
			LinkNode<E> temp = new LinkNode<E>(element, ind);
			last.setNext(temp);
			last = temp;
		}
		listSize++;
	}
}