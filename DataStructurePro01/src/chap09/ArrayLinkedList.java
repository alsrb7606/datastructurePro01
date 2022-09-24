package chap09;

import java.util.Comparator;


//연결리스트클래스(배열 커서)
//free list는 삭제된 노드를 순서대로 LinkedList형태로 만든 것 
//head같은 포인터에서 무슨 일이 벌어진다. 
public class ArrayLinkedList<E> {
	
	//---노드---//
	class Node<E> {
		private E data;	//데이터 
		private int next;	 //리스트의 뒤쪽 포인터 
		private int dnext;	//프리 리스트의 뒤쪽 포인
		
		//---data와 next를 설정---//
		void set(E data, int next) {	//ArrayLinkedList라서 next를 int형 인덱스로 하는 듯.
			this.data = data;
			this.next = next;
		}
	}
	
	private Node<E>[] n;	//리스트 본체 
	private int size;		//리스트 크기 
	private int max;		//사용중인 꼬리 레코드(index)
	private int head;		//머리노드 
	private int crnt;		//ArrayLinkeList라서 crnt도 int 자료형. 
	private int deleted;	//freelist의 머리 노드 
	private static final int NULL = -1;	//뒤쪽노드가 없음 / 리스트가 가득 
	
	//---생성자(constructor)---//
	public ArrayLinkedList(int capacity) {
		head = crnt = max = deleted = NULL;
		try {
			n = new Node[capacity];
			for(int i = 0; i < capacity; i++)
				n[i] = new Node<E>();	//Node객체 생성해서 각 배열index로 참조.
			
			size = capacity;
		} 
		catch(OutOfMemoryError e) {		//메모리의 object영역을 다 썼을 때 
			size = 0;
		}
		
	}
	
	//---다음에 삽입하는 record의 인덱스를 구함---//
	private int getInsertIndex() {
		if(deleted == NULL) {
			if(max < size)
				return ++max;	//max 처음에 -1임 , ++max하면 0됨 
			else 
				return NULL;	//크기넘침(over)
		} else {
			int rec = deleted;		//free list 제일 최근에 삭제된 index가져옴. 
			deleted = n[rec].dnext;	//free list에서 하나 가져갔으니까 다음 index 참
			return rec;				//free list에서 삭제됬었던 index 가져옴. 
		}
		
	}
	
	
	
	//---record idx를 프리 리스트에 등록(idx삭제 원함) ---//
	private void deleteIndex(int idx) {
		if(deleted == NULL) {
			deleted = idx;
			n[idx].dnext = NULL;	//free list에 제일 먼저 들어왔으니 다음 것 참조하지 않음.
		} else {
			int rec = deleted;
			deleted = idx;
			n[idx].dnext = rec;
		}
	}
	
	//---노드를 검색---//
	public E search(E obj, Comparator<? super E> c) { //E의 부모 
		int ptr = head;		//ptr은 스캔중인 노드를 의미.
		
		while(ptr != NULL) {
			if(c.compare(obj, n[ptr].data) == 0) {	//이렇게하면 비교되나?
				crnt = ptr;
				return n[ptr].data;			//검색성공 
			}
			ptr = n[ptr].next;				//뒤쪽 노드 선
		}
		return null;						//검색실패 
	}
	
	//--- 머리노드 삽입 E삽입 ---//
	public void addFirst(E obj) {
		int ptr = head;		//
		int rec = getInsertIndex();
		if(rec != NULL) {
			head = crnt = rec;		//제 rec index에 삽입.
			n[head].set(obj, ptr);
		}
	}
	
	//---꼬리노드 삽입---//
	public void addLast(E obj) {
		if(head == NULL)
			addFirst(obj);
		else {
			int ptr = head;
			while (n[ptr].next != NULL) 
				ptr = n[ptr].next;
			int rec = getInsertIndex();
			if(rec != NULL) {					//rec index에 데이터 삽입 
				n[ptr].next = crnt = rec;	
				n[rec].set(obj, NULL);
			}
		}
	}
	
	//---머리노드 삭제---//
	public void removeFirst() {
		if(head != NULL) {
			int ptr = n[head].next;
			deleteIndex(head);
			head = crnt = ptr;
		}
	}
	
	//---꼬리노드 삭제---//
	public void removeLast() {
		if(head != NULL) {
			if(n[head].next == NULL)
				removeFirst();
			else {
				int ptr = head;		//스캔 중인 노드 
				int pre = head;     //스캔 중인 노드의 앞쪽노드 
				
				while(n[ptr].next != NULL) {
					pre = ptr;
					ptr = n[ptr].next;
				}
				//NULL 이다!
				n[pre].next = NULL;		//pre는 삭제 뒤의 꼬리 노드
				deleteIndex(pre);
				crnt = pre;
				
			}
		}
	}
	
	
}