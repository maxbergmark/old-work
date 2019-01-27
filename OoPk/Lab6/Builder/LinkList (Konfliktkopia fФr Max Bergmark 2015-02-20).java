import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class LinkList<E> {
	public LinkNode<E> first = null;
	public LinkNode<E> last = null;
	public ArrayList<LinkNode<E>> linkArray;
	public int listSize = 0;
	public int div = 1;
	public ArrayList<E> fastArray;
	
	public LinkList() {
		linkArray = new ArrayList<LinkNode<E>>();
	}

	public LinkList(int n) {
		linkArray = new ArrayList<LinkNode<E>>(n/div);
	}

	public void add(int ind, E element) {
		System.out.println(ind + "   " + listSize);
		if (listSize == 0 || ind == listSize-1) {
			add(element);
		} else {
			//LinkNode<E> temp = linkArray.get(ind/div);
			LinkNode<E> temp = first;
			for (int i = 0; i < ind; i++) {
				temp = temp.next;
			}
			LinkNode<E> newNode = new LinkNode<E>(element);
			if (ind != 0) {
				System.out.println((temp == null));
				temp.prev.setNext(newNode);
				newNode.setPrev(temp.prev);
			}
			temp.setPrev(newNode);
			newNode.setNext(temp);
			listSize++;
		}
	}

	public void castToArray() {
		System.out.println("casting");
		LinkNode<E> temp = first;
		fastArray = new ArrayList<E>(listSize);
		for (int i = 0; i < listSize-1; i++) {
			fastArray.add(temp.value);
			temp = temp.next;
		}
		fastArray.add(temp.value);
		System.out.println(fastArray.size());
	}

	public E fastGet(int ind) {
		return fastArray.get(ind);
	}

	public E get(int ind) {
		if (ind < listSize) {
			LinkNode<E> temp = linkArray.get(ind/div);

			for (int i = 0; i < ind%div; i++) {
				
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

	public void add(E element) {
		if (first == null) {
			first = new LinkNode<E>(element);
			last = first;
		} else {
			LinkNode<E> temp = new LinkNode<E>(element);
			last.setNext(temp);
			last = temp;
		}
		listSize++;
	}
}