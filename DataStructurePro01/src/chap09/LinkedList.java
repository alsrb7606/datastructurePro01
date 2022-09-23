package chap09;

import java.util.Comparator;

//항상 메모리의 모습을 머릿속으로 떠올리면서 주소나 데이터 등

public class LinkedList<E> {
	//---노드---//
	class Node<E> {
		private E data;		//데이터 
		private Node<E> next;	//뒤쪽 포인터(다음 노드 참조) 	
		//---생성자constructor)---//
		Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	}

	//LinkedList의 2가지 필드 
	//head는 머리노드를 가리고 crnt는 현재 선택한 노드를 가리킨다. '검색'한 노드를 선택하고 '삭제'하는 등 용도 
	private Node<E> head; 	//머리 노드, 정확히 머리 노드에 대한 참조이다 
	private Node<E> crnt;	//선택 노드, 선택 노드에 대한 참조이다.  
	
	//---생성자(constructor)---//
	public LinkedList() {
		head = crnt = null;
	}
	
	//---노드 검색---//
	public E search(E obj, Comparator<? super E> c) {
		Node<E> ptr = head;		//현재 스캔 중인 노드 
		
		while(ptr != null) {
			if(c.compare(obj, ptr.data) == 0) {		//검색 성
				crnt = ptr;
				return ptr.data;	//데이터 반
			}
			ptr = ptr.next;		//뒤쪽 노드에 주목 , 그리고 다시 null이 아니면 while에 의해 반
		}
		return null;			//검색 실패 
		
	}
	
	//---머리 노드 삽입---//
	public void addFirst(E obj) {
		Node<E> ptr = head;
		head = crnt = new Node<E>(obj, ptr);		//obj객체를 head와 그 다음 노드 사이에 연결하는 작업 
	}
	
	//---꼬리 노드 삽입---//
	public void addLast(E obj) {
		if(head == null) { 	//리스트가 비어있으면 
			addFirst(obj);	// 머리에 삽
		}else {
			Node<E> ptr = head;
			while(ptr.next!=null) 
				ptr = ptr.next;
			
			ptr.next = crnt = new Node<E>(obj, null);
		}
	}
	
	//---머리노드 삭제---//
		public void removeFirst() {
			if(head != null)
				head = crnt = head.next; //crnt에 먼저 head.next참조하고 다시 그걸 head가 참조하는 식으로 삭제한다
		}
	
	//---꼬리노드 삭제---//
	public void removeLast() {
		if(head != null) {
			if(head.next == null)
				removeFirst();
			else {
				Node<E> ptr = head;		//스캔 중인 노드 
				Node<E> pre = head;		//스캔 중인 노드의 앞쪽 노드 
				
				while(ptr.next != null) {
					pre = ptr;
					ptr = ptr.next;
				}
				pre.next = null;   //다음 노드가 참조하는 노드가 없으면 while문 종료하고 pre은 null가리킴
				crnt = pre;		//crnt는 선택노드 
			}
		}
	}
	
	//--노드 p 삭제---//
	public void remove(Node p) {
		if(head != null) {
			if(p == head)			//p가 머리 노드이
				removeFirst();		// 머리 노드 삭제 
			else {
				Node<E> ptr = head;
				
				while(ptr.next != p) {
					ptr = ptr.next;
					if(ptr == null) return;	//p가 리스트에 없음 
				}
				ptr.next = p.next;	//이미지로 생각해보자 노드 떠올리면서
				crnt = ptr;
			}
		}
	}
	
	//---선택노드 삭제---//
	public void removeCurrentNode() {
		remove(crnt);
	}
	
	//---전체노드 삭제---//
	public void clear() {
		while(head != null)
			removeFirst();	//다 빌 때까지 머리노드 삭제 
		crnt = null;
	}
	
	//---선택 노드를 하나 뒤쪽으로 진행---//
	public boolean next() {
		if(crnt == null || crnt.next == null)
			return false;	//나아갈 수 없
		crnt = crnt.next;
		return true;
	}
	
	//---선택 노드 표시---//
	public void printCurrentNode() {
		if(crnt == null)
			System.out.println("주목노드가 없습니다.");
		else 
			System.out.println(crnt.data);
	}
	
	//---전체 노드 표시---//
	public void dump() {
		Node<E> ptr = head;
		
		while(ptr != null) {
			System.out.println(ptr.data);
			ptr = ptr.next;
		}
	}

	
}

