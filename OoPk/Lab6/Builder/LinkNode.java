

public class LinkNode<E> {
	public E value;
	public int index;
	public LinkNode<E> next = null;
	public LinkNode<E> prev = null;
	
	public LinkNode(E element, LinkNode<E> nextIn, LinkNode<E> prevIn) {
		value = element;
		next = nextIn;
		prev = prevIn;
	}

		public LinkNode(E element, int ind) {
		value = element;
		index = ind;
	}

	public void setNext(LinkNode<E> nextIn) {
		next = nextIn;
	}

	public void setPrev(LinkNode<E> prevIn) {
		prev = prevIn;
	}

	public String toString() {
		return String.valueOf(value) + "  " + (prev == null) + "  " + (next == null);
	}
}